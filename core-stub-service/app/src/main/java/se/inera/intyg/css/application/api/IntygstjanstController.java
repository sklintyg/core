package se.inera.intyg.css.application.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.css.application.dto.CertificateExportPageDTO;
import se.inera.intyg.css.application.service.IntygstjanstService;

@RestController
@RequestMapping("/api-intygstjanst/v1")
public class IntygstjanstController {

  private final IntygstjanstService intygstjanstService;

  public IntygstjanstController(IntygstjanstService intygstjanstService) {
    this.intygstjanstService = intygstjanstService;
  }

  @GetMapping("/certificates/{careProvider}")
  public CertificateExportPageDTO getCertificates(
      @PathVariable("careProvider") String careProviderId,
      @RequestParam("size") int size,
      @RequestParam("page") int page) {
    return intygstjanstService.getCertificateExportPage(careProviderId, size, page);
  }
}
