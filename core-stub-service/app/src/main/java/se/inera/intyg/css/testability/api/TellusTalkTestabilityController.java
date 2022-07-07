package se.inera.intyg.css.testability.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.css.testability.service.TellusTalkTestabilityService;

@RestController
@RequestMapping("/testability-tellustalk/v1")
public class TellusTalkTestabilityController {

  private final static Logger LOG = LoggerFactory.getLogger(
      TellusTalkTestabilityController.class);

  private final TellusTalkTestabilityService tellusTalkTestabilityService;

  public TellusTalkTestabilityController(
      TellusTalkTestabilityService tellusTalkTestabilityService) {
    this.tellusTalkTestabilityService = tellusTalkTestabilityService;
  }

  @GetMapping("/passwords/{phoneNumber}")
  public String getSMS(@PathVariable String phoneNumber) {
    LOG.info(String.format("Get password that was sent as sms to: %s", phoneNumber));
    return tellusTalkTestabilityService.getPasswordSentWithSMS(phoneNumber);
  }

  @GetMapping("/smsNotifications/{phoneNumber}")
  public String getSmsNotification(@PathVariable String phoneNumber) {
    LOG.info(String.format("Get notification that was sent as sms to: %s", phoneNumber));
    return tellusTalkTestabilityService.getNotificationSentWithSMS(phoneNumber);
  }

  @DeleteMapping("/sms")
  public void deleteSMS() {
    LOG.info("Delete sms.");
    tellusTalkTestabilityService.deleteSMS();
  }
}
