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

package se.inera.intyg.intygproxyservice.integration.employee.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.intygproxyservice.integration.api.employee.Employee;
import se.inera.intyg.intygproxyservice.integration.api.employee.GetEmployeeIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.employee.client.converter.GetEmployeeIncludingProtectedPersonResponseTypeConverter;
import se.riv.infrastructure.directory.employee.getemployeeincludingprotectedperson.v3.rivtabp21.GetEmployeeIncludingProtectedPersonResponderInterface;
import se.riv.infrastructure.directory.employee.getemployeeincludingprotectedpersonresponder.v3.GetEmployeeIncludingProtectedPersonResponseType;
import se.riv.infrastructure.directory.employee.getemployeeincludingprotectedpersonresponder.v3.GetEmployeeIncludingProtectedPersonType;

@ExtendWith(MockitoExtension.class)
class HsaEmployeeClientTest {

  public static final Employee EMPLOYEE = Employee.builder().build();
  public static final String HSA_ID = "HSA_ID";
  public static final String PERSON_ID = "PERSON_ID";
  public static final GetEmployeeIntegrationRequest REQUEST = GetEmployeeIntegrationRequest.builder()
      .hsaId(HSA_ID).personId(PERSON_ID).build();
  public static final String LOGICAL_ADDRESS = "LOGICAL_ADDRESS";
  @Mock
  GetEmployeeIncludingProtectedPersonResponseTypeConverter getEmployeeIncludingProtectedPersonResponseTypeConverter;
  @Mock
  GetEmployeeIncludingProtectedPersonResponderInterface getEmployeeIncludingProtectedPersonResponderInterface;

  @InjectMocks
  HsaEmployeeClient hsaEmployeeClient;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(hsaEmployeeClient, "logicalAddress", LOGICAL_ADDRESS);
  }

  @Nested
  class UnexpectedError {

    @Test
    void shouldThrowErrorIfInterfaceThrowsError() {
      when(getEmployeeIncludingProtectedPersonResponderInterface
          .getEmployeeIncludingProtectedPerson(
              anyString(),
              any(GetEmployeeIncludingProtectedPersonType.class)
          )
      ).thenThrow(new IllegalStateException());

      assertThrows(IllegalStateException.class, () -> hsaEmployeeClient.getEmployee(REQUEST));
    }
  }

  @Nested
  class CorrectResponseFromInterface {

    @BeforeEach
    void setup() {
      when(getEmployeeIncludingProtectedPersonResponderInterface
          .getEmployeeIncludingProtectedPerson(
              anyString(),
              any(GetEmployeeIncludingProtectedPersonType.class)
          )
      ).thenReturn(new GetEmployeeIncludingProtectedPersonResponseType());
    }

    @Test
    void shouldReturnResponseWithEmployeeReturnedFromConverter() {
      when(getEmployeeIncludingProtectedPersonResponseTypeConverter.convert(
              any(GetEmployeeIncludingProtectedPersonResponseType.class)
          )
      ).thenReturn(EMPLOYEE);

      final var response = hsaEmployeeClient.getEmployee(
          GetEmployeeIntegrationRequest.builder().build()
      );

      assertEquals(EMPLOYEE, response);
    }

    @Test
    void shouldSendHsaIdInRequest() {
      hsaEmployeeClient.getEmployee(
          GetEmployeeIntegrationRequest
              .builder()
              .hsaId(HSA_ID)
              .personId(PERSON_ID)
              .build()
      );

      final var captor = ArgumentCaptor.forClass(GetEmployeeIncludingProtectedPersonType.class);

      verify(
          getEmployeeIncludingProtectedPersonResponderInterface)
          .getEmployeeIncludingProtectedPerson(anyString(), captor.capture());

      assertEquals(HSA_ID, captor.getValue().getPersonHsaId());
    }

    @Test
    void shouldSendPersonIdInRequest() {
      hsaEmployeeClient.getEmployee(
          GetEmployeeIntegrationRequest
              .builder()
              .hsaId(HSA_ID)
              .personId(PERSON_ID)
              .build()
      );

      final var captor = ArgumentCaptor.forClass(GetEmployeeIncludingProtectedPersonType.class);

      verify(
          getEmployeeIncludingProtectedPersonResponderInterface)
          .getEmployeeIncludingProtectedPerson(any(), captor.capture());

      assertEquals(PERSON_ID, captor.getValue().getPersonalIdentityNumber());
    }

    @Test
    void shouldSendLogicalAddressInRequest() {
      hsaEmployeeClient.getEmployee(
          GetEmployeeIntegrationRequest
              .builder()
              .hsaId(HSA_ID)
              .personId(PERSON_ID)
              .build()
      );

      final var captor = ArgumentCaptor.forClass(String.class);

      verify(
          getEmployeeIncludingProtectedPersonResponderInterface)
          .getEmployeeIncludingProtectedPerson(captor.capture(),
              any(GetEmployeeIncludingProtectedPersonType.class));

      assertEquals(LOGICAL_ADDRESS, captor.getValue());
    }

    @Test
    void shouldSendIncludeFeignedObjectsAsFalse() {
      hsaEmployeeClient.getEmployee(
          GetEmployeeIntegrationRequest
              .builder()
              .hsaId(HSA_ID)
              .personId(PERSON_ID)
              .build()
      );

      final var captor = ArgumentCaptor.forClass(GetEmployeeIncludingProtectedPersonType.class);

      verify(
          getEmployeeIncludingProtectedPersonResponderInterface)
          .getEmployeeIncludingProtectedPerson(anyString(),
              captor.capture());

      assertFalse(captor.getValue().isIncludeFeignedObject());
    }

    @Test
    void shouldSendProfileInRequest() {
      hsaEmployeeClient.getEmployee(
          GetEmployeeIntegrationRequest
              .builder()
              .hsaId(HSA_ID)
              .personId(PERSON_ID)
              .build()
      );

      final var captor = ArgumentCaptor.forClass(GetEmployeeIncludingProtectedPersonType.class);

      verify(
          getEmployeeIncludingProtectedPersonResponderInterface)
          .getEmployeeIncludingProtectedPerson(anyString(),
              captor.capture());

      assertEquals("extended1", captor.getValue().getProfile());
    }
  }
}
