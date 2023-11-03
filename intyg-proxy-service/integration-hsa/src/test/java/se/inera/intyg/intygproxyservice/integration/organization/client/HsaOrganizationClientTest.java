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

package se.inera.intyg.intygproxyservice.integration.organization.client;

import static org.junit.jupiter.api.Assertions.*;
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
import se.inera.intyg.intygproxyservice.integration.api.organization.model.GetHealthCareUnitIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.GetHealthCareUnitMembersIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnit;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnitMembers;
import se.inera.intyg.intygproxyservice.integration.organization.client.converter.GetHealthCareUnitMembersResponseTypeConverter;
import se.inera.intyg.intygproxyservice.integration.organization.client.converter.GetHealthCareUnitResponseTypeConverter;
import se.riv.infrastructure.directory.organization.gethealthcareunit.v2.rivtabp21.GetHealthCareUnitResponderInterface;
import se.riv.infrastructure.directory.organization.gethealthcareunitmembers.v2.rivtabp21.GetHealthCareUnitMembersResponderInterface;
import se.riv.infrastructure.directory.organization.gethealthcareunitmembersresponder.v2.GetHealthCareUnitMembersResponseType;
import se.riv.infrastructure.directory.organization.gethealthcareunitmembersresponder.v2.GetHealthCareUnitMembersType;
import se.riv.infrastructure.directory.organization.gethealthcareunitresponder.v2.GetHealthCareUnitResponseType;
import se.riv.infrastructure.directory.organization.gethealthcareunitresponder.v2.GetHealthCareUnitType;

@ExtendWith(MockitoExtension.class)
class HsaOrganizationClientTest {

  public static final HealthCareUnit HEALTH_CARE_UNIT = HealthCareUnit.builder().build();
  public static final HealthCareUnitMembers HEALTH_CARE_UNIT_MEMBERS = HealthCareUnitMembers.builder()
      .build();

  public static final String HSA_ID = "HSA_ID";
  public static final GetHealthCareUnitIntegrationRequest REQUEST = GetHealthCareUnitIntegrationRequest
      .builder()
      .hsaId(HSA_ID)
      .build();

  public static final GetHealthCareUnitMembersIntegrationRequest REQUEST_MEMBERS = GetHealthCareUnitMembersIntegrationRequest
      .builder()
      .hsaId(HSA_ID)
      .build();
  public static final String LOGICAL_ADDRESS = "LOGICAL_ADDRESS";

  @Mock
  GetHealthCareUnitResponseTypeConverter getHealthCareUnitResponseTypeConverter;
  @Mock
  GetHealthCareUnitMembersResponseTypeConverter getHealthCareUnitMembersResponseTypeConverter;
  @Mock
  GetHealthCareUnitMembersResponderInterface getHealthCareUnitMembersResponderInterface;
  @Mock
  GetHealthCareUnitResponderInterface getHealthCareUnitResponderInterface;

