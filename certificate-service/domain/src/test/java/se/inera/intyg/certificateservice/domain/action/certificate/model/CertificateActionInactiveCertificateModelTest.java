package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7804CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.fk7804certificateModelBuilder;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;

@ExtendWith(MockitoExtension.class)
class CertificateActionInactiveCertificateModelTest {

  private CertificateActionInactiveCertificateModel certificateActionInactiveCertificateModel;
  private MedicalCertificate.MedicalCertificateBuilder certificateBuilder;
  private static final CertificateActionSpecification CERTIFICATE_ACTION_SPECIFICATION =
      CertificateActionSpecification.builder()
          .certificateActionType(CertificateActionType.INACTIVE_CERTIFICATE_MODEL)
          .build();
  @Mock
  CertificateActionConfigurationRepository certificateActionConfigurationRepository;
  @InjectMocks
  CertificateActionFactory certificateActionFactory;

  @BeforeEach
  void setUp() {
    certificateActionInactiveCertificateModel = (CertificateActionInactiveCertificateModel) certificateActionFactory.create(
        CERTIFICATE_ACTION_SPECIFICATION
    );

    certificateBuilder = fk7804CertificateBuilder();
  }

  @Test
  void shallReturnTypeFromSpecification() {
    assertEquals(CertificateActionType.INACTIVE_CERTIFICATE_MODEL,
        certificateActionInactiveCertificateModel.getType());
  }

  @Test
  void shouldReturnTrueIfCertificateModelIsInactive() {
    final var certificate = certificateBuilder
        .certificateModel(
            fk7804certificateModelBuilder()
                .activeFrom(LocalDateTime.now().plusDays(1))
                .build()
        )
        .build();

    final var result = certificateActionInactiveCertificateModel.evaluate(
        Optional.of(certificate), Optional.empty());

    assertTrue(result);
  }

  @Test
  void shouldReturnFalseIfCertificateModelIsActive() {
    final var certificate = certificateBuilder
        .certificateModel(
            fk7804certificateModelBuilder()
                .activeFrom(LocalDateTime.now().minusDays(1))
                .build()
        )
        .build();

    final var result = certificateActionInactiveCertificateModel.evaluate(
        Optional.of(certificate), Optional.empty());

    assertFalse(result);
  }
}