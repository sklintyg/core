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

package se.inera.intyg.intygproxyservice.authorization.service;

import static se.inera.intyg.intygproxyservice.common.ValidationUtility.isStringInvalid;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.authorization.dto.CredentialsForPersonRequest;
import se.inera.intyg.intygproxyservice.authorization.dto.CredentialsForPersonResponse;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialsForPersonIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialsForPersonIntegrationService;
import se.inera.intyg.intygproxyservice.logging.LogHashUtility;

@Service
@AllArgsConstructor
@Slf4j
public class CredentialsForPersonService {

  private final GetCredentialsForPersonIntegrationService getCredentialsForPersonIntegrationService;
  private final LogHashUtility logHashUtility;

  public CredentialsForPersonResponse get(CredentialsForPersonRequest request) {
    validateRequest(request);

    log.info(
        String.format(
            "Getting credential info for person with personId: '%s'",
            logHashUtility.hash(request.getPersonId())
        )
    );

    final var response = getCredentialsForPersonIntegrationService.get(
        GetCredentialsForPersonIntegrationRequest.builder()
            .personId(request.getPersonId())
            .build()
    );

    log.info(String.format(
            "Credentials for person with personId: '%s' was retrieved",
            logHashUtility.hash(request.getPersonId())
        )
    );

    return CredentialsForPersonResponse
        .builder()
        .credentials(response.getCredentials())
        .build();
  }

  private void validateRequest(CredentialsForPersonRequest request) {
    if (request == null) {
      throw new IllegalArgumentException("Request to get credentials for person is null");
    }

    if (isStringInvalid(request.getPersonId())) {
      throw new IllegalArgumentException(
          String.format(
              "PersonId is not valid: '%s'",
              logHashUtility.hash(request.getPersonId())
          )
      );
    }
  }
}
