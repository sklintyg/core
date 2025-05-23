package se.inera.intyg.intygproxyservice.person.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.intygproxyservice.person.dto.PersonResponse.PersonResponseBuilder;

@JsonDeserialize(builder = PersonResponseBuilder.class)
@Value
@Builder
public class PersonResponse {

  PersonDTO person;
  StatusDTOType status;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PersonResponseBuilder {

  }
}
