package se.inera.intyg.certificateservice.application.message.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationTypeDTO.COMPLEMENTED;

import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;

class CertificateRelationConverterTest {

  private static final CertificateId CHILD_CERTIFICATE_ID = new CertificateId("childCertificateId");
  private CertificateRelationConverter certificateRelationConverter;
  private Optional<Relation> relation;

  @BeforeEach
  void setUp() {
    certificateRelationConverter = new CertificateRelationConverter();
    relation = Optional.of(
        Relation.builder()
            .type(RelationType.COMPLEMENT)
            .certificate(
                MedicalCertificate.builder()
                    .id(CHILD_CERTIFICATE_ID)
                    .status(Status.SIGNED)
                    .build()
            )
            .created(LocalDateTime.now())
            .build()
    );
  }

  @Test
  void shallIncludeCertificateId() {
    assertEquals(CHILD_CERTIFICATE_ID.id(),
        certificateRelationConverter.convert(relation)
            .getCertificateId());
  }

  @Test
  void shallIncludeStatus() {
    assertEquals(CertificateStatusTypeDTO.SIGNED,
        certificateRelationConverter.convert(relation).getStatus());
  }

  @Test
  void shallIncludeCreated() {
    assertNotNull(
        certificateRelationConverter.convert(relation).getCreated());
  }

  @Test
  void shallIncludeType() {
    assertEquals(COMPLEMENTED,
        certificateRelationConverter.convert(relation).getType());
  }

  @Test
  void shallReturnNullIfEmpty() {
    assertNull(certificateRelationConverter.convert(Optional.empty()));
  }
}