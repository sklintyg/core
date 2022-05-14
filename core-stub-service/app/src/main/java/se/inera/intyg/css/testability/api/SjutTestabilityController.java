package se.inera.intyg.css.testability.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.css.testability.service.SjutTestabilityService;

@RestController
@RequestMapping("/testability-sjut/v1")
public class SjutTestabilityController {

  private final static Logger LOG = LoggerFactory.getLogger(
      SjutTestabilityController.class);

  private final SjutTestabilityService sjutTestabilityService;

  public SjutTestabilityController(SjutTestabilityService sjutTestabilityService) {
    this.sjutTestabilityService = sjutTestabilityService;
  }

  @GetMapping(
      value = "/files/{organizationNumber}",
      produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
  )
  public byte[] getUploadedFile(@PathVariable String organizationNumber) {
    LOG.info(String.format("Get uploaded file for organizationNumber: %s", organizationNumber));
    return sjutTestabilityService.getUploadedFile(organizationNumber);
  }

  @DeleteMapping("/files")
  public void deleteFiles() {
    LOG.info("Delete uploaded files");
    sjutTestabilityService.deleteUploadedFiles();
  }
}
