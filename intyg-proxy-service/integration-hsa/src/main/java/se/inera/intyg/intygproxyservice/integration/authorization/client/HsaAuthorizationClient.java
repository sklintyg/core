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

package se.inera.intyg.intygproxyservice.integration.authorization.client;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialInformationIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialInformation;
import se.inera.intyg.intygproxyservice.integration.authorization.client.converter.GetCredentialInformationResponseTypeConverter;
import se.riv.infrastructure.directory.authorizationmanagement.getcredentialsforpersonincludingprotectedperson.v2.rivtabp21.GetCredentialsForPersonIncludingProtectedPersonResponderInterface;
import se.riv.infrastructure.directory.authorizationmanagement.getcredentialsforpersonincludingprotectedpersonresponder.v2.GetCredentialsForPersonIncludingProtectedPersonType;

@Service
@Slf4j
@RequiredArgsConstructor
public class HsaAuthorizationClient {

  private static final String PROFILE_BASIC = "BASIC";
  
  private final GetCredentialsForPersonIncludingProtectedPersonResponderInterface getCredentialsForPersonIncludingProtectedPersonResponderInterface;

  private final GetCredentialInformationResponseTypeConverter getCredentialInformationResponseTypeConverter;

  @Value("${integration.hsa.logical.address}")
  private String logicalAddress;

  public List<CredentialInformation> getCredentialInformation(
      GetCredentialInformationIntegrationRequest request) {
    final var parameters = getCredentialInformationParameters(request.getPersonHsaId());

    final var type = getCredentialsForPersonIncludingProtectedPersonResponderInterface.getCredentialsForPersonIncludingProtectedPerson(
        logicalAddress,
        parameters
    );

    return getCredentialInformationResponseTypeConverter.convert(type);
  }

  private static GetCredentialsForPersonIncludingProtectedPersonType getCredentialInformationParameters(
      String hsaId) {
    final var parameters = new GetCredentialsForPersonIncludingProtectedPersonType();
    parameters.setPersonHsaId(hsaId);
    parameters.setIncludeFeignedObject(false);
    parameters.setProfile(PROFILE_BASIC);
    return parameters;
  }
}
