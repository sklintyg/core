package se.inera.intyg.intygproxyservice.integration.fakehsa.converters;

import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Unit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedSubUnit;

@Component
public class UnitConverter {

  public Unit convert(ParsedSubUnit parsedSubUnit) {
    return Unit.builder()
        .unitName(parsedSubUnit.getName())
        .unitHsaId(parsedSubUnit.getId())
        .mail(parsedSubUnit.getMail())
        .postalCode(parsedSubUnit.getPostalCode())
        .postalAddress(
            List.of(parsedSubUnit.getPostalAddress(), parsedSubUnit.getPostalTown())
        )
        .countyCode(parsedSubUnit.getCountyCode())
        .municipalityCode(parsedSubUnit.getMunicipalityCode())
        .telephoneNumber(List.of(parsedSubUnit.getTelephoneNumber()))
        .build();
  }

  public Unit convert(ParsedCareUnit parsedCareUnit) {
    return Unit.builder()
        .unitName(parsedCareUnit.getName())
        .unitHsaId(parsedCareUnit.getId())
        .mail(parsedCareUnit.getMail())
        .postalCode(parsedCareUnit.getPostalCode())
        .postalAddress(
            List.of(parsedCareUnit.getPostalAddress(), parsedCareUnit.getPostalTown())
        )
        .countyCode(parsedCareUnit.getCountyCode())
        .municipalityCode(parsedCareUnit.getMunicipalityCode())
        .telephoneNumber(List.of(parsedCareUnit.getTelephoneNumber()))
        .build();
  }
}
