package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.application.testdata.TestDataStaffEntity.AJLA_DOKTOR_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataStaffEntity.ALF_DOKTOR_ENTITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7210CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.ALF_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
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
import se.inera.intyg.certificateservice.domain.certificate.model.ReadyForSign;
import se.inera.intyg.certificateservice.domain.certificate.model.Revoked;
import se.inera.intyg.certificateservice.domain.certificate.model.Sent;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffEntityRepository;

@ExtendWith(MockitoExtension.class)
class StaffRepositoryTest {

  @Mock
  private StaffEntityRepository staffEntityRepository;
  @Mock
  MetadataVersionRepository metadataVersionRepository;
  @InjectMocks
  private StaffRepository staffRepository;


  @Test
  void shallReturnEntityFromRepositoryIfExists() {
    doReturn(Optional.of(AJLA_DOKTOR_ENTITY))
        .when(staffEntityRepository).findByHsaId(AJLA_DOCTOR_HSA_ID);
    assertEquals(AJLA_DOKTOR_ENTITY,
        staffRepository.staff(AJLA_DOKTOR)
    );
  }

  @Test
  void shallReturnMappedEntityIfEntityDontExistInRepository() {
    doReturn(Optional.empty())
        .when(staffEntityRepository).findByHsaId(AJLA_DOCTOR_HSA_ID);
    doReturn(AJLA_DOKTOR_ENTITY)
        .when(staffEntityRepository).save(AJLA_DOKTOR_ENTITY);

    assertEquals(AJLA_DOKTOR_ENTITY,
        staffRepository.staff(AJLA_DOKTOR)
    );
  }

  @Test
  void shallReturnMapOfOneStaffWhenOneUniqueStaffExists() {
    final var expectedStaffs = Map.of(AJLA_DOKTOR.hsaId(), AJLA_DOKTOR_ENTITY);
    final var certificate = fk7210CertificateBuilder()
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
    final var expectedStaffs = Map.of(AJLA_DOKTOR.hsaId(), AJLA_DOKTOR_ENTITY);
    final var certificate = fk7210CertificateBuilder()
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
        AJLA_DOKTOR.hsaId(), AJLA_DOKTOR_ENTITY,
        ALF_DOKTOR.hsaId(), ALF_DOKTOR_ENTITY
    );

    final var certificate = fk7210CertificateBuilder()
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
        AJLA_DOKTOR.hsaId(), AJLA_DOKTOR_ENTITY,
        ALF_DOKTOR.hsaId(), ALF_DOKTOR_ENTITY
    );

    final var certificate = fk7210CertificateBuilder()
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
        AJLA_DOKTOR.hsaId(), AJLA_DOKTOR_ENTITY,
        ALF_DOKTOR.hsaId(), ALF_DOKTOR_ENTITY
    );

    final var certificate = fk7210CertificateBuilder()
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

  @Test
  void shallIncludeRevokedStaffInMap() {
    final var expectedStaffs = Map.of(
        AJLA_DOKTOR.hsaId(), AJLA_DOKTOR_ENTITY,
        ALF_DOKTOR.hsaId(), ALF_DOKTOR_ENTITY
    );

    final var certificate = fk7210CertificateBuilder()
        .revoked(
            Revoked.builder()
                .revokedBy(ALF_DOKTOR)
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

  @Test
  void shallIncludeReadyForSignStaffInMap() {
    final var expectedStaffs = Map.of(
        AJLA_DOKTOR.hsaId(), AJLA_DOKTOR_ENTITY,
        ALF_DOKTOR.hsaId(), ALF_DOKTOR_ENTITY
    );

    final var certificate = fk7210CertificateBuilder()
        .readyForSign(
            ReadyForSign.builder()
                .readyForSignBy(ALF_DOKTOR)
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

  @Test
  void shallUpdateStaffVersionWhenDifferencesExist() {
    final var existingEntity = mock(StaffEntity.class);
    final var updatedEntity = mock(StaffEntity.class);

    doReturn(Optional.of(existingEntity))
        .when(staffEntityRepository).findByHsaId(AJLA_DOCTOR_HSA_ID);
    doReturn(true).when(existingEntity).hasDiff(AJLA_DOKTOR_ENTITY);
    doReturn(updatedEntity)
        .when(metadataVersionRepository).saveStaffVersion(existingEntity, AJLA_DOKTOR_ENTITY);

    final var result = staffRepository.staff(AJLA_DOKTOR);

    assertEquals(updatedEntity, result);
    verify(metadataVersionRepository).saveStaffVersion(existingEntity, AJLA_DOKTOR_ENTITY);
  }

  @Test
  void shallReturnExistingStaffWhenNoDifferencesExist() {
    final var existingEntity = mock(StaffEntity.class);

    doReturn(Optional.of(existingEntity))
        .when(staffEntityRepository).findByHsaId(AJLA_DOCTOR_HSA_ID);
    doReturn(false).when(existingEntity).hasDiff(AJLA_DOKTOR_ENTITY);

    final var result = staffRepository.staff(AJLA_DOKTOR);

    assertEquals(existingEntity, result);
    verify(metadataVersionRepository, never()).saveStaffVersion(existingEntity, AJLA_DOKTOR_ENTITY);
  }
}