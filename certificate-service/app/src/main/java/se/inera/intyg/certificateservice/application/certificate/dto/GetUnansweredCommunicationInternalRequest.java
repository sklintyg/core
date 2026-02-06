package se.inera.intyg.certificateservice.application.certificate.dto;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.certificate.dto.GetUnansweredCommunicationInternalRequest.GetUnansweredCommunicationInternalRequestBuilder;

@JsonDeserialize(builder = GetUnansweredCommunicationInternalRequestBuilder.class)
@Value
@Builder
public class GetUnansweredCommunicationInternalRequest {

  List<String> patientId;
  Integer maxDaysOfUnansweredCommunication;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetUnansweredCommunicationInternalRequestBuilder {

  }

}