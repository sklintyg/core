package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.application.testdata.TestDataUnitEntity.ALFA_ALLERGIMOTTAGNINGEN_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataUnitEntity.ALFA_MEDICINCENTRUM_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataUnitEntity.ALFA_REGIONEN_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataUnitEntity.alfaAllergimottagningenEntityBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataUnitEntity.alfaMedicinCentrumEntityBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataUnitEntity.alfaRegionenEntityBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ID;

import jakarta.persistence.EntityManager;
import jakarta.persistence.OptimisticLockException;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitVersionEntityRepository;

@ExtendWith(MockitoExtension.class)
class UnitRepositoryTest {

  @Mock
  private UnitEntityRepository unitEntityRepository;
  @Mock
  private UnitVersionEntityRepository unitVersionEntityRepository;
  @Mock
  MetadataVersionRepository metadataVersionRepository;
  @Mock
  EntityManager entityManager;
  @InjectMocks
  private UnitRepository unitRepository;

  @Nested
  class CareProvider {

    @Test
    void shallReturnEntityFromRepositoryIfExists() {
      doReturn(Optional.of(ALFA_REGIONEN_ENTITY))
          .when(unitEntityRepository).findByHsaId(ALFA_REGIONEN_ID);
      assertEquals(ALFA_REGIONEN_ENTITY,
          unitRepository.careProvider(ALFA_REGIONEN)
      );
    }

    @Test
    void shallReturnMappedEntityIfEntityDontExistInRepository() {
      doReturn(Optional.empty())
          .when(unitEntityRepository).findByHsaId(ALFA_REGIONEN_ID);
      doReturn(ALFA_REGIONEN_ENTITY)
          .when(unitEntityRepository).save(ALFA_REGIONEN_ENTITY);

      assertEquals(ALFA_REGIONEN_ENTITY,
          unitRepository.careProvider(ALFA_REGIONEN)
      );
    }
  }

  @Nested
  class CareUnit {

    @Test
    void shallReturnEntityFromRepositoryIfExists() {
      doReturn(Optional.of(ALFA_MEDICINCENTRUM_ENTITY))
          .when(unitEntityRepository).findByHsaId(ALFA_MEDICINCENTRUM_ID);
      assertEquals(ALFA_MEDICINCENTRUM_ENTITY,
          unitRepository.careUnit(ALFA_MEDICINCENTRUM)
      );
    }

    @Test
    void shallReturnMappedEntityIfEntityDontExistInRepository() {
      doReturn(Optional.empty())
          .when(unitEntityRepository).findByHsaId(ALFA_MEDICINCENTRUM_ID);
      doReturn(ALFA_MEDICINCENTRUM_ENTITY)
          .when(unitEntityRepository).save(ALFA_MEDICINCENTRUM_ENTITY);

      assertEquals(ALFA_MEDICINCENTRUM_ENTITY,
          unitRepository.careUnit(ALFA_MEDICINCENTRUM)
      );
    }
  }

  @Nested
  class SubUnit {

    @Test
    void shallReturnEntityFromRepositoryIfExists() {
      doReturn(Optional.of(ALFA_ALLERGIMOTTAGNINGEN_ENTITY))
          .when(unitEntityRepository).findByHsaId(ALFA_ALLERGIMOTTAGNINGEN_ID);

      assertEquals(ALFA_ALLERGIMOTTAGNINGEN_ENTITY,
          unitRepository.subUnit(ALFA_ALLERGIMOTTAGNINGEN)
      );
    }

    @Test
    void shallReturnMappedEntityIfEntityDontExistInRepository() {
      doReturn(Optional.empty())
          .when(unitEntityRepository).findByHsaId(ALFA_ALLERGIMOTTAGNINGEN_ID);
      doReturn(ALFA_ALLERGIMOTTAGNINGEN_ENTITY)
          .when(unitEntityRepository).save(ALFA_ALLERGIMOTTAGNINGEN_ENTITY);

      assertEquals(ALFA_ALLERGIMOTTAGNINGEN_ENTITY,
          unitRepository.subUnit(ALFA_ALLERGIMOTTAGNINGEN)

      );
    }
  }

