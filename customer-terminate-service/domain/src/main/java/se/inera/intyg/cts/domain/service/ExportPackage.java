package se.inera.intyg.cts.domain.service;

import java.io.File;
import se.inera.intyg.cts.domain.model.Password;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.repository.TerminationRepository;

public class ExportPackage {

  private final CreatePackage createPackage;
  private final UploadPackage uploadPackage;
  private final TerminationRepository terminationRepository;

  public ExportPackage(CreatePackage createPackage,
      UploadPackage uploadPackage,
      TerminationRepository terminationRepository) {
    this.createPackage = createPackage;
    this.uploadPackage = uploadPackage;
    this.terminationRepository = terminationRepository;
  }

  public void export(Termination termination) {
    // TODO: Use hardcoded password until sms notification has been implemented.
    final var password = new Password("password");
    final var packageToExport = createPackage.create(termination, password);
    uploadPackage.uploadPackage(termination, packageToExport);
    removePackage(packageToExport);

    termination.exported(password);
    terminationRepository.store(termination);
  }

  private void removePackage(File packageToExport) {
    if (packageToExport.exists()) {
      packageToExport.delete();
    }
  }
}
