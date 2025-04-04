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
import se.inera.intyg.intygproxyservice.authorization.dto.HandleCertificationPersonRequest;
import se.inera.intyg.intygproxyservice.authorization.dto.HandleCertificationPersonResponse;
import se.inera.intyg.intygproxyservice.integration.api.authorization.HandleCertificationPersonIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.HandleCertificationPersonIntegrationService;
import se.inera.intyg.intygproxyservice.logging.LogHashUtility;

@Service
@AllArgsConstructor
@Slf4j
public class HandleCertificationPersonService {

  private final HandleCertificationPersonIntegrationService handleCertificationPersonIntegrationService;
  private final LogHashUtility logHashUtility;

  public HandleCertificationPersonResponse handle(HandleCertificationPersonRequest request) {
    validateRequest(request);

    log.info(
        String.format(
            "Performing operation '%s' with reason '%s' on certification person with personId: '%s'",
            request.getOperation(),
            request.getReason(),
            logHashUtility.hash(request.getPersonId())
        )
    );

    final var response = handleCertificationPersonIntegrationService.get(
        HandleCertificationPersonIntegrationRequest.builder()
            .personId(request.getPersonId())
            .certificationId(request.getCertificationId())
            .operation(request.getOperation())
            .reason(request.getReason())
            .build()
    );

    log.info(
        String.format(
            "Performed operation '%s' with reason '%s' on certification person with personId: '%s'. Result code: '%s'. Result text: '%s'.",
            request.getOperation(),
            request.getReason(),
            logHashUtility.hash(request.getPersonId()),
            response.getResult().getResultCode(),
            response.getResult().getResultText()
        )
    );

    return HandleCertificationPersonResponse
        .builder()
        .result(response.getResult())
        .build();
  }

  private void validateRequest(HandleCertificationPersonRequest request) {
    if (request == null) {
      throw new IllegalArgumentException("Request to handle certification person is null");
    }

    validateString(request.getPersonId(), logHashUtility.hash(request.getPersonId()), "PersonId");
    validateString(request.getOperation(), "Operation");
  }

  private static void validateString(String value, String printValue, String label) {
    if (isStringInvalid(value)) {
      throw new IllegalArgumentException(
          String.format(
              "'%s' is not valid: '%s'",
              label,
              printValue
          )
      );
    }
  }

  private static void validateString(String value, String label) {
    validateString(value, value, label);
  }
}
