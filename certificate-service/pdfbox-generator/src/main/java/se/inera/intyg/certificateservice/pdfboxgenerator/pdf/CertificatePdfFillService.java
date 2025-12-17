package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.X_MARGIN_APPENDIX_PAGE;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.Y_MARGIN_APPENDIX_PAGE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.apache.pdfbox.pdmodel.interactive.form.PDVariableText;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfTagIndex;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.TemplatePdfSpecification;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.service.PdfFieldGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.PdfAccessibilityUtil;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.PdfAdditionalInformationTextGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.TextUtil;


@Service
@RequiredArgsConstructor
@Slf4j
public class CertificatePdfFillService {

  public static final int SIGNATURE_X_PADDING = 60;
  public static final int SIGNATURE_Y_PADDING = 2;
  private final PdfFieldGenerator pdfFieldGenerator;
  private final PdfAdditionalInformationTextGenerator pdfAdditionalInformationTextGenerator;
  private final TextUtil textUtil;


  public PDDocument fillDocument(CertificatePdfContext context) {
    try {
      setFields(context);
      addTexts(context);
      return context.getDocument();
    } catch (Exception e) {
      throw new IllegalStateException("Could not create Pdf", e);
    }
  }

  private static PdfTagIndex getSignatureTagIndex(TemplatePdfSpecification templatePdfSpecification,
      boolean includeAddress) {
    final var signature = templatePdfSpecification.signature();

    return includeAddress
        ? signature.signatureWithAddressTagIndex()
        : signature.signatureWithoutAddressTagIndex();
  }

  private static boolean includeAddress(Certificate certificate, boolean isCitizenFormat) {
    if (isCitizenFormat) {
      return false;
    }

    return certificate.sent() == null || certificate.sent().sentAt() == null;
  }

  private void setFields(CertificatePdfContext context) {
    final var document = context.getDocument();
    final var templatePdfSpecification = context.getTemplatePdfSpecification();

    context.getPdfFields().addAll(pdfFieldGenerator.generatePdfFields(context));
    context.addDefaultAppearanceToPdfFields();
    context.sanatizePdfFields();

    final var appendedFields = context.getPdfFields().stream()
        .filter(PdfField::getAppend)
        .toList();

    if (templatePdfSpecification.overFlowPageIndex() != null && appendedFields.isEmpty()) {
      document.removePage(document.getPage(templatePdfSpecification.overFlowPageIndex().value()));
    }

    setFieldValuesAppendix(context, appendedFields);
    setFieldValues(document,
        context.getPdfFields().stream()
            .filter(field -> !field.getAppend())
            .toList()
    );
  }

  private void setFieldValuesAppendix(CertificatePdfContext context,
      List<PdfField> appendedFields) {
    final var templatePdfSpecification = context.getTemplatePdfSpecification();

    if (templatePdfSpecification.overFlowPageIndex() == null || appendedFields.isEmpty()) {
      return;
    }

    try {
      final var certificate = context.getCertificate();
      final var patientField = PdfField.builder()
          .id(templatePdfSpecification.patientIdFieldIds().getLast().id())
          .value(certificate.getMetadataForPrint().patient().id().idWithoutDash())
          .build();

      context.getPdfFields().add(patientField);
      patientField.setValue(patientField.sanitizedValue(context.getFont()));

      setFieldValuesAppendix(context,
          templatePdfSpecification.overFlowPageIndex().value(),
          appendedFields,
          patientField);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  private void setFieldValuesAppendix(CertificatePdfContext context,
      int overFlowPageIndex, List<PdfField> appendedFields, PdfField patientIdField)
      throws IOException {
    final var document = context.getDocument();
    final var font = context.getFont();
    final var acroForm = document.getDocumentCatalog().getAcroForm();
    final var overflowField = acroForm.getField(appendedFields.getFirst().getId());
    final var rectangle = overflowField.getWidgets().getFirst().getRectangle();
    final var fontSize = new TextFieldAppearance((PDVariableText) overflowField).getFontSize();
    int start = 0;
    int count = 0;
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
          final var part2 = PdfField.builder()
              .id(appendedFieldsMutable.get(count).getId())
              .value(parts.partTwo())
              .appearance(context.getDefaultAppearance())
              .append(true)
              .build();

          context.getPdfFields().add(part2);
          part2.setValue(part2.sanitizedValue(context.getFont()));

          appendedFieldsMutable.add(count + 1, part2);
          count++;
        }

        fillFieldsOnPage(context, overFlowPageIndex, appendedFieldsMutable, patientIdField, start,
            count, acroForm, fontSize, rectangle);
        start = count;
      }
      count++;
    }

