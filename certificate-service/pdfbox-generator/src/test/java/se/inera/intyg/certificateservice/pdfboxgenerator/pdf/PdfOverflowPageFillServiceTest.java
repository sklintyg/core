package se.inera.intyg.certificateservice.pdfboxgenerator.pdf;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts.FontName;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.OverflowPageIndex;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.TemplatePdfSpecification;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.factory.PdfOverflowPageFactory;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.factory.TextFieldAppearanceFactory;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.service.PdfOverflowPageFillService;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.PdfAccessibilityUtil;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.PdfAdditionalInformationTextGenerator;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.text.TextUtil;

@ExtendWith(MockitoExtension.class)
class PdfOverflowPageFillServiceTest {

  @Mock
  private PdfPaginationUtil pdfPaginationUtil;
  @Mock
  private PdfOverflowPageFactory pdfOverflowPageFactory;
  @Mock
  private PdfAdditionalInformationTextGenerator pdfAdditionalInformationTextGenerator;
  @Mock
  private TextUtil textUtil;
  @Mock
  private TextFieldAppearanceFactory textFieldAppearanceFactory;

  @InjectMocks
  private PdfOverflowPageFillService pdfOverflowPageFillService;


  @Test
  void shouldReturnWhenNoOverflowPageIndex() {
    final var context = CertificatePdfContext.builder()
        .templatePdfSpecification(
            TemplatePdfSpecification.builder()
                .overFlowPageIndex(null)
                .build()
        )
        .build();

    pdfOverflowPageFillService.setFieldValuesAppendix(context, List.of(mock(PdfField.class)));

    verifyNoInteractions(pdfPaginationUtil);
  }

  @Test
  void shouldReturnWhenNoAppendedFields() {
    final var context = CertificatePdfContext.builder()
        .templatePdfSpecification(
            TemplatePdfSpecification.builder()
                .overFlowPageIndex(new OverflowPageIndex(0))
                .build()
        )
        .build();

    pdfOverflowPageFillService.setFieldValuesAppendix(context, List.of());

    verifyNoInteractions(pdfPaginationUtil);
  }

  @Test
  void shouldFillSingleOverflowPageWithoutCreatingNewPages() throws IOException {
    final var context = mockContext();
    final var pdfField = PdfField.builder()
        .id("overflowField")
        .value("Some long text that causes overflow")
        .append(true)
        .build();

    when(pdfPaginationUtil.paginateFields(
        eq(context),
        eq(List.of(pdfField)),
        any(PDField.class)
    )).thenReturn(List.of(List.of(pdfField)));

    when(context.getFontResolver().resolveFont(pdfField)).thenReturn(
        new PDType1Font(FontName.HELVETICA));

    pdfOverflowPageFillService.setFieldValuesAppendix(context, List.of(pdfField));

    verify(pdfOverflowPageFactory, never()).create(any());
    verify(pdfAdditionalInformationTextGenerator, never())
        .addOverFlowPageText(any(), anyInt(), anyList(), anyFloat(), anyFloat(), anyFloat(), any(),
            anyInt());
  }

  @Test
  void shouldCreateNewPagesForAdditionalOverflowPages() throws IOException {
    final var context = mockContext();

    final var pdfField1 = PdfField.builder()
        .id("overflowField")
        .value("Some long text")
        .append(true)
        .build();

    final var pdfField2 = PdfField.builder()
        .id("overflowField")
        .value("Some more long text")
        .append(true)
        .build();

    when(pdfPaginationUtil.paginateFields(any(), anyList(), any()))
        .thenReturn(List.of(
            List.of(pdfField1),
            List.of(pdfField2)
        ));

    when(context.getFontResolver().resolveFont(any()))
        .thenReturn(new PDType1Font(FontName.HELVETICA));

    when(pdfOverflowPageFactory.create(context))
        .thenReturn(new PDPage());

    final var textFieldAppearance = mock(TextFieldAppearance.class);
    when(textFieldAppearance.getFontSize()).thenReturn(12.0f);
    when(textFieldAppearance.getFont(any())).thenReturn(new PDType1Font(FontName.HELVETICA));
    when(textFieldAppearanceFactory.create(any(PDField.class)))
        .thenReturn(Optional.of(textFieldAppearance));

    try (var mocked =
        mockStatic(PdfAccessibilityUtil.class)) {

      mocked.when(() ->
          PdfAccessibilityUtil.createNewOverflowPageTag(any(), any(), any())
      ).thenReturn(null);

      pdfOverflowPageFillService.setFieldValuesAppendix(context, List.of(pdfField1));
    }

    verify(pdfOverflowPageFactory, times(1)).create(context);
    verify(pdfAdditionalInformationTextGenerator, times(1))
        .addOverFlowPageText(any(), anyInt(), anyList(),
            anyFloat(), anyFloat(), anyFloat(),
            any(), anyInt());
  }

