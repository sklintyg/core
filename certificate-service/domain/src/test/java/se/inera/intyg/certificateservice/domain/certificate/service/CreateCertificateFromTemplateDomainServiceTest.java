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
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@ExtendWith(MockitoExtension.class)
class CreateCertificateFromTemplateDomainServiceTest {

  private static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
  private static final CertificateModelId CERTIFICATE_MODEL_ID = CertificateModelId.builder()
      .build();
  private static final CertificateModel CERTIFICATE_MODEL = CertificateModel.builder()
      .templateFor(CERTIFICATE_MODEL_ID)
      .build();
  @Mock
  CertificateRepository certificateRepository;
  @Mock
  CertificateModelRepository certificateModelRepository;
  @Mock
  CertificateEventDomainService certificateEventDomainService;
  @InjectMocks
  CreateCertificateFromTemplateDomainService createCertificateFromTemplateDomainService;

  @Test
  void shouldThrowCertificateActionForbiddenIfCertificateDoesNotSupportCreateFromTemplate() {
    final var medicalCertificate = mock(MedicalCertificate.class);

    when(certificateRepository.getById(CERTIFICATE_ID)).thenReturn(medicalCertificate);
    when(medicalCertificate.allowTo(CertificateActionType.CREATE_FROM_TEMPLATE,
        Optional.of(ACTION_EVALUATION))).thenReturn(false);

    assertThrows(CertificateActionForbidden.class,
        () -> createCertificateFromTemplateDomainService.create(CERTIFICATE_ID, ACTION_EVALUATION));
  }

  @Test
  void shouldThrowIfCertificateDoesNotContainTemplate() {
    final var medicalCertificate = mock(MedicalCertificate.class);

    when(certificateRepository.getById(CERTIFICATE_ID)).thenReturn(medicalCertificate);
    when(medicalCertificate.allowTo(CertificateActionType.CREATE_FROM_TEMPLATE,
        Optional.of(ACTION_EVALUATION))).thenReturn(true);
    when(medicalCertificate.certificateModel()).thenReturn(CertificateModel.builder().build());

    final var illegalStateException = assertThrows(IllegalStateException.class,
        () -> createCertificateFromTemplateDomainService.create(CERTIFICATE_ID, ACTION_EVALUATION));

    assertEquals(
        "Certificate '%s' is missing required field template for".formatted(CERTIFICATE_ID.id()),
        illegalStateException.getMessage());
  }

  @Test
  void shouldCreateCertificateFromTemplate() {
    final var medicalCertificate = mock(MedicalCertificate.class);

    when(certificateRepository.getById(CERTIFICATE_ID)).thenReturn(medicalCertificate);
    when(medicalCertificate.allowTo(CertificateActionType.CREATE_FROM_TEMPLATE,
        Optional.of(ACTION_EVALUATION))).thenReturn(true);
    when(medicalCertificate.certificateModel()).thenReturn(CERTIFICATE_MODEL);
    when(certificateModelRepository.getById(CERTIFICATE_MODEL_ID)).thenReturn(CERTIFICATE_MODEL);

    createCertificateFromTemplateDomainService.create(CERTIFICATE_ID, ACTION_EVALUATION);

    verify(medicalCertificate).createFromTemplate(ACTION_EVALUATION, CERTIFICATE_MODEL);
  }

  @Test
  void shouldPersistCreatedCertificateFromTemplate() {
    final var expectedCertificate = MedicalCertificate.builder().build();
    final var medicalCertificate = mock(MedicalCertificate.class);

    when(certificateRepository.getById(CERTIFICATE_ID)).thenReturn(medicalCertificate);
    when(medicalCertificate.allowTo(CertificateActionType.CREATE_FROM_TEMPLATE,
        Optional.of(ACTION_EVALUATION))).thenReturn(true);
    when(medicalCertificate.certificateModel()).thenReturn(CERTIFICATE_MODEL);
    when(certificateModelRepository.getById(CERTIFICATE_MODEL_ID)).thenReturn(CERTIFICATE_MODEL);
    when(medicalCertificate.createFromTemplate(ACTION_EVALUATION, CERTIFICATE_MODEL)).thenReturn(
        expectedCertificate);

    createCertificateFromTemplateDomainService.create(CERTIFICATE_ID, ACTION_EVALUATION);

    verify(certificateRepository).save(expectedCertificate);
  }

  @Test
  void shouldReturnCreatedCertificateFromTemplate() {
    final var expectedCertificate = MedicalCertificate.builder().build();
    final var medicalCertificate = mock(MedicalCertificate.class);

    when(certificateRepository.getById(CERTIFICATE_ID)).thenReturn(medicalCertificate);
    when(medicalCertificate.allowTo(CertificateActionType.CREATE_FROM_TEMPLATE,
        Optional.of(ACTION_EVALUATION))).thenReturn(true);
    when(medicalCertificate.certificateModel()).thenReturn(CERTIFICATE_MODEL);
    when(certificateModelRepository.getById(CERTIFICATE_MODEL_ID)).thenReturn(CERTIFICATE_MODEL);
    when(medicalCertificate.createFromTemplate(ACTION_EVALUATION, CERTIFICATE_MODEL)).thenReturn(
        expectedCertificate);
    when(certificateRepository.save(expectedCertificate)).thenReturn(expectedCertificate);

    final var actualCertificate = createCertificateFromTemplateDomainService.create(CERTIFICATE_ID,
        ACTION_EVALUATION);

    assertEquals(expectedCertificate, actualCertificate);
  }

  @Test
  void shouldPublishEventCreateCertificateFromTemplate() {
    final var certificateEventArgumentCaptor = ArgumentCaptor.forClass(CertificateEvent.class);
    final var expectedCertificate = MedicalCertificate.builder().build();
    final var medicalCertificate = mock(MedicalCertificate.class);

    when(certificateRepository.getById(CERTIFICATE_ID)).thenReturn(medicalCertificate);
    when(medicalCertificate.allowTo(CertificateActionType.CREATE_FROM_TEMPLATE,
        Optional.of(ACTION_EVALUATION))).thenReturn(true);
    when(medicalCertificate.certificateModel()).thenReturn(CERTIFICATE_MODEL);
    when(certificateModelRepository.getById(CERTIFICATE_MODEL_ID)).thenReturn(CERTIFICATE_MODEL);
    when(medicalCertificate.createFromTemplate(ACTION_EVALUATION, CERTIFICATE_MODEL)).thenReturn(
        expectedCertificate);
    when(certificateRepository.save(expectedCertificate)).thenReturn(expectedCertificate);

    createCertificateFromTemplateDomainService.create(CERTIFICATE_ID,
        ACTION_EVALUATION);

    verify(certificateEventDomainService).publish(certificateEventArgumentCaptor.capture());

    assertEquals(expectedCertificate, certificateEventArgumentCaptor.getValue().certificate());
    assertEquals(CertificateEventType.CREATE_CERTIFICATE_FROM_TEMPLATE,
        certificateEventArgumentCaptor.getValue().type());
  }
}