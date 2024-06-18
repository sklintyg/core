package se.inera.intyg.certificateservice.application.unit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UnitMessagesController {

  private final GetUnitMessagesService getUnitMessagesService;

  @PostMapping
  public GetUnitMessagesResponse getUnitCertificates(
      @RequestBody GetUnitMessagesRequest request) {
    log.info("Reached endpoint");
    final var response = getUnitMessagesService.get(request);
    log.info("Returning response of size " + response.getQuestions().size());
    return response;
  }
}
