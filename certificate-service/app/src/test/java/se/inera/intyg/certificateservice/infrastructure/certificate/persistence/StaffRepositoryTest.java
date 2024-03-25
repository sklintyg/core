package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.application.testdata.TestDataStaffEntity.AJLA_DOKTOR_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataStaffEntity.ALF_DOKTOR_ENTITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7211CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.ALF_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALF_DOKTOR_HSA_ID;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Sent;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffRole;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffRoleEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffEntityRepository;

@ExtendWith(MockitoExtension.class)
class StaffRepositoryTest {

  @Mock
  private StaffEntityRepository staffEntityRepository;
  @InjectMocks
  private StaffRepository staffRepository;

  private static final StaffEntity STAFF_ENTITY = StaffEntity.builder()
      .hsaId(AJLA_DOCTOR_HSA_ID)
      .firstName(AJLA_DOCTOR_FIRST_NAME)
      .middleName(AJLA_DOCTOR_MIDDLE_NAME)
      .lastName(AJLA_DOCTOR_LAST_NAME)
      .role(StaffRoleEntity.builder()
          .role(StaffRole.DOCTOR.name())
          .key(StaffRole.DOCTOR.getKey())
          .build())
      .build();

  @Test
  void shallReturnEntityFromRepositoryIfExists() {
    doReturn(Optional.of(STAFF_ENTITY))
        .when(staffEntityRepository).findByHsaId(AJLA_DOCTOR_HSA_ID);
    assertEquals(STAFF_ENTITY,
        staffRepository.staff(AJLA_DOKTOR)
    );
  }

  @Test
  void shallReturnMappedEntityIfEntityDontExistInRepository() {
    doReturn(Optional.empty())
        .when(staffEntityRepository).findByHsaId(AJLA_DOCTOR_HSA_ID);
    doReturn(STAFF_ENTITY)
        .when(staffEntityRepository).save(STAFF_ENTITY);

    assertEquals(STAFF_ENTITY,
        staffRepository.staff(AJLA_DOKTOR)
    );
  }

  @Test
  void shallReturnMapOfOneStaffWhenOneUniqueStaffExists() {
    final var expectedStaffs = Map.of(AJLA_DOKTOR_ENTITY.getHsaId(), AJLA_DOKTOR_ENTITY);
    final var certificate = fk7211CertificateBuilder()
        .sent(
            Sent.builder()
                .sentBy(AJLA_DOKTOR)
                .build()
        )
        .build();

    doReturn(List.of(AJLA_DOKTOR_ENTITY))
        .when(staffEntityRepository)
        .findStaffEntitiesByHsaIdIn(List.of(AJLA_DOCTOR_HSA_ID));

    final var actualStaffs = staffRepository.staffs(certificate);

    assertEquals(expectedStaffs, actualStaffs);
  }

  @Test
  void shallReturnMapOfOneStaffWhenOneUniqueStaffDontExists() {
    final var expectedStaffs = Map.of(AJLA_DOKTOR_ENTITY.getHsaId(), AJLA_DOKTOR_ENTITY);
    final var certificate = fk7211CertificateBuilder()
        .sent(
            Sent.builder()
                .sentBy(AJLA_DOKTOR)
                .build()
        )
        .build();

    doReturn(Collections.emptyList())
        .when(staffEntityRepository)
        .findStaffEntitiesByHsaIdIn(List.of(AJLA_DOCTOR_HSA_ID));

    doReturn(AJLA_DOKTOR_ENTITY)
        .when(staffEntityRepository)
        .save(AJLA_DOKTOR_ENTITY);

    final var actualStaffs = staffRepository.staffs(certificate);

    assertEquals(expectedStaffs, actualStaffs);
  }

  @Test
  void shallReturnMapOfTwoStaffWhenTwoUniqueStaffBothExists() {
    final var expectedStaffs = Map.of(
        AJLA_DOKTOR_ENTITY.getHsaId(), AJLA_DOKTOR_ENTITY,
        ALF_DOKTOR_ENTITY.getHsaId(), ALF_DOKTOR_ENTITY
    );

    final var certificate = fk7211CertificateBuilder()
        .sent(
            Sent.builder()
                .sentBy(ALF_DOKTOR)
                .build()
        )
        .build();

    doReturn(List.of(AJLA_DOKTOR_ENTITY, ALF_DOKTOR_ENTITY))
        .when(staffEntityRepository)
        .findStaffEntitiesByHsaIdIn(List.of(AJLA_DOCTOR_HSA_ID, ALF_DOKTOR_HSA_ID));

    final var actualStaffs = staffRepository.staffs(certificate);

    assertEquals(expectedStaffs, actualStaffs);
  }

  @Test
  void shallReturnMapOfTwoStaffWhenTwoUniqueStaffOneExists() {
    final var expectedStaffs = Map.of(
        AJLA_DOKTOR_ENTITY.getHsaId(), AJLA_DOKTOR_ENTITY,
        ALF_DOKTOR_ENTITY.getHsaId(), ALF_DOKTOR_ENTITY
    );

    final var certificate = fk7211CertificateBuilder()
        .sent(
            Sent.builder()
                .sentBy(ALF_DOKTOR)
                .build()
        )
        .build();

    doReturn(List.of(AJLA_DOKTOR_ENTITY))
        .when(staffEntityRepository)
        .findStaffEntitiesByHsaIdIn(List.of(AJLA_DOCTOR_HSA_ID, ALF_DOKTOR_HSA_ID));

    doReturn(ALF_DOKTOR_ENTITY)
        .when(staffEntityRepository)
        .save(ALF_DOKTOR_ENTITY);

    final var actualStaffs = staffRepository.staffs(certificate);

    assertEquals(expectedStaffs, actualStaffs);
  }

  @Test
  void shallReturnMapOfTwoStaffWhenTwoUniqueStaffNoneExists() {
    final var expectedStaffs = Map.of(
        AJLA_DOKTOR_ENTITY.getHsaId(), AJLA_DOKTOR_ENTITY,
        ALF_DOKTOR_ENTITY.getHsaId(), ALF_DOKTOR_ENTITY
    );

    final var certificate = fk7211CertificateBuilder()
        .sent(
            Sent.builder()
                .sentBy(ALF_DOKTOR)
                .build()
        )
        .build();

    doReturn(Collections.emptyList())
        .when(staffEntityRepository)
        .findStaffEntitiesByHsaIdIn(List.of(AJLA_DOCTOR_HSA_ID, ALF_DOKTOR_HSA_ID));

    doReturn(AJLA_DOKTOR_ENTITY)
        .when(staffEntityRepository)
        .save(AJLA_DOKTOR_ENTITY);

    doReturn(ALF_DOKTOR_ENTITY)
        .when(staffEntityRepository)
        .save(ALF_DOKTOR_ENTITY);

    final var actualStaffs = staffRepository.staffs(certificate);

    assertEquals(expectedStaffs, actualStaffs);
  }
}