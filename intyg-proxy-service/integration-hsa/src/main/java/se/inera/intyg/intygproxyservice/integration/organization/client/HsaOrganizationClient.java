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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareUnitIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetHealthCareUnitMembersIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.GetUnitIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnit;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnitMembers;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Unit;
import se.inera.intyg.intygproxyservice.integration.organization.client.converter.GetHealthCareUnitMembersResponseTypeConverter;
import se.inera.intyg.intygproxyservice.integration.organization.client.converter.GetHealthCareUnitResponseTypeConverter;
import se.inera.intyg.intygproxyservice.integration.organization.client.converter.GetUnitResponseTypeConverter;
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

  private final GetHealthCareUnitResponderInterface getHealthCareUnitResponderInterface;
  private final GetHealthCareUnitMembersResponderInterface getHealthCareUnitMembersResponderInterface;
  private final GetUnitResponderInterface getUnitResponderInterface;

  private final GetHealthCareUnitResponseTypeConverter getHealthCareUnitResponseTypeConverter;
  private final GetHealthCareUnitMembersResponseTypeConverter getHealthCareUnitMembersResponseTypeConverter;
  private final GetUnitResponseTypeConverter getUnitResponseTypeConverter;

  @Value("${integration.hsa.logical.address}")
  private String logicalAddress;

  public Unit getUnit(GetUnitIntegrationRequest request) {
    final var parameters = getUnitParameters(request.getHsaId());

    final var type = getUnitResponderInterface.getUnit(
        logicalAddress,
        parameters
    );

    return getUnitResponseTypeConverter.convert(type);
  }

  public HealthCareUnit getHealthCareUnit(GetHealthCareUnitIntegrationRequest request) {
    final var parameters = getHealthCareUnitParameters(request.getHsaId());

    final var type = getHealthCareUnitResponderInterface.getHealthCareUnit(
        logicalAddress,
        parameters
    );

    return getHealthCareUnitResponseTypeConverter.convert(type);
  }

  public HealthCareUnitMembers getHealthCareUnitMembers(
      GetHealthCareUnitMembersIntegrationRequest request) {
    final var parameters = getHealthCareUnitMembersParameters(request.getHsaId());

    final var type = getHealthCareUnitMembersResponderInterface.getHealthCareUnitMembers(
        logicalAddress,
        parameters
    );

    return getHealthCareUnitMembersResponseTypeConverter.convert(type);
  }

  private static GetUnitType getUnitParameters(String hsaId) {
    final var parameters = new GetUnitType();
    parameters.setUnitHsaId(hsaId);
    parameters.setIncludeFeignedObject(false);
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
