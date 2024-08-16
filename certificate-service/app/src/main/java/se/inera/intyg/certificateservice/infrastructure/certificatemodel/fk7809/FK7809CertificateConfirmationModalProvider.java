package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809;

import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateModalActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateConfirmationModal;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateConfirmationModalProvider;
import se.inera.intyg.certificateservice.domain.common.model.AccessScope;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;

public class FK7809CertificateConfirmationModalProvider implements
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
        .alert(String.format(
            "Du är på väg att utfärda Läkarutlåtande för merkostnadsersättning för %s - %s.",
            patient.name().fullName(),
            patient.id().idWithDash())
        ).text(
            "Läkarutlåtande för merkostnadsersättning är till för personer över 18 år som inte har en underhållsskyldig förälder. Om det gäller ett barn ska du istället använda läkarutlåtande för omvårdnadsbidrag och merkostnadsersättning (FK3220)."
        ).checkboxText(
            "Jag är säker på att jag vill utfärda Läkarutlåtande för merkostnadsersättning.")
        .primaryAction(CertificateModalActionType.READ)
        .secondaryAction(hasStrictestAccessScope
            ? CertificateModalActionType.CANCEL
            : CertificateModalActionType.DELETE
        )
        .build();
  }

  private static boolean isEnabled(Certificate certificate, ActionEvaluation actionEvaluation) {
    final var accessScope = actionEvaluation.user().accessScope();
    final var patient = getPatient(certificate, actionEvaluation);

    if (patient.getAge() >= 18
        || (certificate != null && certificate.status() == Status.LOCKED_DRAFT)) {
      return false;
    }

    if (accessScope == AccessScope.WITHIN_CARE_UNIT) {
      return certificate == null || certificate.revision().value() == 0;
    }

    return certificate == null || certificate.isWithinCareUnit(actionEvaluation);
  }

  private static Patient getPatient(Certificate certificate, ActionEvaluation actionEvaluation) {
    return certificate == null
        ? actionEvaluation.patient()
        : certificate.certificateMetaData().patient();
  }
}
