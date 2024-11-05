package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import static java.lang.System.currentTimeMillis;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.cos.COSString;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.pdfbox.pdmodel.interactive.form.PDVariableText;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.PdfAdditionalInformationTextGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value.PdfElementValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value.PdfPatientValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value.PdfSignatureValueGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value.PdfUnitValueGenerator;


@Service
@RequiredArgsConstructor
@Slf4j
public class CertificatePdfFillService {

  public static final int SIGNATURE_X_PADDING = 60;
  public static final int SIGNATURE_Y_PADDING = 2;

  private final PdfUnitValueGenerator pdfUnitValueGenerator;
  private final PdfPatientValueGenerator pdfPatientValueGenerator;
  private final PdfSignatureValueGenerator pdfSignatureValueGenerator;
  private final PdfAdditionalInformationTextGenerator pdfAdditionalInformationTextGenerator;
  private final PdfElementValueGenerator pdfElementValueGenerator;

  public PDDocument fillDocument(Certificate certificate, String additionalInfoText,
      boolean isCitizenFormat) {
    final var template = getTemplatePath(certificate, isCitizenFormat);
    try (final var inputStream = getClass().getClassLoader().getResourceAsStream(template)) {
      if (inputStream == null) {
        throw new IllegalArgumentException("Template not found: " + template);
      }

      final var document = Loader.loadPDF(inputStream.readAllBytes());
      setFields(certificate, document);
      addTexts(certificate, additionalInfoText, document, isCitizenFormat);

      return document;
    } catch (Exception e) {
      throw new IllegalStateException("Could not create Pdf", e);
    }
  }

  private static String getTemplatePath(Certificate certificate, boolean isCitizenFormat) {
    return includeAddress(certificate, isCitizenFormat) ? certificate.certificateModel()
        .pdfSpecification().pdfTemplatePath()
        : certificate.certificateModel().pdfSpecification().pdfNoAddressTemplatePath();
  }

  private static int getSignatureTagIndex(Certificate certificate, boolean isCitizenFormat) {
    if (isCitizenFormat) {
      return certificate.certificateModel().pdfSpecification().signature()
          .signatureWithoutAddressTagIndex()
          .value();
    }
    return certificate.certificateModel().pdfSpecification().signature()
        .signatureWithAddressTagIndex().value();
  }

  private static boolean includeAddress(Certificate certificate, boolean isCitizenFormat) {
    if (isCitizenFormat) {
      return false;
    }

    return certificate.sent() == null || certificate.sent().sentAt() == null;
  }

  private void setFields(Certificate certificate, PDDocument document) {
    final var pdfSpecification = certificate.certificateModel().pdfSpecification();
    if (certificate.status() == Status.SIGNED) {
      setFieldValues(document, pdfSignatureValueGenerator.generate(certificate));
    }

    final var pdfFields = pdfElementValueGenerator.generate(certificate);
    final var appendedFields = pdfFields.stream()
        .filter(f -> Boolean.TRUE.equals(f.getAppend()))
        .toList();

    try {
      setFieldValuesAppendix(document, appendedFields,
          pdfSpecification.patientIdFieldIds().getFirst());
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }

    pdfFields.removeAll(appendedFields);

    setFieldValues(document, pdfFields);
    setFieldValues(document, pdfUnitValueGenerator.generate(certificate));
    setFieldValues(document, pdfPatientValueGenerator.generate(certificate,
        certificate.certificateModel().pdfSpecification().patientIdFieldIds()));
  }

  private void setFieldValuesAppendix(PDDocument document,
      List<PdfField> appendedFields, PdfFieldId patientIdFieldId) throws IOException {
    final var acroForm = document.getDocumentCatalog().getAcroForm();
    final var overflowField = acroForm.getField(appendedFields.getFirst().getId());
    final var rectangle = overflowField.getWidgets().getFirst().getRectangle();

    int start = 0;
    int count = 0;
    while (count < appendedFields.size()) {
      if (isHeightOverFlow(appendedFields.subList(start, count), appendedFields.get(count),
          rectangle)) {
        if (start == 0) {
          fillOverflowPage(appendedFields.subList(start, count), acroForm);
        } else {
          addAndFillOverflowPage(document, appendedFields.subList(start, count), acroForm,
              patientIdFieldId);
        }
        start = count;
      }
      count++;
    }

    addAndFillOverflowPage(document, appendedFields.subList(start, count), acroForm,
        patientIdFieldId);
  }

