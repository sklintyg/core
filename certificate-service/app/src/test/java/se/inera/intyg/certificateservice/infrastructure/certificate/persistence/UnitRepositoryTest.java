package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_NAME;

import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitTypeEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntityRepository;

@ExtendWith(MockitoExtension.class)
class UnitRepositoryTest {

  @Mock
  private UnitEntityRepository unitEntityRepository;
  @InjectMocks
  private UnitRepository unitRepository;

  private static final UnitEntity CARE_PROVIDER_ENTITY = UnitEntity.builder()
      .hsaId(ALFA_REGIONEN_ID)
      .name(ALFA_REGIONEN_NAME)
      .type(
          UnitTypeEntity.builder()
              .key(UnitType.CARE_PROVIDER.getKey())
              .type(UnitType.CARE_PROVIDER.name())
              .build()
      )
      .build();

  private static final UnitEntity CARE_UNIT_ENTITY = UnitEntity.builder()
      .hsaId(ALFA_MEDICINCENTRUM_ID)
      .name(ALFA_MEDICINCENTRUM_NAME)
      .type(
          UnitTypeEntity.builder()
              .key(UnitType.CARE_UNIT.getKey())
              .type(UnitType.CARE_UNIT.name())
              .build()
      )
      .build();

  private static final UnitEntity SUB_UNIT_ENTITY = UnitEntity.builder()
      .hsaId(ALFA_ALLERGIMOTTAGNINGEN_ID)
      .name(ALFA_ALLERGIMOTTAGNINGEN_NAME)
      .type(
          UnitTypeEntity.builder()
              .key(UnitType.SUB_UNIT.getKey())
              .type(UnitType.SUB_UNIT.name())
              .build()
      )
      .build();

  @Nested
  class CareProvider {

    @Test
    void shallReturnEntityFromRepositoryIfExists() {
      doReturn(Optional.of(CARE_PROVIDER_ENTITY))
          .when(unitEntityRepository).findByHsaId(ALFA_REGIONEN_ID);
      assertEquals(CARE_PROVIDER_ENTITY,
          unitRepository.careProvider(ALFA_REGIONEN)
      );
    }

    @Test
    void shallReturnMappedEntityIfEntityDontExistInRepository() {
      doReturn(Optional.empty())
          .when(unitEntityRepository).findByHsaId(ALFA_REGIONEN_ID);
      doReturn(CARE_PROVIDER_ENTITY)
          .when(unitEntityRepository).save(CARE_PROVIDER_ENTITY);

      assertEquals(CARE_PROVIDER_ENTITY,
          unitRepository.careProvider(ALFA_REGIONEN)
      );
    }
  }

  @Nested
  class CareUnit {

    @Test
    void shallReturnEntityFromRepositoryIfExists() {
      doReturn(Optional.of(CARE_UNIT_ENTITY))
          .when(unitEntityRepository).findByHsaId(ALFA_MEDICINCENTRUM_ID);
      assertEquals(CARE_UNIT_ENTITY,
          unitRepository.careUnit(ALFA_MEDICINCENTRUM)
      );
    }

    @Test
    void shallReturnMappedEntityIfEntityDontExistInRepository() {
      doReturn(Optional.empty())
          .when(unitEntityRepository).findByHsaId(ALFA_MEDICINCENTRUM_ID);
      doReturn(CARE_UNIT_ENTITY)
          .when(unitEntityRepository).save(CARE_UNIT_ENTITY);

      assertEquals(CARE_UNIT_ENTITY,
          unitRepository.careUnit(ALFA_MEDICINCENTRUM)
      );
    }
  }

  @Nested
  class SubUnit {

    @Test
    void shallReturnEntityFromRepositoryIfExists() {
      doReturn(Optional.of(SUB_UNIT_ENTITY))
          .when(unitEntityRepository).findByHsaId(ALFA_ALLERGIMOTTAGNINGEN_ID);

      assertEquals(SUB_UNIT_ENTITY,
          unitRepository.subUnit(ALFA_ALLERGIMOTTAGNINGEN)
      );
    }

    @Test
    void shallReturnMappedEntityIfEntityDontExistInRepository() {
      doReturn(Optional.empty())
          .when(unitEntityRepository).findByHsaId(ALFA_ALLERGIMOTTAGNINGEN_ID);
      doReturn(SUB_UNIT_ENTITY)
          .when(unitEntityRepository).save(SUB_UNIT_ENTITY);

      assertEquals(SUB_UNIT_ENTITY,
          unitRepository.subUnit(ALFA_ALLERGIMOTTAGNINGEN)

      );
    }
  }

  @Nested
  class IssuingUnit {

    @Test
    void shallReturnSubUnitIfSubUnit() {
      doReturn(Optional.of(SUB_UNIT_ENTITY))
          .when(unitEntityRepository).findByHsaId(ALFA_ALLERGIMOTTAGNINGEN_ID);

      assertEquals(SUB_UNIT_ENTITY,
          unitRepository.issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
      );
    }

    @Test
    void shallReturnCareUnitIfCareUnit() {
      doReturn(Optional.of(CARE_UNIT_ENTITY))
          .when(unitEntityRepository).findByHsaId(ALFA_MEDICINCENTRUM_ID);
      assertEquals(CARE_UNIT_ENTITY,
          unitRepository.issuingUnit(ALFA_MEDICINCENTRUM)
      );
    }
  }
}