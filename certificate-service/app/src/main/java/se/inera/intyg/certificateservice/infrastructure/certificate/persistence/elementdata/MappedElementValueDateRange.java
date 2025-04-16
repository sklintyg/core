package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MappedElementValueDateRange implements MappedElementValue {

  String dateId;
  LocalDate toDate;
  LocalDate fromDate;

}
