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

package se.inera.intyg.intygproxyservice.integration.employee.client.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static se.inera.intyg.intygproxyservice.integration.common.TypeConverterHelper.toXMLGregorianCalendar;
import static se.inera.intyg.intygproxyservice.integration.common.TypeConverterHelper.truncateToSeconds;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.riv.infrastructure.directory.employee.v3.HealthCareProfessionalLicenceSpecialityType;
import se.riv.infrastructure.directory.employee.v3.PersonInformationType;

@ExtendWith(MockitoExtension.class)
class PersonInformationTypeConverterTest {

  public static final LocalDateTime END_DATE = LocalDateTime.now().minusDays(1);
  public static final LocalDateTime START_DATE = LocalDateTime.now();
  @InjectMocks
  private PersonInformationTypeConverter personInformationTypeConverter;

  @Test
  void shouldConvertAge() {
    final var type = getPersonInformationType();

    final var response = personInformationTypeConverter.convert(type);

    assertEquals(type.getAge(), response.getAge());
  }

  @Test
  void shouldConvertFeignedPerson() {
    final var type = getPersonInformationType();

    final var response = personInformationTypeConverter.convert(type);

    assertEquals(type.isFeignedPerson(), response.getFeignedPerson());
  }

  @Test
  void shouldConvertGender() {
    final var type = getPersonInformationType();

    final var response = personInformationTypeConverter.convert(type);

    assertEquals(type.getGender(), response.getGender());
  }

  @Test
  void shouldConvertPersonHsaId() {
    final var type = getPersonInformationType();

    final var response = personInformationTypeConverter.convert(type);

    assertEquals(type.getPersonHsaId(), response.getPersonHsaId());
  }

  @Test
  void shouldConvertGivenName() {
    final var type = getPersonInformationType();

    final var response = personInformationTypeConverter.convert(type);

    assertEquals(type.getGivenName(), response.getGivenName());
  }

  @Test
  void shouldConvertMiddleAndSurName() {
    final var type = getPersonInformationType();

    final var response = personInformationTypeConverter.convert(type);

    assertEquals(type.getMiddleAndSurName(), response.getMiddleAndSurName());
  }

  @Test
  void shouldConvertProtectedPerson() {
    final var type = getPersonInformationType();

    final var response = personInformationTypeConverter.convert(type);

    assertEquals(type.isProtectedPerson(), response.getProtectedPerson());
  }

  @Test
  void shouldConvertTitle() {
    final var type = getPersonInformationType();

    final var response = personInformationTypeConverter.convert(type);

    assertEquals(type.getTitle(), response.getTitle());
  }

  @Test
  void shouldConvertPersonEndDate() {
    final var type = getPersonInformationType();

    final var response = personInformationTypeConverter.convert(type);

    assertEquals(truncateToSeconds(END_DATE),
        truncateToSeconds(response.getPersonEndDate()));
  }

  @Test
  void shouldConvertPersonStartDate() {
    final var type = getPersonInformationType();

    final var response = personInformationTypeConverter.convert(type);

    assertEquals(truncateToSeconds(START_DATE),
        response.getPersonStartDate().truncatedTo(ChronoUnit.SECONDS));
  }

  @Test
  void shouldConvertSpecialityLicense() {
    final var type = mock(PersonInformationType.class);
    when(type.getHealthCareProfessionalLicence()).thenReturn(
        List.of("SPECIALITY", "SPECIALITY_2")
    );

    final var response = personInformationTypeConverter.convert(type);

    assertEquals(
        type.getHealthCareProfessionalLicence(),
        response.getHealthCareProfessionalLicence()
    );
  }

  @Nested
  class HCPSpecialityCodes {

    private PersonInformationType type;

    @BeforeEach
    void setup() {
      type = mock(PersonInformationType.class);
      final var hcpCode = getHCPCode("CODE", "NAME", "LICENCE");
      final var hcpCode2 = getHCPCode("CODE2", "NAME2", "LICENCE2");

      when(type.getHealthCareProfessionalLicenceSpeciality()).thenReturn(
          List.of(
              hcpCode, hcpCode2
          )
      );
    }

    @Test
    void shouldConvertSpecialityLicenceSpecialityCode() {
      final var response = personInformationTypeConverter.convert(type);

      assertEquals(
          type.getHealthCareProfessionalLicenceSpeciality().get(0).getSpecialityCode(),
          response.getHealthCareProfessionalLicenceSpeciality().get(0).getSpecialityCode()
      );
    }

    @Test
    void shouldConvertSpecialityLicenceSpecialityName() {
      final var response = personInformationTypeConverter.convert(type);

      assertEquals(
          type.getHealthCareProfessionalLicenceSpeciality().get(0).getSpecialityName(),
          response.getHealthCareProfessionalLicenceSpeciality().get(0).getSpecialityName()
      );
    }

    @Test
    void shouldConvertSpecialityLicenceSpecialityLicence() {
      final var response = personInformationTypeConverter.convert(type);

      assertEquals(
          type.getHealthCareProfessionalLicenceSpeciality().get(0)
              .getHealthCareProfessionalLicence(),
          response.getHealthCareProfessionalLicenceSpeciality().get(0)
              .getHealthCareProfessionalLicenceCode()
      );
    }
  }

  PersonInformationType getPersonInformationType() {
    final var type = new PersonInformationType();
    type.setAge("AGE");
    type.setFeignedPerson(true);
    type.setGender("GENDER");
    type.setPersonHsaId("PERSON_HSA_ID");
    type.setGivenName("GIVEN_NAME");
    type.setMiddleAndSurName("MIDDLE_SURNAME");
    type.setProtectedPerson(true);
    type.setTitle("TITLE");
    type.setPersonEndDate(toXMLGregorianCalendar(END_DATE));
    type.setPersonStartDate(toXMLGregorianCalendar(START_DATE));
    return type;
  }

  private HealthCareProfessionalLicenceSpecialityType getHCPCode(String name, String code,
      String licence) {
    final var hcpCode = new HealthCareProfessionalLicenceSpecialityType();

    hcpCode.setSpecialityCode(code);
    hcpCode.setSpecialityName(name);
    hcpCode.setHealthCareProfessionalLicence(licence);

    return hcpCode;
  }
}