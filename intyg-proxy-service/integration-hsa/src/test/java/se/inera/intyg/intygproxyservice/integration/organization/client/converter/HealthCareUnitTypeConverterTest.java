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
import static se.inera.intyg.intygproxyservice.integration.common.TypeConverterHelper.toXMLGregorianCalendar;
import static se.inera.intyg.intygproxyservice.integration.common.TypeConverterHelper.truncateToSeconds;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.riv.infrastructure.directory.organization.gethealthcareunitresponder.v2.HealthCareUnitType;

@ExtendWith(MockitoExtension.class)
class HealthCareUnitTypeConverterTest {

  public static final LocalDateTime PROVIDER_END_DATE = LocalDateTime.now().plusDays(5);
  public static final LocalDateTime PROVIDER_START_DATE = LocalDateTime.now().plusDays(4);
  public static final LocalDateTime UNIT_END_DATE = LocalDateTime.now().plusDays(10);
  public static final LocalDateTime UNIT_START_DATE = LocalDateTime.now().plusDays(9);
  public static final LocalDateTime MEMBER_END_DATE = LocalDateTime.now().plusDays(15);
  public static final LocalDateTime MEMBER_START_DATE = LocalDateTime.now().plusDays(14);
  @InjectMocks
  HealthCareUnitTypeConverter healthCareUnitTypeConverter;

  @Nested
  class HealthCareUnit {

    @Test
    void shouldConvertArchived() {
      final var type = getType();
      final var response = healthCareUnitTypeConverter.convert(type);

      assertEquals(type.isArchivedHealthCareUnit(), response.getArchivedHealthCareUnit());
    }

    @Test
    void shouldConvertFeigned() {
      final var type = getType();
      final var response = healthCareUnitTypeConverter.convert(type);

      assertEquals(type.isFeignedHealthCareUnit(), response.getFeignedHealthCareUnit());
    }

    @Test
    void shouldConvertName() {
      final var type = getType();
      final var response = healthCareUnitTypeConverter.convert(type);

      assertEquals(type.getHealthCareUnitName(), response.getHealthCareUnitName());
    }

    @Test
    void shouldConvertHsaId() {
      final var type = getType();
      final var response = healthCareUnitTypeConverter.convert(type);

      assertEquals(type.getHealthCareUnitHsaId(), response.getHealthCareUnitHsaId());
    }

    @Test
    void shouldConvertPublicName() {
      final var type = getType();
      final var response = healthCareUnitTypeConverter.convert(type);

      assertEquals(type.getHealthCareUnitPublicName(), response.getHealthCareUnitPublicName());
    }

    @Test
    void shouldConvertEndDate() {
      final var type = getType();
      final var response = healthCareUnitTypeConverter.convert(type);

      assertEquals(truncateToSeconds(UNIT_END_DATE),
          truncateToSeconds(response.getHealthCareUnitEndDate()));
    }

    @Test
    void shouldConvertStartDate() {
      final var type = getType();
      final var response = healthCareUnitTypeConverter.convert(type);

      assertEquals(truncateToSeconds(UNIT_START_DATE),
          truncateToSeconds(response.getHealthCareUnitStartDate()));
    }
  }

  @Nested
  class HealthCareUnitMember {

    @Test
    void shouldConvertArchived() {
      final var type = getType();
      final var response = healthCareUnitTypeConverter.convert(type);

      assertEquals(type.isArchivedHealthCareUnitMember(),
          response.getArchivedHealthCareUnitMember());
    }

    @Test
    void shouldConvertFeigned() {
      final var type = getType();
      final var response = healthCareUnitTypeConverter.convert(type);

      assertEquals(type.isFeignedHealthCareUnitMember(), response.getFeignedHealthCareUnitMember());
    }

    @Test
    void shouldConvertName() {
      final var type = getType();
      final var response = healthCareUnitTypeConverter.convert(type);

      assertEquals(type.getHealthCareUnitMemberName(), response.getHealthCareUnitMemberName());
    }

    @Test
    void shouldConvertHsaId() {
      final var type = getType();
      final var response = healthCareUnitTypeConverter.convert(type);

      assertEquals(type.getHealthCareUnitMemberHsaId(), response.getHealthCareUnitMemberHsaId());
    }

    @Test
    void shouldConvertPublicName() {
      final var type = getType();
      final var response = healthCareUnitTypeConverter.convert(type);

      assertEquals(type.getHealthCareUnitMemberPublicName(),
          response.getHealthCareUnitMemberPublicName());
    }

