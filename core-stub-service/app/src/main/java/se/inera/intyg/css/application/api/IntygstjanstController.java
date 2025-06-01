package se.inera.intyg.css.application.api;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.css.application.dto.CertificateExportPageDTO;
import se.inera.intyg.css.application.dto.CertificateTextDTO;
import se.inera.intyg.css.application.service.IntygstjanstService;

@Slf4j
@RestController
@RequestMapping("/inera-certificate/internalapi/v1")
public class IntygstjanstController {

  private final IntygstjanstService intygstjanstService;

  public IntygstjanstController(IntygstjanstService intygstjanstService) {
    this.intygstjanstService = intygstjanstService;
  }

  @GetMapping("/certificates/{id}")
  public CertificateExportPageDTO getCertificates(
      @PathVariable("id") String careProviderId,
      @RequestParam("batchSize") int batchSize,
      @RequestParam("collected") int collected) {
    log.info("Get certificates for care provider '{}', size '{}' and page '{}'.",
        careProviderId, batchSize, collected);
    return intygstjanstService.getCertificateExportPage(careProviderId, collected, batchSize);
  }

  @GetMapping("/certificatetexts")
  public List<CertificateTextDTO> getCertificateTexts() {
    log.info("Get certificate texts.");
    return intygstjanstService.getCertificateTexts();
  }

  @DeleteMapping("/certificates/{careProvider}")
  public void eraseCareProvider(@PathVariable("careProvider") String careProviderId) {
    log.info("Erase care provider '{}}.", careProviderId);
    intygstjanstService.eraseCareProvider(careProviderId);
  }
}
