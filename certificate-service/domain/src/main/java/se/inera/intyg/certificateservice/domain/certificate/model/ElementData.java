package se.inera.intyg.certificateservice.domain.certificate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import lombok.extern.jackson.Jacksonized;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

@Value
@Builder
@Jacksonized
public class ElementData {

  ElementId id;
  @With
  ElementValue value;

  @JsonCreator
  public ElementData(@JsonProperty("id") ElementId id, @JsonProperty("value") ElementValue value) {
    this.id = id;
    this.value = value;
  }
}