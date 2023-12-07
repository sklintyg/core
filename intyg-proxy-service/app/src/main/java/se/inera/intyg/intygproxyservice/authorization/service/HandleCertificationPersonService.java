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
import se.inera.intyg.intygproxyservice.authorization.dto.HandleCertificationPersonRequest;
import se.inera.intyg.intygproxyservice.authorization.dto.HandleCertificationPersonResponse;
import se.inera.intyg.intygproxyservice.common.HashUtility;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetHandleCertificationPersonIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetHandleCertificationPersonIntegrationService;

@Service
@AllArgsConstructor
@Slf4j
public class HandleCertificationPersonService {

  private final GetHandleCertificationPersonIntegrationService getHandleCertificationPersonIntegrationService;

  public HandleCertificationPersonResponse get(HandleCertificationPersonRequest request) {
    validateRequest(request);

    log.info(
        String.format(
            "Performing operation '%s' with reason '%s' on certification person with personId: '%s'",
            request.getOperation(),
            request.getReason(),
            HashUtility.hash(request.getPersonId())
        )
    );

    final var response = getHandleCertificationPersonIntegrationService.get(
        GetHandleCertificationPersonIntegrationRequest.builder()
            .personId(request.getPersonId())
            .certificationId(request.getCertificationId())
            .operation(request.getOperation())
            .reason(request.getReason())
            .build()
    );

    log.info(
        String.format(
            "Performed operation '%s' with reason '%s' on certification person with personId: '%s'",
            request.getOperation(),
            request.getReason(),
            HashUtility.hash(request.getPersonId())
        )
    );

    return HandleCertificationPersonResponse
        .builder()
        .result(response.getResult())
        .build();
  }

  private static void validateRequest(HandleCertificationPersonRequest request) {
    if (request == null) {
      throw new IllegalArgumentException("Request to handle certification person is null");
    }

    validateString(request.getPersonId(), HashUtility.hash(request.getPersonId()), "PersonId");
    validateString(request.getCertificationId(), "CertificationId");
    validateString(request.getOperation(), "Operation");
    validateString(request.getReason(), "Reason");
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
