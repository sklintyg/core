package se.inera.intyg.certificateservice.patient.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.patient.dto.PersonsRequestDTO.PersonsRequestDTOBuilder;

@JsonDeserialize(builder = PersonsRequestDTOBuilder.class)
@Value
@Builder
public class PersonsRequestDTO {

  List<String> personIds;

  @JsonPOJOBuilder(withPrefix = "")
  public static class PersonsRequestDTOBuilder {

  }
}