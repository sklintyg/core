package se.inera.intyg.intygproxyservice.authorization;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.intygproxyservice.authorization.dto.HandleCertificationPersonRequest;
import se.inera.intyg.intygproxyservice.authorization.dto.HandleCertificationPersonResponse;
import se.inera.intyg.intygproxyservice.authorization.service.HandleCertificationPersonService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/certificationPerson")
public class HandleCertificationPersonController {

  private final HandleCertificationPersonService handleCertificationPersonService;

  @PostMapping
  HandleCertificationPersonResponse getCredentialsForPerson(
      @RequestBody HandleCertificationPersonRequest request) {
    return handleCertificationPersonService.get(request);
  }
}
