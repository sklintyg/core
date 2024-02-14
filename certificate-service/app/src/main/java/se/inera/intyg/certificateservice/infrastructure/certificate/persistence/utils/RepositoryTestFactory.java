package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.patient.model.PersonIdType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateDataEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientIdTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitTypeEntity;

public class RepositoryTestFactory {

  private RepositoryTestFactory() {
    throw new IllegalStateException("Utility class");
  }

  public static String jsonString(LocalDate date) {
    return "[{\"id\":\"F10\",\"value\":{\"type\":\"DATE\",\"date\":[" + date.getYear() + ","
        + date.getMonthValue() + "," + date.getDayOfMonth() + "]}}]";
  }

  public static CertificateModel certificateModel() {
    return CertificateModel.builder()
        .name("NAME")
        .id(CertificateModelId.builder()
            .version(new CertificateVersion("VERSION"))
            .type(new CertificateType("TYPE"))
            .build()
        )
        .build();
  }

  public static PatientEntity patientEntity() {
    return patientEntity(false, false, false);
  }

  public static PatientEntity patientEntity(
      boolean testIndicated,
      boolean isDeceased,
      boolean isProtectedPerson) {
    return PatientEntity.builder()
        .id("ID")
        .protectedPerson(isProtectedPerson)
        .testIndicated(testIndicated)
        .deceased(isDeceased)
        .type(PatientIdTypeEntity.builder()
            .type(PersonIdType.PERSONAL_IDENTITY_NUMBER.name())
            .key(1)
            .build())
        .firstName("FIRST")
        .middleName("MIDDLE")
        .lastName("LAST")
        .build();
  }

  public static StaffEntity staffEntity() {
    return staffEntity("NAME", "HSA_ID");
  }

  public static StaffEntity staffEntity(String name, String hsaId) {
    return StaffEntity.builder()
        .name(name)
        .hsaId(hsaId)
        .build();
  }

  public static CertificateEntity certificateEntity(String json) {
    return CertificateEntity.builder()
        .version(1L)
        .modified(LocalDateTime.now())
        .certificateId("ID")
        .created(LocalDateTime.now())
        .careProvider(
            UnitEntity.builder()
                .type(
                    UnitTypeEntity.builder()
                        .type(UnitType.CARE_PROVIDER.name())
                        .key(UnitType.CARE_PROVIDER.getKey())
                        .build()
                )
                .hsaId("HSA_ID_PROVIDER")
                .name("NAME_PROVIDER")
                .build()
        )
        .careUnit(
            UnitEntity.builder()
                .type(
                    UnitTypeEntity.builder()
                        .type(UnitType.CARE_UNIT.name())
                        .key(UnitType.CARE_UNIT.getKey())
                        .build()
                )
                .hsaId("HSA_ID_UNIT")
                .name("NAME_UNIT")
                .build()
        )
        .issuedOnUnit(
            UnitEntity.builder()
                .type(
                    UnitTypeEntity.builder()
                        .type(UnitType.SUB_UNIT.name())
                        .key(UnitType.SUB_UNIT.getKey())
                        .build()
                )
                .hsaId("HSA_ID_ISSUED")
                .name("NAME_ISSUED")
                .build()
        )
        .issuedBy(staffEntity("ISSUED_BY_NAME", "ISSUED_BY_HSA_ID"))
        .patient(patientEntity())
        .data(new CertificateDataEntity(json))
        .build();
  }
}
