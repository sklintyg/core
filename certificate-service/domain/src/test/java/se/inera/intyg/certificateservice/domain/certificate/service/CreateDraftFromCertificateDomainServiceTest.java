package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@ExtendWith(MockitoExtension.class)
class CreateDraftFromCertificateDomainServiceTest {

  private static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
  private static final CertificateModelId CERTIFICATE_MODEL_ID = CertificateModelId.builder()
      .build();
  private static final CertificateModel CERTIFICATE_MODEL = CertificateModel.builder()
      .ableToCreateDraftForModel(CERTIFICATE_MODEL_ID)
      .build();
  private static final MedicalCertificate CREATED_CERTIFICATE = MedicalCertificate.builder()
      .revision(new Revision(0))
      .build();
  private static final ExternalReference EXTERNAL_REFERENCE = new ExternalReference(
      "externalReference");
  @Mock
  CertificateRepository certificateRepository;
  @Mock
  CertificateModelRepository certificateModelRepository;
  @Mock
  CertificateEventDomainService certificateEventDomainService;
  @InjectMocks
  CreateDraftFromCertificateDomainService createDraftFromCertificateDomainService;

  @Test
  void shouldThrowCertificateActionForbiddenIfCertificateDoesNotSupportCreateDraftFromCertificate() {
    final var medicalCertificate = mock(MedicalCertificate.class);

    when(certificateRepository.getById(CERTIFICATE_ID)).thenReturn(medicalCertificate);
    when(medicalCertificate.allowTo(CertificateActionType.CREATE_DRAFT_FROM_CERTIFICATE,
        Optional.of(ACTION_EVALUATION))).thenReturn(false);

    assertThrows(CertificateActionForbidden.class,
        () -> createDraftFromCertificateDomainService.create(
            CERTIFICATE_ID,
            ACTION_EVALUATION,
            EXTERNAL_REFERENCE
        )
    );
  }

  @Test
  void shouldThrowIfCertificateDoesNotContainAbleToCreateDraftForModel() {
    final var medicalCertificate = mock(MedicalCertificate.class);

    when(certificateRepository.getById(CERTIFICATE_ID)).thenReturn(medicalCertificate);
    when(medicalCertificate.allowTo(CertificateActionType.CREATE_DRAFT_FROM_CERTIFICATE,
        Optional.of(ACTION_EVALUATION))).thenReturn(true);
    when(medicalCertificate.certificateModel()).thenReturn(CertificateModel.builder().build());

    final var illegalStateException = assertThrows(IllegalStateException.class,
        () -> createDraftFromCertificateDomainService.create(
            CERTIFICATE_ID,
            ACTION_EVALUATION,
            EXTERNAL_REFERENCE
        )
    );

    assertEquals(
        "Certificate '%s' is not able to create draft for model".formatted(CERTIFICATE_ID.id()),
        illegalStateException.getMessage());
  }

  @Test
  void shouldCreateCertificateFromProvidedModel() {
    final var medicalCertificate = mock(MedicalCertificate.class);

    when(certificateRepository.getById(CERTIFICATE_ID)).thenReturn(medicalCertificate);
    when(medicalCertificate.allowTo(CertificateActionType.CREATE_DRAFT_FROM_CERTIFICATE,
        Optional.of(ACTION_EVALUATION))).thenReturn(true);
    when(medicalCertificate.certificateModel()).thenReturn(CERTIFICATE_MODEL);
    when(certificateModelRepository.getById(CERTIFICATE_MODEL_ID)).thenReturn(CERTIFICATE_MODEL);
    when(certificateRepository.create(CERTIFICATE_MODEL)).thenReturn(medicalCertificate);

    createDraftFromCertificateDomainService.create(
        CERTIFICATE_ID,
        ACTION_EVALUATION,
        EXTERNAL_REFERENCE
    );

    verify(certificateRepository).create(CERTIFICATE_MODEL);
  }

  @Test
  void shouldFillCertificateWithCertificate() {
    final var medicalCertificate = mock(MedicalCertificate.class);
    final var medicalCertificateDraft = mock(MedicalCertificate.class);

    when(certificateRepository.getById(CERTIFICATE_ID)).thenReturn(medicalCertificate);
    when(medicalCertificate.allowTo(CertificateActionType.CREATE_DRAFT_FROM_CERTIFICATE,
        Optional.of(ACTION_EVALUATION))).thenReturn(true);
    when(medicalCertificate.certificateModel()).thenReturn(CERTIFICATE_MODEL);
    when(certificateModelRepository.getById(CERTIFICATE_MODEL_ID)).thenReturn(CERTIFICATE_MODEL);
    when(certificateRepository.create(CERTIFICATE_MODEL)).thenReturn(medicalCertificateDraft);

    createDraftFromCertificateDomainService.create(
        CERTIFICATE_ID,
        ACTION_EVALUATION,
        EXTERNAL_REFERENCE
    );

    verify(medicalCertificateDraft).fillFromCertificate(medicalCertificate);
  }

