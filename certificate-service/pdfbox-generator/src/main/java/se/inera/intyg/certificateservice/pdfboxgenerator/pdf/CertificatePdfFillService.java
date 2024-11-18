package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.X_MAGIN_APPENDIX_PAGE;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.Y_MAGIN_APPENDIX_PAGE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.pdfbox.pdmodel.interactive.form.PDVariableText;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfTagIndex;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.PdfAccessibilityUtil;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.PdfAdditionalInformationTextGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.TextUtil;
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
  private final TextUtil textUtil;
  @Value("classpath:fonts/verdana.ttf")
  Resource fontResource;


  public PDDocument fillDocument(Certificate certificate, String additionalInfoText,
      boolean isCitizenFormat) {
    final var template = getTemplatePath(certificate, isCitizenFormat);

    final var mcid = new AtomicInteger(
        certificate.certificateModel().pdfSpecification().pdfMcid().value()
    );

    try (final var inputStream = getClass().getClassLoader().getResourceAsStream(template)) {
      if (inputStream == null) {
        throw new IllegalArgumentException("Template not found: " + template);
      }

      final var document = Loader.loadPDF(inputStream.readAllBytes());
      setFields(certificate, document, mcid);
      addTexts(certificate, additionalInfoText, document, isCitizenFormat, mcid);
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

  private static PdfTagIndex getSignatureTagIndex(Certificate certificate, boolean includeAddress) {
    final var signature = certificate.certificateModel().pdfSpecification().signature();

    return includeAddress ? signature.signatureWithAddressTagIndex()
        : signature.signatureWithoutAddressTagIndex();
  }

  private static boolean includeAddress(Certificate certificate, boolean isCitizenFormat) {
    if (isCitizenFormat) {
      return false;
    }

    return certificate.sent() == null || certificate.sent().sentAt() == null;
  }

  private void setFields(Certificate certificate, PDDocument document, AtomicInteger mcid) {
    final var pdfSpecification = certificate.certificateModel().pdfSpecification();
    if (certificate.status() == Status.SIGNED) {
      setFieldValues(document, pdfSignatureValueGenerator.generate(certificate));
    }

    final var pdfFields = pdfElementValueGenerator.generate(certificate);
    final var appendedFields = pdfFields.stream()
        .filter(PdfField::getAppend)
        .toList();
    final var fieldsWithoutAppend = pdfFields.stream()
        .filter(field -> !field.getAppend())
        .toList();

    setFieldValuesAppendix(document, certificate, pdfSpecification, appendedFields, mcid);
    setFieldValues(document, fieldsWithoutAppend);
    setFieldValues(document, pdfUnitValueGenerator.generate(certificate));
    setFieldValues(document, pdfPatientValueGenerator.generate(certificate,
        certificate.certificateModel().pdfSpecification().patientIdFieldIds()));
  }

  private void setFieldValuesAppendix(PDDocument document, Certificate certificate,
      PdfSpecification pdfSpecification, List<PdfField> appendedFields, AtomicInteger mcid) {
    if (pdfSpecification.overFlowPageIndex() == null || appendedFields.isEmpty()) {
      return;
    }

    try {
      final var patientField = PdfField.builder()
          .id(pdfSpecification.patientIdFieldIds().getLast().id())
          .value(certificate.certificateMetaData().patient().id().idWithoutDash())
          .build();
      setFieldValuesAppendix(document, pdfSpecification.overFlowPageIndex().value(), appendedFields,
          patientField, mcid);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  private void setFieldValuesAppendix(PDDocument document,
      int overFlowPageIndex, List<PdfField> appendedFields, PdfField patientIdField,
      AtomicInteger mcid)
      throws IOException {
    final var acroForm = document.getDocumentCatalog().getAcroForm();
    final var overflowField = acroForm.getField(appendedFields.getFirst().getId());
    final var rectangle = overflowField.getWidgets().getFirst().getRectangle();
    final var fontSize = new TextFieldAppearance((PDVariableText) overflowField).getFontSize();
    int start = 0;
    int count = 0;
    final var font = PDType0Font.load(document, fontResource.getInputStream());
    var appendedFieldsMutable = new ArrayList<>(appendedFields);

    while (count < appendedFieldsMutable.size()) {

      var overFlowLines = textUtil.getOverflowingLines(appendedFieldsMutable.subList(start, count),
          appendedFieldsMutable.get(count),
          rectangle, fontSize, font);

      if (overFlowLines.isPresent()) {
        var parts = overFlowLines.get();

        if (parts.partOne() != null) {
          appendedFieldsMutable.get(count).setValue(parts.partOne());
        }

        if (parts.partTwo() != null) {
          var part2 = new PdfField(appendedFieldsMutable.get(count).getId(),
              parts.partTwo(), true, null);
          appendedFieldsMutable.add(count + 1, part2);
          count++;
        }

        fillFieldsOnPage(document, overFlowPageIndex, appendedFieldsMutable, patientIdField, start,
            count, acroForm, fontSize, font, rectangle, mcid);
        start = count;
      }
      count++;
    }

    fillFieldsOnPage(document, overFlowPageIndex, appendedFieldsMutable, patientIdField, start,
        count, acroForm, fontSize, font, rectangle, mcid);
  }

  private void fillFieldsOnPage(PDDocument document, int overFlowPageIndex,
      List<PdfField> appendedFields,
      PdfField patientIdField, int start, int count, PDAcroForm acroForm, float fontSize,
      PDType0Font font, PDRectangle rectangle, AtomicInteger mcid)
      throws IOException {
    if (start == 0) {
      fillOverflowPage(appendedFields.subList(start, count), acroForm);
    } else {
      addAndFillOverflowPage(document, overFlowPageIndex, appendedFields.subList(start, count),
          acroForm,
          patientIdField, fontSize, font, rectangle, mcid);
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
      int overFlowPageIndex, List<PdfField> fields,
      PDAcroForm acroForm, PdfField patientIdField, float fontSize, PDFont font,
      PDRectangle rectangle, AtomicInteger mcid)
      throws IOException {
    final var pageToClone = document.getPage(overFlowPageIndex);
    COSDictionary pageDict = pageToClone.getCOSObject();
    COSDictionary newPageDict = new COSDictionary(pageDict);

    newPageDict.removeItem(COSName.ANNOTS);

    PDPage clonedPage = new PDPage(newPageDict);

    String allText = fields.stream()
        .map(PdfField::getValue)
        .collect(Collectors.joining("\n"));

    List<String> lines = new ArrayList<>();
    for (String line : allText.split("\n")) {
      lines.addAll(textUtil.wrapLine(line, rectangle.getWidth(), fontSize, font));
    }

    document.addPage(clonedPage);
    PdfAccessibilityUtil.createNewOverflowPageTag(document, clonedPage);

    float startX = rectangle.getLowerLeftX() + X_MAGIN_APPENDIX_PAGE;
    float startY = rectangle.getUpperRightY() - Y_MAGIN_APPENDIX_PAGE;
    pdfAdditionalInformationTextGenerator.addOverFlowPageText(document, overFlowPageIndex,
        document.getNumberOfPages() - 1, lines, startX,
        startY, fontSize, font, mcid.getAndIncrement());

    addPatientId(document, document.getNumberOfPages() - 1, patientIdField, acroForm, fontSize,
        mcid.getAndIncrement());
  }

  private void addPatientId(PDDocument document, int pageIndex,
      PdfField patientIdField,
      PDAcroForm acroForm, float fontSize, int mcid) throws IOException {
    final var patientIdRect = acroForm.getField(patientIdField.getId()).getWidgets().getFirst()
        .getRectangle();
    final var marginY = 8;
    pdfAdditionalInformationTextGenerator.addPatientId(document, pageIndex,
        patientIdRect.getLowerLeftX(), patientIdRect.getLowerLeftY() + marginY,
        patientIdField.getValue(), fontSize, mcid);

  }

  private void addTexts(Certificate certificate, String additionalInfoText, PDDocument document,
      boolean isCitizenFormat, AtomicInteger mcid)
      throws IOException {
    setDraftWatermark(document, certificate, mcid);
    setSignatureText(
        document,
        certificate,
        mcid,
        isCitizenFormat
    );
    setSentText(document, certificate, mcid);

    final var nbrOfPages = document.getNumberOfPages();
    for (int pageIndex = 0; pageIndex < nbrOfPages; pageIndex++) {
      setMarginText(document, certificate, additionalInfoText, mcid, pageIndex);
      setPageNumber(document, certificate, pageIndex, nbrOfPages, mcid);
    }
  }

  private void setPageNumber(PDDocument document, Certificate certificate, int pageIndex,
      int nbrOfPages,
      AtomicInteger mcid) throws IOException {
    if (!certificate.certificateModel().pdfSpecification().hasPageNbr()) {
      pdfAdditionalInformationTextGenerator.setPageNumber(
          document,
          pageIndex,
          nbrOfPages,
          mcid.incrementAndGet()
      );
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
          getSignatureTagIndex(certificate, includeAddress(certificate, isCitizenFormat)).value(),
          pageIndex
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
