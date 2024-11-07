package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSFloat;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.pdfbox.pdmodel.interactive.form.PDVariableText;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
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
  @Value("classpath:fonts/verdana.ttf")
  Resource fontResource;

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

    final var fieldsWithoutAppend = pdfFields.stream()
        .filter(field -> !field.getAppend())
        .toList();

    try {
      final var patientField = PdfField.builder()
          .id(pdfSpecification.patientIdFieldIds().getLast().id())
          .value(certificate.certificateMetaData().patient().id().idWithoutDash())
          .build();
      setFieldValuesAppendix(document, appendedFields, patientField);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }

    setFieldValues(document, fieldsWithoutAppend);
    setFieldValues(document, pdfUnitValueGenerator.generate(certificate));
    setFieldValues(document, pdfPatientValueGenerator.generate(certificate,
        certificate.certificateModel().pdfSpecification().patientIdFieldIds()));
  }

  private void setFieldValuesAppendix(PDDocument document,
      List<PdfField> appendedFields, PdfField patientIdField) throws IOException {
    final var acroForm = document.getDocumentCatalog().getAcroForm();
    final var overflowField = acroForm.getField(appendedFields.getFirst().getId());
    final var rectangle = overflowField.getWidgets().getFirst().getRectangle();
    final var fontSize = new TextFieldAppearance((PDVariableText) overflowField).getFontSize();
    int start = 0;
    int count = 0;
    while (count < appendedFields.size()) {
      if (isHeightOverFlow(appendedFields.subList(start, count), appendedFields.get(count),
          rectangle, fontSize)) {
        if (start == 0) {
          fillOverflowPage(appendedFields.subList(start, count), acroForm);
        } else {
          addAndFillOverflowPage(document, appendedFields.subList(start, count),
              acroForm,
              patientIdField);
        }
        start = count;
      }
      count++;
    }
    if (start == 0) {
      fillOverflowPage(appendedFields.subList(start, count), acroForm);
    } else {
      addAndFillOverflowPage(document, appendedFields.subList(start, count),
          acroForm,
          patientIdField);
    }
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

  private void addAndFillOverflowPage(PDDocument document,
      List<PdfField> fields,
      PDAcroForm acroForm, PdfField patientIdField)
      throws IOException {

    final var pageToClone = document.getPage(4);
    final var meidaBoxToClone = document.getPage(4).getMediaBox();
    final var mediabox = new PDRectangle(meidaBoxToClone.getLowerLeftX(),
        meidaBoxToClone.getLowerLeftY(),
        meidaBoxToClone.getWidth(), meidaBoxToClone.getHeight());

    final var clonedPage = new PDPage(mediabox);

    List<String> lines = new ArrayList<>();
    float fontSize = 10;
    float leading = 1.5f * fontSize;
    float marginX = 52;
    float marginY = 96;
    float width = mediabox.getWidth() - 2 * marginX;
    float startX = mediabox.getLowerLeftX() + marginX;
    float startY = mediabox.getUpperRightY() - marginY;

    try (PDPageContentStream contentStream = new PDPageContentStream(document,
        clonedPage)) {
      contentStream.appendRawCommands(pageToClone.getContents().readAllBytes());
    }

    PDType0Font font = PDType0Font.load(document,
        fontResource.getInputStream());

    String allText = fields.stream()
        .map(PdfField::getValue)
        .collect(Collectors.joining("\n"));

    for (String line : allText.split("\n")) {
      lines.addAll(wrapLine(line, width, fontSize, font));
    }

    document.addPage(clonedPage);
    pdfAdditionalInformationTextGenerator.addOverFlowPageText(document, 4,
        document.getNumberOfPages() - 1, lines, startX, startY, leading, fontSize);

    addPatientId(document, document.getNumberOfPages() - 1, patientIdField, acroForm, fontSize);
  }

  public List<String> wrapLine(String line, float width, float fontSize, PDFont font)
      throws IOException {
    List<String> wrappedLines = new ArrayList<>();

    final float lineWidth = fontSize * font.getStringWidth(line) / 1000;

    // If the line fits, no need to wrap it
    if (lineWidth <= width) {
      wrappedLines.add(line);
      return wrappedLines;
    }

    int lastSpaceIndex = -1;
    while (!line.isEmpty()) {
      int spaceIndex = line.indexOf(' ', lastSpaceIndex + 1);

      if (spaceIndex == -1) {
        // If no space found, take the entire remaining text
        spaceIndex = line.length();
      }

      String substring = line.substring(0, spaceIndex);
      float substringWidth = fontSize * font.getStringWidth(substring) / 1000;

      // If the substring is too long, add the previous part to the line
      if (substringWidth > width) {
        // If the substring is one long word without spaces it will not wrap correctly
        if (lastSpaceIndex != -1) {
          substring = line.substring(0, lastSpaceIndex);
        }

        wrappedLines.add(substring);
        line = line.substring(lastSpaceIndex == -1 ? spaceIndex : lastSpaceIndex).trim();
        lastSpaceIndex = -1; // Reset to process the next part
      } else if (spaceIndex == line.length()) {
        // If we've reached the end of the line, add the last portion and break
        wrappedLines.add(line);
        break;
      } else {
        // Update lastSpaceIndex to continue wrapping
        lastSpaceIndex = spaceIndex;
      }
    }

    return wrappedLines;
  }

  private void addPatientId(PDDocument document, int pageIndex,
      PdfField patientIdField,
      PDAcroForm acroForm, float fontSize) throws IOException {
    final var patientIdRect = acroForm.getField(patientIdField.getId())
        .getCOSObject().getCOSArray(COSName.RECT);
    pdfAdditionalInformationTextGenerator.addPatientId(document, pageIndex,
        getPatientIdXOffset(patientIdRect), getPatientIdYOffset(patientIdRect),
        patientIdField.getValue(), fontSize);

  }

  private static float getPatientIdYOffset(COSArray patientIdRect) {
    // Adjust y cord as is done on all other pnr fields using TextFieldAppearance
    return ((COSFloat) patientIdRect.get(1)).floatValue() + 8;
  }

  private static float getPatientIdXOffset(COSArray patientIdRect) {
    return ((COSFloat) patientIdRect.get(0)).floatValue();
  }

  private static boolean isHeightOverFlow(List<PdfField> currentFields, PdfField newTextField,
      PDRectangle rectangle, float fontSize) throws IOException {
    // TODO: Do we want to break fields if they're very long? Or should one field always be on the same overflow sheet? What do we do then if headline and text are separated on two pages?
    // TODO: If we want to break up text then we need to go over line by line of field, create a new field, return this field if max is met and then add the new field to the appendedFields list in parent function
    final var currentText = currentFields.stream()
        .map(PdfField::getValue)
        .collect(Collectors.joining("\n"));

    String newText = currentText + (currentText.isEmpty() ? "" : "\n") + newTextField.getValue();

    float textHeight = calculateTextHeight(newText, fontSize, rectangle.getWidth());

    return textHeight > rectangle.getHeight();
  }

  private static float calculateTextHeight(String text, float fontSize, float width)
      throws IOException {

    //TODO: should calculate height based on font from field
    PDType1Font font = new PDType1Font(FontName.HELVETICA);
    String[] lines = text.split("\n");
    int totalLines = 0;

    for (String line : lines) {

      final var lineWidth = font.getStringWidth(line) / 1000 * fontSize;

      if (lineWidth > width) {
        totalLines += (int) Math.ceil(lineWidth / width);
      } else {
        totalLines++;
      }
    }

    float ascent = font.getFontDescriptor().getAscent() / 1000 * fontSize;
    float descent = font.getFontDescriptor().getDescent() / 1000 * fontSize;
    float lineHeight = ascent - descent + 5f;

    return totalLines * lineHeight;
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
      pdfAdditionalInformationTextGenerator.setPageNumber(document, i, nbrOfPages,
          mcid.incrementAndGet());
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
    return signedDate.getWidgets().getFirst().getRectangle();
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

      extractedField.setValue(field.getValue());
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
