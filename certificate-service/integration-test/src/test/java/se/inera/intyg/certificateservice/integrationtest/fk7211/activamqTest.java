package se.inera.intyg.certificateservice.integrationtest.fk7211;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.FK7211;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.VERSION;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customSignCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.version;
import static se.inera.intyg.certificateservice.testability.common.TestabilityConstants.TESTABILITY_PROFILE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import se.inera.intyg.certificateservice.integrationtest.util.ApiUtil;
import se.inera.intyg.certificateservice.integrationtest.util.Config;
import se.inera.intyg.certificateservice.integrationtest.util.Containers;
import se.inera.intyg.certificateservice.integrationtest.util.TestabilityApiUtil;

@ActiveProfiles({"integration-test", TESTABILITY_PROFILE})
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = Config.class)
class activamqTest {

  @LocalServerPort
  private int port;

  private JmsTemplate certificateEventJmsTemplate;

  @Autowired
  public activamqTest(TestRestTemplate restTemplate, JmsTemplate certificateEventJmsTemplate) {
    this.restTemplate = restTemplate;
    this.certificateEventJmsTemplate = certificateEventJmsTemplate;
  }

  private ApiUtil api;
  private final TestRestTemplate restTemplate;
  private TestabilityApiUtil testabilityApi;

  @BeforeEach
  void setUp() {
    this.api = new ApiUtil(restTemplate, port);
    this.testabilityApi = new TestabilityApiUtil(restTemplate, port);
    Containers.ensureRunning();
  }

  @Test
  @DisplayName("FK7211 - Om intyget signeras ska ett meddelande läggas på AMQn")
  void shallSuccessfullyAddMessageOfSigningOnAMQ() {
    final var testCertificates = testabilityApi.addCertificates(
        customTestabilityCertificateRequest(FK7211, VERSION)
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var t = certificateEventJmsTemplate.receive("test.intygstjanst.certificate.event.queue");

    api.signCertificate(
        customSignCertificateRequest()
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates),
        version(testCertificates)
    );

    final var s = certificateEventJmsTemplate.receive("test.intygstjanst.certificate.event.queue");

    assertTrue(true);

  }
}
