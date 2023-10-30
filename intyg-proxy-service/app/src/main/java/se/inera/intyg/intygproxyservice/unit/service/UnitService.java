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

package se.inera.intyg.intygproxyservice.unit.service;

import com.google.common.base.Strings;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.unit.GetUnitIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.unit.GetUnitIntegrationService;
import se.inera.intyg.intygproxyservice.unit.dto.UnitRequest;
import se.inera.intyg.intygproxyservice.unit.dto.UnitResponse;

@Service
@AllArgsConstructor
@Slf4j
public class UnitService {

  private final GetUnitIntegrationService getUnitIntegrationService;

  public UnitResponse get(UnitRequest request) {
    validateRequest(request);

    log.info(String.format("Getting unit with hsaId: '%s'", request.getHsaId()));

    final var response = getUnitIntegrationService.get(
        GetUnitIntegrationRequest.builder()
            .hsaId(request.getHsaId())
            .build()
    );

    log.info(String.format(
            "Unit with hsaId: '%s' was retrieved, response had length: '%s'",
            request.getHsaId(),
            0
        )
    );

    return UnitResponse
        .builder()
        .unit(response.getUnit())
        .build();
  }

  private static void validateRequest(UnitRequest request) {
    if (request == null) {
      throw new IllegalArgumentException("Request to get health care unit is null");
    }

    if (isStringInvalid(request.getHsaId())) {
      throw new IllegalArgumentException(
          String.format("HsaId is not valid: '%s'", request.getHsaId()));
    }
  }

  private static boolean isStringInvalid(String value) {
    return Strings.isNullOrEmpty(value) || value.isBlank();
  }
}
