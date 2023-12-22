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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.employee.Employee;
import se.inera.intyg.intygproxyservice.integration.api.employee.PersonInformation;
import se.riv.infrastructure.directory.employee.getemployeeincludingprotectedpersonresponder.v3.GetEmployeeIncludingProtectedPersonResponseType;
import se.riv.infrastructure.directory.employee.v3.PersonInformationType;

@ExtendWith(MockitoExtension.class)
class GetEmployeeIncludingProtectedPersonResponseTypeConverterTest {

  public static final PersonInformation PERSON_INFORMATION = PersonInformation.builder().build();
  @Mock
  PersonInformationTypeConverter personInformationTypeConverter;

  @InjectMocks
  GetEmployeeIncludingProtectedPersonResponseTypeConverter getEmployeeIncludingProtectedPersonResponseTypeConverter;

  @Test
  void shouldReturnEmployeeWithEmptyPersonalInformationIfTypeIsNull() {
    final var expectedResponse = Employee.builder()
        .personInformation(Collections.emptyList())
        .build();

    final var response = getEmployeeIncludingProtectedPersonResponseTypeConverter.convert(null);

    assertEquals(expectedResponse, response);
  }

  @Test
  void shouldReturnEmployeeWithEmptyPersonalInformationIfPersonalInformationIsNull() {
    final var expectedResponse = Employee.builder()
        .personInformation(Collections.emptyList())
        .build();

    final var type = mock(GetEmployeeIncludingProtectedPersonResponseType.class);
    when(type.getPersonInformation()).thenReturn(null);

    final var response = getEmployeeIncludingProtectedPersonResponseTypeConverter.convert(type);

    assertEquals(expectedResponse, response);
  }

  @Test
  void shouldConvertPersonInformationList() {
    when(personInformationTypeConverter.convert(any(PersonInformationType.class)))
        .thenReturn(PERSON_INFORMATION);

    final var response = getEmployeeIncludingProtectedPersonResponseTypeConverter
        .convert(getType(List.of(new PersonInformationType())));

    assertEquals(1, response.getPersonInformation().size());
    assertEquals(PERSON_INFORMATION, response.getPersonInformation().get(0));
  }

  @Test
  void shouldConvertPersonInformationListWithSeveralValues() {
    when(personInformationTypeConverter.convert(any(PersonInformationType.class)))
        .thenReturn(PERSON_INFORMATION);

    final var response = getEmployeeIncludingProtectedPersonResponseTypeConverter
        .convert(getType(List.of(new PersonInformationType(), new PersonInformationType())));

    verify(personInformationTypeConverter, times(2))
        .convert(any(PersonInformationType.class));

    assertEquals(2, response.getPersonInformation().size());
  }

  private GetEmployeeIncludingProtectedPersonResponseType getType(
      List<PersonInformationType> list) {
    final var type = mock(GetEmployeeIncludingProtectedPersonResponseType.class);
    when(type.getPersonInformation()).thenReturn(list);

    return type;
  }
}
