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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static se.inera.intyg.intygproxyservice.integration.common.TypeConverterHelper.toXMLGregorianCalendar;
import static se.inera.intyg.intygproxyservice.integration.common.TypeConverterHelper.truncateToSeconds;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.riv.infrastructure.directory.organization.gethealthcareunitmembersresponder.v2.HealthCareProviderType;
import se.riv.infrastructure.directory.organization.v2.AddressType;

@ExtendWith(MockitoExtension.class)
class HealthCareProviderTypeConverterTest {

  public static final LocalDateTime PROVIDER_END_DATE = LocalDateTime.now().plusDays(5);
  public static final LocalDateTime PROVIDER_START_DATE = LocalDateTime.now().plusDays(4);

  @InjectMocks
  HealthCareProviderTypeConverter healthCareProviderTypeConverter;
  @Mock
  AddressTypeConverter addressTypeConverter;

  @Test
  void shouldConvertArchived() {
    final var type = getType();
    final var response = healthCareProviderTypeConverter.convert(type);

    assertEquals(type.isArchivedHealthCareProvider(), response.getArchivedHealthCareProvider());
  }

  @Test
  void shouldConvertFeigned() {
    final var type = getType();
    final var response = healthCareProviderTypeConverter.convert(type);

    assertEquals(type.isFeignedHealthCareProvider(), response.getFeignedHealthCareProvider());
  }

  @Test
  void shouldConvertName() {
    final var type = getType();
    final var response = healthCareProviderTypeConverter.convert(type);

    assertEquals(type.getHealthCareProviderName(), response.getHealthCareProviderName());
  }

  @Test
  void shouldConvertHsaId() {
    final var type = getType();
    final var response = healthCareProviderTypeConverter.convert(type);

    assertEquals(type.getHealthCareProviderHsaId(), response.getHealthCareProviderHsaId());
  }

  @Test
  void shouldConvertPublicName() {
    final var type = getType();
    final var response = healthCareProviderTypeConverter.convert(type);

    assertEquals(type.getHealthCareProviderPublicName(),
        response.getHealthCareProviderPublicName());
  }

  @Test
  void shouldConvertOrgNo() {
    final var type = getType();
    final var response = healthCareProviderTypeConverter.convert(type);

    assertEquals(type.getHealthCareProviderOrgNo(), response.getHealthCareProviderOrgNo());
  }

  @Test
  void shouldConvertEndDate() {
    final var type = getType();
    final var response = healthCareProviderTypeConverter.convert(type);

    assertEquals(truncateToSeconds(PROVIDER_END_DATE),
        truncateToSeconds(response.getHealthCareProviderEndDate()));
  }

  @Test
  void shouldConvertStartDate() {
    final var type = getType();
    final var response = healthCareProviderTypeConverter.convert(type);

    assertEquals(truncateToSeconds(PROVIDER_START_DATE),
        truncateToSeconds(response.getHealthCareProviderStartDate()));
  }

  @Nested
  class TestMockedValues {

    HealthCareProviderType type;

    @BeforeEach
    void setup() {
      type = mock(HealthCareProviderType.class);
      when(type.getTelephoneNumber()).thenReturn(
          Collections.singletonList("PHONENUMBER")
      );
      when(type.getHealthCareProviderPrescriptionCode()).thenReturn(List.of("CODE1", "CODE2"));
    }

    @Test
    void shouldConvertPhoneNumber() {
      final var response = healthCareProviderTypeConverter.convert(type);

      assertEquals(
          type.getTelephoneNumber(), "")
      ;
    }

    @Test
    void shouldConvertPrescriptionCode() {
      final var response = healthCareProviderTypeConverter.convert(type);

      assertEquals(
          type.getHealthCareProviderPrescriptionCode(), "")
      ;
    }
  }

  private HealthCareProviderType getType() {
    final var type = new HealthCareProviderType();
    type.setArchivedHealthCareProvider(true);
    type.setFeignedHealthCareProvider(true);
    type.setHealthCareProviderName("PROVIDER_NAME");
    type.setHealthCareProviderHsaId("PROVIDER_HSA_ID");
    type.setHealthCareProviderOrgNo("ORG_NO");
    type.setHealthCareProviderPublicName("PROVIDER_PUBLIC_NAME");
    type.setHealthCareProviderEndDate(toXMLGregorianCalendar(PROVIDER_END_DATE));
    type.setHealthCareProviderStartDate(toXMLGregorianCalendar(PROVIDER_START_DATE));
    type.setPostalAddress(new AddressType());
    type.setPostalCode("POSTAL_CODE");

    return type;
  }
}