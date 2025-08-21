package se.inera.intyg.intygproxyservice.integration.fakepu.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParsedPerson {

  ParsedPersonalIdentity personalIdentity;
  boolean protectedPersonIndicator;
  boolean protectedPopulationRecord;
  boolean testIndicator;
  Boolean isActive;
  boolean primaryIdentity;
  ParsedNameWrapper name;
  ParsedAddressInformation addressInformation;
  ParsedDeregistration deregistration;
}