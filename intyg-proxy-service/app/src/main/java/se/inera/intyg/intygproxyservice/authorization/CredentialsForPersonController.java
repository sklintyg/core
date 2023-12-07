package se.inera.intyg.intygproxyservice.authorization;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.intygproxyservice.authorization.dto.CredentialsForPersonRequest;
import se.inera.intyg.intygproxyservice.authorization.dto.CredentialsForPersonResponse;
import se.inera.intyg.intygproxyservice.authorization.service.CredentialsForPersonService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/credentialsForPerson")
public class CredentialsForPersonController {

  private final CredentialsForPersonService credentialsForPersonService;

  @PostMapping
  CredentialsForPersonResponse getCredentialsForPerson(
      @RequestBody CredentialsForPersonRequest request) {
    return credentialsForPersonService.get(request);
  }
}
