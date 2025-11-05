package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_VARDCENTRAL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.EXTERNAL_REFERENCE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.XML;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_HUDMOTTAGNINGEN;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.configuration.unitaccess.dto.CertificateAccessConfiguration;
import se.inera.intyg.certificateservice.domain.configuration.unitaccess.dto.CertificateAccessUnitConfiguration;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@ExtendWith(MockitoExtension.class)
class CreateCertificateDomainServiceTest {

  @Mock
  private PrefillProcessor prefillProcessor;
  @Mock
  private CertificateActionConfigurationRepository certificateActionConfigurationRepository;
  @Mock
  private CertificateModelRepository certificateModelRepository;
  @Mock
  private CertificateRepository certificateRepository;
  @Mock
  private CertificateEventDomainService certificateEventDomainService;
  @InjectMocks
  private CreateCertificateDomainService createCertificateDomainService;

  private static final String TYPE = "type";
  private static final CertificateModelId CERTIFICATE_MODEL_ID = CertificateModelId.builder()
      .type(new CertificateType(TYPE))
      .version(new CertificateVersion("version"))
      .build();
  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder()
      .subUnit(ALFA_HUDMOTTAGNINGEN)
      .careUnit(ALFA_VARDCENTRAL)
      .careProvider(ALFA_REGIONEN)
      .build();
  private Certificate certificate = mock(MedicalCertificate.class);
  private CertificateModel certificateModel = mock(CertificateModel.class);

  @Nested
  class SuccessfulScenario {

    @BeforeEach
    void setUp() {
      doReturn(CERTIFICATE_MODEL_ID).when(certificateModel).id();
      doReturn(Collections.emptyList()).when(certificateActionConfigurationRepository)
          .findAccessConfiguration(CERTIFICATE_MODEL_ID.type());
      doReturn(certificateModel).when(certificateModelRepository)
          .getActiveById(CERTIFICATE_MODEL_ID);
      doReturn(certificate).when(certificateRepository).create(certificateModel);
      doReturn(true).when(certificateModel)
          .allowTo(CertificateActionType.CREATE, Optional.of(ACTION_EVALUATION));
      doReturn(certificate).when(certificateRepository).save(certificate);
    }

    @Test
    void shallReturnCertificate() {
      final var actualCertificate = createCertificateDomainService.create(CERTIFICATE_MODEL_ID,
          ACTION_EVALUATION, null, null);
      assertEquals(certificate, actualCertificate);
    }

    @Test
    void shallPublishCreateCertificateEvent() {
      createCertificateDomainService.create(CERTIFICATE_MODEL_ID, ACTION_EVALUATION, null, null);

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
      createCertificateDomainService.create(CERTIFICATE_MODEL_ID, ACTION_EVALUATION, null, null);
      verify(certificateModel).allowTo(CertificateActionType.CREATE,
          Optional.of(ACTION_EVALUATION));
    }

    @Test
    void shallUpdateCertificateMetadata() {
      createCertificateDomainService.create(CERTIFICATE_MODEL_ID, ACTION_EVALUATION, null, null);
      verify(certificate).updateMetadata(ACTION_EVALUATION);
    }

    @Test
    void shallSetExternalReference() {
      createCertificateDomainService.create(CERTIFICATE_MODEL_ID, ACTION_EVALUATION,
          EXTERNAL_REFERENCE, null);
      verify(certificate).externalReference(EXTERNAL_REFERENCE);
    }

    @Test
    void shallPrefill() {
      createCertificateDomainService.create(CERTIFICATE_MODEL_ID, ACTION_EVALUATION,
          EXTERNAL_REFERENCE, XML);
      verify(certificate).prefill(XML, prefillProcessor);
    }

    @Test
    void shallSaveNewCertificate() {
      createCertificateDomainService.create(CERTIFICATE_MODEL_ID, ACTION_EVALUATION, null, null);
      verify(certificateRepository).save(certificate);
    }
  }

  @Nested
  class FailedScenario {

    @Nested
    class AllowToTests {

      @BeforeEach
      void setUp() {
        doReturn(certificateModel).when(certificateModelRepository)
            .getActiveById(CERTIFICATE_MODEL_ID);
        doReturn(false).when(certificateModel)
            .allowTo(CertificateActionType.CREATE, Optional.of(ACTION_EVALUATION));
      }

      @Test
      void shallThrowExceptionIfNotAllowedToCreate() {
        assertThrows(CertificateActionForbidden.class,
            () -> createCertificateDomainService.create(CERTIFICATE_MODEL_ID, ACTION_EVALUATION,
                null, null)
        );
      }

      @Test
      void shallIncludeReasonNotAllowedToException() {
        final var expectedReason = List.of("expectedReason");
        doReturn(expectedReason).when(certificateModel)
            .reasonNotAllowed(CertificateActionType.CREATE, Optional.of(ACTION_EVALUATION));
        final var certificateActionForbidden = assertThrows(CertificateActionForbidden.class,
            () -> createCertificateDomainService.create(CERTIFICATE_MODEL_ID, ACTION_EVALUATION,
                null, null)
        );
        assertEquals(expectedReason, certificateActionForbidden.reason());
      }
    }

    @Nested
    class UnitAccessTests {

      @BeforeEach
      void setUp() {
        final var certificateAccessConfigurations = List.of(
            CertificateAccessConfiguration.builder()
                .certificateType(TYPE)
                .configuration(
                    List.of(
                        CertificateAccessUnitConfiguration.builder()
                            .type("block")
                            .fromDateTime(LocalDateTime.now().minusDays(1))
                            .careUnit(List.of(ALFA_VARDCENTRAL.hsaId().id()))
                            .build()
                    )
                )
                .build()
        );
        doReturn(CERTIFICATE_MODEL_ID).when(certificateModel).id();
        doReturn(certificateAccessConfigurations).when(certificateActionConfigurationRepository)
            .findAccessConfiguration(CERTIFICATE_MODEL_ID.type());
        doReturn(certificateModel).when(certificateModelRepository)
            .getActiveById(CERTIFICATE_MODEL_ID);
        doReturn(true).when(certificateModel)
            .allowTo(CertificateActionType.CREATE, Optional.of(ACTION_EVALUATION));
      }

      @Test
      void shallThrowExceptionIfUnitNotAllowedToCreate() {
        assertThrows(CertificateActionForbidden.class,
            () -> createCertificateDomainService.create(CERTIFICATE_MODEL_ID, ACTION_EVALUATION,
                null, null)
        );
      }
    }
  }
}