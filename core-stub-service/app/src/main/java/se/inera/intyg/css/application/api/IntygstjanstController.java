package se.inera.intyg.css.application.api;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.css.application.dto.CertificateExportPageDTO;
import se.inera.intyg.css.application.dto.CertificateTextDTO;
import se.inera.intyg.css.application.service.IntygstjanstService;

@RestController
@RequestMapping("/inera-certificate/internalapi/v1")
public class IntygstjanstController {

  private final static Logger LOG = LoggerFactory.getLogger(IntygstjanstController.class);

  private final IntygstjanstService intygstjanstService;

  public IntygstjanstController(IntygstjanstService intygstjanstService) {
    this.intygstjanstService = intygstjanstService;
  }

  @GetMapping("/certificates/{careProvider}")
  public CertificateExportPageDTO getCertificates(
      @PathVariable("careProvider") String careProviderId,
      @RequestParam("size") int size,
      @RequestParam("page") int page) {
    LOG.info(String.format("Get certificates for care provider '%s', size '%s' and page '%s'.",
        careProviderId, size, page));
    return intygstjanstService.getCertificateExportPage(careProviderId, size, page);
  }

  @GetMapping("/certificatetexts")
  public List<CertificateTextDTO> getCertificateTexts() {
    LOG.info("Get certificate texts.");
    return intygstjanstService.getCertificateTexts();
  }
}
