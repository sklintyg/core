package se.inera.intyg.cts.domain.service;

import java.io.File;
import java.io.IOException;
import se.inera.intyg.cts.domain.model.Password;
import se.inera.intyg.cts.domain.model.Termination;

public class DummyCreatePackage implements CreatePackage {

  private Termination termination;
  private Password password;
  private File packageFile;

  @Override
  public File create(Termination termination, Password password) {
    this.termination = termination;
    this.password = password;
    try {
      this.packageFile = File.createTempFile("dummy", "zip");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return packageFile;
  }

  public Termination termination() {
    return termination;
  }

  public Password password() {
    return password;
  }

  public File packageFile() {
    return packageFile;
  }

  public void removePackageFile() {
    if (packageFile != null && packageFile.exists()) {
      packageFile.delete();
    }
  }
}
