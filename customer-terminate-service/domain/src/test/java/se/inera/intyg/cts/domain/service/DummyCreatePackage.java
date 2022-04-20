package se.inera.intyg.cts.domain.service;

import java.io.File;
import se.inera.intyg.cts.domain.model.Password;
import se.inera.intyg.cts.domain.model.Termination;

public class DummyCreatePackage implements CreatePackage {

  private Termination termination;
  private Password password;

  @Override
  public File create(Termination termination, Password password) {
    this.termination = termination;
    this.password = password;
    return new File("./dummyfile.zip");
  }

  public Termination termination() {
    return termination;
  }

  public Password password() {
    return password;
  }
}
