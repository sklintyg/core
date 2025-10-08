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
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@ExtendWith(MockitoExtension.class)
class CreateCertificateFromCertificateDomainServiceTest {

  private static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
  private static final CertificateModelId CERTIFICATE_MODEL_ID = CertificateModelId.builder()
      .build();
  private static final CertificateModel CERTIFICATE_MODEL = CertificateModel.builder()
      .templateFor(CERTIFICATE_MODEL_ID)
      .build();
  private static final MedicalCertificate CREATED_CERTIFICATE = MedicalCertificate.builder()
      .revision(new Revision(0)).build();
  @Mock
  CertificateRepository certificateRepository;
  @Mock
  CertificateModelRepository certificateModelRepository;
  @Mock
  CertificateEventDomainService certificateEventDomainService;
  @InjectMocks
  CreateCertificateFromCertificateDomainService createCertificateFromCertificateDomainService;

  @Test
  void shouldThrowCertificateActionForbiddenIfCertificateDoesNotSupportCreateFromTemplate() {
    final var medicalCertificate = mock(MedicalCertificate.class);

    when(certificateRepository.getById(CERTIFICATE_ID)).thenReturn(medicalCertificate);
    when(medicalCertificate.allowTo(CertificateActionType.CREATE_FROM_CERTIFICATE,
        Optional.of(ACTION_EVALUATION))).thenReturn(false);

    assertThrows(CertificateActionForbidden.class,
        () -> createCertificateFromCertificateDomainService.create(CERTIFICATE_ID,
            ACTION_EVALUATION));
  }

  @Test
  void shouldThrowIfCertificateDoesNotContainTemplate() {
    final var medicalCertificate = mock(MedicalCertificate.class);

    when(certificateRepository.getById(CERTIFICATE_ID)).thenReturn(medicalCertificate);
    when(medicalCertificate.allowTo(CertificateActionType.CREATE_FROM_CERTIFICATE,
        Optional.of(ACTION_EVALUATION))).thenReturn(true);
    when(medicalCertificate.certificateModel()).thenReturn(CertificateModel.builder().build());

    final var illegalStateException = assertThrows(IllegalStateException.class,
        () -> createCertificateFromCertificateDomainService.create(CERTIFICATE_ID,
            ACTION_EVALUATION));

    assertEquals(
        "Certificate '%s' is missing required field template for".formatted(CERTIFICATE_ID.id()),
        illegalStateException.getMessage());
  }

  @Test
  void shouldCreateCertificateFromProvidedModel() {
    final var medicalCertificate = mock(MedicalCertificate.class);

    when(certificateRepository.getById(CERTIFICATE_ID)).thenReturn(medicalCertificate);
    when(medicalCertificate.allowTo(CertificateActionType.CREATE_FROM_CERTIFICATE,
        Optional.of(ACTION_EVALUATION))).thenReturn(true);
    when(medicalCertificate.certificateModel()).thenReturn(CERTIFICATE_MODEL);
    when(certificateModelRepository.getById(CERTIFICATE_MODEL_ID)).thenReturn(CERTIFICATE_MODEL);
    when(certificateRepository.create(CERTIFICATE_MODEL)).thenReturn(medicalCertificate);

    createCertificateFromCertificateDomainService.create(CERTIFICATE_ID, ACTION_EVALUATION);

    verify(certificateRepository).create(CERTIFICATE_MODEL);
  }

  @Test
  void shouldFillCertificateWithCertificate() {
    final var medicalCertificate = mock(MedicalCertificate.class);
    final var medicalCertificateDraft = mock(MedicalCertificate.class);

    when(certificateRepository.getById(CERTIFICATE_ID)).thenReturn(medicalCertificate);
    when(medicalCertificate.allowTo(CertificateActionType.CREATE_FROM_CERTIFICATE,
        Optional.of(ACTION_EVALUATION))).thenReturn(true);
    when(medicalCertificate.certificateModel()).thenReturn(CERTIFICATE_MODEL);
    when(certificateModelRepository.getById(CERTIFICATE_MODEL_ID)).thenReturn(CERTIFICATE_MODEL);
    when(certificateRepository.create(CERTIFICATE_MODEL)).thenReturn(medicalCertificateDraft);

    createCertificateFromCertificateDomainService.create(CERTIFICATE_ID, ACTION_EVALUATION);

    verify(medicalCertificateDraft).fillFromCertificate(medicalCertificate);
  }