  @Nested
  class IssuingUnit {

    @Test
    void shallReturnSubUnitIfSubUnit() {
      doReturn(Optional.of(ALFA_ALLERGIMOTTAGNINGEN_ENTITY))
          .when(unitEntityRepository).findByHsaId(ALFA_ALLERGIMOTTAGNINGEN_ID);

      assertEquals(ALFA_ALLERGIMOTTAGNINGEN_ENTITY,
          unitRepository.issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
      );
    }

    @Test
    void shallReturnCareUnitIfCareUnit() {
      doReturn(Optional.of(ALFA_MEDICINCENTRUM_ENTITY))
          .when(unitEntityRepository).findByHsaId(ALFA_MEDICINCENTRUM_ID);
      assertEquals(ALFA_MEDICINCENTRUM_ENTITY,
          unitRepository.issuingUnit(ALFA_MEDICINCENTRUM)
      );
    }
  }

  @Nested
  class UpdateEntitiesTest {

    @Test
    void shallReturnUpdatedCareProviderIfOptimisticLockIsThrown() {

      final var UPPDATERAD_ALFA_REGIONEN_ENTITY =
          alfaRegionenEntityBuilder().name("gammaltNamn").build();

      doReturn(Optional.of(UPPDATERAD_ALFA_REGIONEN_ENTITY)).when(unitEntityRepository).findByHsaId(
          ALFA_REGIONEN_ID);
      doThrow(OptimisticLockException.class).when(metadataVersionRepository)
          .saveUnitVersion(UPPDATERAD_ALFA_REGIONEN_ENTITY, ALFA_REGIONEN_ENTITY);

      final var result = unitRepository.careProvider(ALFA_REGIONEN);

      verify(entityManager).refresh(UPPDATERAD_ALFA_REGIONEN_ENTITY);
      assertEquals(UPPDATERAD_ALFA_REGIONEN_ENTITY, result);
    }

    @Test
    void shallReturnUpdatedCareUnitIfOptimisticLockIsThrown() {

      final var UPPDATERAD_ALFA_MEDICINCENTRUM_ENTITY = alfaMedicinCentrumEntityBuilder().name(
          "gammaltNamn").build();

      doReturn(Optional.of(UPPDATERAD_ALFA_MEDICINCENTRUM_ENTITY)).when(unitEntityRepository)
          .findByHsaId(ALFA_MEDICINCENTRUM_ID);
      doThrow(OptimisticLockException.class).when(metadataVersionRepository)
          .saveUnitVersion(UPPDATERAD_ALFA_MEDICINCENTRUM_ENTITY, ALFA_MEDICINCENTRUM_ENTITY);

      final var result = unitRepository.careUnit(ALFA_MEDICINCENTRUM);

      verify(entityManager).refresh(UPPDATERAD_ALFA_MEDICINCENTRUM_ENTITY);
      assertEquals(UPPDATERAD_ALFA_MEDICINCENTRUM_ENTITY, result);

    }

    @Test
    void shallReturnUpdatedSubUnitIfOptimisticLockIsThrown() {

      final var UPPDATERAD_ALFA_ALLERGIMOTTAGNINGEN_ENTITY =
          alfaAllergimottagningenEntityBuilder().name("gammaltNamn").build();

      doReturn(Optional.of(UPPDATERAD_ALFA_ALLERGIMOTTAGNINGEN_ENTITY)).when(unitEntityRepository)
          .findByHsaId(ALFA_ALLERGIMOTTAGNINGEN_ID);
      doThrow(OptimisticLockException.class).when(metadataVersionRepository)
          .saveUnitVersion(UPPDATERAD_ALFA_ALLERGIMOTTAGNINGEN_ENTITY,
              ALFA_ALLERGIMOTTAGNINGEN_ENTITY);

      final var result = unitRepository.issuingUnit(ALFA_ALLERGIMOTTAGNINGEN);

      verify(entityManager).refresh(UPPDATERAD_ALFA_ALLERGIMOTTAGNINGEN_ENTITY);
      assertEquals(UPPDATERAD_ALFA_ALLERGIMOTTAGNINGEN_ENTITY, result);
    }
  }
}