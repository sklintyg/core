package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.List;

public record ElementDiagnosisTerminology(String id, String label, String codeSystem,
                                          List<String> acceptedCodeSystems) {

  public ElementDiagnosisTerminology {
    if (acceptedCodeSystems == null) {
      throw new IllegalArgumentException(
          "acceptedCodeSystems must not be null, set to empty list if none");
    }
  }

  public boolean isValidCodeSystem(String codeSystem) {
    return acceptedCodeSystems().contains(codeSystem);
  }
}
