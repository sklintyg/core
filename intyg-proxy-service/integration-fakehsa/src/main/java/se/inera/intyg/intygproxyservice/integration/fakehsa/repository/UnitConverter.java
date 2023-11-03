package se.inera.intyg.intygproxyservice.integration.fakehsa.repository;

import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedSubUnit;

public interface UnitConverter {

  HealthCareUnit convert(ParsedCareUnit parsedCareUnit);

  HealthCareUnit convert(ParsedSubUnit parsedSubUnit);
}
