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

package se.inera.intyg.intygproxyservice.organization.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import se.inera.intyg.intygproxyservice.integration.api.organization.GetUnitIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetUnitIntegrationResponse;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetUnitIntegrationService;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Unit;
import se.inera.intyg.intygproxyservice.organization.dto.UnitRequest;

@ExtendWith(MockitoExtension.class)
class UnitServiceTest {

  private static final String HSA_ID = "HSA_ID";

  private static final UnitRequest REQUEST = UnitRequest
      .builder()
      .hsaId(HSA_ID)
      .build();

  private static final GetUnitIntegrationResponse RESPONSE = GetUnitIntegrationResponse
      .builder()
      .unit(Unit.builder().build())
      .build();

  @Mock
  private GetUnitIntegrationService getUnitIntegrationService;

  @InjectMocks
  private UnitService unitService;

  @Test
  void shouldThrowIllegalArgumentExceptionIfRequestIsNull() {
    assertThrows(IllegalArgumentException.class, () -> unitService.get(null));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfHsaIdIsNull() {
    final var request = UnitRequest.builder().build();
    assertThrows(IllegalArgumentException.class, () -> unitService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfHsaIdIsEmpty() {
    final var request = UnitRequest.builder().hsaId("").build();
    assertThrows(IllegalArgumentException.class, () -> unitService.get(request));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfHsaIdIsBlank() {
    final var request = UnitRequest.builder().hsaId(" ").build();
    assertThrows(IllegalArgumentException.class, () -> unitService.get(request));
  }

  @Nested
  class ValidRequest {

    @BeforeEach
    void setUp() {
      when(getUnitIntegrationService.get(any(GetUnitIntegrationRequest.class)))
          .thenReturn(RESPONSE);
    }

    @Test
    void shallReturnUnit() {
      final var response = unitService.get(REQUEST);

      assertEquals(RESPONSE.getUnit(), response.getUnit());
    }

    @Test
    void shallSetHsaIdInRequest() {
      unitService.get(REQUEST);

      final var captor = ArgumentCaptor.forClass(GetUnitIntegrationRequest.class);
      verify(getUnitIntegrationService).get(captor.capture());

      assertEquals(REQUEST.getHsaId(), captor.getValue().getHsaId());
    }
  }
}
