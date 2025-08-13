package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MappedElementValueIcf implements MappedElementValue {

  private String id;
  private String text;
  private List<String> icfCodes;
}