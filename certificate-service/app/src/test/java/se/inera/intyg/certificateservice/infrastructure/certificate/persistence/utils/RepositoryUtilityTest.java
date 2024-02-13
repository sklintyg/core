package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.utils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.HsaId;
import se.inera.intyg.certificateservice.domain.certificate.model.Staff;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.StaffEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.StaffEntityRepository;

@ExtendWith(MockitoExtension.class)
class RepositoryUtilityTest {

  private static final StaffEntity ENTITY = StaffEntity.builder()
      .hsaId("ID")
      .name("NAME")
      .build();

  private static final Staff STAFF = Staff.builder()
      .hsaId(new HsaId("HSA_ID_STAFF"))
      .name(
          Name.builder()
              .lastName("NAME_STAFF")
              .build()
      )
      .build();

  private static final StaffEntity SAVED_ENTITY = StaffEntityMapper.toEntity(STAFF);
  private static final StaffEntity CONVERTED_ENTITY = StaffEntityMapper.toEntity(STAFF);

  @Mock
  StaffEntityRepository repository;

  @Nested
  class SaveIfNotExists {

    @Test
    void shouldReturnSavedEntityIfEntityIsNull() {
      when(repository.save(CONVERTED_ENTITY))
          .thenReturn(SAVED_ENTITY);

      final var response = RepositoryUtility.saveIfNotExists(
          null, STAFF, StaffEntityMapper::toEntity, repository
      );

      assertEquals(SAVED_ENTITY, response);
    }

    @Test
    void shouldReturnEntityIfDefined() {
      final var response = RepositoryUtility.saveIfNotExists(ENTITY, STAFF,
          StaffEntityMapper::toEntity, repository
      );

      assertEquals(ENTITY, response);
    }
  }

  @Nested
  class GetEntity {

    @Test
    void shouldReturnEntityIfDifferentFromNull() {
      final var response = RepositoryUtility.getEntity(ENTITY, STAFF, StaffEntityMapper::toEntity);

      assertEquals(ENTITY, response);
    }

    @Test
    void shouldReturnConvertedEntityIfNull() {
      final var response = RepositoryUtility.getEntity(null, STAFF, StaffEntityMapper::toEntity);

      assertEquals(CONVERTED_ENTITY, response);
    }
  }

}