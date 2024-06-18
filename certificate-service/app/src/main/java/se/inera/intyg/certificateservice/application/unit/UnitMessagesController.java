package se.inera.intyg.certificateservice.application.unit;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitMessagesRequest;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitMessagesResponse;
import se.inera.intyg.certificateservice.application.unit.service.GetUnitMessagesService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/unit/messages")
public class UnitMessagesController {

  private final GetUnitMessagesService getUnitMessagesService;

  @PostMapping
  public GetUnitMessagesResponse getUnitCertificates(
      @RequestBody GetUnitMessagesRequest request) {
    return getUnitMessagesService.get(request);
  }
}
