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

package se.inera.intyg.intygproxyservice.authorization;

import static se.inera.intyg.intygproxyservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.intygproxyservice.authorization.dto.CredentialInformationRequest;
import se.inera.intyg.intygproxyservice.authorization.dto.CredentialInformationResponse;
import se.inera.intyg.intygproxyservice.authorization.service.CredentialInformationService;
import se.inera.intyg.intygproxyservice.logging.PerformanceLogging;

@RestController()
@RequestMapping("/api/v1/credentialinformation")
@AllArgsConstructor
public class CredentialInformationController {

  private final CredentialInformationService credentialInformationService;

  @PostMapping
  @PerformanceLogging(eventAction = "retrieve-credential-information", eventType = EVENT_TYPE_ACCESSED)
  CredentialInformationResponse getCredentialInformation(
      @RequestBody CredentialInformationRequest request) {
    return credentialInformationService.get(request);
  }
}
