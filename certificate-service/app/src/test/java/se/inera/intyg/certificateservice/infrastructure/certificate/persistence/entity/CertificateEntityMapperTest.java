package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.CertificateEntityMapper;

class CertificateEntityMapperTest {

  private final static Certificate CERTIFICATE = Certificate.builder()
      .id(new CertificateId("ID"))
      .created(LocalDateTime.now().minusDays(2))
      .build();

  public static final CertificateEntity CERTIFICATE_ENTITY = CertificateEntity.builder()
      .version(1L)
      .modified(LocalDateTime.now())
      .certificateId("ID")
      .created(LocalDateTime.now())
      .build();

  @Nested
  class ToEntity {

    @Test
    void shouldMapId() {
      final var response = CertificateEntityMapper.toEntity(CERTIFICATE);

      assertEquals(CERTIFICATE.id().id(), response.getCertificateId());
    }

    @Test
    void shouldMapCreated() {
      final var response = CertificateEntityMapper.toEntity(CERTIFICATE);

      assertEquals(CERTIFICATE.created(), response.getCreated());
    }

  }

  @Nested
  class UpdateEntity {

    @Test
    void shouldSetVersion() {
      final var response = CertificateEntityMapper.updateEntity(CERTIFICATE_ENTITY);
      assertEquals(CERTIFICATE_ENTITY.getVersion(), response.getVersion());

    }

    @Test
    void shouldSetModified() {
      final var response = CertificateEntityMapper.updateEntity(CERTIFICATE_ENTITY);
      assertEquals(CERTIFICATE_ENTITY.getModified(), response.getModified());

    }
  }

  @Nested
  class ToDomain {

    @Test
    void shouldMapId() {
      final var response = CertificateEntityMapper.toDomain(CERTIFICATE_ENTITY);

      assertEquals(CERTIFICATE_ENTITY.getCertificateId(), response.id().id());
    }

    @Test
    void shouldMapCreated() {
      final var response = CertificateEntityMapper.toDomain(CERTIFICATE_ENTITY);

      assertEquals(CERTIFICATE_ENTITY.getCreated(), response.created());
    }
  }

}