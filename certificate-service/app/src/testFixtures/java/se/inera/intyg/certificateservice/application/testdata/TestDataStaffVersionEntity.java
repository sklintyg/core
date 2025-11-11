package se.inera.intyg.certificateservice.application.testdata;

import static se.inera.intyg.certificateservice.application.testdata.TestDataStaffEntity.AJLA_DOKTOR_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataStaffEntity.ALF_DOKTOR_ENTITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HEALTH_CARE_PROFESSIONAL_LICENCES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_PA_TITLES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_SPECIALITIES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOCTOR_HEALTH_CARE_PROFESSIONAL_LICENCES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_PA_TITLES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_SPECIALITIES;

import java.time.LocalDateTime;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.HealthcareProfessionalLicenceVersionEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PaTitleVersionEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.SpecialityVersionEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffRole;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffRoleEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffVersionEntity;

public class TestDataStaffVersionEntity {

  private TestDataStaffVersionEntity() {
    throw new IllegalStateException("Utility class");
  }

  public static final LocalDateTime VALID_TO = LocalDateTime.now().plusYears(1);
  public static final StaffVersionEntity AJLA_DOKTOR_VERSION_ENTITY = ajlaDoctorVersionEntityBuilder().build();

  public static final StaffVersionEntity ALF_DOKTOR_VERSION_ENTITY = alfDoktorVersionEntityBuilder().build();


  public static StaffVersionEntity.StaffVersionEntityBuilder ajlaDoctorVersionEntityBuilder() {
    return StaffVersionEntity.builder()
        .hsaId(AJLA_DOCTOR_HSA_ID)
        .firstName(AJLA_DOCTOR_FIRST_NAME)
        .middleName(AJLA_DOCTOR_MIDDLE_NAME)
        .lastName(AJLA_DOCTOR_LAST_NAME)
        .validTo(VALID_TO)
        .role(
            StaffRoleEntity.builder()
                .role(StaffRole.DOCTOR.name())
                .key(StaffRole.DOCTOR.getKey())
                .build()
        )
        .paTitles(
            AJLA_DOCTOR_PA_TITLES.stream()
                .map(paTitle -> PaTitleVersionEmbeddable.builder()
                    .code(paTitle.code())
                    .description(paTitle.description())
                    .build()
                )
                .toList()
        )
        .specialities(
            AJLA_DOCTOR_SPECIALITIES.stream()
                .map(speciality -> SpecialityVersionEmbeddable.builder()
                    .speciality(speciality.value())
                    .build()
                )
                .toList()
        )
        .healthcareProfessionalLicences(
            AJLA_DOCTOR_HEALTH_CARE_PROFESSIONAL_LICENCES.stream()
                .map(
                    healthCareProfessionalLicence ->
                        HealthcareProfessionalLicenceVersionEmbeddable.builder()
                            .healthcareProfessionalLicence(healthCareProfessionalLicence.value())
                            .build()
                )
                .toList()
        )
        .staff(AJLA_DOKTOR_ENTITY);
  }

  public static StaffVersionEntity.StaffVersionEntityBuilder alfDoktorVersionEntityBuilder() {
    return StaffVersionEntity.builder()
        .hsaId(ALF_DOKTOR_HSA_ID)
        .firstName(ALF_DOKTOR_FIRST_NAME)
        .middleName(ALF_DOKTOR_MIDDLE_NAME)
        .lastName(ALF_DOKTOR_LAST_NAME)
        .validTo(VALID_TO)
        .role(
            StaffRoleEntity.builder()
                .role(StaffRole.DOCTOR.name())
                .key(StaffRole.DOCTOR.getKey())
                .build()
        )
        .paTitles(
            ALF_DOKTOR_PA_TITLES.stream()
                .map(paTitle -> PaTitleVersionEmbeddable.builder()
                    .code(paTitle.code())
                    .description(paTitle.description())
                    .build()
                )
                .toList()
        )
        .specialities(
            ALF_DOKTOR_SPECIALITIES.stream()
                .map(speciality -> SpecialityVersionEmbeddable.builder()
                    .speciality(speciality.value())
                    .build()
                )
                .toList()
        )
        .healthcareProfessionalLicences(
            ALF_DOCTOR_HEALTH_CARE_PROFESSIONAL_LICENCES.stream()
                .map(
                    healthCareProfessionalLicence ->
                        HealthcareProfessionalLicenceVersionEmbeddable.builder()
                            .healthcareProfessionalLicence(healthCareProfessionalLicence.value())
                            .build()
                )
                .toList()
        )
        .staff(ALF_DOKTOR_ENTITY);
  }
}
