package se.inera.intyg.cts.domain.service;

import java.util.Collections;
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

  public void export(Termination termination, String password) {
    final var newPassword = new Password(password);
    final var packageToExport = createPackage.create(termination, newPassword);
    // TODO: Package will be uploaded to sjut.
    termination.exported(newPassword);
    terminationRepository.store(termination);
  }

}