  @Test
  void shouldAddPatientIdToNewOverflowPages() throws IOException {
    final var context = mockContext();

    final var pdfField = PdfField.builder()
        .id("overflowField")
        .value("Some long text that causes overflow")
        .append(true)
        .build();

    when(pdfPaginationUtil.paginateFields(any(), anyList(), any()))
        .thenReturn(List.of(
            List.of(pdfField),
            List.of(pdfField)
        ));

    when(context.getFontResolver().resolveFont(any()))
        .thenReturn(new PDType1Font(FontName.HELVETICA));

    when(pdfOverflowPageFactory.create(context))
        .thenReturn(new PDPage());

    final var textFieldAppearance = mock(TextFieldAppearance.class);
    when(textFieldAppearance.getFontSize()).thenReturn(12.0f);
    when(textFieldAppearance.getFont(any())).thenReturn(new PDType1Font(FontName.HELVETICA));
    when(textFieldAppearanceFactory.create(any(PDField.class)))
        .thenReturn(Optional.of(textFieldAppearance));

    when(textUtil.wrapLine(anyString(), anyFloat(), anyFloat(), any()))
        .thenReturn(List.of("wrapped line"));

    try (var mocked =
        mockStatic(PdfAccessibilityUtil.class)) {

      mocked.when(() ->
          PdfAccessibilityUtil.createNewOverflowPageTag(any(), any(), any())
      ).thenReturn(null);

      pdfOverflowPageFillService.setFieldValuesAppendix(context, List.of(pdfField));
    }

    verify(pdfAdditionalInformationTextGenerator, times(1))
        .addPatientId(
            any(),
            anyInt(),
            anyFloat(),
            anyFloat(),
            anyString(),
            anyFloat(),
            anyInt()
        );
  }

  @Test
  void shouldThrowExceptionWhenOverflowFieldIsNotVariableTextField() {
    final var context = mockContext();

    final var pdfField1 = PdfField.builder()
        .id("overflowField")
        .value("Some long text")
        .append(true)
        .build();

    final var pdfField2 = PdfField.builder()
        .id("overflowField")
        .value("Some more long text")
        .append(true)
        .build();

    when(pdfPaginationUtil.paginateFields(any(), anyList(), any()))
        .thenReturn(List.of(
            List.of(pdfField1),
            List.of(pdfField2)
        ));

    when(context.getFontResolver().resolveFont(any()))
        .thenReturn(new PDType1Font(FontName.HELVETICA));

    when(pdfOverflowPageFactory.create(context))
        .thenReturn(new PDPage());

    when(textFieldAppearanceFactory.create(any(PDField.class)))
        .thenReturn(Optional.empty());

    try (var mocked = mockStatic(PdfAccessibilityUtil.class)) {
      mocked.when(() ->
          PdfAccessibilityUtil.createNewOverflowPageTag(any(), any(), any())
      ).thenReturn(null);

      final var pdfFieldList = List.of(pdfField1);

      assertThrows(IllegalStateException.class, () ->
          pdfOverflowPageFillService.setFieldValuesAppendix(context, pdfFieldList)
      );
    }
  }

  private CertificatePdfContext mockContext() {
    PDDocument document = new PDDocument();
    PDAcroForm acroForm = new PDAcroForm(document);
    document.getDocumentCatalog().setAcroForm(acroForm);

    PDTextField overflowField = new PDTextField(acroForm);
    overflowField.setPartialName("overflowField");

    acroForm.getFields().add(overflowField);

    var widget = overflowField.getWidgets().getFirst();
    widget.setRectangle(new PDRectangle(0, 0, 200, 200));

    return CertificatePdfContext.builder()
        .document(document)
        .mcid(new AtomicInteger())
        .pdfFields(
            List.of(
                PdfField.builder()
                    .value("191212-1212")
                    .patientField(true)
                    .build()
            )
        )
        .certificate(MedicalCertificate.builder().build())
        .templatePdfSpecification(
            TemplatePdfSpecification.builder()
                .overFlowPageIndex(new OverflowPageIndex(0))
                .patientIdFieldIds(
                    List.of(
                        new PdfFieldId("overflowField")
                    )
                )
                .build()
        )
        .fontResolver(mock(PdfFontResolver.class))
        .build();
  }
}