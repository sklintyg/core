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

package se.inera.intyg.intygproxyservice.integration.organization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetUnitIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Unit;
import se.inera.intyg.intygproxyservice.integration.organization.client.HsaOrganizationClient;

@ExtendWith(MockitoExtension.class)
class HsaGetUnitIntegrationServiceTest {

  public static final Unit UNIT = Unit.builder().build();
  @Mock
  HsaOrganizationClient hsaOrganizationClient;

  @InjectMocks
  HsaGetUnitIntegrationService hsaGetUnitIntegrationService;

  @Test
  void shouldReturnUnit() {
    when(hsaOrganizationClient.getUnit(any(GetUnitIntegrationRequest.class)))
        .thenReturn(UNIT);
    final var response = hsaGetUnitIntegrationService.get(
        GetUnitIntegrationRequest
            .builder()
            .build()
    );

    assertEquals(UNIT, response.getUnit());
  }
}