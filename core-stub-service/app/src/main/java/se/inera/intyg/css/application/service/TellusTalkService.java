package se.inera.intyg.css.application.service;

import org.springframework.stereotype.Service;
import se.inera.intyg.css.application.dto.EmailRequestDTO;
import se.inera.intyg.css.application.dto.SMSRequestDTO;
import se.inera.intyg.css.application.dto.TellusTalkResponseDTO;
import se.inera.intyg.css.infrastructure.persistence.TellusTalkRepository;

@Service
public class TellusTalkService {

  private final TellusTalkRepository tellusTalkRepository;

  public TellusTalkService(TellusTalkRepository tellusTalkRepository) {
    this.tellusTalkRepository = tellusTalkRepository;
  }

  public TellusTalkResponseDTO send(SMSRequestDTO smsRequestDTO) {
    tellusTalkRepository.store(smsRequestDTO);
    return new TellusTalkResponseDTO("Fake-job-id", "Fake-log-href");
  }

  public TellusTalkResponseDTO send(EmailRequestDTO emailRequestDTO) {
    tellusTalkRepository.store(emailRequestDTO);
    return new TellusTalkResponseDTO("Fake-job-id", "Fake-log-href");
  }
}
