package se.inera.intyg.css.testability.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.css.testability.service.PrivatePractitionerTestabilityService;

@RestController
@RequestMapping("/testability-privatepractioner/v1")
public class PrivatePractitionerTestabilityController {

  private final static Logger LOG = LoggerFactory.getLogger(
      PrivatePractitionerTestabilityController.class);

  private final PrivatePractitionerTestabilityService privatePractitionerTestabilityService;

  public PrivatePractitionerTestabilityController(
      PrivatePractitionerTestabilityService privatePractitionerTestabilityService) {
    this.privatePractitionerTestabilityService = privatePractitionerTestabilityService;
  }

  @GetMapping("/erased/{careProviderId}")
  public boolean isCareProviderErased(@PathVariable String careProviderId) {
    LOG.info(String.format("Check if care provider '%s' has been erased", careProviderId));
    return privatePractitionerTestabilityService.isCareProviderErased(careProviderId);
  }

  @DeleteMapping("/erased")
  public void clearErasedCareProviders() {
    LOG.info("Clear erased care providers");
    privatePractitionerTestabilityService.clearErasedCareProviders();
  }
}
