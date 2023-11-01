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

package se.inera.intyg.intygproxyservice.integration.employee.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.employee.Employee;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.employee.client.converter.GetEmployeeIncludingProtectedPersonResponseTypeConverter;
import se.riv.infrastructure.directory.employee.getemployeeincludingprotectedperson.v3.rivtabp21.GetEmployeeIncludingProtectedPersonResponderInterface;
import se.riv.infrastructure.directory.employee.getemployeeincludingprotectedpersonresponder.v3.GetEmployeeIncludingProtectedPersonType;

@Service
@Slf4j
@RequiredArgsConstructor
public class HsaEmployeeClient {

  public static final String PROFILE = "extended1";
  private final GetEmployeeIncludingProtectedPersonResponderInterface getEmployeeIncludingProtectedPersonResponderInterface;
  private final GetEmployeeIncludingProtectedPersonResponseTypeConverter getEmployeeIncludingProtectedPersonResponseTypeConverter;

  @Value("${integration.hsa.logical.address}")
  private String logicalAddress;

  public Employee getEmployee(GetEmployeeIntegrationRequest request) {
    final var parameters = getParameters(request.getHsaId(), request.getPersonId());
    final var type = getEmployeeIncludingProtectedPersonResponderInterface
        .getEmployeeIncludingProtectedPerson(logicalAddress, parameters);
    return getEmployeeIncludingProtectedPersonResponseTypeConverter.convert(type);
  }

  private static GetEmployeeIncludingProtectedPersonType getParameters(String hsaId,
      String personId) {
    final var parameters = new GetEmployeeIncludingProtectedPersonType();

    parameters.setPersonHsaId(hsaId);
    parameters.setIncludeFeignedObject(false);
    parameters.setProfile(PROFILE);
    parameters.setPersonalIdentityNumber(personId);

    return parameters;
  }
}
