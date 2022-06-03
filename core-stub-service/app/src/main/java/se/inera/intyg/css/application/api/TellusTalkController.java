package se.inera.intyg.css.application.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.css.application.dto.EmailRequestDTO;
import se.inera.intyg.css.application.dto.SMSRequestDTO;
import se.inera.intyg.css.application.dto.TellusTalkResponseDTO;
import se.inera.intyg.css.application.service.TellusTalkService;

@RestController
public class TellusTalkController {

  private final static Logger LOG = LoggerFactory.getLogger(TellusTalkController.class);

  private final static String TO = "to";
  private final static String SMS_PREFIX = "sms:";
  private final static String EMAIL_PREFIX = "email:";

  private final ObjectMapper objectMapper;
  private final TellusTalkService tellusTalkService;

  public TellusTalkController(TellusTalkService tellusTalkService, ObjectMapper objectMapper) {
    this.tellusTalkService = tellusTalkService;
    this.objectMapper = objectMapper;
  }

  @PostMapping("/send/v1")
  public TellusTalkResponseDTO send(@RequestBody String requestJson) throws JsonProcessingException {
    final var mappedJson = JsonParserFactory.getJsonParser().parseMap(requestJson);

    if (mappedJson.get(TO).toString().startsWith(SMS_PREFIX)) {
      final var smsRequestDTO = objectMapper.readValue(requestJson, SMSRequestDTO.class);
      LOG.info(String.format("Send sms with following data: '%s'", smsRequestDTO));
      return tellusTalkService.send(smsRequestDTO);

    } else if (mappedJson.get(TO).toString().startsWith(EMAIL_PREFIX)) {
      final var emailRequestDTO = objectMapper.readValue(requestJson, EmailRequestDTO.class);
      LOG.info(String.format("Send email with following data: '%s'", emailRequestDTO));
      return tellusTalkService.send(emailRequestDTO);
    }

    LOG.error("TellusTalk stub only supports sms and email. Sent message '{}' is neither.",
        mappedJson.get(TO));
    return new TellusTalkResponseDTO("", "");
  }
}