  @Test
  void shouldPersistCreatedCertificate() {
    final var medicalCertificate = mock(MedicalCertificate.class);

    when(certificateRepository.getById(CERTIFICATE_ID)).thenReturn(medicalCertificate);
    when(medicalCertificate.allowTo(CertificateActionType.CREATE_FROM_CERTIFICATE,
        Optional.of(ACTION_EVALUATION))).thenReturn(true);
    when(medicalCertificate.certificateModel()).thenReturn(CERTIFICATE_MODEL);
    when(certificateModelRepository.getById(CERTIFICATE_MODEL_ID)).thenReturn(CERTIFICATE_MODEL);
    when(certificateRepository.create(CERTIFICATE_MODEL)).thenReturn(CREATED_CERTIFICATE);

    createCertificateFromCertificateDomainService.create(CERTIFICATE_ID, ACTION_EVALUATION);

    verify(certificateRepository).save(CREATED_CERTIFICATE);
  }

  @Test
  void shouldReturnCreatedCertificate() {
    final var medicalCertificate = mock(MedicalCertificate.class);

    when(certificateRepository.getById(CERTIFICATE_ID)).thenReturn(medicalCertificate);
    when(medicalCertificate.allowTo(CertificateActionType.CREATE_FROM_CERTIFICATE,
        Optional.of(ACTION_EVALUATION))).thenReturn(true);
    when(medicalCertificate.certificateModel()).thenReturn(CERTIFICATE_MODEL);
    when(certificateModelRepository.getById(CERTIFICATE_MODEL_ID)).thenReturn(CERTIFICATE_MODEL);
    when(certificateRepository.create(CERTIFICATE_MODEL)).thenReturn(CREATED_CERTIFICATE);
    when(certificateRepository.save(CREATED_CERTIFICATE)).thenReturn(CREATED_CERTIFICATE);

    final var actualCertificate = createCertificateFromCertificateDomainService.create(
        CERTIFICATE_ID,
        ACTION_EVALUATION);

    assertEquals(CREATED_CERTIFICATE, actualCertificate);
  }

  @Test
  void shouldPublishEventCreateCertificateFromTemplate() {
    final var certificateEventArgumentCaptor = ArgumentCaptor.forClass(CertificateEvent.class);
    final var medicalCertificate = mock(MedicalCertificate.class);

    when(certificateRepository.getById(CERTIFICATE_ID)).thenReturn(medicalCertificate);
    when(medicalCertificate.allowTo(CertificateActionType.CREATE_FROM_CERTIFICATE,
        Optional.of(ACTION_EVALUATION))).thenReturn(true);
    when(medicalCertificate.certificateModel()).thenReturn(CERTIFICATE_MODEL);
    when(certificateModelRepository.getById(CERTIFICATE_MODEL_ID)).thenReturn(CERTIFICATE_MODEL);
    when(certificateRepository.create(CERTIFICATE_MODEL)).thenReturn(CREATED_CERTIFICATE);
    when(certificateRepository.save(CREATED_CERTIFICATE)).thenReturn(CREATED_CERTIFICATE);

    createCertificateFromCertificateDomainService.create(CERTIFICATE_ID,
        ACTION_EVALUATION);

    verify(certificateEventDomainService).publish(certificateEventArgumentCaptor.capture());

    assertEquals(CREATED_CERTIFICATE, certificateEventArgumentCaptor.getValue().certificate());
    assertEquals(CertificateEventType.CREATE_CERTIFICATE_FROM_CERTIFICATE,
        certificateEventArgumentCaptor.getValue().type());
  }
}