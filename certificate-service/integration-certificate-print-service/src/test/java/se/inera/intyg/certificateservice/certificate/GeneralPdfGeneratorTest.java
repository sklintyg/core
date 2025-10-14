package se.inera.intyg.certificateservice.certificate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.certificate.converter.PrintCertificateCategoryConverter;
import se.inera.intyg.certificateservice.certificate.converter.PrintCertificateMetadataConverter;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateCategoryDTO;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateMetadataDTO;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateQuestionDTO;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateRequestDTO;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateResponseDTO;
import se.inera.intyg.certificateservice.certificate.integration.PrintCertificateFromCertificatePrintService;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGeneratorOptions;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;

@ExtendWith(MockitoExtension.class)
class GeneralPdfGeneratorTest {

  private static final PrintCertificateCategoryDTO PRINT_CERTIFICATE_CATEGORY_DTO = PrintCertificateCategoryDTO
      .builder()
      .questions(List.of())
      .build();
  private static final PrintCertificateMetadataDTO PRINT_CERTIFICATE_METADATA_DTO = PrintCertificateMetadataDTO
      .builder()
      .build();
  public static final ElementSpecification ELEMENT_SPECIFICATION = ElementSpecification.builder()
      .build();
  private static final Boolean IS_CITIZEN = true;
  private static final String TEXT = "Text";
  private static final String CERTIFICATE_ID = "CertificateId";
  private static final byte[] PDF_DATA = "pdfData".getBytes();
  private static final Certificate CERTIFICATE = MedicalCertificate.builder()
      .id(new CertificateId(CERTIFICATE_ID))
      .certificateModel(
          CertificateModel.builder()
              .name("åäö 123 test")
              .elementSpecifications(
                  List.of(ELEMENT_SPECIFICATION)
              )
              .recipient(
                  new Recipient(new RecipientId("TS"), "TS", "LA", "logo",
                      "Läkarintyg Transportstyrelsen")
              )
              .build()
      )
      .build();

  private static final Certificate CERTIFICATE_NO_GENERAL_RECIPIENT_NAME = MedicalCertificate.builder()
      .id(new CertificateId(CERTIFICATE_ID))
      .certificateModel(
          CertificateModel.builder()
              .name("åäö 123 test")
              .elementSpecifications(
                  List.of(ELEMENT_SPECIFICATION)
              )
              .recipient(
                  new Recipient(new RecipientId("TS"), "TS", "LA")
              )
              .build()
      )
      .build();
  private static final PrintCertificateResponseDTO RESPONSE = PrintCertificateResponseDTO.builder()
      .pdfData(PDF_DATA)
      .build();
  private static final String FILE_NAME = "aao_123_test";
  private static final PdfGeneratorOptions OPTIONS = PdfGeneratorOptions.builder()
      .additionalInfoText(TEXT)
      .citizenFormat(IS_CITIZEN)
      .hiddenElements(List.of())
      .build();

  @Mock
  private PrintCertificateCategoryConverter printCertificateCategoryConverter;
  @Mock
  private PrintCertificateFromCertificatePrintService printCertificateFromCertificatePrintService;
  @Mock
  private PrintCertificateMetadataConverter printCertificateMetadataConverter;

  @InjectMocks
  private GeneralPdfGenerator generalPdfGenerator;

  @BeforeEach
  void setUp() {
    when(printCertificateFromCertificatePrintService.print(any()))
        .thenReturn(RESPONSE);
  }

  @Nested
  class NoGeneralRecipientName {

    @BeforeEach
    void setup() {
      when(printCertificateCategoryConverter.convert(CERTIFICATE_NO_GENERAL_RECIPIENT_NAME,
          ELEMENT_SPECIFICATION, OPTIONS))
          .thenReturn(PRINT_CERTIFICATE_CATEGORY_DTO);
      when(
          printCertificateMetadataConverter.convert(CERTIFICATE_NO_GENERAL_RECIPIENT_NAME,
              IS_CITIZEN,
              FILE_NAME))
          .thenReturn(PRINT_CERTIFICATE_METADATA_DTO);
    }

    @Test
    void shouldConvertRequest() {
      final var captor = ArgumentCaptor.forClass(PrintCertificateRequestDTO.class);
      final var expected = PrintCertificateRequestDTO.builder()
          .categories(List.of())
          .metadata(PRINT_CERTIFICATE_METADATA_DTO)
          .build();

      generalPdfGenerator.generate(CERTIFICATE_NO_GENERAL_RECIPIENT_NAME, OPTIONS);
      verify(printCertificateFromCertificatePrintService).print(captor.capture());

      assertEquals(expected, captor.getValue());
    }


