package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_MIDDLE_NAME;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
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
}