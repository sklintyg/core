/*
 * Copyright (C) 2023 Inera AB (http://www.inera.se)
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
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareUnitMembersIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareUnitMembersIntegrationService;
import se.inera.intyg.intygproxyservice.organization.dto.HealthCareUnitMembersRequest;
import se.inera.intyg.intygproxyservice.organization.dto.HealthCareUnitMembersResponse;

@Service
@AllArgsConstructor
@Slf4j
public class HealthCareUnitMembersService {

  private final GetHealthCareUnitMembersIntegrationService getHealthCareUnitMembersIntegrationService;

  public HealthCareUnitMembersResponse get(HealthCareUnitMembersRequest request) {
    validateRequest(request);

    log.info(String.format("Getting health care unit members related to hsaId: '%s'",
        request.getHsaId()));

    final var response = getHealthCareUnitMembersIntegrationService.get(
        GetHealthCareUnitMembersIntegrationRequest.builder()
            .hsaId(request.getHsaId())
            .build()
    );

    log.info(String.format("Health care unit members related to hsaId: '%s' were retrieved",
        request.getHsaId()));

    return HealthCareUnitMembersResponse
        .builder()
        .healthCareUnitMembers(response.getHealthCareUnitMembers())
        .build();
  }

  private static void validateRequest(HealthCareUnitMembersRequest request) {
    if (request == null) {
      throw new IllegalArgumentException("Request to get health care unit members is null");
    }

    if (isStringInvalid(request.getHsaId())) {
      throw new IllegalArgumentException(
          String.format("HsaId is not valid: '%s'", request.getHsaId()));
    }
  }
}
