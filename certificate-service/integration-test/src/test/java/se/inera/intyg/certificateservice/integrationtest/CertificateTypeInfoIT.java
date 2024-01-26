package se.inera.intyg.certificateservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateTypeInfoDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.RoleTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;


@ActiveProfiles("integration-test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CertificateTypeInfoIT {

  private static final String DOKTOR_AJLA = "TSTNMT2321000156-DRAA";
  private static final String TOLVAN_TOLVANSSON = "191212121212";
  private static final String ALFA_REGIONEN = "TSTNMT2321000156-ALFA";
  private static final String ALFA_HUDMOTTAGNINGEN = "TSTNMT2321000156-ALHM";
  private static final String ALFA_MEDICINCENTRUM = "TSTNMT2321000156-ALMC";
  @LocalServerPort
  private int port;

  private final TestRestTemplate restTemplate;

  @Autowired
  public CertificateTypeInfoIT(TestRestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  @Test
  void contextLoads() {
  }

  @Test
  void shallReturnCertificateActionCreateIfPatientIsAliveAndUnitNotBlocked() {
    final var requestUrl = "http://localhost:" + port + "/api/certificatetypeinfo";
    final var certificateTypeInfoDTOS = this.restTemplate.postForObject(
        requestUrl,
        buildRequest(false, false),
        CertificateTypeInfoDTO[].class);

    assertEquals(ResourceLinkTypeDTO.CREATE_CERTIFICATE,
        certificateTypeInfoDTOS[0].getLinks().get(0).getType());
  }

  @Test
  void shallNotReturnCertificateActionCreateIfPatientIsDeceasedAndUnitNotBlocked() {
    final var requestUrl = "http://localhost:" + port + "/api/certificatetypeinfo";
    final var certificateTypeInfoDTOS = this.restTemplate.postForObject(
        requestUrl,
        buildRequest(true, false),
        CertificateTypeInfoDTO[].class);

    assertEquals(0, certificateTypeInfoDTOS[0].getLinks().size());
  }

  @Test
  void shallNotReturnCertificateActionCreateIfPatientIsAliveAndUnitIsBlocked() {
    final var requestUrl = "http://localhost:" + port + "/api/certificatetypeinfo";
    final var certificateTypeInfoDTOS = this.restTemplate.postForObject(
        requestUrl,
        buildRequest(false, true),
        CertificateTypeInfoDTO[].class);

    assertEquals(0, certificateTypeInfoDTOS[0].getLinks().size());
  }

  private static GetCertificateTypeInfoRequest buildRequest(boolean deceased, boolean blocked) {
    return GetCertificateTypeInfoRequest.builder()
        .user(
            UserDTO.builder()
                .id(DOKTOR_AJLA)
                .blocked(blocked)
                .role(RoleTypeDTO.DOCTOR)
                .build()
        )
        .patient(
            PatientDTO.builder()
                .id(
                    PersonIdDTO.builder()
                        .id(TOLVAN_TOLVANSSON)
                        .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                        .build()
                )
                .protectedPerson(false)
                .deceased(deceased)
                .testIndicated(false)
                .build()
        )
        .careProvider(
            UnitDTO.builder()
                .id(ALFA_REGIONEN)
                .build()
        )
        .unit(
            UnitDTO.builder()
                .id(ALFA_HUDMOTTAGNINGEN)
                .isInactive(false)
                .build()
        )
        .careUnit(
            UnitDTO.builder()
                .id(ALFA_MEDICINCENTRUM)
                .build()
        )
        .build();
  }
}
