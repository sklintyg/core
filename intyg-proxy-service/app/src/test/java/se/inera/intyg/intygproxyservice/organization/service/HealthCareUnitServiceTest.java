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
import se.inera.intyg.intygproxyservice.integration.api.organization.model.GetHealthCareUnitIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.GetHealthCareUnitIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareUnitIntegrationService;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnit;
import se.inera.intyg.intygproxyservice.organization.dto.HealthCareUnitRequest;

@ExtendWith(MockitoExtension.class)
class HealthCareUnitServiceTest {

  private static final String HSA_ID = "HSA_ID";

  private static final HealthCareUnitRequest REQUEST = HealthCareUnitRequest
      .builder()
      .hsaId(HSA_ID)
      .build();

  private static final GetHealthCareUnitIntegrationResponse RESPONSE = GetHealthCareUnitIntegrationResponse
      .builder()
      .healthCareUnit(HealthCareUnit
          .builder()
          .build())
      .build();

  @Mock
  private GetHealthCareUnitIntegrationService getHealthCareUnitIntegrationService;

  @InjectMocks
  private HealthCareUnitService healthCareUnitService;

  @Test
  void shouldThrowIllegalArgumentExceptionIfRequestIsNull() {
    assertThrows(IllegalArgumentException.class, () -> healthCareUnitService.get(null));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfHsaIdIsNull() {
    final var request = HealthCareUnitRequest.builder().build();
    assertThrows(IllegalArgumentException.class, () -> healthCareUnitService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfHsaIdIsEmpty() {
    final var request = HealthCareUnitRequest.builder().hsaId("").build();
    assertThrows(IllegalArgumentException.class, () -> healthCareUnitService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfHsaIdIsBlank() {
    final var request = HealthCareUnitRequest.builder().hsaId(" ").build();
    assertThrows(IllegalArgumentException.class, () -> healthCareUnitService.get(request));
  }

  @Nested
  class ValidRequest {

    @BeforeEach
    void setUp() {
      when(getHealthCareUnitIntegrationService.get(any(GetHealthCareUnitIntegrationRequest.class)))
          .thenReturn(RESPONSE);
    }

    @Test
    void shallReturnUnit() {
      final var response = healthCareUnitService.get(REQUEST);

      assertEquals(RESPONSE.getHealthCareUnit(), response.getHealthCareUnit());
    }

    @Test
    void shallSetHsaIdInRequest() {
      healthCareUnitService.get(REQUEST);

      final var captor = ArgumentCaptor.forClass(GetHealthCareUnitIntegrationRequest.class);
      verify(getHealthCareUnitIntegrationService).get(captor.capture());

      assertEquals(REQUEST.getHsaId(), captor.getValue().getHsaId());
    }
  }
}