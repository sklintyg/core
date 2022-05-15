package se.inera.intyg.css.testability.service;

import org.springframework.stereotype.Service;
import se.inera.intyg.css.infrastructure.persistence.SjutRepository;

@Service
public class SjutTestabilityService {

  private final SjutRepository sjutRepository;

  public SjutTestabilityService(SjutRepository sjutRepository) {
    this.sjutRepository = sjutRepository;
  }

  public byte[] getUploadedFile(String organizationNumber) {
    return sjutRepository.get(organizationNumber);
  }

  public void deleteUploadedFiles() {
    sjutRepository.removeAll();
  }
}
