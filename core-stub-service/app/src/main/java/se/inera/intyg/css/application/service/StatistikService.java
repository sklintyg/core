package se.inera.intyg.css.application.service;

import org.springframework.stereotype.Service;
import se.inera.intyg.css.infrastructure.persistence.StatistikRepository;

@Service
public class StatistikService {

  private final StatistikRepository statistikRepository;

  public StatistikService(StatistikRepository statistikRepository) {
    this.statistikRepository = statistikRepository;
  }

  public void eraseCareProvider(String careProviderId) {
    statistikRepository.eraseCareProvider(careProviderId);
  }
}
