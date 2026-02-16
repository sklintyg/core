package se.inera.intyg.certificateservice.application.message.dto;

import java.util.Map;

public record GetSentMessagesCountResponse(Map<String, MessageCount> messages) {

}
