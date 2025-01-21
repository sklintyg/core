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
import static org.mockito.ArgumentMatchers.any;
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
import se.riv.infrastructure.directory.organization.gethealthcareunitmembersresponder.v2.HealthCareUnitMemberType;
import se.riv.infrastructure.directory.organization.v2.AddressType;

@ExtendWith(MockitoExtension.class)
class HealthCareUnitMemberTypeConverterTest {

  public static final LocalDateTime MEMBER_END_DATE = LocalDateTime.now().plusDays(15);
  public static final LocalDateTime MEMBER_START_DATE = LocalDateTime.now().plusDays(14);

  @Mock
  AddressTypeConverter addressTypeConverter;

  @InjectMocks
  HealthCareUnitMemberTypeConverter healthCareUnitMemberTypeConverter;

  @Test
  void shouldConvertArchived() {
    final var type = getType();
    final var response = healthCareUnitMemberTypeConverter.convert(type);

    assertEquals(type.isArchivedHealthCareUnitMember(),
        response.getArchivedHealthCareUnitMember());
  }

  @Test
  void shouldConvertFeigned() {
    final var type = getType();
    final var response = healthCareUnitMemberTypeConverter.convert(type);

    assertEquals(type.isFeignedHealthCareUnitMember(), response.getFeignedHealthCareUnitMember());
  }

  @Test
  void shouldConvertName() {
    final var type = getType();
    final var response = healthCareUnitMemberTypeConverter.convert(type);

    assertEquals(type.getHealthCareUnitMemberName(), response.getHealthCareUnitMemberName());
  }

  @Test
  void shouldConvertHsaId() {
    final var type = getType();
    final var response = healthCareUnitMemberTypeConverter.convert(type);

    assertEquals(type.getHealthCareUnitMemberHsaId(), response.getHealthCareUnitMemberHsaId());
  }

  @Test
  void shouldConvertPublicName() {
    final var type = getType();
    final var response = healthCareUnitMemberTypeConverter.convert(type);

    assertEquals(type.getHealthCareUnitMemberPublicName(),
        response.getHealthCareUnitMemberPublicName());
  }

  @Test
  void shouldConvertEndDate() {
    final var type = getType();
    final var response = healthCareUnitMemberTypeConverter.convert(type);

    assertEquals(truncateToSeconds(MEMBER_END_DATE),
        truncateToSeconds(response.getHealthCareUnitMemberEndDate()));
  }

  @Test
  void shouldConvertStartDate() {
    final var type = getType();
    final var response = healthCareUnitMemberTypeConverter.convert(type);

    assertEquals(truncateToSeconds(MEMBER_START_DATE),
        truncateToSeconds(response.getHealthCareUnitMemberStartDate()));
  }

  @Nested
  class TestAddress {

    HealthCareUnitMemberType type;

    @BeforeEach
    void setup() {
      type = mock(HealthCareUnitMemberType.class);

      when(type.getHealthCareUnitMemberTelephoneNumber()).thenReturn(
          Collections.singletonList("PHONENUMBER")
      );
      when(type.getHealthCareUnitMemberpostalCode()).thenReturn("POSTAL_CODE");
      when(type.getHealthCareUnitMemberPrescriptionCode()).thenReturn(List.of("CODE1", "CODE2"));
    }

    @Test
    void shouldConvertAddress() {
      final var address = List.of("ADDRESS", "ADDRESS_2");
      final var addressType = mock(AddressType.class);
      when(addressTypeConverter.convertV2(any(AddressType.class))).thenReturn(address);
      when(type.getHealthCareUnitMemberpostalAddress()).thenReturn(addressType);

      final var response = healthCareUnitMemberTypeConverter.convert(type);

      assertEquals(
          address,
          response.getHealthCareUnitMemberpostalAddress()
      );
    }

    @Test
    void shouldConvertPhoneNumber() {
      final var response = healthCareUnitMemberTypeConverter.convert(type);

      assertEquals(
          type.getHealthCareUnitMemberTelephoneNumber(),
          response.getHealthCareUnitMemberTelephoneNumber()
      );
    }

    @Test
    void shouldConvertPostalCode() {
      final var response = healthCareUnitMemberTypeConverter.convert(type);

      assertEquals(
          type.getHealthCareUnitMemberpostalCode(),
          response.getHealthCareUnitMemberpostalCode()
      );
    }

    @Test
    void shouldConvertPrescriptionCode() {
      final var response = healthCareUnitMemberTypeConverter.convert(type);

      assertEquals(
          type.getHealthCareUnitMemberPrescriptionCode(),
          response.getHealthCareUnitMemberPrescriptionCode()
      );
    }
  }

  private HealthCareUnitMemberType getType() {
    final var type = new HealthCareUnitMemberType();
    type.setArchivedHealthCareUnitMember(true);
    type.setFeignedHealthCareUnitMember(true);
    type.setHealthCareUnitMemberName("MEMBER_NAME");
    type.setHealthCareUnitMemberHsaId("M_HSA_ID");
    type.setHealthCareUnitMemberPublicName("M_P_NAME");
    type.setHealthCareUnitMemberStartDate(toXMLGregorianCalendar(MEMBER_START_DATE));
    type.setHealthCareUnitMemberEndDate(toXMLGregorianCalendar(MEMBER_END_DATE));

    return type;
  }
}
