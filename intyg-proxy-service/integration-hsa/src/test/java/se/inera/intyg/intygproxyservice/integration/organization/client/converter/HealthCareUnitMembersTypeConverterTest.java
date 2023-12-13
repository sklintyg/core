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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.intygproxyservice.integration.common.TypeConverterHelper.toXMLGregorianCalendar;
import static se.inera.intyg.intygproxyservice.integration.common.TypeConverterHelper.truncateToSeconds;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareProvider;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnitMember;
import se.riv.infrastructure.directory.organization.gethealthcareunitmembersresponder.v2.HealthCareProviderType;
import se.riv.infrastructure.directory.organization.gethealthcareunitmembersresponder.v2.HealthCareUnitMemberType;
import se.riv.infrastructure.directory.organization.gethealthcareunitmembersresponder.v2.HealthCareUnitMembersType;
import se.riv.infrastructure.directory.organization.v2.AddressType;

@ExtendWith(MockitoExtension.class)
class HealthCareUnitMembersTypeConverterTest {

  public static final LocalDateTime UNIT_END_DATE = LocalDateTime.now().plusDays(10);
  public static final LocalDateTime UNIT_START_DATE = LocalDateTime.now().plusDays(9);

  @Mock
  HealthCareProviderTypeConverter healthCareProviderTypeConverter;
  @Mock
  HealthCareUnitMemberTypeConverter healthCareUnitMemberTypeConverter;
  @Mock
  AddressTypeConverter addressTypeConverter;

  @InjectMocks
  HealthCareUnitMembersTypeConverter healthCareUnitMembersTypeConverter;

  @Nested
  class HealthCareUnit {

    @Test
    void shouldConvertArchived() {
      final var type = getType();
      final var response = healthCareUnitMembersTypeConverter.convert(type);

      assertEquals(type.isArchivedHealthCareUnit(), response.getArchivedHealthCareUnit());
    }

    @Test
    void shouldConvertFeigned() {
      final var type = getType();
      final var response = healthCareUnitMembersTypeConverter.convert(type);

      assertEquals(type.isFeignedHealthCareUnit(), response.getFeignedHealthCareUnit());
    }

    @Test
    void shouldConvertName() {
      final var type = getType();
      final var response = healthCareUnitMembersTypeConverter.convert(type);

      assertEquals(type.getHealthCareUnitName(), response.getHealthCareUnitName());
    }

    @Test
    void shouldConvertHsaId() {
      final var type = getType();
      final var response = healthCareUnitMembersTypeConverter.convert(type);

      assertEquals(type.getHealthCareUnitHsaId(), response.getHealthCareUnitHsaId());
    }

    @Test
    void shouldConvertPublicName() {
      final var type = getType();
      final var response = healthCareUnitMembersTypeConverter.convert(type);

      assertEquals(type.getHealthCareUnitPublicName(), response.getHealthCareUnitPublicName());
    }

    @Test
    void shouldConvertPostalCode() {
      final var type = getType();
      final var response = healthCareUnitMembersTypeConverter.convert(type);

      assertEquals(type.getPostalCode(), response.getPostalCode());
    }

    @Test
    void shouldConvertPrescriptionCode() {
      final var type = mock(HealthCareUnitMembersType.class);
      when(type.getHealthCareUnitPrescriptionCode())
          .thenReturn(List.of("CODE1", "CODE2"));

      final var response = healthCareUnitMembersTypeConverter.convert(type);

      assertEquals(
          type.getHealthCareUnitPrescriptionCode(),
          response.getHealthCareUnitPrescriptionCode()
      );
    }

    @Test
    void shouldConvertEndDate() {
      final var type = getType();
      final var response = healthCareUnitMembersTypeConverter.convert(type);

      assertEquals(truncateToSeconds(UNIT_END_DATE),
          truncateToSeconds(response.getHealthCareUnitEndDate()));
    }

    @Test
    void shouldConvertStartDate() {
      final var type = getType();
      final var response = healthCareUnitMembersTypeConverter.convert(type);

      assertEquals(truncateToSeconds(UNIT_START_DATE),
          truncateToSeconds(response.getHealthCareUnitStartDate()));
    }
  }

  @Test
  void shouldConvertHealthCareProvider() {
    final var provider = HealthCareProvider.builder().healthCareProviderName("NAME").build();
    when(healthCareProviderTypeConverter.convertV2(any(HealthCareProviderType.class)))
        .thenReturn(provider);
    final var type = getType();

    final var response = healthCareUnitMembersTypeConverter.convert(type);
    final var captor = ArgumentCaptor.forClass(HealthCareProviderType.class);

    verify(healthCareProviderTypeConverter).convertV2(captor.capture());

    assertEquals(type.getHealthCareProvider(), captor.getValue());
    assertEquals(provider, response.getHealthCareProvider());
  }

  @Test
  void shouldConvertHealthCareMember() {
    final var member = HealthCareUnitMember.builder().build();
    final var type = mock(HealthCareUnitMembersType.class);
    when(type.getHealthCareUnitMember())
        .thenReturn(List.of(new HealthCareUnitMemberType(), new HealthCareUnitMemberType()));
    when(healthCareUnitMemberTypeConverter.convert(any(HealthCareUnitMemberType.class)))
        .thenReturn(member);

    final var response = healthCareUnitMembersTypeConverter.convert(type);

    verify(healthCareUnitMemberTypeConverter, times(2))
        .convert(any(HealthCareUnitMemberType.class));

    assertEquals(2, response.getHealthCareUnitMember().size());
    assertEquals(member, response.getHealthCareUnitMember().get(0));
  }

  @Test
  void shouldConvertTelephoneNumber() {
    final var type = mock(HealthCareUnitMembersType.class);
    when(type.getTelephoneNumber())
        .thenReturn(List.of("PHONENUMBER"));

    final var response = healthCareUnitMembersTypeConverter.convert(type);

    assertEquals(type.getTelephoneNumber(), response.getTelephoneNumber());
  }

  @Test
  void shouldConvertAddress() {
    final var type = mock(HealthCareUnitMembersType.class);
    final var address = List.of("ADDRESS", "ADDRESS_2");
    final var addressType = mock(AddressType.class);

    when(addressTypeConverter.convertV2(any(AddressType.class))).thenReturn(address);
    when(type.getPostalAddress()).thenReturn(addressType);

    final var response = healthCareUnitMembersTypeConverter.convert(type);

    assertEquals(
        address,
        response.getPostalAddress()
    );
  }

  private HealthCareUnitMembersType getType() {
    final var type = new HealthCareUnitMembersType();
    type.setArchivedHealthCareUnit(true);
    type.setFeignedHealthCareUnit(true);
    type.setHealthCareUnitName("UNIT_NAME");
    type.setHealthCareUnitHsaId("UNIT_HSA_ID");
    type.setHealthCareUnitPublicName("UNIT_P_NAME");
    type.setHealthCareUnitEndDate(toXMLGregorianCalendar(UNIT_END_DATE));
    type.setHealthCareUnitStartDate(toXMLGregorianCalendar(UNIT_START_DATE));
    type.setHealthCareProvider(new HealthCareProviderType());
    type.setPostalCode("POSTAL_CODE");
    type.setPostalAddress(new AddressType());

    return type;
  }
}