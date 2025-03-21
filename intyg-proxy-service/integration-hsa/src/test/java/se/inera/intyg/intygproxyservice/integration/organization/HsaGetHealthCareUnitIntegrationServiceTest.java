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

package se.inera.intyg.intygproxyservice.integration.organization;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareUnitIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnit;
import se.inera.intyg.intygproxyservice.integration.organization.client.HsaOrganizationClient;

@ExtendWith(MockitoExtension.class)
class HsaGetHealthCareUnitIntegrationServiceTest {

  public static final HealthCareUnit HEALTH_CARE_UNIT = HealthCareUnit.builder().build();
  @Mock
  HsaOrganizationClient hsaOrganizationClient;

  @InjectMocks
  HsaGetHealthCareUnitIntegrationService hsaGetUnitIntegrationService;

  @Test
  void shouldReturnUnit() {
    when(hsaOrganizationClient.getHealthCareUnit(any(GetHealthCareUnitIntegrationRequest.class)))
        .thenReturn(HEALTH_CARE_UNIT);
    final var response = hsaGetUnitIntegrationService.get(
        GetHealthCareUnitIntegrationRequest
            .builder()
            .build()
    );

    assertEquals(HEALTH_CARE_UNIT, response.getHealthCareUnit());
  }
}
