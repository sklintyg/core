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

package se.inera.intyg.intygproxyservice.authorization.service;

import static se.inera.intyg.intygproxyservice.common.ValidationUtility.isStringInvalid;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.authorization.dto.CredentialInformationRequest;
import se.inera.intyg.intygproxyservice.authorization.dto.CredentialInformationResponse;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialInformationIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialInformationIntegrationService;

@Service
@AllArgsConstructor
@Slf4j
public class CredentialInformationService {

  private final GetCredentialInformationIntegrationService getCredentialInformationIntegrationService;

  public CredentialInformationResponse get(CredentialInformationRequest request) {
    validateRequest(request);

    log.info(
        String.format("Getting credential info for personHsaId: '%s'", request.getPersonHsaId()));

    final var response = getCredentialInformationIntegrationService.get(
        GetCredentialInformationIntegrationRequest.builder()
            .personHsaId(request.getPersonHsaId())
            .build()
    );

    log.info(String.format("Credential information for personHsaId: '%s' was retrieved",
        request.getPersonHsaId()));

    return CredentialInformationResponse
        .builder()
        .credentialInformation(response.getCredentialInformation())
        .build();
  }

  private static void validateRequest(CredentialInformationRequest request) {
    if (request == null) {
      throw new IllegalArgumentException("Request to get credential information is null");
    }

    if (isStringInvalid(request.getPersonHsaId())) {
      throw new IllegalArgumentException(
          String.format("PersonHsaId is not valid: '%s'", request.getPersonHsaId()));
    }
  }
}
