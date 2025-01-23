/*
 * Copyright (C) 2025 Inera AB (http://www.inera.se)
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

package se.inera.intyg.intygproxyservice.integration.employee;

import static se.inera.intyg.intygproxyservice.integration.api.constants.HsaConstants.FAKE_HSA_PROFILE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeIntegrationService;
import se.inera.intyg.intygproxyservice.integration.employee.client.HsaEmployeeClient;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile("!" + FAKE_HSA_PROFILE)
public class HsaGetEmployeeIntegrationService implements GetEmployeeIntegrationService {

  private final HsaEmployeeClient hsaEmployeeClient;

  @Override
  public GetEmployeeIntegrationResponse get(
      GetEmployeeIntegrationRequest getEmployeeIntegrationRequest) {
    return GetEmployeeIntegrationResponse.builder()
        .employee(
            hsaEmployeeClient.getEmployee(getEmployeeIntegrationRequest)
        )
        .build();
  }
}
