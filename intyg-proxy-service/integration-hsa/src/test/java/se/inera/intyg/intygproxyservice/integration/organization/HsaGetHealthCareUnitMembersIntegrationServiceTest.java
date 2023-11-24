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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareUnitMembersIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnitMembers;
import se.inera.intyg.intygproxyservice.integration.organization.client.HsaOrganizationClient;

@ExtendWith(MockitoExtension.class)
class HsaGetHealthCareUnitMembersIntegrationServiceTest {

  private static final HealthCareUnitMembers HEALTH_CARE_UNIT_MEMBERS = HealthCareUnitMembers
      .builder()
      .build();

  @InjectMocks
  private HsaGetHealthCareUnitMembersIntegrationService hsaGetHealthCareUnitMembersIntegrationService;

  @Mock
  private HsaOrganizationClient hsaOrganizationClient;

  @Test
  void shouldReturnUnitMembers() {
    when(hsaOrganizationClient.getHealthCareUnitMembers(
        any(GetHealthCareUnitMembersIntegrationRequest.class)))
        .thenReturn(HEALTH_CARE_UNIT_MEMBERS);

    final var response = hsaGetHealthCareUnitMembersIntegrationService.get(
        GetHealthCareUnitMembersIntegrationRequest
            .builder()
            .build()
    );

    assertEquals(HEALTH_CARE_UNIT_MEMBERS, response.getHealthCareUnitMembers());
  }

}