    @Test
    void shouldConvertEndDate() {
      final var type = getType();
      final var response = healthCareUnitTypeConverter.convert(type);

      assertEquals(truncateToSeconds(MEMBER_END_DATE),
          truncateToSeconds(response.getHealthCareUnitMemberEndDate()));
    }

    @Test
    void shouldConvertStartDate() {
      final var type = getType();
      final var response = healthCareUnitTypeConverter.convert(type);

      assertEquals(truncateToSeconds(MEMBER_START_DATE),
          truncateToSeconds(response.getHealthCareUnitMemberStartDate()));
    }
  }

  @Nested
  class HealthCareProvider {

    @Test
    void shouldConvertArchived() {
      final var type = getType();
      final var response = healthCareUnitTypeConverter.convert(type);

      assertEquals(type.isArchivedHealthCareProvider(), response.getArchivedHealthCareProvider());
    }

    @Test
    void shouldConvertFeigned() {
      final var type = getType();
      final var response = healthCareUnitTypeConverter.convert(type);

      assertEquals(type.isFeignedHealthCareProvider(), response.getFeignedHealthCareProvider());
    }

    @Test
    void shouldConvertName() {
      final var type = getType();
      final var response = healthCareUnitTypeConverter.convert(type);

      assertEquals(type.getHealthCareProviderName(), response.getHealthCareProviderName());
    }

    @Test
    void shouldConvertHsaId() {
      final var type = getType();
      final var response = healthCareUnitTypeConverter.convert(type);

      assertEquals(type.getHealthCareProviderHsaId(), response.getHealthCareProviderHsaId());
    }

    @Test
    void shouldConvertPublicName() {
      final var type = getType();
      final var response = healthCareUnitTypeConverter.convert(type);

      assertEquals(type.getHealthCareProviderPublicName(),
          response.getHealthCareProviderPublicName());
    }

    @Test
    void shouldConvertOrgNo() {
      final var type = getType();
      final var response = healthCareUnitTypeConverter.convert(type);

      assertEquals(type.getHealthCareProviderOrgNo(), response.getHealthCareProviderOrgNo());
    }

    @Test
    void shouldConvertEndDate() {
      final var type = getType();
      final var response = healthCareUnitTypeConverter.convert(type);

      assertEquals(truncateToSeconds(PROVIDER_END_DATE),
          truncateToSeconds(response.getHealthCareProviderEndDate()));
    }

    @Test
    void shouldConvertStartDate() {
      final var type = getType();
      final var response = healthCareUnitTypeConverter.convert(type);

      assertEquals(truncateToSeconds(PROVIDER_START_DATE),
          truncateToSeconds(response.getHealthCareProviderStartDate()));
    }
  }

  private HealthCareUnitType getType() {
    final var type = new HealthCareUnitType();
    type.setArchivedHealthCareUnit(true);
    type.setArchivedHealthCareProvider(true);
    type.setArchivedHealthCareUnitMember(true);

    type.setFeignedHealthCareUnit(true);
    type.setFeignedHealthCareProvider(true);
    type.setFeignedHealthCareUnitMember(true);

    type.setHealthCareProviderName("PROVIDER_NAME");
    type.setHealthCareProviderHsaId("PROVIDER_HSA_ID");
    type.setHealthCareProviderOrgNo("ORG_NO");
    type.setHealthCareProviderPublicName("PROVIDER_PUBLIC_NAME");
    type.setHealthCareProviderEndDate(toXMLGregorianCalendar(PROVIDER_END_DATE));
    type.setHealthCareProviderStartDate(toXMLGregorianCalendar(PROVIDER_START_DATE));

    type.setHealthCareUnitName("UNIT_NAME");
    type.setHealthCareUnitHsaId("UNIT_HSA_ID");
    type.setHealthCareUnitPublicName("UNIT_P_NAME");
    type.setHealthCareUnitEndDate(toXMLGregorianCalendar(UNIT_END_DATE));
    type.setHealthCareUnitStartDate(toXMLGregorianCalendar(UNIT_START_DATE));

    type.setHealthCareUnitMemberName("MEMBER_NAME");
    type.setHealthCareUnitMemberHsaId("M_HSA_ID");
    type.setHealthCareUnitMemberPublicName("M_P_NAME");
    type.setHealthCareUnitMemberStartDate(toXMLGregorianCalendar(MEMBER_START_DATE));
    type.setHealthCareUnitMemberEndDate(toXMLGregorianCalendar(MEMBER_END_DATE));

    return type;
  }
}