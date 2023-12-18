package se.inera.intyg.intygproxyservice.integration.authorization.client.converter;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialInformation;
import se.riv.infrastructure.directory.authorizationmanagement.getcredentialsforpersonincludingprotectedpersonresponder.v2.GetCredentialsForPersonIncludingProtectedPersonResponseType;

@Service
@RequiredArgsConstructor
public class GetCredentialInformationResponseTypeConverter {

  private final CredentialInformationTypeConverter credentialInformationTypeConverter;

  public List<CredentialInformation> convert(
      GetCredentialsForPersonIncludingProtectedPersonResponseType type) {
    if (type == null || type.getCredentialInformation() == null) {
      return Collections.emptyList();
    }

    return type.getCredentialInformation().stream()
        .map(credentialInformationTypeConverter::convert)
        .toList();
  }
}
