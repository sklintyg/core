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

package se.inera.intyg.intygproxyservice.integration.organization.client.converter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnitMembers;
import se.riv.infrastructure.directory.organization.gethealthcareunitmembersresponder.v2.GetHealthCareUnitMembersResponseType;
import se.riv.infrastructure.directory.organization.gethealthcareunitmembersresponder.v2.HealthCareUnitMembersType;

@ExtendWith(MockitoExtension.class)
class GetHealthCareUnitMembersResponseTypeConverterTest {

  public static final HealthCareUnitMembers HEALTH_CARE_UNIT_MEMBERS = HealthCareUnitMembers
      .builder()
      .healthCareUnitName("NAME")
      .build();

  @Mock
  HealthCareUnitMembersTypeConverter healthCareUnitMembersTypeConverter;

  @InjectMocks
  GetHealthCareUnitMembersResponseTypeConverter getHealthCareUnitMembersResponseTypeConverter;

  @Test
  void shouldConvertGetHealthCareUnitResponse() {
    final var type = new GetHealthCareUnitMembersResponseType();
    type.setHealthCareUnitMembers(new HealthCareUnitMembersType());

    when(healthCareUnitMembersTypeConverter.convert(any(HealthCareUnitMembersType.class)))
        .thenReturn(HEALTH_CARE_UNIT_MEMBERS);

    final var response = getHealthCareUnitMembersResponseTypeConverter.convert(type);

    assertEquals(HEALTH_CARE_UNIT_MEMBERS, response);
  }

  @Test
  void shouldReturnEmptyResponseIfUnitIsNull() {
    final var type = new GetHealthCareUnitMembersResponseType();

    final var response = getHealthCareUnitMembersResponseTypeConverter.convert(type);

    assertEquals(HealthCareUnitMembers.builder().build(), response);
  }

  @Test
  void shouldReturnEmptyResponseIfResponseIsNull() {
    final var response = getHealthCareUnitMembersResponseTypeConverter.convert(null);

    assertEquals(HealthCareUnitMembers.builder().build(), response);
  }
}