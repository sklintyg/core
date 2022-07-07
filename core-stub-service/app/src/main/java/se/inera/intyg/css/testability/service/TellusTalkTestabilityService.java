package se.inera.intyg.css.testability.service;

import org.springframework.stereotype.Service;
import se.inera.intyg.css.application.dto.SMSRequestDTO;
import se.inera.intyg.css.infrastructure.persistence.TellusTalkRepository;

@Service
public class TellusTalkTestabilityService {

  private final TellusTalkRepository tellusTalkRepository;

  public TellusTalkTestabilityService(TellusTalkRepository tellusTalkRepository) {
    this.tellusTalkRepository = tellusTalkRepository;
  }

  public String getPasswordSentWithSMS(String phoneNumber) {
    return tellusTalkRepository.findByPhoneNumber(phoneNumber)
        .map(SMSRequestDTO::text)
        .orElse("");
  }

  public String getNotificationSentWithSMS(String phoneNumber) {
    return tellusTalkRepository.findByPhoneNumber(phoneNumber)
        .map(SMSRequestDTO::text)
        .orElse("");
  }

  public void deleteSMS() {
    tellusTalkRepository.removeAll();
  }
}
