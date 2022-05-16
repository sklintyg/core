package se.inera.intyg.css.application.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.css.application.service.WebcertService;

@RestController
@RequestMapping("/internalapi/v1/erase")
public class WebcertController {

  private final static Logger LOG = LoggerFactory.getLogger(WebcertController.class);
  private final WebcertService webcertService;

  public WebcertController(WebcertService webcertService) {
    this.webcertService = webcertService;
  }

  @DeleteMapping("/{careProvider}")
  public void eraseCareProvider(@PathVariable("careProvider") String careProviderId) {
    LOG.info(String.format("Erase care provider '%s'.", careProviderId));
    webcertService.eraseCareProvider(careProviderId);
  }
}
