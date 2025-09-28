package se.inera.intyg.certificateanalyticsservice.testability.messages;

import static se.inera.intyg.certificateanalyticsservice.testability.common.TestabilityConstants.TESTABILITY_PROFILE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventMessageV1;
import se.inera.intyg.certificateanalyticsservice.application.messages.repository.AnalyticMessageRepository;


@Profile(TESTABILITY_PROFILE)
@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/testability")
public class TestabilityMessageController {

  private final AnalyticMessageRepository analyticMessageRepository;

  @GetMapping("/messages/v1/{messageId}")
  public CertificateAnalyticsEventMessageV1 getTestabilityMessageV1(
      @PathVariable String messageId) {
    final var messages = analyticMessageRepository.findByMessageId(messageId);
    if (messages instanceof CertificateAnalyticsEventMessageV1 messageV1) {
      return messageV1;
    }
    throw new IllegalStateException("Message with id " + messageId + " is not of type V1");
  }

  @GetMapping("/messages/clear")
  public void clearMessages() {
    analyticMessageRepository.clear();
    log.info("Clearing testability messages");
  }
}