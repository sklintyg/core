package se.inera.intyg.cts.domain.service;

import se.inera.intyg.cts.domain.model.Password;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.repository.TerminationRepository;

public class ExportPackage {

  private final CreatePackage createPackage;

  private final PasswordGenerator passwordGenerator;
  private final TerminationRepository terminationRepository;

  public ExportPackage(CreatePackage createPackage,
      PasswordGenerator passwordGenerator, TerminationRepository terminationRepository) {
    this.createPackage = createPackage;
    this.passwordGenerator = passwordGenerator;
    this.terminationRepository = terminationRepository;
  }

  public void export(Termination termination) {
    final var newPassword = new Password(passwordGenerator.generateSecurePassword());
    final var packageToExport = createPackage.create(termination, newPassword);
    // TODO: Package will be uploaded to sjut.
    termination.exported(newPassword);
    terminationRepository.store(termination);
  }

}
