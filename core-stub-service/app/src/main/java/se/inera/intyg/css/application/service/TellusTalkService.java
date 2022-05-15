package se.inera.intyg.css.application.service;

import org.springframework.stereotype.Service;
import se.inera.intyg.css.application.dto.SMSRequestDTO;
import se.inera.intyg.css.application.dto.SMSResponseDTO;
import se.inera.intyg.css.infrastructure.persistence.TellusTalkRepository;

@Service
public class TellusTalkService {

  private final TellusTalkRepository tellusTalkRepository;

  public TellusTalkService(TellusTalkRepository tellusTalkRepository) {
    this.tellusTalkRepository = tellusTalkRepository;
  }

  public SMSResponseDTO send(SMSRequestDTO smsRequestDTO) {
    tellusTalkRepository.store(smsRequestDTO);
    return new SMSResponseDTO("Fake-job-id", "Fake-log-href");
  }
}