  private static void fillOverflowPage(List<PdfField> fields, PDAcroForm acroForm) {
    final var field = acroForm.getField(fields.getFirst().getId());
    try {
      field.setValue(
          fields.stream()
              .map(PdfField::getValue)
              .collect(Collectors.joining("\n"))
      );
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  private static void addAndFillOverflowPage(PDDocument document, List<PdfField> fields,
      PDAcroForm acroForm, PdfFieldId patientIdFieldId)
      throws IOException {
    final var pageToClone = document.getPage(4);
    final var mediabox = new PDRectangle(pageToClone.getMediaBox().getLowerLeftX(),
        pageToClone.getMediaBox().getLowerLeftY(), pageToClone.getMediaBox().getWidth(),
        pageToClone.getMediaBox().getHeight());
    final var clonedPage = new PDPage(mediabox);

    try (PDPageContentStream contentStream = new PDPageContentStream(document,
        clonedPage)) {
      contentStream.appendRawCommands(pageToClone.getContents().readAllBytes());
    }

    final var originalField = acroForm.getField(fields.getFirst().getId());
    final var value = fields.stream()
        .map(PdfField::getValue)
        .collect(Collectors.joining("\n"));
    final var widget = addAndFillTextField(value, acroForm, originalField);

    clonedPage.getAnnotations().add(widget);
    document.addPage(clonedPage);
  }

  private static PDAnnotationWidget addAndFillTextField(String value, PDAcroForm acroForm,
      PDField originalField)
      throws IOException {
    final var widget = new PDAnnotationWidget();
    final var originalWidgetRectangle = originalField.getWidgets().getFirst().getRectangle();
    final var rect = new PDRectangle(originalWidgetRectangle.getLowerLeftX(),
        originalWidgetRectangle.getLowerLeftY(), originalWidgetRectangle.getWidth(),
        originalWidgetRectangle.getHeight());
    widget.setRectangle(rect);
    final var textField = new PDTextField(acroForm);
    textField.setPartialName(originalField.getPartialName() + currentTimeMillis());

    textField.setValue(value);
    textField.getWidgets().add(widget);
    textField.setMultiline(true);
    widget.getCOSObject().setItem(COSName.PARENT, textField);

    final var fieldAppearance = new PDAppearanceCharacteristicsDictionary(
        new COSDictionary());
    widget.setAppearanceCharacteristics(fieldAppearance);
    widget.setPrinted(true);
    return widget;
  }


  public static COSDictionary deepCloneDictionary(COSDictionary original) {
    // Create a new COSDictionary
    COSDictionary clone = new COSDictionary();

    // Iterate through each entry in the original dictionary
    Set<COSName> keys = original.keySet();
    for (COSName key : keys) {
      COSBase value = original.getItem(key);
      // Deep copy the value
      COSBase clonedValue = deepCloneValue(value);
      clone.setItem(key, clonedValue);
    }

    return clone;
  }

  private static COSBase deepCloneValue(COSBase value) {
    if (value instanceof COSDictionary) {
      return deepCloneDictionary((COSDictionary) value);
    } else if (value instanceof COSArray) {
      return deepCloneArray((COSArray) value);
    } else if (value instanceof COSString) {
      return new COSString(
          ((COSString) value).getBytes()); // Return other types as-is (immutable types)
    } else {
      return value;
    }
  }

  private static COSArray deepCloneArray(COSArray original) {
    COSArray clone = new COSArray();
    for (COSBase item : original) {
      COSBase clonedItem = deepCloneValue(item);
      clone.add(clonedItem);
    }
    return clone;
  }

  private static boolean isHeightOverFlow(List<PdfField> currentFields, PdfField newTextField,
      PDRectangle rectangle) {
    // TODO: Do we want to break fields if they're very long? Or should one field always be on the same overflow sheet? What do we do then if headline and text are separated on two pages?
    // TODO: If we want to break up text then we need to go over line by line of field, create a new field, return this field if max is met and then add the new field to the appendedFields list in parent function
    final var currentText = currentFields.stream()
        .map(PdfField::getValue)
        .collect(Collectors.joining("\n"));

    String newText = currentText + (currentText.isEmpty() ? "" : "\n") + newTextField.getValue();

    // Assuming the font size is 12
    float fontSize = 12;
    float textHeight = calculateTextHeight(newText, fontSize, rectangle.getWidth());

    return textHeight > rectangle.getHeight();
  }

  private static float calculateTextHeight(String text, float fontSize, float width) {
    // Assuming single line height plus some extra space for line height
    float lineHeight = fontSize * 1.2f; // Adjust line height factor as needed
    float averageCharWidth = fontSize * 0.6f; // Adjust based on font characteristics

    String[] lines = text.split("\n"); // Split by new line feeds
    int totalLines = 0;

    for (String line : lines) {
      float currentLineWidth = 0;
      String[] words = line.split(" "); // Split line into words

      for (String word : words) {
        float wordWidth = word.length() * averageCharWidth;

        // If adding this word exceeds the rectangle width, wrap to the next line
        if (currentLineWidth + wordWidth > width) {
          totalLines++; // Increment line count
          currentLineWidth = wordWidth; // Start a new line with the current word
        } else {
          currentLineWidth += wordWidth + (fontSize * 0.2f); // Add some spacing
        }
      }

      // Account for the last line if there's any content
      if (currentLineWidth > 0) {
        totalLines++;
      }
    }

    return totalLines * lineHeight; // To
  }


  private void addTexts(Certificate certificate, String additionalInfoText, PDDocument document,
      boolean isCitizenFormat)
      throws IOException {
    final var mcid = new AtomicInteger(
        certificate.certificateModel().pdfSpecification().pdfMcid().value()
    );

    setDraftWatermark(document, certificate, mcid);
    setSignatureText(
        document,
        certificate,
        mcid,
        isCitizenFormat
    );
    setSentText(document, certificate, mcid);

    final var nbrOfPages = document.getNumberOfPages();
    for (int i = 0; i < nbrOfPages; i++) {
      setMarginText(document, certificate, additionalInfoText, mcid, i);
    }
  }

  private void setFieldValues(PDDocument document, List<PdfField> fields) {
    final var acroForm = document.getDocumentCatalog().getAcroForm();
    fields
        .stream()
        .filter(Objects::nonNull)
        .forEach(field -> {
          try {
            setFieldValue(acroForm, field);
          } catch (IOException e) {
            throw new IllegalStateException(e);
          }
        });
  }

  private void setSentText(PDDocument document, Certificate certificate, AtomicInteger mcid)
      throws IOException {
    if (certificate.sent() != null && certificate.sent().sentAt() != null) {
      pdfAdditionalInformationTextGenerator.addSentText(document, certificate,
          mcid.getAndIncrement()
      );
      if (Boolean.TRUE.equals(certificate.certificateModel().availableForCitizen())) {
        pdfAdditionalInformationTextGenerator.addSentVisibilityText(document,
            mcid.getAndIncrement()
        );
      }
    }
  }

  private void setMarginText(PDDocument document, Certificate certificate,
      String additionalInfoText, AtomicInteger mcid, int pageIndex)
      throws IOException {
    if (certificate.status() == Status.SIGNED) {
      pdfAdditionalInformationTextGenerator.addMarginAdditionalInfoText(
          document,
          certificate.id().id(),
          additionalInfoText,
          mcid.getAndIncrement(),
          pageIndex
      );
    }
  }

  private void setSignatureText(PDDocument document, Certificate certificate,
      AtomicInteger mcid, boolean isCitizenFormat)
      throws IOException {
    final var acroForm = document.getDocumentCatalog().getAcroForm();
    final var pageIndex = certificate.certificateModel().pdfSpecification().signature()
        .signaturePageIndex();
    if (certificate.status() == Status.SIGNED) {
      pdfAdditionalInformationTextGenerator.addDigitalSignatureText(
          document, getSignatureOffsetX(acroForm, certificate),
          getSignatureOffsetY(acroForm, certificate), mcid.getAndIncrement(),
          getSignatureTagIndex(certificate, includeAddress(certificate, isCitizenFormat)), pageIndex
      );
    }
  }

  private void setDraftWatermark(PDDocument document, Certificate certificate, AtomicInteger mcid)
      throws IOException {
    if (certificate.status() == Status.DRAFT) {
      pdfAdditionalInformationTextGenerator.addDraftWatermark(document, mcid.getAndIncrement());
    }
  }

  private float getSignatureOffsetY(PDAcroForm acroForm, Certificate certificate) {
    final var rectangle = getSignedDateRectangle(acroForm, certificate);
    return rectangle.getLowerLeftY() + SIGNATURE_Y_PADDING;
  }

  private float getSignatureOffsetX(PDAcroForm acroForm, Certificate certificate) {
    final var rectangle = getSignedDateRectangle(acroForm, certificate);
    return rectangle.getUpperRightX() + SIGNATURE_X_PADDING;
  }

  private static PDRectangle getSignedDateRectangle(PDAcroForm acroForm, Certificate certificate) {
    final var signedDate = acroForm.getField(
        certificate.certificateModel().pdfSpecification().signature().signedDateFieldId().id());
    return signedDate.getWidgets().get(0).getRectangle();
  }

  private void setFieldValue(PDAcroForm acroForm, PdfField field)
      throws IOException {
    if (field.getValue() != null) {
      final var extractedField = acroForm.getField(field.getId());
      validateField(field.getId(), extractedField);

      final var append = field.getAppend() != null && field.getAppend();

      if (extractedField instanceof PDTextField textField && field.getAppearance() != null) {
        textField.setDefaultAppearance(field.getAppearance());
      }

      if ((extractedField instanceof PDVariableText textField) && !append) {
        final var textAppearance = new TextFieldAppearance(textField);
        textAppearance.adjustFieldHeight();
      }

      if (append) {
        extractedField.setValue(
            extractedField.getValueAsString()
                + (extractedField.getValueAsString().isEmpty() ? "" : "\n")
                + field.getValue()
        );
      } else {
        extractedField.setValue(field.getValue());
      }
    }
  }

  private static void validateField(String fieldId, PDField field) {
    if (field == null) {
      throw new IllegalStateException(
          String.format("Field is null when getting field of id '%s'", fieldId)
      );
    }
  }
}
