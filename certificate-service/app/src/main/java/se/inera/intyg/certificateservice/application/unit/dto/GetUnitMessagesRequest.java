package se.inera.intyg.certificateservice.application.unit.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitMessagesRequest.GetUnitMessagesRequestBuilder;

@JsonDeserialize(builder = GetUnitMessagesRequestBuilder.class)
@Value
@Builder
public class GetUnitMessagesRequest {

  UserDTO user;
  UnitDTO unit;
  UnitDTO careUnit;
  UnitDTO careProvider;
  MessagesQueryCriteriaDTO messagesQueryCriteria;

  @JsonPOJOBuilder(withPrefix = "")
  public static class GetUnitMessagesRequestBuilder {

  }
}
