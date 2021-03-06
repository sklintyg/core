package se.inera.intyg.css.testability.api;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.css.application.dto.CertificateTextDTO;
import se.inera.intyg.css.testability.dto.IntygstjanstCertificatesDTO;
import se.inera.intyg.css.testability.service.IntygstjanstTestabilityService;

@RestController
@RequestMapping("/testability-intygstjanst/v1")
public class IntygstjanstTestabilityController {

  private final static Logger LOG = LoggerFactory.getLogger(
      IntygstjanstTestabilityController.class);

  private final IntygstjanstTestabilityService intygstjanstTestabilityService;

  public IntygstjanstTestabilityController(
      IntygstjanstTestabilityService intygstjanstTestabilityService) {
    this.intygstjanstTestabilityService = intygstjanstTestabilityService;
  }

  @PostMapping("/certificates/{careProvider}")
  public void saveCertificates(@PathVariable String careProvider,
      @RequestBody IntygstjanstCertificatesDTO intygstjanstCertificatesDTO) {
    LOG.info(String.format("Save certificates: %s %s", careProvider,
        intygstjanstCertificatesDTO.certificates().size()));
    intygstjanstTestabilityService.setCertificates(careProvider, intygstjanstCertificatesDTO);
  }

  @DeleteMapping("/certificates/{careProvider}")
  public void deleteCertificates(@PathVariable String careProvider) {
    LOG.info(String.format("Delete certificates: %s", careProvider));
    intygstjanstTestabilityService.deleteCertificates(careProvider);
  }

  @PostMapping("/certificatetexts")
  public void saveCertificateTexts(
      @RequestBody List<CertificateTextDTO> certificateTextDTOList) {
    LOG.info(String.format("Save certificatetexts: %s", certificateTextDTOList.size()));
    intygstjanstTestabilityService.setCertificateTexts(certificateTextDTOList);
  }

  @DeleteMapping("/certificatetexts")
  public void deleteCertificateTexts() {
    LOG.info("Delete certificatetexts");
    intygstjanstTestabilityService.deleteCertificateTexts();
  }
}
