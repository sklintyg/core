package se.inera.intyg.intygproxyservice.integration.fakepu.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParsedNameWrapper {

  ParsedName givenName;
  ParsedName middleName;
  ParsedName surname;
}
