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

package se.inera.intyg.intygproxyservice.integration.employee.client.converter;

import java.util.Collections;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.employee.Employee;
import se.riv.infrastructure.directory.employee.getemployeeincludingprotectedpersonresponder.v3.GetEmployeeIncludingProtectedPersonResponseType;

@AllArgsConstructor
@Service
public class GetEmployeeIncludingProtectedPersonResponseTypeConverter {

  private PersonInformationTypeConverter personInformationTypeConverter;

  public Employee convert(GetEmployeeIncludingProtectedPersonResponseType type) {
    if (type == null || type.getPersonInformation() == null) {
      return Employee.builder()
          .personInformation(Collections.emptyList())
          .build();
    }

    return Employee.builder()
        .personInformation(
            type.getPersonInformation().stream()
                .map(personInformationTypeConverter::convert)
                .toList()
        )
        .build();
  }
}
