package se.inera.intyg.css.application.service;

import org.springframework.stereotype.Service;
import se.inera.intyg.css.application.dto.PackageMetadata;
import se.inera.intyg.css.infrastructure.persistence.FileUpload;
import se.inera.intyg.css.infrastructure.persistence.SjutRepository;

@Service
public class SjutService {

  private final SjutRepository sjutRepository;

  public SjutService(SjutRepository sjutRepository) {
    this.sjutRepository = sjutRepository;
  }

  public void upload(PackageMetadata packageMetadata, byte[] bytes) {
    sjutRepository.store(new FileUpload(packageMetadata, bytes));
  }
}
