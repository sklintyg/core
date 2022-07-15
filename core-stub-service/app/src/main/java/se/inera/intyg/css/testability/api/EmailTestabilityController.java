package se.inera.intyg.css.testability.api;


import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.css.testability.service.EmailTestabilityService;

@RestController
@RequestMapping("/testability-email/v1")
public class EmailTestabilityController {

  private final static Logger LOG = LoggerFactory.getLogger(EmailTestabilityController.class);

  private final EmailTestabilityService emailTestabilityService;

  public EmailTestabilityController(EmailTestabilityService emailTestabilityService) {
    this.emailTestabilityService = emailTestabilityService;
  }

  @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Map<String, List<String>>> getAllEmails() {
    LOG.info("Get all emails in development mail server");
    return emailTestabilityService.getAllEmails();
  }

  @GetMapping(value = "/{emailAddress}", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Map<String, List<String>>> getEmailsForAddress(@PathVariable String emailAddress) {
    LOG.info(String.format("Get emails for address: %s", emailAddress));
    return emailTestabilityService.getEmailsForAddress(emailAddress);
  }

  @DeleteMapping("/")
  public void deleteEmail() {
    LOG.info("Delete emails on development mail server");
    emailTestabilityService.deleteEmails();
  }
}
