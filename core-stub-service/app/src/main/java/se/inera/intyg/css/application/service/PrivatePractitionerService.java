package se.inera.intyg.css.application.service;

import org.springframework.stereotype.Service;
import se.inera.intyg.css.infrastructure.persistence.PrivatePractitionerRepository;

@Service
public class PrivatePractitionerService {

  private final PrivatePractitionerRepository privatePractitionerRepository;

  public PrivatePractitionerService(PrivatePractitionerRepository privatePractitionerRepository) {
    this.privatePractitionerRepository = privatePractitionerRepository;
  }

  public void eraseCareProvider(String careProviderId) {
    privatePractitionerRepository.eraseCareProvider(careProviderId);
  }
}
