package se.inera.intyg.certificateservice.patient.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.patient.dto.PersonsResponse.PersonsResponseBuilder;

@JsonDeserialize(builder = PersonsResponseBuilder.class)
@Value
@Builder
public class PersonsResponse {

  List<PersonResponse> persons;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PersonsResponseBuilder {

  }
}