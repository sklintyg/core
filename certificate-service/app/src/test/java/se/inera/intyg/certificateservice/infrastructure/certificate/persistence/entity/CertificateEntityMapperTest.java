package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import static org.junit.jupiter.api.Assertions.*;

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

  private final static CertificateEntity ENTITY = CertificateEntity.builder()
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
  class ToDomain {

    @Test
    void shouldMapId() {
      final var response = CertificateEntityMapper.toDomain(ENTITY);

      assertEquals(ENTITY.getCertificateId(), response.id().id());
    }

    @Test
    void shouldMapCreated() {
      final var response = CertificateEntityMapper.toDomain(ENTITY);

      assertEquals(ENTITY.getCreated(), response.created());
    }
  }

}