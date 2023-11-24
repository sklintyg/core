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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Unit;
import se.riv.infrastructure.directory.organization.getunitresponder.v4.GetUnitResponseType;
import se.riv.infrastructure.directory.organization.getunitresponder.v4.UnitType;

@ExtendWith(MockitoExtension.class)
class GetUnitResponseTypeConverterTest {

  public static final Unit UNIT = Unit
      .builder()
      .unitName("NAME")
      .build();

  @Mock
  UnitTypeConverter unitTypeConverter;

  @InjectMocks
  GetUnitResponseTypeConverter getUnitResponseTypeConverter;

  @Test
  void shouldConvertGetUnitResponse() {
    final var type = new GetUnitResponseType();
    type.setUnit(new UnitType());
    when(unitTypeConverter.convert(any(UnitType.class)))
        .thenReturn(UNIT);

    final var response = getUnitResponseTypeConverter.convert(type);

    assertEquals(UNIT, response);
  }

  @Test
  void shouldReturnEmptyResponseIfUnitIsNull() {
    final var type = new GetUnitResponseType();
    type.setUnit(null);
    final var response = getUnitResponseTypeConverter.convert(type);

    assertEquals(Unit.builder().build(), response);
  }

  @Test
  void shouldReturnEmptyResponseIfResponseIsNull() {
    final var response = getUnitResponseTypeConverter.convert(null);

    assertEquals(Unit.builder().build(), response);
  }
}