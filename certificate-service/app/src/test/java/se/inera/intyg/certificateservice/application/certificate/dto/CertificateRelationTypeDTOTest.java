package se.inera.intyg.certificateservice.application.certificate.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;

class CertificateRelationTypeDTOTest {

  @Test
  void shallConvertReplace() {
    assertEquals(CertificateRelationTypeDTO.REPLACED,
        CertificateRelationTypeDTO.toType(RelationType.REPLACE)
    );
  }

  @Test
  void shallConvertRenew() {
    assertEquals(CertificateRelationTypeDTO.EXTENDED,
        CertificateRelationTypeDTO.toType(RelationType.RENEW)
    );
  }

  @Test
  void shallConvertComplement() {
    assertEquals(CertificateRelationTypeDTO.COMPLEMENTED,
        CertificateRelationTypeDTO.toType(RelationType.COMPLEMENT)
    );
  }
}