package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateModelEntity;

class CertificateModelEntityMapperTest {

  private static final CertificateModel MODEL = CertificateModel.builder()
      .name("NAME")
      .id(
          CertificateModelId.builder()
              .type(new CertificateType("TYPE"))
              .version(new CertificateVersion("VERSION"))
              .build()
      )
      .build();

  private static final CertificateModelEntity ENTITY = CertificateModelEntity.builder()
      .type("TYPE")
      .version("VERSION")
      .name("NAME")
      .build();

  @Nested
  class ToEntity {

    @Test
    void shouldMapType() {
      final var response = CertificateModelEntityMapper.toEntity(MODEL);

      assertEquals(MODEL.id().type().type(), response.getType());
    }

    @Test
    void shouldMapVersion() {
      final var response = CertificateModelEntityMapper.toEntity(MODEL);

      assertEquals(MODEL.id().version().version(), response.getVersion());
    }

    @Test
    void shouldMapName() {
      final var response = CertificateModelEntityMapper.toEntity(MODEL);

      assertEquals(MODEL.name(), response.getName());
    }
  }

  @Nested
  class ToDomain {

    @Test
    void shouldMapType() {
      final var response = CertificateModelEntityMapper.toDomain(ENTITY);

      assertEquals(ENTITY.getType(), response.id().type().type());
    }

    @Test
    void shouldMapVersion() {
      final var response = CertificateModelEntityMapper.toDomain(ENTITY);

      assertEquals(ENTITY.getVersion(), response.id().version().version());
    }

    @Test
    void shouldMapName() {
      final var response = CertificateModelEntityMapper.toDomain(ENTITY);

      assertEquals(ENTITY.getName(), response.name());
    }
  }

}