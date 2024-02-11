package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.utils;

import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Staff;
import se.inera.intyg.certificateservice.domain.certificate.model.SubUnit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.Unit;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitType;

public class CertificateToObjectUtility {

  public static CertificateModel getCertificateModel(Certificate certificate) {
    return certificate.certificateModel();
  }

  public static Patient getPatient(Certificate certificate) {
    return certificate.certificateMetaData().patient();
  }

  public static Staff getIssuer(Certificate certificate) {
    return certificate.certificateMetaData().issuer();
  }

  public static Unit getCareProvider(Certificate certificate) {
    return Unit.builder()
        .hsaId(certificate.certificateMetaData().careProvider().hsaId().id())
        .name(certificate.certificateMetaData().careProvider().name().name())
        .type(UnitType.CARE_PROVIDER)
        .build();
  }

  public static Unit getIssuingUnit(Certificate certificate) {
    return Unit.builder()
        .hsaId(certificate.certificateMetaData().issuingUnit().hsaId().id())
        .name(certificate.certificateMetaData().issuingUnit().name().name())
        .type(certificate.certificateMetaData().issuingUnit() instanceof SubUnit
            ? UnitType.SUB_UNIT
            : UnitType.CARE_UNIT)
        .build();
  }

  public static Unit getCareUnit(Certificate certificate) {
    return Unit.builder()
        .hsaId(certificate.certificateMetaData().careUnit().hsaId().id())
        .name(certificate.certificateMetaData().careUnit().name().name())
        .type(UnitType.CARE_UNIT)
        .build();
  }
}
