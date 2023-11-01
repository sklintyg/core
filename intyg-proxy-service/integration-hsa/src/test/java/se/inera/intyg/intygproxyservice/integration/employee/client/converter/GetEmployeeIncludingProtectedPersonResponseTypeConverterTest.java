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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import riv.infrastructure.directory.employee._3.PersonInformationType;
import se.inera.intyg.intygproxyservice.integration.api.employee.PersonInformation;
import se.riv.infrastructure.directory.employee.getemployeeincludingprotectedpersonresponder.v3.GetEmployeeIncludingProtectedPersonResponseType;

@ExtendWith(MockitoExtension.class)
class GetEmployeeIncludingProtectedPersonResponseTypeConverterTest {

  public static final PersonInformation PERSON_INFORMATION = PersonInformation.builder().build();
  @Mock
  PersonInformationTypeConverter personInformationTypeConverter;

  @InjectMocks
  GetEmployeeIncludingProtectedPersonResponseTypeConverter getEmployeeIncludingProtectedPersonResponseTypeConverter;

  @BeforeEach
  void setup() {
    when(personInformationTypeConverter.convert(any(PersonInformationType.class)))
        .thenReturn(PERSON_INFORMATION);
  }

  @Test
  void shouldConvertPersonInformationList() {
    final var response = getEmployeeIncludingProtectedPersonResponseTypeConverter.convert(getType());

    assertEquals(1, response.getPersonInformation().size());
    assertEquals(PERSON_INFORMATION, response.getPersonInformation().get(0));
  }

  private GetEmployeeIncludingProtectedPersonResponseType getType() {
    final var type = mock(GetEmployeeIncludingProtectedPersonResponseType.class);
    when(type.getPersonInformation()).thenReturn(
          List.of(
            new PersonInformationType()
        )
    );

    return type;
  }
}