  @Test
  void shouldUpdateMetaData() {
    final var medicalCertificate = mock(MedicalCertificate.class);
    final var medicalCertificateDraft = mock(MedicalCertificate.class);

    when(certificateRepository.getById(CERTIFICATE_ID)).thenReturn(medicalCertificate);
    when(medicalCertificate.allowTo(CertificateActionType.CREATE_DRAFT_FROM_CERTIFICATE,
        Optional.of(ACTION_EVALUATION))).thenReturn(true);
    when(medicalCertificate.certificateModel()).thenReturn(CERTIFICATE_MODEL);
    when(certificateModelRepository.getById(CERTIFICATE_MODEL_ID)).thenReturn(CERTIFICATE_MODEL);
    when(certificateRepository.create(CERTIFICATE_MODEL)).thenReturn(medicalCertificateDraft);

    createDraftFromCertificateDomainService.create(
        CERTIFICATE_ID,
        ACTION_EVALUATION,
        EXTERNAL_REFERENCE
    );

    verify(medicalCertificateDraft).updateMetadata(ACTION_EVALUATION);
  }

  @Test
  void shouldSetExternalReference() {
    final var medicalCertificate = mock(MedicalCertificate.class);
    final var medicalCertificateDraft = mock(MedicalCertificate.class);

    when(certificateRepository.getById(CERTIFICATE_ID)).thenReturn(medicalCertificate);
    when(medicalCertificate.allowTo(CertificateActionType.CREATE_DRAFT_FROM_CERTIFICATE,
        Optional.of(ACTION_EVALUATION))).thenReturn(true);
    when(medicalCertificate.certificateModel()).thenReturn(CERTIFICATE_MODEL);
    when(certificateModelRepository.getById(CERTIFICATE_MODEL_ID)).thenReturn(CERTIFICATE_MODEL);
    when(certificateRepository.create(CERTIFICATE_MODEL)).thenReturn(medicalCertificateDraft);

    createDraftFromCertificateDomainService.create(
        CERTIFICATE_ID,
        ACTION_EVALUATION,
        EXTERNAL_REFERENCE
    );

    verify(medicalCertificateDraft).externalReference(EXTERNAL_REFERENCE);
  }

  @Test
  void shouldPersistCreatedCertificate() {
    final var medicalCertificate = mock(MedicalCertificate.class);
    final var medicalCertificateDraft = mock(MedicalCertificate.class);

    when(certificateRepository.getById(CERTIFICATE_ID)).thenReturn(medicalCertificate);
    when(medicalCertificate.allowTo(CertificateActionType.CREATE_DRAFT_FROM_CERTIFICATE,
        Optional.of(ACTION_EVALUATION))).thenReturn(true);
    when(medicalCertificate.certificateModel()).thenReturn(CERTIFICATE_MODEL);
    when(certificateModelRepository.getById(CERTIFICATE_MODEL_ID)).thenReturn(CERTIFICATE_MODEL);
    when(certificateRepository.create(CERTIFICATE_MODEL)).thenReturn(medicalCertificateDraft);

    createDraftFromCertificateDomainService.create(
        CERTIFICATE_ID,
        ACTION_EVALUATION,
        EXTERNAL_REFERENCE
    );

    verify(certificateRepository).save(medicalCertificateDraft);
  }

  @Test
  void shouldReturnCreatedCertificate() {
    final var medicalCertificate = mock(MedicalCertificate.class);
    final var medicalCertificateDraft = mock(MedicalCertificate.class);

    when(certificateRepository.getById(CERTIFICATE_ID)).thenReturn(medicalCertificate);
    when(medicalCertificate.allowTo(CertificateActionType.CREATE_DRAFT_FROM_CERTIFICATE,
        Optional.of(ACTION_EVALUATION))).thenReturn(true);
    when(medicalCertificate.certificateModel()).thenReturn(CERTIFICATE_MODEL);
    when(certificateModelRepository.getById(CERTIFICATE_MODEL_ID)).thenReturn(CERTIFICATE_MODEL);
    when(certificateRepository.create(CERTIFICATE_MODEL)).thenReturn(medicalCertificateDraft);
    when(certificateRepository.save(medicalCertificateDraft)).thenReturn(CREATED_CERTIFICATE);

    final var actualCertificate = createDraftFromCertificateDomainService.create(
        CERTIFICATE_ID,
        ACTION_EVALUATION,
        EXTERNAL_REFERENCE
    );

    assertEquals(CREATED_CERTIFICATE, actualCertificate);
  }

  @Test
  void shouldPublishEventCreateDraftFromCertificate() {
    final var certificateEventArgumentCaptor = ArgumentCaptor.forClass(CertificateEvent.class);
    final var medicalCertificate = mock(MedicalCertificate.class);

    when(certificateRepository.getById(CERTIFICATE_ID)).thenReturn(medicalCertificate);
    when(medicalCertificate.allowTo(CertificateActionType.CREATE_DRAFT_FROM_CERTIFICATE,
        Optional.of(ACTION_EVALUATION))).thenReturn(true);
    when(medicalCertificate.certificateModel()).thenReturn(CERTIFICATE_MODEL);
    when(certificateModelRepository.getById(CERTIFICATE_MODEL_ID)).thenReturn(CERTIFICATE_MODEL);
    when(certificateRepository.create(CERTIFICATE_MODEL)).thenReturn(CREATED_CERTIFICATE);
    when(certificateRepository.save(CREATED_CERTIFICATE)).thenReturn(CREATED_CERTIFICATE);

    createDraftFromCertificateDomainService.create(
        CERTIFICATE_ID,
        ACTION_EVALUATION,
        EXTERNAL_REFERENCE
    );

    verify(certificateEventDomainService).publish(certificateEventArgumentCaptor.capture());

    assertEquals(CREATED_CERTIFICATE, certificateEventArgumentCaptor.getValue().certificate());
    assertEquals(CertificateEventType.CREATE_DRAFT_FROM_CERTIFICATE,
        certificateEventArgumentCaptor.getValue().type());
  }
}