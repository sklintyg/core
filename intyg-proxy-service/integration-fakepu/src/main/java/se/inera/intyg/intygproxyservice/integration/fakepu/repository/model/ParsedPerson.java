package se.inera.intyg.intygproxyservice.integration.fakepu.repository.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParsedPerson {

  ParsedPersonalIdentity personalIdentity;
  boolean protectedPersonIndicator;
  boolean protectedPopulationRecord;
  boolean testIndicator;
  boolean isActive;
  boolean primaryIdentity;
  ParsedNameWrapper name;
  ParsedAddressInformation addressInformation;
  ParsedDeregistration deregistration;
}