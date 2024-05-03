package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.certificate.model.Status.SIGNED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;

import java.time.LocalDateTime;
import java.time.ZoneId;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;

public class TestDataRelation {

  private TestDataRelation() {

  }

  public static final Relation RELATION_REPLACE = relationReplaceBuilder().build();

  public static Relation.RelationBuilder relationReplaceBuilder() {
    return Relation.builder()
        .certificateId(CERTIFICATE_ID)
        .type(RelationType.REPLACE)
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .status(SIGNED);
  }
}
