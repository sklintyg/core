/*
 * Copyright (C) 2024 Inera AB (http://www.inera.se)
 *
 * This file is part of sklintyg (https://github.com/sklintyg).
 *
 * sklintyg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sklintyg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package se.inera.intyg.intygproxyservice.organization.service;

import static se.inera.intyg.intygproxyservice.common.ValidationUtility.isStringInvalid;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareProviderIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareProviderIntegrationService;
import se.inera.intyg.intygproxyservice.organization.dto.HealthCareProviderRequest;
import se.inera.intyg.intygproxyservice.organization.dto.HealthCareProviderResponse;

@Service
@AllArgsConstructor
@Slf4j
public class HealthCareProviderService {

  private final GetHealthCareProviderIntegrationService getHealthCareProviderIntegrationService;

  public HealthCareProviderResponse get(HealthCareProviderRequest request) {
    validateRequest(request);

    log.info(
        String.format(
            "Getting health care provider with hsaId: '%s' and organizationNumber '%s'",
            request.getHsaId(), request.getOrganizationNumber()
        )
    );

    final var response = getHealthCareProviderIntegrationService.get(
        GetHealthCareProviderIntegrationRequest.builder()
            .hsaId(request.getHsaId())
            .organizationNumber(request.getOrganizationNumber())
            .build()
    );

    log.info(String.format(
        "Health care provider with hsaId: '%s' and organizationNumber '%s' was retrieved. Number of results: '%s'",
        request.getHsaId(),
        request.getOrganizationNumber(),
        response.getHealthCareProviders().size())
    );

    return HealthCareProviderResponse
        .builder()
        .healthCareProviders(response.getHealthCareProviders())
        .build();
  }

  private static void validateRequest(HealthCareProviderRequest request) {
    if (request == null) {
      throw new IllegalArgumentException("Request to get health care provider is null");
    }

    if (!isStringInvalid(request.getHsaId()) && !isStringInvalid(request.getOrganizationNumber())) {
      throw new IllegalArgumentException("Both hsaId and organizationNumber cannot be defined");
    }

    if (isStringInvalid(request.getHsaId()) && isStringInvalid(request.getOrganizationNumber())) {
      throw new IllegalArgumentException("One of hsaId or organizationNumber has to be defined");
    }
  }
}
