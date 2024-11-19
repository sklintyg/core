package se.inera.intyg.certificateservice.domain.message.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.COMPLEMENT_MESSAGE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.complementMessageBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.COMPLEMENT_QUESTION_ID_ONE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.common.exception.CertificateActionForbidden;
import se.inera.intyg.certificateservice.domain.message.repository.MessageRepository;

@ExtendWith(MockitoExtension.class)
class ReceiveComplementMessageDomainServiceTest {

  @Mock
  private CertificateModel certificateModel;

  @Mock
  private Certificate certificate;

  @Mock
  private CertificateRepository certificateRepository;

  @Mock
  private MessageRepository messageRepository;

  @InjectMocks
  private ReceiveComplementMessageDomainService receiveComplementMessageDomainService;

  @BeforeEach
  void setUp() {
    doReturn(certificate).when(certificateRepository).getById(CERTIFICATE_ID);
  }

  @Test
  void shallThrowExceptionIfNotAllowedOnCertificate() {
    doReturn(false).when(certificate)
        .allowTo(CertificateActionType.RECEIVE_COMPLEMENT, Optional.empty());
    doReturn(CERTIFICATE_ID).when(certificate).id();

    assertThrows(CertificateActionForbidden.class,
        () -> receiveComplementMessageDomainService.receive(COMPLEMENT_MESSAGE)
    );
  }

  @Test
  void shallThrowExceptionIfNotSamePatientAsCertificate() {
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.RECEIVE_COMPLEMENT, Optional.empty());
    doReturn(CERTIFICATE_ID).when(certificate).id();
    doReturn(false).when(certificate).isCertificateIssuedOnPatient(ATHENA_REACT_ANDERSSON.id());

    assertThrows(CertificateActionForbidden.class,
        () -> receiveComplementMessageDomainService.receive(COMPLEMENT_MESSAGE)
    );
  }

  @Test
  void shallThrowExceptionIfElementIdFromComplementIsNotPresentInCertificate() {
    doReturn(true).when(certificate)
        .allowTo(CertificateActionType.RECEIVE_COMPLEMENT, Optional.empty());
    doReturn(CERTIFICATE_ID).when(certificate).id();
    doReturn(true).when(certificate).isCertificateIssuedOnPatient(ATHENA_REACT_ANDERSSON.id());
    doReturn(certificateModel).when(certificate).certificateModel();
    doReturn(false).when(certificateModel)
        .elementSpecificationExists(new ElementId(COMPLEMENT_QUESTION_ID_ONE));

    assertThrows(IllegalStateException.class,
        () -> receiveComplementMessageDomainService.receive(COMPLEMENT_MESSAGE)
    );
  }

  @Nested
  class ComplementMessageAllowed {

    @BeforeEach
    void setUp() {
      doReturn(true).when(certificate)
          .allowTo(CertificateActionType.RECEIVE_COMPLEMENT, Optional.empty());
      doReturn(true).when(certificate).isCertificateIssuedOnPatient(ATHENA_REACT_ANDERSSON.id());
      doReturn(certificateModel).when(certificate).certificateModel();
      doReturn(true).when(certificateModel)
          .elementSpecificationExists(new ElementId(COMPLEMENT_QUESTION_ID_ONE));
    }

    @Test
    void shallStoreReceivedMessage() {
      final var expectedMessage = complementMessageBuilder().build();
      receiveComplementMessageDomainService.receive(expectedMessage);

      verify(messageRepository).save(expectedMessage);
    }

    @Test
    void shallReturnSavedMessage() {
      final var expectedMessage = complementMessageBuilder().build();

      doReturn(expectedMessage).when(messageRepository).save(COMPLEMENT_MESSAGE);

      final var actualMessage = receiveComplementMessageDomainService.receive(COMPLEMENT_MESSAGE
      );

      assertEquals(expectedMessage, actualMessage);
    }
  }
}
