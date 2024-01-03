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

package se.inera.intyg.intygproxyservice.integration.authorization.client;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialInformationIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialsForPersonIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.HandleCertificationPersonIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialInformation;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialsForPerson;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.Result;
import se.inera.intyg.intygproxyservice.integration.authorization.client.converter.GetCredentialInformationResponseTypeConverter;
import se.inera.intyg.intygproxyservice.integration.authorization.client.converter.GetCredentialsForPersonResponseTypeConverter;
import se.inera.intyg.intygproxyservice.integration.authorization.client.converter.GetLastUpdateResponseTypeConverter;
import se.inera.intyg.intygproxyservice.integration.authorization.client.converter.HandleCertificationPersonResponseTypeConverter;
import se.riv.infrastructure.directory.authorizationmanagement.getcredentialsforpersonincludingprotectedperson.v2.rivtabp21.GetCredentialsForPersonIncludingProtectedPersonResponderInterface;
import se.riv.infrastructure.directory.authorizationmanagement.getcredentialsforpersonincludingprotectedpersonresponder.v2.GetCredentialsForPersonIncludingProtectedPersonType;
import se.riv.infrastructure.directory.authorizationmanagement.gethospcredentialsforperson.v1.rivtabp21.GetHospCredentialsForPersonResponderInterface;
import se.riv.infrastructure.directory.authorizationmanagement.gethospcredentialsforpersonresponder.v1.GetHospCredentialsForPersonType;
import se.riv.infrastructure.directory.authorizationmanagement.gethosplastupdate.v1.rivtabp21.GetHospLastUpdateResponderInterface;
import se.riv.infrastructure.directory.authorizationmanagement.gethosplastupdateresponder.v1.GetHospLastUpdateType;
import se.riv.infrastructure.directory.authorizationmanagement.handlehospcertificationperson.v1.rivtabp21.HandleHospCertificationPersonResponderInterface;
import se.riv.infrastructure.directory.authorizationmanagement.handlehospcertificationpersonresponder.v1.HandleHospCertificationPersonType;
import se.riv.infrastructure.directory.authorizationmanagement.handlehospcertificationpersonresponder.v1.OperationEnum;

@Service
@Slf4j
@RequiredArgsConstructor
public class HsaAuthorizationClient {

  private static final String PROFILE_EXTENDED_1 = "extended1";

  private final GetCredentialsForPersonIncludingProtectedPersonResponderInterface getCredentialsForPersonIncludingProtectedPersonResponderInterface;
  private final GetHospLastUpdateResponderInterface getHospLastUpdateResponderInterface;
  private final GetHospCredentialsForPersonResponderInterface getHospCredentialsForPersonResponderInterface;
  private final HandleHospCertificationPersonResponderInterface handleHospCertificationPersonResponderInterface;

  private final GetCredentialInformationResponseTypeConverter getCredentialInformationResponseTypeConverter;
  private final GetLastUpdateResponseTypeConverter getLastUpdateResponseTypeConverter;
  private final GetCredentialsForPersonResponseTypeConverter getCredentialsForPersonResponseTypeConverter;
  private final HandleCertificationPersonResponseTypeConverter handleCertificationPersonResponseTypeConverter;

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

  public LocalDateTime getLastUpdate() {
    final var response = getHospLastUpdateResponderInterface.getHospLastUpdate(
        logicalAddress,
        new GetHospLastUpdateType()
    );

    return getLastUpdateResponseTypeConverter.convert(response);
  }

  public CredentialsForPerson getCredentialsForPerson(
      GetCredentialsForPersonIntegrationRequest request) {
    final var parameters = getHospCredentialsForPersonType(request.getPersonId());

    final var type = getHospCredentialsForPersonResponderInterface.getHospCredentialsForPerson(
        logicalAddress,
        parameters
    );

    return getCredentialsForPersonResponseTypeConverter.convert(type);
  }

  public Result handleCertificationPerson(HandleCertificationPersonIntegrationRequest request) {
    final var parameters = getHospHandleCertificationPersonType(request);

    final var type = handleHospCertificationPersonResponderInterface.handleHospCertificationPerson(
        logicalAddress,
        parameters
    );

    return handleCertificationPersonResponseTypeConverter.convert(type);
  }

  private static HandleHospCertificationPersonType getHospHandleCertificationPersonType(
      HandleCertificationPersonIntegrationRequest request) {
    final var parameters = new HandleHospCertificationPersonType();

    parameters.setCertificationId(request.getCertificationId());
    parameters.setOperation(OperationEnum.fromValue(request.getOperation()));
    parameters.setPersonalIdentityNumber(request.getPersonId());
    parameters.setReason(request.getReason());

    return parameters;
  }

  private static GetHospCredentialsForPersonType getHospCredentialsForPersonType(String personId) {
    final var parameters = new GetHospCredentialsForPersonType();
    parameters.setPersonalIdentityNumber(personId);

    return parameters;
  }

  private static GetCredentialsForPersonIncludingProtectedPersonType getCredentialInformationParameters(
      String hsaId) {
    final var parameters = new GetCredentialsForPersonIncludingProtectedPersonType();
    parameters.setPersonHsaId(hsaId);
    parameters.setIncludeFeignedObject(false);
    parameters.setProfile(PROFILE_EXTENDED_1);
    return parameters;
  }
}
