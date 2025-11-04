package se.inera.intyg.certificateservice.domain.certificate.model;

import lombok.Builder;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;
import se.inera.intyg.certificateservice.domain.unit.model.CareProvider;
import se.inera.intyg.certificateservice.domain.unit.model.CareUnit;
import se.inera.intyg.certificateservice.domain.unit.model.IssuingUnit;
import se.inera.intyg.certificateservice.domain.user.model.ResponsibleIssuer;


@Value
@Builder
public class CertificateMetaData {

  @With
  Patient patient;
  Staff issuer;
  IssuingUnit issuingUnit;
  CareUnit careUnit;
  CareProvider careProvider;
  ResponsibleIssuer responsibleIssuer;
  Staff creator;

  public CertificateMetaData updated(ActionEvaluation actionEvaluation) {
    return CertificateMetaData.builder()
        .patient(
            actionEvaluation.patient() == null ? patient : actionEvaluation.patient()
        )
        .issuer(Staff.create(actionEvaluation.user()))
        .careUnit(
            careUnit.hsaId().equals(actionEvaluation.careUnit().hsaId())
                ? actionEvaluation.careUnit()
                : careUnit
        )
        .careProvider(
            careProvider.hsaId().equals(actionEvaluation.careProvider().hsaId())
                ? actionEvaluation.careProvider()
                : careProvider
        )
        .issuingUnit(
            issuingUnit.hsaId().equals(actionEvaluation.subUnit().hsaId())
                ? actionEvaluation.subUnit()
                : issuingUnit
        )
        .responsibleIssuer(actionEvaluation.user().responsibleIssuer())
        .build();
  }
}