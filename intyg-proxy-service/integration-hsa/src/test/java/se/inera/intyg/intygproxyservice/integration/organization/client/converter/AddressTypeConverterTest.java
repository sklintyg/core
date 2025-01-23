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

package se.inera.intyg.intygproxyservice.integration.organization.client.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.riv.infrastructure.directory.organization.v2.AddressType;

@ExtendWith(MockitoExtension.class)
class AddressTypeConverterTest {

  @InjectMocks
  AddressTypeConverter addressTypeConverter;

  @Nested
  class V2 {

    @Test
    void shouldConvertAddressType() {
      final var address = List.of("A1", "A2");
      final var type = mock(AddressType.class);
      when(type.getAddressLine())
          .thenReturn(address);

      final var response = addressTypeConverter.convertV2(type);

      assertEquals(address, response);
    }

    @Test
    void shouldReturnEmptyListIfAddressLineIsNull() {
      final var type = mock(AddressType.class);
      when(type.getAddressLine())
          .thenReturn(null);

      final var response = addressTypeConverter.convertV2(type);

      assertEquals(Collections.emptyList(), response);
    }

    @Test
    void shouldReturnEmptyListIfAddressIsNull() {
      final var response = addressTypeConverter.convertV2(null);

      assertEquals(Collections.emptyList(), response);
    }
  }

  @Nested
  class V3 {

    @Test
    void shouldConvertAddressType() {
      final var address = List.of("A1", "A2");
      final var type = mock(se.riv.infrastructure.directory.organization.v3.AddressType.class);
      when(type.getAddressLine())
          .thenReturn(address);

      final var response = addressTypeConverter.convertV3(type);

      assertEquals(address, response);
    }

    @Test
    void shouldReturnEmptyListIfAddressLineIsNull() {
      final var type = mock(se.riv.infrastructure.directory.organization.v3.AddressType.class);
      when(type.getAddressLine())
          .thenReturn(null);

      final var response = addressTypeConverter.convertV3(type);

      assertEquals(Collections.emptyList(), response);
    }

    @Test
    void shouldReturnEmptyListIfAddressIsNull() {
      final var response = addressTypeConverter.convertV3(null);

      assertEquals(Collections.emptyList(), response);
    }
  }
}
