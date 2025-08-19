package se.inera.intyg.intygproxyservice.integration.api.elva77;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Elva77Response {

  User user;
  Result result;

  public static Elva77Response error() {
    return Elva77Response.builder()
        .result(Result.ERROR)
        .build();
  }

  public static Elva77Response inactive(String subjectOfCareId) {
    return Elva77Response.builder()
        .user(User.inactive(subjectOfCareId))
        .result(Result.INFO)
        .build();
  }
}