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

package se.inera.intyg.intygproxyservice.integration.employee;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeIntegrationRequest;

@ExtendWith(MockitoExtension.class)
class HsaGetEmployeeIntegrationServiceTest {

  public static final String HSA_ID = "HSA_ID";
  public static final GetEmployeeIntegrationRequest REQUEST = GetEmployeeIntegrationRequest
      .builder()
      .hsaId(HSA_ID)
      .build();
  @InjectMocks
  HsaGetEmployeeIntegrationIntegrationService hsaGetEmployeeIntegrationService;

  @Test
  void shouldReturnEmployeeHsaId() {
    final var response = hsaGetEmployeeIntegrationService.get(REQUEST);

    assertEquals(
        "TSTNMT2321000156-DRAA",
        response.getEmployee().getPersonalInformation().get(0).getPersonHsaId()
    );
  }

  @Test
  void shouldReturnEmployeeFirstName() {
    final var response = hsaGetEmployeeIntegrationService.get(REQUEST);

    assertEquals(
        "Ajla",
        response.getEmployee().getPersonalInformation().get(0).getGivenName()
    );
  }

  @Test
  void shouldReturnEmployeeLastname() {
    final var response = hsaGetEmployeeIntegrationService.get(REQUEST);

    assertEquals(
        "Doktor",
        response.getEmployee().getPersonalInformation().get(0).getMiddleAndSurName()
    );
  }

}