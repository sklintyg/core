package se.inera.intyg.certificateanalyticsservice.testability.messages;

import static se.inera.intyg.certificateanalyticsservice.testability.configuration.TestabilityConfiguration.TESTABILITY_PROFILE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.v1.CertificateAnalyticsEventMessageV1;
import se.inera.intyg.certificateanalyticsservice.application.messages.repository.AnalyticMessageRepository;

@Slf4j
@Profile(TESTABILITY_PROFILE)
@RestController
@RequestMapping("/testability")
@RequiredArgsConstructor
public class TestabilityMessageController {

  private final TestabilityAnalyticsMessageService testabilityAnalyticsMessageService;
  private final AnalyticMessageRepository analyticMessageRepository;

  @GetMapping("/messages/v1/{messageId}")
  public CertificateAnalyticsEventMessageV1 getTestabilityMessageV1(
      @PathVariable String messageId) {
    log.info("Testability get message V1 with id {}", messageId);
    final var messages = analyticMessageRepository.findByMessageId(messageId);
    if (messages instanceof CertificateAnalyticsEventMessageV1 messageV1) {
      return messageV1;
    }
    throw new IllegalStateException("Message with id " + messageId + " is not of type V1");
  }

  @GetMapping("/messages/reset")
  public void reset() {
    log.info("Testability reset");
    analyticMessageRepository.clear();
    testabilityAnalyticsMessageService.reset();
  }

  @GetMapping("/messages/fail/temporary/{numberOfTimes}")
  public void failNext(@PathVariable int numberOfTimes) {
    log.info("Testability temporary failure for next {} calls", numberOfTimes);
    testabilityAnalyticsMessageService.toggleTemporaryFailure(numberOfTimes);
  }

  @GetMapping("/messages/fail/permanent/{permanentFailure}")
  public void permanent(@PathVariable boolean permanentFailure) {
    log.info("Testability permanent failure set to {}", permanentFailure);
    testabilityAnalyticsMessageService.togglePermanentFailure(permanentFailure);
  }
}