package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
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
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.CertificatePdfContext;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.TextFieldAppearance;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.PdfAdditionalInformationTextGenerator;


@Service
@RequiredArgsConstructor
@Slf4j
public class CertificatePdfFillService {

  public static final int SIGNATURE_X_PADDING = 60;
  public static final int SIGNATURE_Y_PADDING = 2;
  private final PdfFieldGeneratorService pdfFieldGeneratorService;
  private final PdfAdditionalInformationTextGenerator pdfAdditionalInformationTextGenerator;
  private final PdfOverflowPageFillService pdfOverflowPageFillService;


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

    context.getPdfFields()
        .addAll(
            context.sanitizePdfFields(pdfFieldGeneratorService.generatePdfFields(context))
        );

    final var appendedFields = context.getPdfFields(PdfField::getAppend);

    if (templatePdfSpecification.overFlowPageIndex() != null && appendedFields.isEmpty()) {
      document.removePage(document.getPage(templatePdfSpecification.overFlowPageIndex().value()));
    }

    pdfOverflowPageFillService.setFieldValuesAppendix(context, appendedFields);
    setFieldValues(document, context.getPdfFields(field -> !field.getAppend())
    );
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