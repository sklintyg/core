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
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareUnitIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareUnitIntegrationService;
import se.inera.intyg.intygproxyservice.organization.dto.HealthCareUnitRequest;
import se.inera.intyg.intygproxyservice.organization.dto.HealthCareUnitResponse;

@Service
@AllArgsConstructor
@Slf4j
public class HealthCareUnitService {

    private final GetHealthCareUnitIntegrationService getHealthCareUnitIntegrationService;

    public HealthCareUnitResponse get(HealthCareUnitRequest request) {
        validateRequest(request);

        log.info(String.format("Getting health care unit with hsaId: '%s'", request.getHsaId()));

        final var response = getHealthCareUnitIntegrationService.get(
            GetHealthCareUnitIntegrationRequest.builder()
                .hsaId(request.getHsaId())
                .build()
        );

        log.info(String.format("Health care unit with hsaId: '%s' was retrieved", request.getHsaId()));

        return HealthCareUnitResponse
            .builder()
            .healthCareUnit(response.getHealthCareUnit())
            .build();
    }

    private static void validateRequest(HealthCareUnitRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Request to get health care unit is null");
        }

        if (isStringInvalid(request.getHsaId())) {
            throw new IllegalArgumentException(
                String.format("HsaId is not valid: '%s'", request.getHsaId()));
        }
    }
}
