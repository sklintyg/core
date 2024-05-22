package se.inera.intyg.certificateservice.application.message.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationTypeDTO.COMPLEMENTED;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;

class CertificateRelationConverterTest {

  private static final CertificateId CHILD_CERTIFICATE_ID = new CertificateId("childCertificateId");
  private CertificateRelationConverter certificateRelationConverter;
  private Certificate certificate;

  @BeforeEach
  void setUp() {
    certificateRelationConverter = new CertificateRelationConverter();
    certificate = Certificate.builder()
        .children(
            List.of(
                Relation.builder()
                    .type(RelationType.COMPLEMENT)
                    .certificate(
                        Certificate.builder()
                            .id(CHILD_CERTIFICATE_ID)
                            .status(Status.SIGNED)
                            .build()
                    )
                    .created(LocalDateTime.now())
                    .build()
            )
        )
        .build();
  }

  @Test
  void shallIncludeCertificateId() {
    assertEquals(CHILD_CERTIFICATE_ID.id(),
        certificateRelationConverter.convert(certificate).getCertificateId());
  }

  @Test
  void shallIncludeStatus() {
    assertEquals(CertificateStatusTypeDTO.SIGNED,
        certificateRelationConverter.convert(certificate).getStatus());
  }

  @Test
  void shallIncludeCreated() {
    assertNotNull(certificateRelationConverter.convert(certificate).getCreated());
  }

  @Test
  void shallIncludeType() {
    assertEquals(COMPLEMENTED, certificateRelationConverter.convert(certificate).getType());
  }

  @Test
  void shallFilterChildrenThatsNotComplemented() {
    assertNull(certificateRelationConverter.convert(Certificate.builder()
        .children(
            List.of(
                Relation.builder()
                    .type(RelationType.REPLACE)
                    .build()
            )
        )
        .build())
    );
  }

  @Test
  void shallReturnLastestRelationIfMultipleComplementedRelationsArePresent() {
    final var now = LocalDateTime.now();
    final var convert = certificateRelationConverter.convert(
        Certificate.builder()
            .children(
                List.of(
                    Relation.builder()
                        .certificate(
                            Certificate.builder()
                                .id(CHILD_CERTIFICATE_ID)
                                .status(Status.SIGNED)
                                .build()
                        )
                        .type(RelationType.COMPLEMENT)
                        .created(now)
                        .build(),
                    Relation.builder()
                        .type(RelationType.COMPLEMENT)
                        .created(LocalDateTime.now().minusDays(1))
                        .build()
                )
            )
            .build());
    assertEquals(now, convert.getCreated());
  }
}
