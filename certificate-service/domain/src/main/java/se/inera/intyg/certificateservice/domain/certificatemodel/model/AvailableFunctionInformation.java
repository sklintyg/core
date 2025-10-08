package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

@JsonDeserialize(builder = AvailableFunctionInformation.InformationDTOBuilder.class)
@Value
@Builder
public class AvailableFunctionInformation {

  ElementId id;
  String text;
  AvailableFunctionInformationType type;

  @JsonPOJOBuilder(withPrefix = "")
  public static class InformationDTOBuilder {

  }
}
