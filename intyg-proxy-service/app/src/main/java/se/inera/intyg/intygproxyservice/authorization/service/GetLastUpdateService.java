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

package se.inera.intyg.intygproxyservice.authorization.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.authorization.dto.GetLastUpdateResponse;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetLastUpdateIntegrationService;

@Service
@AllArgsConstructor
@Slf4j
public class GetLastUpdateService {

  private final GetLastUpdateIntegrationService getLastUpdateIntegrationService;

  public GetLastUpdateResponse get() {

    log.info("Getting last update for hsa");

    final var response = getLastUpdateIntegrationService.get();

    log.info("Last update for hsa was retrieved");

    return GetLastUpdateResponse
        .builder()
        .lastUpdate(response.getLastUpdate())
        .build();
  }
}
