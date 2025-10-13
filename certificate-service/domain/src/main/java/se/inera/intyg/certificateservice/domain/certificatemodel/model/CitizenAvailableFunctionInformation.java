package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@JsonDeserialize(builder = CitizenAvailableFunctionInformation.InformationDTOBuilder.class)
@Value
@Builder
public class CitizenAvailableFunctionInformation {

  ElementId id;
  String text;
  CitizenAvailableFunctionInformationType type;

  @JsonPOJOBuilder(withPrefix = "")
  public static class InformationDTOBuilder {

  }
}
