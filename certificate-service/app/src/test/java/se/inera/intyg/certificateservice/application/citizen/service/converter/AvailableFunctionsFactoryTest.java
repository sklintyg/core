package se.inera.intyg.certificateservice.application.citizen.service.converter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.citizen.service.converter.AvailableFunctionsFactory.AVAILABLE_FUNCTION_PRINT_NAME;
import static se.inera.intyg.certificateservice.application.citizen.service.converter.AvailableFunctionsFactory.SEND_CERTIFICATE_BODY;
import static se.inera.intyg.certificateservice.application.citizen.service.converter.AvailableFunctionsFactory.SEND_CERTIFICATE_NAME;
import static se.inera.intyg.certificateservice.application.citizen.service.converter.AvailableFunctionsFactory.SEND_CERTIFICATE_TITLE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7211CertificateBuilder;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.common.dto.AvailableFunctionDTO;
import se.inera.intyg.certificateservice.application.common.dto.AvailableFunctionType;
import se.inera.intyg.certificateservice.application.common.dto.InformationDTO;
import se.inera.intyg.certificateservice.application.common.dto.InformationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Sent;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;

@ExtendWith(MockitoExtension.class)
class AvailableFunctionsFactoryTest {

  private static final String FILENAME = "intyg_om_graviditet";
  private static final AvailableFunctionDTO EXPECTED_PRINT =
      AvailableFunctionDTO.builder()
          .name(AVAILABLE_FUNCTION_PRINT_NAME)
          .type(AvailableFunctionType.PRINT_CERTIFICATE)
          .information(
              List.of(
                  InformationDTO.builder()
                      .type(InformationType.FILENAME)
                      .text(FILENAME)
                      .build()
              )
          )
          .build();

  public static final AvailableFunctionDTO EXPECTED_SEND =
      AvailableFunctionDTO.builder()
          .title(SEND_CERTIFICATE_TITLE)
          .name(SEND_CERTIFICATE_NAME)
          .type(AvailableFunctionType.SEND_CERTIFICATE)
          .body(SEND_CERTIFICATE_BODY)
          .build();

  @InjectMocks
  private AvailableFunctionsFactory availableFunctionsFactory;

  @Nested
  class Send {

    @Test
    void shouldNotReturnSendIfNoRecipient() {
      final var result = availableFunctionsFactory.get(
          fk7211CertificateBuilder()
              .sent(null)
              .status(Status.SIGNED)
              .certificateModel(CertificateModel.builder()
                  .name("Intyg om graviditet")
                  .build())
              .build()
      );

      assertFalse(result.contains(EXPECTED_SEND));
    }

    @Test
    void shouldNotReturnSendIfDraft() {
      final var result = availableFunctionsFactory.get(
          fk7211CertificateBuilder()
              .sent(null)
              .status(Status.DRAFT)
              .build()
      );

      assertFalse(result.contains(EXPECTED_SEND));
    }

    @Test
    void shouldNotReturnSendIfAlreadySent() {
      final var result = availableFunctionsFactory.get(
          fk7211CertificateBuilder()
              .status(Status.SIGNED)
              .sent(Sent.builder()
                  .sentAt(LocalDateTime.now())
                  .build())
              .build()
      );

      assertFalse(result.contains(EXPECTED_SEND));
    }

    @Test
    void shouldNotReturnSendIfReplacedBySignedCertificate() {
      final var result = availableFunctionsFactory.get(
          fk7211CertificateBuilder()
              .sent(null)
              .status(Status.SIGNED)
              .children(List.of(
                  Relation.builder()
                      .status(Status.SIGNED)
                      .type(RelationType.REPLACE)
                      .build()
              ))
              .build()
      );

      assertFalse(result.contains(EXPECTED_SEND));
    }

    @Test
    void shouldReturnSendIfReplacedByDraft() {
      final var result = availableFunctionsFactory.get(
          fk7211CertificateBuilder()
              .sent(null)
              .status(Status.SIGNED)
              .children(List.of(
                  Relation.builder()
                      .status(Status.DRAFT)
                      .type(RelationType.REPLACE)
                      .build()
              ))
              .build()
      );

      assertTrue(result.contains(EXPECTED_SEND));
    }

    @Test
    void shouldReturnSendIfRenewed() {
      final var result = availableFunctionsFactory.get(
          fk7211CertificateBuilder()
              .sent(null)
              .status(Status.SIGNED)
              .children(List.of(
                  Relation.builder()
                      .status(Status.SIGNED)
                      .type(RelationType.RENEW)
                      .build()
              ))
              .build()
      );

      assertTrue(result.contains(EXPECTED_SEND));
    }

    @Test
    void shouldReturnSendIfAllConditionsAreMet() {
      final var result = availableFunctionsFactory.get(
          fk7211CertificateBuilder()
              .sent(null)
              .status(Status.SIGNED)
              .build()
      );

      assertTrue(result.contains(EXPECTED_SEND));
    }
  }

  @Nested
  class Print {

    @Test
    void shouldReturnAvailableFunctionPrint() {
      final var result = availableFunctionsFactory.get(fk7211CertificateBuilder().build());

      assertTrue(result.contains(EXPECTED_PRINT));
    }
  }
}