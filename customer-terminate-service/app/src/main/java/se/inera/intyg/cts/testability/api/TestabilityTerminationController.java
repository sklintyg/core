package se.inera.intyg.cts.testability.api;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.cts.infrastructure.integration.Intygstjanst.dto.CertificateTextDTO;
import se.inera.intyg.cts.infrastructure.integration.Intygstjanst.dto.CertificateXmlDTO;
import se.inera.intyg.cts.testability.dto.TestabilityExportEmbeddableDTO;
import se.inera.intyg.cts.testability.dto.TestabilityTerminationDTO;
import se.inera.intyg.cts.testability.service.TestabilityTerminationService;

@RestController
@Profile("testability")
@RequestMapping("/testability/v1/terminations")
public class TestabilityTerminationController {

  private final static Logger LOG = LoggerFactory.getLogger(TestabilityTerminationController.class);

  private final TestabilityTerminationService testabilityTerminationService;

  public TestabilityTerminationController(
      TestabilityTerminationService testabilityTerminationService) {
    this.testabilityTerminationService = testabilityTerminationService;
  }

  @PostMapping
  void create(@RequestBody TestabilityTerminationDTO testabilityTerminationDTO) {
    LOG.info(String.format("Create termination '%s'", testabilityTerminationDTO));
    testabilityTerminationService.createTermination(testabilityTerminationDTO);
  }

  @PostMapping("/{terminationId}/certificates")
  void saveCertificates(@PathVariable UUID terminationId,
      @RequestBody List<CertificateXmlDTO> certificateXmlDTOList) {
    LOG.info(String.format("Save '%s' certificates for termination '%s'",
        certificateXmlDTOList.size(), terminationId));
    testabilityTerminationService.saveCertificates(terminationId, certificateXmlDTOList);
  }

  @PostMapping("/{terminationId}/certificatetexts")
  void saveCertificateTexts(@PathVariable UUID terminationId,
      @RequestBody List<CertificateTextDTO> certificateTextDTOList) {
    LOG.info(String.format("Save '%s' certificate texts for termination '%s'",
        certificateTextDTOList.size(), terminationId));
    testabilityTerminationService.saveCertificateTexts(terminationId, certificateTextDTOList);
  }

  @PostMapping("/{terminationId}/upload")
  void setAsUploaded(@PathVariable UUID terminationId, @RequestBody String password) {
    LOG.info(String.format("Set termination '%s' as uploaded with password '%s'",
        terminationId, password));
    testabilityTerminationService.setAsUploaded(terminationId, password);
  }

  @PostMapping("/{terminationId}/sendNotification")
  void setAsNotificationSent(@PathVariable UUID terminationId,
      @RequestBody LocalDateTime notificationTime) {
    LOG.info(String.format("Set termination '%s' to notification sent at '%s'",
        terminationId, notificationTime));
    testabilityTerminationService.setAsNotificationSent(terminationId, notificationTime);
  }

  @PostMapping("/{terminationId}/receipt")
  void setAsReceiptRecieved(@PathVariable UUID terminationId) {
    LOG.info(String.format("Set status for termination '%s' to receipt received", terminationId));
    testabilityTerminationService.setAsReceiptReceived(terminationId);
  }

  @DeleteMapping("/{terminationId}")
  void delete(@PathVariable UUID terminationId) {
    LOG.info(String.format("Delete termination '%s'", terminationId));
    testabilityTerminationService.deleteTermination(terminationId);
  }

  @GetMapping("/export/{terminationId}")
  TestabilityExportEmbeddableDTO getExportEmbeddable(@PathVariable UUID terminationId) {
    return testabilityTerminationService.getExportEmbeddable(terminationId);
  }

  @GetMapping("/{terminationId}/certificatesCount")
  int getCertificatesCount(@PathVariable UUID terminationId) {
    LOG.info(String.format("Get certificates count for termination '%s'", terminationId));
    return testabilityTerminationService.getCertificatesCount(terminationId);
  }

  @GetMapping("/{terminationId}/certificateTextsCount")
  int getCertificateTextCount(@PathVariable UUID terminationId) {
    LOG.info(String.format("Get certificate texts count for termination '%s'", terminationId));
    return testabilityTerminationService.getCertificateTextsCount(terminationId);
  }

  @GetMapping("/{terminationId}/password")
  String getPassword(@PathVariable UUID terminationId) {
    LOG.info(String.format("Get package password for termination '%s'", terminationId));
    return testabilityTerminationService.getPassword(terminationId);
  }

  @GetMapping("/{terminationId}/status")
  String getStatus(@PathVariable UUID terminationId) {
    LOG.info(String.format("Get status for termination '%s'", terminationId));
    return testabilityTerminationService.getStatus(terminationId);
  }
}
