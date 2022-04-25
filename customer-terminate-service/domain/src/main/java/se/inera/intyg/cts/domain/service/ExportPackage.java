package se.inera.intyg.cts.domain.service;

import se.inera.intyg.cts.domain.model.Password;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.repository.TerminationRepository;

public class ExportPackage {

  private final CreatePackage createPackage;
  private final TerminationRepository terminationRepository;

  public ExportPackage(CreatePackage createPackage,
      TerminationRepository terminationRepository) {
    this.createPackage = createPackage;
    this.terminationRepository = terminationRepository;
  }

  public void export(Termination termination) {
    // TODO: Use hardcoded password until sms notification has been implemented.
    final var password = new Password("password");
    final var packageToExport = createPackage.create(termination, password);
    // TODO: Package will be uploaded to sjut.
    termination.exported(password);
    terminationRepository.store(termination);
  }
}
