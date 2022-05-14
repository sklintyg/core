package se.inera.intyg.css.infrastructure.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import se.inera.intyg.css.application.dto.SMSRequestDTO;

@Repository
public class TellusTalkRepository {

  private final Map<String, SMSRequestDTO> smsRequestDTOMap = new HashMap<>();

  public void store(SMSRequestDTO smsRequestDTO) {
    smsRequestDTOMap.put(smsRequestDTO.to(), smsRequestDTO);
  }

  public Optional<SMSRequestDTO> find(String to) {
    return Optional.ofNullable(smsRequestDTOMap.get(to));
  }

  public Optional<SMSRequestDTO> findByPhoneNumber(String phoneNumber) {
    return find(
        smsRequestDTOMap.keySet().stream()
            .filter(to -> to.contains(phoneNumber.replace("-", "").substring(1)))
            .findAny()
            .orElse("")
    );
  }

  public void removeAll() {
    smsRequestDTOMap.clear();
  }
}
