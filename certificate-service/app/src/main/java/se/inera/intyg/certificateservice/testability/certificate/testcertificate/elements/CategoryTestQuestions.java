package se.inera.intyg.certificateservice.testability.certificate.testcertificate.elements;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

public class CategoryTestQuestions {

  private CategoryTestQuestions() {
    throw new IllegalStateException("Utility class");
  }

  public static ElementSpecification categoryTestQuestions(String id, String name,
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(new ElementId(id))
        .configuration(
            ElementConfigurationCategory.builder()
                .name(name)
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }
}