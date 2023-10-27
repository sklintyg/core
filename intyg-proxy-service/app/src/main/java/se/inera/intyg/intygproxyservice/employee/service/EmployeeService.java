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

package se.inera.intyg.intygproxyservice.employee.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.employee.dto.EmployeeRequest;
import se.inera.intyg.intygproxyservice.employee.dto.EmployeeResponse;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeRequest;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeService;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeService {

  private final GetEmployeeService getEmployeeService;

  public EmployeeResponse getEmployee(EmployeeRequest request) {
    validateRequest(request);

    log.info(String.format("Getting employee with hsaId: '%s' and personId: '%s'", request.getHsaId(), request.getPersonId()));

    final var response = getEmployeeService.get(
        GetEmployeeRequest.builder()
            .hsaId(request.getHsaId())
            .personId(request.getPersonId())
            .build()
    );

    // TODO: Add more info like hsaId, but how to do this if we get list of info?
    log.info("Employee retrieved");

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
          String.format("PersonId and HsaId are not valid: '%s', '%s'", request.getPersonId(), request.getHsaId())
      );
    }
  }

  private static boolean isStringInvalid(String value) {
    return value == null || value.isBlank() || value.isEmpty();
  }
}
