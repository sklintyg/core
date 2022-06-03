package se.inera.intyg.css.infrastructure.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import se.inera.intyg.css.application.dto.EmailRequestDTO;
import se.inera.intyg.css.application.dto.SMSRequestDTO;

@Repository
public class TellusTalkRepository {

  private final Map<String, SMSRequestDTO> smsRequestDTOMap = new HashMap<>();
  private final Map<String, EmailRequestDTO> emailRequestDTOMap = new HashMap<>();

  public void store(SMSRequestDTO smsRequestDTO) {
    smsRequestDTOMap.put(smsRequestDTO.to(), smsRequestDTO);
  }

  public void store(EmailRequestDTO emailRequestDTO) {
    emailRequestDTOMap.put(emailRequestDTO.to(), emailRequestDTO);
  }

  public Optional<SMSRequestDTO> findSms(String to) {
    return Optional.ofNullable(smsRequestDTOMap.get(to));
  }

  public Optional<EmailRequestDTO> findEmail(String to) {
    return Optional.ofNullable(emailRequestDTOMap.get(to));
  }

  public Optional<SMSRequestDTO> findByPhoneNumber(String phoneNumber) {
    return findSms(
        smsRequestDTOMap.keySet().stream()
            .filter(to -> to.contains(phoneNumber.replace("-", "").substring(1)))
            .findAny()
            .orElse("")
    );
  }

  public Optional<EmailRequestDTO> findByEmailAddress(String emailAddress) {
    return findEmail(
        emailRequestDTOMap.keySet().stream()
            .filter(to -> to.contains(emailAddress))
            .findAny()
            .orElse("")
    );
  }

  public void removeAll() {
    smsRequestDTOMap.clear();
  }

  public void removeAllEmails() {
    emailRequestDTOMap.clear();
  }
}
