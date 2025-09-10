package se.inera.intyg.certificateservice.domain.certificate.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.unit.model.UnitName;

@Value
@Builder
public class SickLeaveCertificate {

  CertificateId id;
  String type;
  HsaId signingDoctorId;
  Name signingDoctorName;
  LocalDateTime signingDateTime;
  HsaId careUnitId;
  UnitName careUnitName;
  HsaId careGiverId;
  PersonId civicRegistrationNumber;
  Name patientName;
  ElementValueDiagnosis diagnoseCode;
  ElementValueDiagnosis biDiagnoseCode1;
  ElementValueDiagnosis biDiagnoseCode2;
  List<ElementValueCode> employment;
  Revoked deleted;
  List<DateRange> workCapacities;
  boolean testCertificate;
}