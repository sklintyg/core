package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7210CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPdfSpecificationConstants.FK7210_PDF_FODELSEDATUM_FIELD_ID;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@ExtendWith(MockitoExtension.class)
class PdfElementValueGeneratorTest {

  @Mock
  private PdfDateValueGenerator pdfDateValueGenerator;
  @Mock
  private PdfTextValueGenerator pdfTextValueGenerator;
  @Mock
  private CertificateModel certificateModel;

  private PdfElementValueGenerator pdfElementValueGenerator;

  private final Certificate.CertificateBuilder certificateBuilder = fk7210CertificateBuilder();
  private final ElementSpecification elementSpecification = ElementSpecification.builder().build();

  @BeforeEach
  void setUp() {
    doReturn(ElementValueDate.class).when(pdfDateValueGenerator).getType();
    doReturn(ElementValueText.class).when(pdfTextValueGenerator).getType();

    pdfElementValueGenerator = new PdfElementValueGenerator(
        List.of(pdfDateValueGenerator, pdfTextValueGenerator)
    );

    certificateBuilder.certificateModel(certificateModel);
  }

  @Test
  void shouldReturnListOfPdfFields() {
    final var expected = List.of(
        PdfField.builder()
            .id(FK7210_PDF_FODELSEDATUM_FIELD_ID.id())
            .value(LocalDate.now().toString())
            .build()
    );

    final var elementValue = ElementValueDate.builder().build();

    final var certificate = certificateBuilder.elementData(
            List.of(
                ElementData.builder()
                    .id(new ElementId("999"))
                    .value(elementValue)
                    .build()
            )
        )
        .build();

    doReturn(elementSpecification).when(certificateModel)
        .elementSpecification(new ElementId("999"));

    doReturn(expected).when(pdfDateValueGenerator).generate(
        elementSpecification, ElementValueDate.builder().build()
    );

    final var response = pdfElementValueGenerator.generate(certificate);

    assertEquals(expected, response);
  }

  @Test
  void shouldThrowIllegalStateExceptionIfUnableToFindGeneratorForType() {
    final var elementValue = ElementValueBoolean.builder().build();

    final var certificate = certificateBuilder.elementData(
            List.of(
                ElementData.builder()
                    .id(new ElementId("999"))
                    .value(elementValue)
                    .build()
            )
        )
        .build();

    doReturn(elementSpecification).when(certificateModel)
        .elementSpecification(new ElementId("999"));

    assertThrows(IllegalStateException.class,
        () -> pdfElementValueGenerator.generate(certificate)
    );
  }
}


