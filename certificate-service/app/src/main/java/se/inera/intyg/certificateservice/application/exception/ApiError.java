package se.inera.intyg.certificateservice.application.exception;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.exception.ApiError.ApiErrorBuilder;

@JsonDeserialize(builder = ApiErrorBuilder.class)
@Value
@Builder
public class ApiError {

  String message;

  @JsonPOJOBuilder(withPrefix = "")
  public static class ApiErrorBuilder {

  }
}