  @InjectMocks
  HsaOrganizationClient hsaOrganizationClient;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(hsaOrganizationClient, "logicalAddress", LOGICAL_ADDRESS);
  }

  @Nested
  class HealthCareUnitTest {

    @Nested
    class UnexpectedError {

      @Test
      void shouldThrowErrorIfInterfaceThrowsError() {
        when(getHealthCareUnitResponderInterface
            .getHealthCareUnit(
                anyString(),
                any(GetHealthCareUnitType.class)
            )
        ).thenThrow(new IllegalStateException());

        assertThrows(IllegalStateException.class,
            () -> hsaOrganizationClient.getHealthCareUnit(REQUEST));
      }
    }

    @Nested
    class CorrectResponseFromInterface {

      @BeforeEach
      void setup() {
        when(getHealthCareUnitResponderInterface
            .getHealthCareUnit(
                anyString(),
                any(GetHealthCareUnitType.class)
            )
        ).thenReturn(new GetHealthCareUnitResponseType());
      }

      @Test
      void shouldReturnResponseWithHealthCareUnitReturnedFromConverter() {
        when(getHealthCareUnitResponseTypeConverter.convert(
                any(GetHealthCareUnitResponseType.class)
            )
        ).thenReturn(HEALTH_CARE_UNIT);

        final var response = hsaOrganizationClient.getHealthCareUnit(
            GetHealthCareUnitIntegrationRequest.builder().build()
        );

        assertEquals(HEALTH_CARE_UNIT, response);
      }

      @Test
      void shouldSendHsaIdInRequest() {
        hsaOrganizationClient.getHealthCareUnit(
            GetHealthCareUnitIntegrationRequest
                .builder()
                .hsaId(HSA_ID)
                .build()
        );

        final var captor = ArgumentCaptor.forClass(GetHealthCareUnitType.class);

        verify(
            getHealthCareUnitResponderInterface)
            .getHealthCareUnit(anyString(), captor.capture());

        assertEquals(HSA_ID, captor.getValue().getHealthCareUnitMemberHsaId());
      }

      @Test
      void shouldSendIncludeFeignedObjectsAsFalseInRquest() {
        hsaOrganizationClient.getHealthCareUnit(
            GetHealthCareUnitIntegrationRequest
                .builder()
                .hsaId(HSA_ID)
                .build()
        );

        final var captor = ArgumentCaptor.forClass(GetHealthCareUnitType.class);

        verify(
            getHealthCareUnitResponderInterface)
            .getHealthCareUnit(anyString(), captor.capture());

        assertFalse(captor.getValue().isIncludeFeignedObject());
      }

      @Test
      void shouldSendLogicalAddressInRequest() {
        hsaOrganizationClient.getHealthCareUnit(
            GetHealthCareUnitIntegrationRequest
                .builder()
                .hsaId(HSA_ID)
                .build()
        );

        final var captor = ArgumentCaptor.forClass(String.class);

        verify(
            getHealthCareUnitResponderInterface)
            .getHealthCareUnit(captor.capture(),
                any(GetHealthCareUnitType.class));

        assertEquals(LOGICAL_ADDRESS, captor.getValue());
      }
    }
  }

  @Nested
  class HealthCareUnitMembersTest {

    @Nested
    class UnexpectedError {

      @Test
      void shouldThrowErrorIfInterfaceThrowsError() {
        when(getHealthCareUnitMembersResponderInterface
            .getHealthCareUnitMembers(
                anyString(),
                any(GetHealthCareUnitMembersType.class)
            )
        ).thenThrow(new IllegalStateException());

        assertThrows(IllegalStateException.class,
            () -> hsaOrganizationClient.getHealthCareUnitMembers(REQUEST_MEMBERS));
      }
    }

    @Nested
    class CorrectResponseFromInterface {

      @BeforeEach
      void setup() {
        when(getHealthCareUnitMembersResponderInterface
            .getHealthCareUnitMembers(
                anyString(),
                any(GetHealthCareUnitMembersType.class)
            )
        ).thenReturn(new GetHealthCareUnitMembersResponseType());
      }

      @Test
      void shouldReturnResponseWithHealthCareUnitReturnedFromConverter() {
        when(getHealthCareUnitMembersResponseTypeConverter.convert(
                any(GetHealthCareUnitMembersResponseType.class)
            )
        ).thenReturn(HEALTH_CARE_UNIT_MEMBERS);

        final var response = hsaOrganizationClient.getHealthCareUnitMembers(
            GetHealthCareUnitMembersIntegrationRequest.builder().build()
        );

        assertEquals(HEALTH_CARE_UNIT_MEMBERS, response);
      }

      @Test
      void shouldSendHsaIdInRequest() {
        hsaOrganizationClient.getHealthCareUnitMembers(
            GetHealthCareUnitMembersIntegrationRequest
                .builder()
                .hsaId(HSA_ID)
                .build()
        );

        final var captor = ArgumentCaptor.forClass(GetHealthCareUnitMembersType.class);

        verify(
            getHealthCareUnitMembersResponderInterface)
            .getHealthCareUnitMembers(anyString(), captor.capture());

        assertEquals(HSA_ID, captor.getValue().getHealthCareUnitHsaId());
      }

      @Test
      void shouldSendIncludeFeignedObjectsAsFalseInRquest() {
        hsaOrganizationClient.getHealthCareUnitMembers(
            GetHealthCareUnitMembersIntegrationRequest
                .builder()
                .hsaId(HSA_ID)
                .build()
        );

        final var captor = ArgumentCaptor.forClass(GetHealthCareUnitMembersType.class);

        verify(
            getHealthCareUnitMembersResponderInterface)
            .getHealthCareUnitMembers(anyString(), captor.capture());

        assertFalse(captor.getValue().isIncludeFeignedObject());
      }

      @Test
      void shouldSendLogicalAddressInRequest() {
        hsaOrganizationClient.getHealthCareUnitMembers(
            GetHealthCareUnitMembersIntegrationRequest
                .builder()
                .hsaId(HSA_ID)
                .build()
        );

        final var captor = ArgumentCaptor.forClass(String.class);

        verify(
            getHealthCareUnitMembersResponderInterface)
            .getHealthCareUnitMembers(captor.capture(),
                any(GetHealthCareUnitMembersType.class));

        assertEquals(LOGICAL_ADDRESS, captor.getValue());
      }
    }
  }
}