package se.inera.intyg.certificateanalyticsservice.application.messages.model.v1;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsAdministrativeMessageV1.CertificateAnalyticsMessageV1Builder;

@Value
@Builder
@JsonDeserialize(builder = CertificateAnalyticsMessageV1Builder.class)
public class CertificateAnalyticsAdministrativeMessageV1 {

  String id;
  String answerId;
  String reminderId;

  String type;

  LocalDateTime sent;
  LocalDate lastDateToAnswer;

  List<String> questionId;

  String sender;
  String recipient;

  @JsonPOJOBuilder(withPrefix = "")
  public static class CertificateAnalyticsMessageV1Builder {

  }
}