    fillFieldsOnPage(context, overFlowPageIndex, appendedFieldsMutable, patientIdField, start,
        count, acroForm, fontSize, rectangle);
  }

  private void fillFieldsOnPage(CertificatePdfContext context, int overFlowPageIndex,
      List<PdfField> appendedFields, PdfField patientIdField, int start, int count,
      PDAcroForm acroForm, float fontSize, PDRectangle rectangle)
      throws IOException {
    if (start == 0) {
      fillOverflowPage(appendedFields.subList(start, count), acroForm);
    } else {
      addAndFillOverflowPage(context, overFlowPageIndex, appendedFields.subList(start, count),
          acroForm, patientIdField, fontSize, rectangle);
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

  private void addAndFillOverflowPage(CertificatePdfContext context,
      int overFlowPageIndex, List<PdfField> fields,
      PDAcroForm acroForm, PdfField patientIdField, float fontSize, PDRectangle rectangle)
      throws IOException {
    final var document = context.getDocument();
    final var font = context.getFont();
    final var templatePdfSpecification = context.getTemplatePdfSpecification();

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
    PdfAccessibilityUtil.createNewOverflowPageTag(document, clonedPage, templatePdfSpecification);

    float startX = rectangle.getLowerLeftX() + X_MARGIN_APPENDIX_PAGE;
    float startY = rectangle.getUpperRightY() - Y_MARGIN_APPENDIX_PAGE;
    pdfAdditionalInformationTextGenerator.addOverFlowPageText(document,
        document.getNumberOfPages() - 1, lines, startX, startY, fontSize, font,
        context.nextMcid());

    addPatientId(document, document.getNumberOfPages() - 1, patientIdField, acroForm, fontSize,
        context.nextMcid());
  }

  private void addPatientId(PDDocument document, int pageIndex,
      PdfField patientIdField,
      PDAcroForm acroForm, float fontSize, int mcid) throws IOException {
    final var patientIdRect = acroForm.getField(patientIdField.getId()).getWidgets().getFirst()
        .getRectangle();
    final var marginY = 6;
    pdfAdditionalInformationTextGenerator.addPatientId(document, pageIndex,
        patientIdRect.getLowerLeftX(), patientIdRect.getLowerLeftY() + marginY,
        patientIdField.getValue(), fontSize, mcid);

  }

  private void addTexts(CertificatePdfContext context)
      throws IOException {
    final var document = context.getDocument();

    setDraftWatermark(context);
    setSignatureText(context);
    setSentText(context);

    final var nbrOfPages = document.getNumberOfPages();
    for (int pageIndex = 0; pageIndex < nbrOfPages; pageIndex++) {
      setMarginText(context, pageIndex);
      setPageNumber(context, pageIndex, nbrOfPages);
    }
  }

  private void setPageNumber(CertificatePdfContext context, int pageIndex, int nbrOfPages)
      throws IOException {
    if (!context.getTemplatePdfSpecification().hasPageNbr()) {
      pdfAdditionalInformationTextGenerator.setPageNumber(
          context.getDocument(),
          pageIndex,
          nbrOfPages,
          context.nextMcid()
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

  private void setSentText(CertificatePdfContext context)
      throws IOException {
    final var certificate = context.getCertificate();
    final var document = context.getDocument();

    if (certificate.sent() != null && certificate.sent().sentAt() != null) {
      pdfAdditionalInformationTextGenerator.addSentText(document, certificate,
          context.nextMcid()
      );
      if (Boolean.TRUE.equals(certificate.certificateModel().availableForCitizen())) {
        pdfAdditionalInformationTextGenerator.addSentVisibilityText(document,
            context.nextMcid()
        );
      }
    }
  }

  private void setMarginText(CertificatePdfContext context, int pageIndex)
      throws IOException {
    final var certificate = context.getCertificate();
    final var document = context.getDocument();
    final var additionalInfoText = context.getAdditionalInfoText();

    if (certificate.status() == Status.SIGNED) {
      pdfAdditionalInformationTextGenerator.addMarginAdditionalInfoText(
          document,
          certificate.id().id(),
          additionalInfoText,
          context.nextMcid(),
          pageIndex
      );
    }
  }

  private void setSignatureText(CertificatePdfContext context)
      throws IOException {
    final var certificate = context.getCertificate();
    final var document = context.getDocument();
    final var isCitizenFormat = context.isCitizenFormat();
    final var templatePdfSpecification = context.getTemplatePdfSpecification();
    final var acroForm = document.getDocumentCatalog().getAcroForm();
    final var pageIndex = templatePdfSpecification.signature()
        .signaturePageIndex();

    if (certificate.status() == Status.SIGNED) {
      pdfAdditionalInformationTextGenerator.addDigitalSignatureText(
          document,
          getSignatureOffsetX(acroForm, templatePdfSpecification),
          getSignatureOffsetY(acroForm, templatePdfSpecification),
          context.nextMcid(),
          getSignatureTagIndex(templatePdfSpecification,
              includeAddress(certificate, isCitizenFormat)).value(),
          pageIndex
      );
    }
  }

  private void setDraftWatermark(CertificatePdfContext context)
      throws IOException {
    final var certificate = context.getCertificate();
    final var document = context.getDocument();

    if (certificate.status() == Status.DRAFT) {
      pdfAdditionalInformationTextGenerator.addDraftWatermark(document, context.nextMcid());
    }
  }

  private float getSignatureOffsetY(PDAcroForm acroForm,
      TemplatePdfSpecification templatePdfSpecification) {
    final var rectangle = getSignedDateRectangle(acroForm, templatePdfSpecification);
    return rectangle.getLowerLeftY() + SIGNATURE_Y_PADDING;
  }

  private float getSignatureOffsetX(PDAcroForm acroForm,
      TemplatePdfSpecification templatePdfSpecification) {
    final var rectangle = getSignedDateRectangle(acroForm, templatePdfSpecification);
    return rectangle.getUpperRightX() + SIGNATURE_X_PADDING;
  }

  private static PDRectangle getSignedDateRectangle(PDAcroForm acroForm,
      TemplatePdfSpecification templatePdfSpecification) {
    final var signedDate = acroForm.getField(
        templatePdfSpecification.signature().signedDateFieldId().id());
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

      if (((extractedField instanceof PDVariableText textField) && !append)) {
        final var textAppearance = new TextFieldAppearance(textField);
        textAppearance.adjustFieldHeight(field.getOffset());
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