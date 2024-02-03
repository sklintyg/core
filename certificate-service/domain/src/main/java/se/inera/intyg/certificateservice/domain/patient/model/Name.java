package se.inera.intyg.certificateservice.domain.patient.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Name {

  private static final String SPACE = " ";
  
  String firstName;
  String lastName;
  String middleName;

  public String fullName() {
    final var nameBuilder = new StringBuilder();
    appendName(nameBuilder, firstName);
    appendName(nameBuilder, middleName);
    appendName(nameBuilder, lastName);
    return nameBuilder.toString();
  }

  private static void appendName(StringBuilder nameBuilder, String nameToAppend) {
    if (nameToAppend == null || nameToAppend.isBlank()) {
      return;
    }

    if (!nameBuilder.isEmpty()) {
      nameBuilder.append(SPACE);
    }

    nameBuilder.append(nameToAppend);
  }
}
