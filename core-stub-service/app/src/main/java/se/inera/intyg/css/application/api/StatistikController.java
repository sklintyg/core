package se.inera.intyg.css.application.api;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.css.application.service.StatistikService;

@RestController
@RequestMapping("/api/internalapi/v1")
public class StatistikController {

  private final static Logger LOG = LoggerFactory.getLogger(StatistikController.class);
  private final StatistikService statistikService;

  public StatistikController(StatistikService statistikService) {
    this.statistikService = statistikService;
  }

  @DeleteMapping("/intygsidlist")
  public void eraseCertificates(@RequestBody List<String> certificateIds) {
    LOG.info(String.format("Erase certificateIds '%s'.", certificateIds));
    statistikService.eraseCertificates(certificateIds);
  }

  @DeleteMapping("/vardgivareidlist")
  public void eraseCareProvider(@RequestBody List<String> careProviderIds) {
    LOG.info(String.format("Erase care providers '%s'.", careProviderIds));
    careProviderIds.forEach(statistikService::eraseCareProvider);
  }
}
