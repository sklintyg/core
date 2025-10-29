package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.List;

public record ElementDiagnosisTerminology(String id, String label, String codeSystem,
                                          List<String> equivalentCodeSystems) {

  public ElementDiagnosisTerminology {
    if (equivalentCodeSystems == null) {
      equivalentCodeSystems = List.of();
    }
  }

  public boolean isValidCodeSystem(String codeSystem) {
    return codeSystem != null && (this.codeSystem.equals(codeSystem)
        || equivalentCodeSystems().contains(codeSystem));
  }
}
