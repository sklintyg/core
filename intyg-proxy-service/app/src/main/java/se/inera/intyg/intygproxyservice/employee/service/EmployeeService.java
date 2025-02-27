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

package se.inera.intyg.intygproxyservice.employee.service;

import static se.inera.intyg.intygproxyservice.common.ValidationUtility.isStringInvalid;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.employee.dto.EmployeeRequest;
import se.inera.intyg.intygproxyservice.employee.dto.EmployeeResponse;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeIntegrationService;
import se.inera.intyg.intygproxyservice.logging.LogHashUtility;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeService {

  private final GetEmployeeIntegrationService getEmployeeIntegrationService;
  private final LogHashUtility logHashUtility;

  public EmployeeResponse getEmployee(EmployeeRequest request) {
    validateRequest(request);

    log.info(String.format(
            "Getting employee with hsaId: '%s' and personId: '%s'",
            request.getHsaId(),
            logHashUtility.hash(request.getPersonId())
        )
    );

    final var response = getEmployeeIntegrationService.get(
        GetEmployeeIntegrationRequest.builder()
            .hsaId(request.getHsaId())
            .personId(request.getPersonId())
            .build()
    );

    log.info(String.format(
            "Employee with hsaId: '%s' and personId: '%s' was retrieved, response had length: '%s'",
            request.getHsaId(),
            logHashUtility.hash(request.getPersonId()),
            response.getEmployee().getPersonInformation().size()
        )
    );

    return EmployeeResponse
        .builder()
        .employee(response.getEmployee())
        .build();
  }

  private static void validateRequest(EmployeeRequest request) {
    if (request == null) {
      throw new IllegalArgumentException("Request to get employee is null");
    }

    if (isStringInvalid(request.getPersonId()) && isStringInvalid(request.getHsaId())) {
      throw new IllegalArgumentException(
          String.format("PersonId and HsaId are not valid: '%s', '%s'",
              request.getPersonId(),
              request.getHsaId())
      );
    }
  }
}
