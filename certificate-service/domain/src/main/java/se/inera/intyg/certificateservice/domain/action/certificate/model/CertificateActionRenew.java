package se.inera.intyg.certificateservice.domain.action.certificate.model;

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
  private static final String BODY_PART_1 =
      """
          Förnya intyg innebär att ett nytt intygsutkast skapas med delvis samma information som i det ursprungliga intyget.<br><br>
          Uppgifterna i det nya intygsutkastet går att ändra innan det signeras.<br><br>
          I de fall patienten har ändrat namn eller adress så uppdateras den informationen.<br><br>
          """;

  private static final String BODY_PART_2_SAME_UNIT =
      """
          Eventuell kompletteringsbegäran kommer att klarmarkeras.
          """;

  private static final String BODY_PART_2_OTHER_UNIT =
      """
          Eventuell kompletteringsbegäran kommer inte att klarmarkeras.
          """;

  private static final String BODY_PART_3 =
      """
          Det nya utkastet skapas på den enhet du är inloggad på.
          """;

  private final CertificateActionSpecification certificateActionSpecification;
  private final List<ActionRule> actionRules;

  @Override
  public List<String> reasonNotAllowed(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
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
  public boolean evaluate(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    return actionRules.stream()
        .filter(value -> value.evaluate(certificate, actionEvaluation))
        .count() == actionRules.size();
  }

  @Override
  public String getName(Optional<Certificate> certificate) {
    return NAME;
  }

  @Override
  public String getDescription(Optional<Certificate> certificate) {
    return DESCRIPTION;
  }

  @Override
  public String getBody(Optional<Certificate> certificate,
      Optional<ActionEvaluation> actionEvaluation) {
    if (certificate.isEmpty() || actionEvaluation.isEmpty()) {
      return BODY_PART_1 + BODY_PART_3;
    }

    final var stringBuilder = new StringBuilder();
    stringBuilder.append(BODY_PART_1);
    if (isCareUnitMatchingSubUnit(actionEvaluation.get(), certificate.get()) ||
        isIssuingUnitMatchingSubUnit(actionEvaluation.get(), certificate.get())) {
      stringBuilder.append(BODY_PART_2_SAME_UNIT);
    } else {
      stringBuilder.append(BODY_PART_2_OTHER_UNIT);
    }

    stringBuilder.append(BODY_PART_3);

    return stringBuilder.toString();
  }

  private static boolean isCareUnitMatchingSubUnit(ActionEvaluation actionEvaluation,
      Certificate value) {
    return value.certificateMetaData().careUnit().hsaId().equals(
        actionEvaluation.subUnit().hsaId()
    );
  }

  private static boolean isIssuingUnitMatchingSubUnit(ActionEvaluation actionEvaluation,
      Certificate value) {
    return value.certificateMetaData().issuingUnit().hsaId().equals(
        actionEvaluation.subUnit().hsaId()
    );
  }
}