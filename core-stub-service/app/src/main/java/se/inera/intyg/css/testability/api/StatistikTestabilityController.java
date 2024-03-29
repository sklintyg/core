package se.inera.intyg.css.testability.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.css.testability.service.StatistikTestabilityService;

@RestController
@RequestMapping("/testability-statistik/v1")
public class StatistikTestabilityController {

  private final static Logger LOG = LoggerFactory.getLogger(
      StatistikTestabilityController.class);

  private final StatistikTestabilityService statistikTestabilityService;

  public StatistikTestabilityController(StatistikTestabilityService statistikTestabilityService) {
    this.statistikTestabilityService = statistikTestabilityService;
  }

  @GetMapping("/erased/{certificateId}")
  public boolean isCertificateErased(@PathVariable String certificateId) {
    LOG.info(String.format("Check if certificate '%s' has been erased", certificateId));
    return statistikTestabilityService.isCertificateErased(certificateId);
  }

  @DeleteMapping("/certificates/erased")
  public void clearErasedCertificates() {
    LOG.info("Clear erased certificates");
    statistikTestabilityService.clearErasedCertificates();
  }

  @GetMapping("/careproviders/erased/{careProviderId}")
  public boolean isCareProviderErased(@PathVariable String careProviderId) {
    LOG.info(String.format("Check if care provider '%s' has been erased", careProviderId));
    return statistikTestabilityService.isCareProviderErased(careProviderId);
  }

  @DeleteMapping("/careproviders/erased")
  public void clearErasedCareProviders() {
    LOG.info("Clear erased care providers");
    statistikTestabilityService.clearErasedCareProviders();
  }
}
