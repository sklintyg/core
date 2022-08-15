package se.inera.intyg.css.testability.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.css.testability.service.WebcertTestabilityService;

@RestController
@RequestMapping("/testability-webcert/v1")
public class WebcertTestabilityController {

  private final static Logger LOG = LoggerFactory.getLogger(
      WebcertTestabilityController.class);

  private final WebcertTestabilityService webcertTestabilityService;

  public WebcertTestabilityController(WebcertTestabilityService webcertTestabilityService) {
    this.webcertTestabilityService = webcertTestabilityService;
  }

  @GetMapping("/erased/{careProviderId}")
  public boolean isCareProviderErased(@PathVariable String careProviderId) {
    LOG.info(String.format("Check if care provider '%s' has been erased", careProviderId));
    return webcertTestabilityService.isCareProviderErased(careProviderId);
  }

  @DeleteMapping("/erased")
  public void clearErasedCareProviders() {
    LOG.info("Clear erased care providers");
    webcertTestabilityService.clearErasedCareProviders();
  }
}
