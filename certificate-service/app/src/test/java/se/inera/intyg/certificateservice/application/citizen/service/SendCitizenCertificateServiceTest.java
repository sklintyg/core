package se.inera.intyg.certificateservice.application.citizen.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.citizen.dto.SendCitizenCertificateRequest;
import se.inera.intyg.certificateservice.application.citizen.dto.SendCitizenCertificateResponse;
import se.inera.intyg.certificateservice.application.citizen.validation.CitizenCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.citizen.service.SendCitizenCertificateDomainService;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.common.model.PersonIdType;

@ExtendWith(MockitoExtension.class)
class SendCitizenCertificateServiceTest {

  private static final PersonIdDTO PERSON_ID_DTO = PersonIdDTO.builder()
      .id("191212121212")
      .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
      .build();
  private static final String CERTIFICATE_ID = "certificateId";
  private static final SendCitizenCertificateRequest SEND_CITIZEN_CERTIFICATE_REQUEST = SendCitizenCertificateRequest.builder()
      .personId(PERSON_ID_DTO)
      .build();
  @Mock
  SendCitizenCertificateDomainService sendCitizenCertificateDomainService;
  @Mock
  CitizenCertificateRequestValidator citizenCertificateRequestValidator;
  @Mock
  CertificateConverter certificateConverter;
  @InjectMocks
  SendCitizenCertificateService sendCitizenCertificateService;

  @Test
  void shallThrowIfRequestIsInvalid() {
    doThrow(IllegalArgumentException.class).when(citizenCertificateRequestValidator).validate(
        CERTIFICATE_ID, PERSON_ID_DTO
    );

    assertThrows(IllegalArgumentException.class,
        () -> sendCitizenCertificateService.send(SEND_CITIZEN_CERTIFICATE_REQUEST, CERTIFICATE_ID));
  }

  @Test
  void shallReturnSendCitizenCertificateResponse() {
    final var expectedCertificate = CertificateDTO.builder().build();
    final var expectedResult = SendCitizenCertificateResponse.builder()
        .citizenCertificate(expectedCertificate)
        .build();
    final var certificate = MedicalCertificate.builder().build();

    doReturn(certificate).when(sendCitizenCertificateDomainService)
        .send(
            new CertificateId(CERTIFICATE_ID),
            PersonId.builder()
                .id(PERSON_ID_DTO.getId())
                .type(PersonIdType.PERSONAL_IDENTITY_NUMBER)
                .build()
        );
    doReturn(expectedCertificate).when(certificateConverter)
        .convertForCitizen(certificate, Collections.emptyList());

    final var result = sendCitizenCertificateService.send(SEND_CITIZEN_CERTIFICATE_REQUEST,
        CERTIFICATE_ID);

    assertEquals(expectedResult, result);
  }
}