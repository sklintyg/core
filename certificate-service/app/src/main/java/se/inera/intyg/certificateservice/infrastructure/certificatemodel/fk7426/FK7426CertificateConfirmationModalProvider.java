package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426;

import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateModalActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.Alert;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateConfirmationModal;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateConfirmationModalProvider;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageLevel;
import se.inera.intyg.certificateservice.domain.common.model.AccessScope;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;

public class FK7426CertificateConfirmationModalProvider implements
    CertificateConfirmationModalProvider {

  @Override
  public CertificateConfirmationModal of(Certificate certificate,
      ActionEvaluation actionEvaluation) {
    if (actionEvaluation == null || !isEnabled(certificate, actionEvaluation)) {
      return null;
    }

    final var hasStrictestAccessScope =
        AccessScope.WITHIN_CARE_UNIT == actionEvaluation.user().accessScope();
    final var patient = getPatient(certificate, actionEvaluation);
    return CertificateConfirmationModal.builder()
        .title("Kontrollera att du använder dig av rätt läkarutlåtande")
        .alert(
            Alert.builder()
                .type(MessageLevel.INFO)
                .text(
                    """
                        <p>Du är på väg att utfärda Läkarutlåtande tillfällig föräldrapenning för ett allvarligt sjukt barn som inte har fyllt 18 år för</p>
                        <b>%s - %s</b>
                        """.formatted(
                        patient.name().fullName(),
                        patient.id().idWithDash())
                ).build()
        ).text(
            """
                <p>Läkarutlåtande tillfällig föräldrapenning för ett allvarligt sjukt barn som inte har fyllt 18 år ska endast användas om barnet har eller misstänks ha en sjukdom som innebär påtaglig fara för barnets liv, eller om barnet behandlas efter ett sådant tillstånd och det finns fara för barnets liv utan denna behandling.</p>
                """
        )
        .checkboxText(
            "Jag är säker på att jag vill utfärda Läkarutlåtande tillfällig föräldrapenning för ett allvarligt sjukt barn som inte har fyllt 18 år")
        .primaryAction(CertificateModalActionType.READ)
        .secondaryAction(hasStrictestAccessScope
            ? CertificateModalActionType.CANCEL
            : CertificateModalActionType.DELETE
        )
        .build();
  }

  private static boolean isEnabled(Certificate certificate, ActionEvaluation actionEvaluation) {
    final var accessScope = actionEvaluation.user().accessScope();

    if (accessScope == AccessScope.WITHIN_CARE_UNIT) {
      return certificate == null;
    }

    return certificate == null
        || (certificate.revision().value() == 0 && certificate.isWithinCareUnit(actionEvaluation));
  }

  private static Patient getPatient(Certificate certificate, ActionEvaluation actionEvaluation) {
    return certificate == null
        ? actionEvaluation.patient()
        : certificate.certificateMetaData().patient();
  }
}