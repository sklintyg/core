package se.inera.intyg.certificateservice.application.certificate.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.GetSentInternalRequest.GetSentInternalRequestBuilder;

@JsonDeserialize(builder = GetSentInternalRequestBuilder.class)
@Value
@Builder
public class GetSentInternalRequest {

  List<String> patientIdList;
  int maxDays;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetSentInternalRequestBuilder {

  }

}