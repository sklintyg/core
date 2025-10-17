package se.inera.intyg.certificateservice.application.testdata;

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

import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.HealthcareProfessionalLicenceEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PaTitleEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.SpecialityEmbeddable;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffRole;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffRoleEntity;

public class TestDataStaffEntity {

  private TestDataStaffEntity() {
    throw new IllegalStateException("Utility class");
  }

  public static final StaffEntity AJLA_DOKTOR_ENTITY = ajlaDoctorEntityBuilder().build();
  public static final StaffEntity ALF_DOKTOR_ENTITY = alfDoktorEntityBuilder().build();

  public static StaffEntity.StaffEntityBuilder ajlaDoctorEntityBuilder() {
    return StaffEntity.builder()
        .hsaId(AJLA_DOCTOR_HSA_ID)
        .firstName(AJLA_DOCTOR_FIRST_NAME)
        .middleName(AJLA_DOCTOR_MIDDLE_NAME)
        .lastName(AJLA_DOCTOR_LAST_NAME)
        .role(
            StaffRoleEntity.builder()
                .role(StaffRole.DOCTOR.name())
                .key(StaffRole.DOCTOR.getKey())
                .build()
        )
        .paTitles(
            AJLA_DOCTOR_PA_TITLES.stream()
                .map(paTitle -> PaTitleEmbeddable.builder()
                    .code(paTitle.code())
                    .description(paTitle.description())
                    .build()
                )
                .toList()
        )
        .specialities(
            AJLA_DOCTOR_SPECIALITIES.stream()
                .map(speciality -> SpecialityEmbeddable.builder()
                    .speciality(speciality.value())
                    .build()
                )
                .toList()
        )
        .healthcareProfessionalLicences(
            AJLA_DOCTOR_HEALTH_CARE_PROFESSIONAL_LICENCES.stream()
                .map(
                    healthCareProfessionalLicence -> HealthcareProfessionalLicenceEmbeddable.builder()
                        .healthcareProfessionalLicence(healthCareProfessionalLicence.value())
                        .build()
                )
                .toList()
        );
  }
  
  public static StaffEntity.StaffEntityBuilder alfDoktorEntityBuilder() {
    return StaffEntity.builder()
        .hsaId(ALF_DOKTOR_HSA_ID)
        .firstName(ALF_DOKTOR_FIRST_NAME)
        .middleName(ALF_DOKTOR_MIDDLE_NAME)
        .lastName(ALF_DOKTOR_LAST_NAME)
        .role(
            StaffRoleEntity.builder()
                .role(StaffRole.DOCTOR.name())
                .key(StaffRole.DOCTOR.getKey())
                .build()
        )
        .paTitles(
            ALF_DOKTOR_PA_TITLES.stream()
                .map(paTitle -> PaTitleEmbeddable.builder()
                    .code(paTitle.code())
                    .description(paTitle.description())
                    .build()
                )
                .toList()
        )
        .specialities(
            ALF_DOKTOR_SPECIALITIES.stream()
                .map(speciality -> SpecialityEmbeddable.builder()
                    .speciality(speciality.value())
                    .build()
                )
                .toList()
        )
        .healthcareProfessionalLicences(
            ALF_DOCTOR_HEALTH_CARE_PROFESSIONAL_LICENCES.stream()
                .map(
                    healthCareProfessionalLicence -> HealthcareProfessionalLicenceEmbeddable.builder()
                        .healthcareProfessionalLicence(healthCareProfessionalLicence.value())
                        .build()
                )
                .toList()
        );
  }
}
