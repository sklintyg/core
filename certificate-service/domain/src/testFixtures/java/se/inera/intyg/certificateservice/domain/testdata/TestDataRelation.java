package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7210_CERTIFICATE;

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
        .certificate(FK7210_CERTIFICATE)
        .type(RelationType.REPLACE)
        .created(LocalDateTime.now(ZoneId.systemDefault()));
  }
}
