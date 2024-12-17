package se.inera.intyg.certificateservice.domain.validation.model;

import java.util.List;
import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

public interface ElementValidation {

  List<ValidationError> validate(ElementData data, Optional<ElementId> categoryId,
      List<ElementData> dataList);
}