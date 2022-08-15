package se.inera.intyg.css.application.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.css.application.service.PrivatePractitionerService;

@RestController
@RequestMapping("/internalapi/privatepractitioner")
public class PrivatePractitionerController {

  private final static Logger LOG = LoggerFactory.getLogger(PrivatePractitionerController.class);
  private final PrivatePractitionerService privatePractitionerService;

  public PrivatePractitionerController(PrivatePractitionerService privatePractitionerService) {
    this.privatePractitionerService = privatePractitionerService;
  }

  @DeleteMapping("/erase/{careProvider}")
  public void eraseCareProvider(@PathVariable("careProvider") String careProviderId) {
    LOG.info(String.format("Erase care provider '%s'.", careProviderId));
    privatePractitionerService.eraseCareProvider(careProviderId);
  }
}
