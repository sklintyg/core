package se.inera.intyg.css.application.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.css.application.dto.SMSRequestDTO;
import se.inera.intyg.css.application.dto.SMSResponseDTO;
import se.inera.intyg.css.application.service.TellusTalkService;

@RestController
public class TellusTalkController {

  private final static Logger LOG = LoggerFactory.getLogger(TellusTalkController.class);

  private final TellusTalkService tellusTalkService;

  public TellusTalkController(TellusTalkService tellusTalkService) {
    this.tellusTalkService = tellusTalkService;
  }

  @PostMapping("/send/v1")
  public SMSResponseDTO send(@RequestBody SMSRequestDTO smsRequestDTO) {
    LOG.info(String.format("Send sms with following data: '%s'", smsRequestDTO));
    return tellusTalkService.send(smsRequestDTO);
  }
}
