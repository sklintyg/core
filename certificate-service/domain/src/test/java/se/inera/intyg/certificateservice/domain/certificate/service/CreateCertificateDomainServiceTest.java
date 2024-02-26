package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
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
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@ExtendWith(MockitoExtension.class)
class CreateCertificateDomainServiceTest {

  @Mock
  private CertificateModelRepository certificateModelRepository;
  @Mock
  private CertificateRepository certificateRepository;
  @Mock
  private CertificateEventDomainService certificateEventDomainService;
  @InjectMocks
  private CreateCertificateDomainService createCertificateDomainService;

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
      final var actualCertificate = createCertificateDomainService.create(CERTIFICATE_MODEL_ID,
          ACTION_EVALUATION);
      assertEquals(certificate, actualCertificate);
    }

    @Test
    void shallPublishCreateCertificateEvent() {
      createCertificateDomainService.create(CERTIFICATE_MODEL_ID, ACTION_EVALUATION);

      final var certificateEventCaptor = ArgumentCaptor.forClass(CertificateEvent.class);
      verify(certificateEventDomainService).publish(certificateEventCaptor.capture());

      assertAll(
          () -> assertEquals(CertificateEventType.CREATED,
              certificateEventCaptor.getValue().type()),
          () -> assertEquals(certificate, certificateEventCaptor.getValue().certificate()),
          () -> assertEquals(ACTION_EVALUATION,
              certificateEventCaptor.getValue().actionEvaluation()),
          () -> assertTrue(certificateEventCaptor.getValue().duration() >= 0)
      );
    }

    @Test
    void shallValidateIfAllowedToCreateCertificate() {
      createCertificateDomainService.create(CERTIFICATE_MODEL_ID, ACTION_EVALUATION);
      verify(certificateModel).allowTo(CertificateActionType.CREATE, ACTION_EVALUATION);
    }

    @Test
    void shallUpdateCertificateMetadata() {
      createCertificateDomainService.create(CERTIFICATE_MODEL_ID, ACTION_EVALUATION);
      verify(certificate).updateMetadata(ACTION_EVALUATION);
    }

    @Test
    void shallSaveNewCertificate() {
      createCertificateDomainService.create(CERTIFICATE_MODEL_ID, ACTION_EVALUATION);
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
      assertThrows(CertificateActionForbidden.class,
          () -> createCertificateDomainService.create(CERTIFICATE_MODEL_ID, ACTION_EVALUATION)
      );
    }
  }
}