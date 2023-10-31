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

package se.inera.intyg.intygproxyservice.organization.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.organization.dto.GetHealthCareUnitMembersIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.dto.GetHealthCareUnitMembersIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.organization.service.GetHealthCareUnitMembersIntegrationService;
import se.inera.intyg.intygproxyservice.organization.dto.HealthCareUnitMembersRequest;

@ExtendWith(MockitoExtension.class)
class HealthCareUnitMembersServiceTest {

  private static final String HSA_ID = "HSA_ID";

  private static final HealthCareUnitMembersRequest REQUEST = HealthCareUnitMembersRequest
      .builder()
      .hsaId(HSA_ID)
      .build();

  private static final GetHealthCareUnitMembersIntegrationResponse RESPONSE = GetHealthCareUnitMembersIntegrationResponse
      .builder()
      .build();

  @Mock
  private GetHealthCareUnitMembersIntegrationService getHealthCareUnitMembersIntegrationService;

  @InjectMocks
  private HealthCareUnitMembersService HealthCareUnitMembersService;

  @Test
  void shouldThrowIllegalArgumentExceptionIfRequestIsNull() {
    assertThrows(IllegalArgumentException.class, () -> HealthCareUnitMembersService.get(null));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfHsaIdIsNull() {
    final var request = HealthCareUnitMembersRequest.builder().build();
    assertThrows(IllegalArgumentException.class, () -> HealthCareUnitMembersService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfHsaIdIsEmpty() {
    final var request = HealthCareUnitMembersRequest.builder().hsaId("").build();
    assertThrows(IllegalArgumentException.class, () -> HealthCareUnitMembersService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfHsaIdIsBlank() {
    final var request = HealthCareUnitMembersRequest.builder().hsaId(" ").build();
    assertThrows(IllegalArgumentException.class, () -> HealthCareUnitMembersService.get(request));
  }

  @Nested
  class ValidRequest {

    @BeforeEach
    void setUp() {
      when(getHealthCareUnitMembersIntegrationService.get(
          any(GetHealthCareUnitMembersIntegrationRequest.class))
      ).thenReturn(RESPONSE);
    }

    @Test
    void shallReturnUnit() {
      final var response = HealthCareUnitMembersService.get(REQUEST);

      assertEquals(RESPONSE.getHealthCareUnitMembers(), response.getHealthCareUnitMembers());
    }

    @Test
    void shallSetHsaIdInRequest() {
      HealthCareUnitMembersService.get(REQUEST);

      final var captor = ArgumentCaptor.forClass(GetHealthCareUnitMembersIntegrationRequest.class);
      verify(getHealthCareUnitMembersIntegrationService).get(captor.capture());

      assertEquals(REQUEST.getHsaId(), captor.getValue().getHsaId());
    }
  }
}