    @Test
    void shouldConvertResponseWhenNoGeneralFileName() {
      final var expectedResponse = new Pdf(PDF_DATA, FILE_NAME);
      final var response = generalPdfGenerator.generate(CERTIFICATE_NO_GENERAL_RECIPIENT_NAME,
          OPTIONS);

      assertEquals(expectedResponse, response);
    }
  }

  @Nested
  class GeneralRecipientName {

    @BeforeEach
    void setup() {
      when(printCertificateCategoryConverter.convert(CERTIFICATE, ELEMENT_SPECIFICATION, OPTIONS))
          .thenReturn(PRINT_CERTIFICATE_CATEGORY_DTO);
      when(printCertificateMetadataConverter.convert(CERTIFICATE, IS_CITIZEN,
          "lakarintyg_transportstyrelsen"))
          .thenReturn(PRINT_CERTIFICATE_METADATA_DTO);
    }


    @Test
    void shouldConvertResponseWhenGeneralFileName() {
      final var expectedResponse = new Pdf(PDF_DATA, "lakarintyg_transportstyrelsen");

      final var response = generalPdfGenerator.generate(CERTIFICATE, OPTIONS);

      assertEquals(expectedResponse, response);
    }
  }

  @Test
  void shouldNotConvertContactInfoToCategory() {

    var certificateModel = CertificateModel.builder()
        .name("åäö 123 test")
        .recipient(
            new Recipient(new RecipientId("TS"), "TS", "LA")
        )
        .elementSpecifications(
            List.of(ElementSpecification.builder()
                .id(new ElementId("UNIT_CONTACT_INFORMATION"))
                .configuration(ElementConfigurationUnitContactInformation.builder()
                    .build()
                ).build()
            )
        )
        .build();

    final var cert = MedicalCertificate.builder()
        .id(new CertificateId(CERTIFICATE_ID))
        .certificateModel(certificateModel)
        .build();

    when(
        printCertificateMetadataConverter.convert(cert,
            IS_CITIZEN,
            FILE_NAME))
        .thenReturn(PRINT_CERTIFICATE_METADATA_DTO);

    final var captor = ArgumentCaptor.forClass(PrintCertificateRequestDTO.class);
    final var expected = PrintCertificateRequestDTO.builder()
        .categories(List.of())
        .metadata(PRINT_CERTIFICATE_METADATA_DTO)
        .build();

    generalPdfGenerator.generate(cert, OPTIONS);
    verify(printCertificateFromCertificatePrintService).print(captor.capture());

    assertEquals(expected, captor.getValue());

  }

  @Test
  void shouldFilterCategoriesWithoutQuestions() {

    var categoryWithQuestion = PrintCertificateCategoryDTO
        .builder()
        .questions(List.of(PrintCertificateQuestionDTO.builder()
            .name("name")
            .build()))
        .build();

    var certWithMultipleElements = MedicalCertificate.builder()
        .id(new CertificateId(CERTIFICATE_ID))
        .certificateModel(
            CertificateModel.builder()
                .name("åäö 123 test")
                .elementSpecifications(
                    List.of(ELEMENT_SPECIFICATION, ELEMENT_SPECIFICATION)
                )
                .recipient(
                    new Recipient(new RecipientId("TS"), "TS", "LA")
                )
                .build()
        )
        .build();

    when(printCertificateCategoryConverter.convert(certWithMultipleElements,
        ELEMENT_SPECIFICATION, OPTIONS))
        .thenReturn(PRINT_CERTIFICATE_CATEGORY_DTO).thenReturn(categoryWithQuestion);

    when(
        printCertificateMetadataConverter.convert(certWithMultipleElements,
            IS_CITIZEN,
            FILE_NAME))
        .thenReturn(PRINT_CERTIFICATE_METADATA_DTO);

    final var captor = ArgumentCaptor.forClass(PrintCertificateRequestDTO.class);

    generalPdfGenerator.generate(certWithMultipleElements, OPTIONS);
    verify(printCertificateFromCertificatePrintService).print(captor.capture());

    assertEquals(1, captor.getValue().getCategories().size());

  }
}