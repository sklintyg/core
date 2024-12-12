package se.inera.intyg.certificateservice.certificate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
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
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateRequestDTO;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateResponseDTO;
import se.inera.intyg.certificateservice.certificate.integration.PrintCertificateFromCertificatePrintService;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

@ExtendWith(MockitoExtension.class)
class GeneralPdfGeneratorTest {

  private static final PrintCertificateCategoryDTO PRINT_CERTIFICATE_CATEGORY_DTO = PrintCertificateCategoryDTO
      .builder()
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
  private static final Certificate CERTIFICATE = Certificate.builder()
      .id(new CertificateId(CERTIFICATE_ID))
      .certificateModel(
          CertificateModel.builder()
              .elementSpecifications(
                  List.of(ELEMENT_SPECIFICATION)
              )
              .build()
      )
      .build();
  private static final PrintCertificateResponseDTO RESPONSE = PrintCertificateResponseDTO.builder()
      .pdfData(PDF_DATA)
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
    when(printCertificateCategoryConverter.convert(CERTIFICATE, ELEMENT_SPECIFICATION))
        .thenReturn(PRINT_CERTIFICATE_CATEGORY_DTO);
    when(printCertificateMetadataConverter.convert(CERTIFICATE, IS_CITIZEN))
        .thenReturn(PRINT_CERTIFICATE_METADATA_DTO);
    when(printCertificateFromCertificatePrintService.print(any(), anyString()))
        .thenReturn(RESPONSE);
  }

  @Test
  void shouldConvertRequest() {
    final var captor = ArgumentCaptor.forClass(PrintCertificateRequestDTO.class);
    final var expected = PrintCertificateRequestDTO.builder()
        .categories(List.of(PRINT_CERTIFICATE_CATEGORY_DTO))
        .metadata(PRINT_CERTIFICATE_METADATA_DTO)
        .build();

    generalPdfGenerator.generate(CERTIFICATE, TEXT, IS_CITIZEN);
    verify(printCertificateFromCertificatePrintService).print(captor.capture(), anyString());

    assertEquals(expected, captor.getValue());
  }

  @Test
  void shouldConvertCertificateId() {
    final var captor = ArgumentCaptor.forClass(String.class);

    generalPdfGenerator.generate(CERTIFICATE, TEXT, IS_CITIZEN);
    verify(printCertificateFromCertificatePrintService).print(any(PrintCertificateRequestDTO.class),
        captor.capture());

    assertEquals(CERTIFICATE_ID, captor.getValue());
  }

  @Test
  void shouldConvertResponse() {
    final var expectedResponse = new Pdf(PDF_DATA, "");
    final var response = generalPdfGenerator.generate(CERTIFICATE, TEXT, IS_CITIZEN);

    assertEquals(expectedResponse, response);
  }
}