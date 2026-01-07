package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.service;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.X_MARGIN_APPENDIX_PAGE;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.Y_MARGIN_APPENDIX_PAGE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.CertificatePdfContext;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfPaginationUtil;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.factory.PdfOverflowPageFactory;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.factory.TextFieldAppearanceFactory;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.PdfAccessibilityUtil;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.PdfAdditionalInformationTextGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.TextUtil;

@Service
@RequiredArgsConstructor
public class PdfOverflowPageFillService {

  private final PdfPaginationUtil pdfPaginationUtil;
  private final PdfOverflowPageFactory pdfOverflowPageFactory;
  private final PdfAdditionalInformationTextGenerator pdfAdditionalInformationTextGenerator;
  private final TextUtil textUtil;
  private final TextFieldAppearanceFactory textFieldAppearanceFactory;


  public void setFieldValuesAppendix(CertificatePdfContext context,
      List<PdfField> appendedFields) {

    if (context.getTemplatePdfSpecification().overFlowPageIndex() == null
        || appendedFields.isEmpty()) {
      return;
    }
    final var acroForm = context.getDocument().getDocumentCatalog().getAcroForm();
    final var overFlowPageField = acroForm.getField(appendedFields.getFirst().getId());

    final var pages = pdfPaginationUtil.paginateFields(context, appendedFields, overFlowPageField);

    fillOverflowPage(pages.getFirst(), context);

    pages.stream()
        .skip(1)
        .forEach(fields -> {
          try {
            addAndFillOverflowPage(
                context,
                fields,
                overFlowPageField,
                pdfOverflowPageFactory.create(context)
            );
          } catch (IOException e) {
            throw new IllegalStateException("Unable to add and fill overflow page", e);
          }
        });
  }

  private static void fillOverflowPage(List<PdfField> fields, CertificatePdfContext context) {
    final var field = context.getAcroForm().getField(fields.getFirst().getId());
    try {
      field.setValue(
          fields.stream()
              .map(pdfField -> pdfField.normalizedValue(
                  context.getFontResolver().resolveFont(pdfField)))
              .collect(Collectors.joining("\n"))
      );
    } catch (IOException e) {
      throw new IllegalStateException("Unable to set value for overflow field", e);
    }
  }

  private void addAndFillOverflowPage(CertificatePdfContext context, List<PdfField> fields,
      PDField pdField, PDPage newPage)
      throws IOException {
    final var rectangle = pdField.getWidgets().getFirst().getRectangle();

    final var textFieldAppearance = textFieldAppearanceFactory.create(pdField)
        .orElseThrow(() -> new IllegalStateException(
            "Overflow field is not a variable text field: "
                + pdField.getFullyQualifiedName()));

    final var fontSize = textFieldAppearance.getFontSize();
    final var font = textFieldAppearance.getFont(context.getAcroForm().getDefaultResources());

    final var document = context.getDocument();

    String allText = fields.stream()
        .map(pdfField -> pdfField.normalizedValue(font))
        .collect(Collectors.joining("\n"));

    List<String> lines = new ArrayList<>();
    for (String line : allText.split("\n")) {
      lines.addAll(textUtil.wrapLine(line, rectangle.getWidth(), fontSize, font));
    }

    document.addPage(newPage);
    PdfAccessibilityUtil.createNewOverflowPageTag(document, newPage,
        context.getTemplatePdfSpecification());

    float startX = rectangle.getLowerLeftX() + X_MARGIN_APPENDIX_PAGE;
    float startY = rectangle.getUpperRightY() - Y_MARGIN_APPENDIX_PAGE;
    pdfAdditionalInformationTextGenerator.addOverFlowPageText(document,
        document.getNumberOfPages() - 1, lines, startX, startY, fontSize, font,
        context.nextMcid());

    addPatientId(context, context.getPdfFields(PdfField::isPatientField).getFirst(), fontSize);
  }

  private void addPatientId(CertificatePdfContext context, PdfField patientIdField, float fontSize)
      throws IOException {
    final var patientIdRect = context.getPatientIdRectangleForOverflow();
    final var document = context.getDocument();
    final var marginY = 6;
    pdfAdditionalInformationTextGenerator.addPatientId(document, document.getNumberOfPages() - 1,
        patientIdRect.getLowerLeftX(), patientIdRect.getLowerLeftY() + marginY,
        patientIdField.getValue(), fontSize, context.nextMcid());

  }
}
