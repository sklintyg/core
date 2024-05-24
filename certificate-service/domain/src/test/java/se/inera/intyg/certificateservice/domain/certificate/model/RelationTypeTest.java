package se.inera.intyg.certificateservice.domain.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.domain.certificate.model.RelationType.COMPLEMENT;
import static se.inera.intyg.certificateservice.domain.certificate.model.RelationType.RENEW;
import static se.inera.intyg.certificateservice.domain.certificate.model.RelationType.REPLACE;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class RelationTypeTest {

  @Nested
  class ToRelationKodTests {

    @Test
    void shallConvertReplace() {
      assertEquals("ERSATT", REPLACE.toRelationKod());
    }

    @Test
    void shallConvertRenew() {
      assertEquals("FRLANG", RENEW.toRelationKod());
    }

    @Test
    void shallConvertComplement() {
      assertEquals("KOMPLT", COMPLEMENT.toRelationKod());
    }
  }

  @Nested
  class ToRelationKodTextTests {

    @Test
    void shallConvertReplace() {
      assertEquals("Ersätter", REPLACE.toRelationKodText());
    }

    @Test
    void shallConvertRenew() {
      assertEquals("Förlänger", RENEW.toRelationKodText());
    }

    @Test
    void shallConvertComplement() {
      assertEquals("Kompletterar", COMPLEMENT.toRelationKodText());
    }
  }
}
