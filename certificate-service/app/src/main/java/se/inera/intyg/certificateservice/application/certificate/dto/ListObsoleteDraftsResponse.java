package se.inera.intyg.certificateservice.application.certificate.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.ListObsoleteDraftsResponse.ListObsoleteDraftsResponseBuilder;

@JsonDeserialize(builder = ListObsoleteDraftsResponseBuilder.class)
@Value
@Builder
public class ListObsoleteDraftsResponse {

  List<String> certificateIds;

  @JsonPOJOBuilder(withPrefix = "")
  public static class ListObsoleteDraftsResponseBuilder {

  }
}
