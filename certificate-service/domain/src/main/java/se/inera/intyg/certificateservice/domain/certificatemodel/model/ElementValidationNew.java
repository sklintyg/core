package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.List;
import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.validation.model.ValidationError;

public interface ElementValidationNew {

  List<ValidationError> validate(Optional<ElementData> data, Optional<ElementId> categoryId);
}
