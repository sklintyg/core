package se.inera.intyg.certificateservice.domain.action.model;

import java.util.List;
import java.util.Optional;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;

@Builder
@Getter(AccessLevel.NONE)
public class CertificateActionRenew implements CertificateAction {

  private static final String NAME = "Förnya";
  private static final String DESCRIPTION = "Skapar en redigerbar kopia av intyget på den enhet som du är inloggad på.";
  private static final String BODY =
      """
          Förnya intyg innebär att ett nytt intygsutkast skapas med delvis samma information som i det ursprungliga intyget.<br><br>
          Uppgifterna i det nya intygsutkastet går att ändra innan det signeras.<br><br>
          I de fall patienten har ändrat namn eller adress så uppdateras den informationen.<br><br>
          Det nya utkastet skapas på den enhet du är inloggad på.
          """;

  private final CertificateActionSpecification certificateActionSpecification;
  private final List<ActionRule> actionRules;

  @Override
  public List<String> reasonNotAllowed(Optional<Certificate> certificate,
      ActionEvaluation actionEvaluation) {
    return actionRules.stream()
        .filter(value -> !value.evaluate(certificate, actionEvaluation))
        .map(ActionRule::getReasonForPermissionDenied)
        .toList();
  }

  @Override
  public CertificateActionType getType() {
    return certificateActionSpecification.certificateActionType();
  }

  @Override
  public boolean evaluate(Optional<Certificate> certificate, ActionEvaluation actionEvaluation) {
    return actionRules.stream()
        .filter(value -> value.evaluate(certificate, actionEvaluation))
        .count() == actionRules.size();
  }

  @Override
  public String getName() {
    return NAME;
  }

  @Override
  public String getDescription() {
    return DESCRIPTION;
  }

  @Override
  public String getBody() {
    return BODY;
  }
}