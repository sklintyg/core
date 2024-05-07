package se.inera.intyg.certificateservice.domain.action.model;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public class ActionRuleCitizenPatientMatch implements ActionRule {

  @Override
  public boolean evaluate(Optional<Certificate> certificate, ActionEvaluation actionEvaluation) {
    return certificate.map(value -> value.certificateMetaData().patient().id().id()
        .equals(actionEvaluation.citizen().id())).orElse(false);
  }

  @Override
  public String getReasonForPermissionDenied() {
    return "Kunde inte hämta intyg. "
        + "Invånaren som försöker hämta intyget stämmer inte överens "
        + "med patienten som intyget är utfärdat på.";
  }
}
