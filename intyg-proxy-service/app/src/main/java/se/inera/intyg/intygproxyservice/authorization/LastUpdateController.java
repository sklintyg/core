package se.inera.intyg.intygproxyservice.authorization;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.intygproxyservice.authorization.dto.GetLastUpdateResponse;
import se.inera.intyg.intygproxyservice.authorization.service.GetLastUpdateService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lastUpdate")
public class LastUpdateController {

  private final GetLastUpdateService getLastUpdateService;

  @GetMapping
  GetLastUpdateResponse getLastUpdate() {
    return getLastUpdateService.get();
  }
}
