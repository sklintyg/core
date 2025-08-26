package se.inera.intyg.certificateservice.domain.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.EXTERNAL_REFERENCE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK7804_CERTIFICATE_MODEL_ID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PlaceholderRequest;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEvent;
import se.inera.intyg.certificateservice.domain.event.model.CertificateEventType;
import se.inera.intyg.certificateservice.domain.event.service.CertificateEventDomainService;

@ExtendWith(MockitoExtension.class)
class RenewExternalCertificateDomainServiceTest {

  @Mock
  private CertificateRepository certificateRepository;
  @Mock
  private CertificateModelRepository certificateModelRepository;
  @Mock
  private CertificateEventDomainService certificateEventDomainService;
  @InjectMocks
  private RenewExternalCertificateDomainService renewExternalCertificateDomainService;

  @Test
  void shouldUpdateMetadataOnCertificate() {
    final var placeholderRequest = PlaceholderRequest.builder()
        .certificateModelId(FK7804_CERTIFICATE_MODEL_ID)
        .build();
    final var certificate = mock(MedicalCertificate.class);
    final var certificateModel = mock(CertificateModel.class);

    doReturn(certificateModel).when(certificateModelRepository)
        .getById(FK7804_CERTIFICATE_MODEL_ID);
    doReturn(certificate).when(certificateRepository)
        .createFromPlaceholder(placeholderRequest, certificateModel);

    renewExternalCertificateDomainService.renew(ACTION_EVALUATION, EXTERNAL_REFERENCE,
        placeholderRequest);

    verify(certificate).updateMetadata(ACTION_EVALUATION);
  }

  @Test
  void shallSetExternalReferenceOnRenewCertificate() {
    final var placeholderRequest = PlaceholderRequest.builder()
        .certificateModelId(FK7804_CERTIFICATE_MODEL_ID)
        .build();
    final var certificate = mock(MedicalCertificate.class);
    final var certificateModel = mock(CertificateModel.class);

    doReturn(certificateModel).when(certificateModelRepository)
        .getById(FK7804_CERTIFICATE_MODEL_ID);
    doReturn(certificate).when(certificateRepository)
        .createFromPlaceholder(placeholderRequest, certificateModel);

    renewExternalCertificateDomainService.renew(ACTION_EVALUATION, EXTERNAL_REFERENCE,
        placeholderRequest);

    verify(certificate).externalReference(EXTERNAL_REFERENCE);
  }

  @Test
  void shallReturnNewCertificate() {
    final var placeholderRequest = PlaceholderRequest.builder()
        .certificateModelId(FK7804_CERTIFICATE_MODEL_ID)
        .build();
    final var certificate = mock(MedicalCertificate.class);
    final var certificateModel = mock(CertificateModel.class);

    doReturn(certificateModel).when(certificateModelRepository)
        .getById(FK7804_CERTIFICATE_MODEL_ID);
    doReturn(certificate).when(certificateRepository)
        .createFromPlaceholder(placeholderRequest, certificateModel);
    doReturn(certificate).when(certificateRepository).save(certificate);

    final var actualCertificate = renewExternalCertificateDomainService.renew(ACTION_EVALUATION,
        EXTERNAL_REFERENCE, placeholderRequest);

    assertEquals(certificate, actualCertificate);
  }

  @Test
  void shouldPublishDomainEvent() {
    final var captor = ArgumentCaptor.forClass(CertificateEvent.class);
    final var placeholderRequest = PlaceholderRequest.builder()
        .certificateModelId(FK7804_CERTIFICATE_MODEL_ID)
        .build();
    final var certificate = mock(MedicalCertificate.class);
    final var certificateModel = mock(CertificateModel.class);

    doReturn(certificateModel).when(certificateModelRepository)
        .getById(FK7804_CERTIFICATE_MODEL_ID);
    doReturn(certificate).when(certificateRepository)
        .createFromPlaceholder(placeholderRequest, certificateModel);

    renewExternalCertificateDomainService.renew(ACTION_EVALUATION, EXTERNAL_REFERENCE,
        placeholderRequest);

    verify(certificateEventDomainService).publish(
        captor.capture()
    );

    assertEquals(CertificateEventType.RENEW, captor.getValue().type());
  }
}