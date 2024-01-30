package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;

@ExtendWith(MockitoExtension.class)
class CreateCertificateServiceTest {

  @Mock
  private CertificateModelRepository certificateModelRepository;
  @Mock
  private CertificateRepository certificateRepository;
  @InjectMocks
  private CreateCertificateService createCertificateService;

  private static final CertificateModelId CERTIFICATE_MODEL_ID = CertificateModelId.builder()
      .type(new CertificateType("type"))
      .version(new CertificateVersion("version"))
      .build();
  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder().build();
  private Certificate certificate = mock(Certificate.class);
  private CertificateModel certificateModel = mock(CertificateModel.class);

  @Nested
  class SuccessfulScenario {

    @BeforeEach
    void setUp() {
      doReturn(certificateModel).when(certificateModelRepository).getById(CERTIFICATE_MODEL_ID);
      doReturn(certificate).when(certificateRepository).create(certificateModel);
      doReturn(true).when(certificateModel)
          .allowTo(CertificateActionType.CREATE, ACTION_EVALUATION);
      doReturn(certificate).when(certificateRepository).save(certificate);
    }

    @Test
    void shallReturnCertificate() {
      final var actualCertificate = createCertificateService.create(CERTIFICATE_MODEL_ID,
          ACTION_EVALUATION);
      assertEquals(certificate, actualCertificate);
    }

    @Test
    void shallValidateIfAllowedToCreateCertificate() {
      createCertificateService.create(CERTIFICATE_MODEL_ID, ACTION_EVALUATION);
      verify(certificateModel).allowTo(CertificateActionType.CREATE, ACTION_EVALUATION);
    }

    @Test
    void shallUpdateCertificateMetadata() {
      createCertificateService.create(CERTIFICATE_MODEL_ID, ACTION_EVALUATION);
      verify(certificate).updateMetadata(ACTION_EVALUATION);
    }

    @Test
    void shallSaveNewCertificate() {
      createCertificateService.create(CERTIFICATE_MODEL_ID, ACTION_EVALUATION);
      verify(certificateRepository).save(certificate);
    }
  }

  @Nested
  class FailedScenario {

    @BeforeEach
    void setUp() {
      doReturn(certificateModel).when(certificateModelRepository).getById(CERTIFICATE_MODEL_ID);
      doReturn(false).when(certificateModel)
          .allowTo(CertificateActionType.CREATE, ACTION_EVALUATION);
    }

    @Test
    void shallThrowExceptionIfNotAllowedToCreate() {
      assertThrows(IllegalStateException.class,
          () -> createCertificateService.create(CERTIFICATE_MODEL_ID, ACTION_EVALUATION)
      );
    }
  }
}