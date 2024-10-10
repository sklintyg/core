/*
 * Copyright (C) 2024 Inera AB (http://www.inera.se)
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

import static se.inera.intyg.intygproxyservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareProviderIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareUnitIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareUnitMembersIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetUnitIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareProvider;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnit;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnitMembers;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Unit;
import se.inera.intyg.intygproxyservice.integration.organization.client.converter.GetHealthCareProviderResponseTypeConverter;
import se.inera.intyg.intygproxyservice.integration.organization.client.converter.GetHealthCareUnitMembersResponseTypeConverter;
import se.inera.intyg.intygproxyservice.integration.organization.client.converter.GetHealthCareUnitResponseTypeConverter;
import se.inera.intyg.intygproxyservice.integration.organization.client.converter.GetUnitResponseTypeConverter;
import se.inera.intyg.intygproxyservice.logging.PerformanceLogging;
import se.riv.infrastructure.directory.organization.gethealthcareprovider.v1.rivtabp21.GetHealthCareProviderResponderInterface;
import se.riv.infrastructure.directory.organization.gethealthcareproviderresponder.v1.GetHealthCareProviderType;
import se.riv.infrastructure.directory.organization.gethealthcareunit.v2.rivtabp21.GetHealthCareUnitResponderInterface;
import se.riv.infrastructure.directory.organization.gethealthcareunitmembers.v2.rivtabp21.GetHealthCareUnitMembersResponderInterface;
import se.riv.infrastructure.directory.organization.gethealthcareunitmembersresponder.v2.GetHealthCareUnitMembersType;
import se.riv.infrastructure.directory.organization.gethealthcareunitresponder.v2.GetHealthCareUnitType;
import se.riv.infrastructure.directory.organization.getunit.v4.rivtabp21.GetUnitResponderInterface;
import se.riv.infrastructure.directory.organization.getunitresponder.v4.GetUnitType;

@Service
@Slf4j
@RequiredArgsConstructor
public class HsaOrganizationClient {

  private static final String PROFILE_BASIC = "basic";

  private final GetHealthCareUnitResponderInterface getHealthCareUnitResponderInterface;
  private final GetHealthCareUnitMembersResponderInterface getHealthCareUnitMembersResponderInterface;
  private final GetUnitResponderInterface getUnitResponderInterface;
  private final GetHealthCareProviderResponderInterface getHealthCareProviderResponderInterface;

  private final GetHealthCareUnitResponseTypeConverter getHealthCareUnitResponseTypeConverter;
  private final GetHealthCareUnitMembersResponseTypeConverter getHealthCareUnitMembersResponseTypeConverter;
  private final GetUnitResponseTypeConverter getUnitResponseTypeConverter;
  private final GetHealthCareProviderResponseTypeConverter getHealthCareProviderResponseTypeConverter;

  @Value("${integration.hsa.logical.address}")
  private String logicalAddress;

  @PerformanceLogging(eventAction = "get-unit", eventType = EVENT_TYPE_ACCESSED)
  public Unit getUnit(GetUnitIntegrationRequest request) {
    final var parameters = getUnitParameters(request.getHsaId());

    final var type = getUnitResponderInterface.getUnit(
        logicalAddress,
        parameters
    );

    return getUnitResponseTypeConverter.convert(type);
  }

  @PerformanceLogging(eventAction = "get-health-care-unit", eventType = EVENT_TYPE_ACCESSED)
  public HealthCareUnit getHealthCareUnit(GetHealthCareUnitIntegrationRequest request) {
    final var parameters = getHealthCareUnitParameters(request.getHsaId());

    final var type = getHealthCareUnitResponderInterface.getHealthCareUnit(
        logicalAddress,
        parameters
    );

    return getHealthCareUnitResponseTypeConverter.convert(type);
  }

  @PerformanceLogging(eventAction = "get-health-care-provider", eventType = EVENT_TYPE_ACCESSED)
  public List<HealthCareProvider> getHealthCareProvider(
      GetHealthCareProviderIntegrationRequest request) {
    final var parameters = getProviderParameters(request.getHsaId(),
        request.getOrganizationNumber());

    final var type = getHealthCareProviderResponderInterface.getHealthCareProvider(
        logicalAddress,
        parameters
    );

    return getHealthCareProviderResponseTypeConverter.convert(type);
  }

  @PerformanceLogging(eventAction = "get-health-care-unit-members", eventType = EVENT_TYPE_ACCESSED)
  public HealthCareUnitMembers getHealthCareUnitMembers(
      GetHealthCareUnitMembersIntegrationRequest request) {
    final var parameters = getHealthCareUnitMembersParameters(request.getHsaId());

    final var type = getHealthCareUnitMembersResponderInterface.getHealthCareUnitMembers(
        logicalAddress,
        parameters
    );

    return getHealthCareUnitMembersResponseTypeConverter.convert(type);
  }

  private static GetHealthCareProviderType getProviderParameters(String hsaId,
      String organizationNumber) {
    final var parameters = new GetHealthCareProviderType();
    parameters.setHealthCareProviderHsaId(hsaId);
    parameters.setHealthCareProviderOrgNo(organizationNumber);
    parameters.setIncludeFeignedObject(false);

    return parameters;
  }

  private static GetUnitType getUnitParameters(String hsaId) {
    final var parameters = new GetUnitType();
    parameters.setUnitHsaId(hsaId);
    parameters.setIncludeFeignedObject(false);
    parameters.getProfile().add(PROFILE_BASIC);
    return parameters;
  }

  private static GetHealthCareUnitType getHealthCareUnitParameters(String hsaId) {
    final var parameters = new GetHealthCareUnitType();
    parameters.setHealthCareUnitMemberHsaId(hsaId);
    parameters.setIncludeFeignedObject(false);
    return parameters;
  }

  private static GetHealthCareUnitMembersType getHealthCareUnitMembersParameters(String hsaId) {
    final var parameters = new GetHealthCareUnitMembersType();
    parameters.setHealthCareUnitHsaId(hsaId);
    parameters.setIncludeFeignedObject(false);
    return parameters;
  }
}
