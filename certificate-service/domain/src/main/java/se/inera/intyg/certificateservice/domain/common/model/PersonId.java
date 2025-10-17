package se.inera.intyg.certificateservice.domain.common.model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PersonId {

  String id;
  PersonIdType type;

  public LocalDate birthDate() {
    return LocalDate.of(year(), month(), dayOfMonth());
  }

  private int year() {
    return Integer.parseInt(id.substring(0, 4));
  }

  private int month() {
    return Integer.parseInt(id.substring(4, 6));
  }

  private int dayOfMonth() {
    final var dayOfMonth = Integer.parseInt(id.substring(6, 8));
    if (PersonIdType.COORDINATION_NUMBER.equals(type)) {
      return dayOfMonth - 60;
    }
    return dayOfMonth;
  }

  public String idWithoutDash() {
    return id.replace("-", "");
  }

  public String idWithDash() {
    if (id.contains("-")) {
      return id;
    }
    return String.join(
        "-",
        id.substring(0, 8),
        id.substring(8)
    );
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof PersonId other)) {
      return false;
    }
    return type == other.type &&
        idWithoutDash().equals(other.idWithoutDash());
  }

  @Override
  public int hashCode() {
    return java.util.Objects.hash(type, idWithoutDash());
  }
}
