package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;

public interface ElementValidation {

  boolean validate(ElementData data);
}
