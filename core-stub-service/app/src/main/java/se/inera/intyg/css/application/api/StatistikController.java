package se.inera.intyg.css.application.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.css.application.service.StatistikService;

@RestController
@RequestMapping("/internalapi/v1/erase")
public class StatistikController {

  private final static Logger LOG = LoggerFactory.getLogger(StatistikController.class);
  private final StatistikService statistikService;

  public StatistikController(StatistikService statistikService) {
    this.statistikService = statistikService;
  }

  @DeleteMapping("/{careProvider}")
  public void eraseCareProvider(@PathVariable("careProvider") String careProviderId) {
    LOG.info(String.format("Erase care provider '%s'.", careProviderId));
    statistikService.eraseCareProvider(careProviderId);
  }
}
