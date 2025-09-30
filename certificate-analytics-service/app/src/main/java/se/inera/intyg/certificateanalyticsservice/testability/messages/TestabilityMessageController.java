package se.inera.intyg.certificateanalyticsservice.testability.messages;

import static se.inera.intyg.certificateanalyticsservice.testability.configuration.TestabilityConfiguration.TESTABILITY_PROFILE;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateanalyticsservice.application.messages.model.PseudonymizedAnalyticsMessage;
import se.inera.intyg.certificateanalyticsservice.application.messages.repository.CertificateAnalyticsMessageRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.pseudonymization.PseudonymizationTokenGenerator;

@Slf4j
@Profile(TESTABILITY_PROFILE)
@RestController
@RequestMapping("/testability")
@RequiredArgsConstructor
public class TestabilityMessageController {

  private final TestabilityAnalyticsMessageService testabilityAnalyticsMessageService;
  private final CertificateAnalyticsMessageRepository certificateAnalyticsMessageRepository;
  private final PseudonymizationTokenGenerator pseudonymizationTokenGenerator;

  @GetMapping("/messages/v1/{messageId}")
  public PseudonymizedAnalyticsMessage getMessage(
      @PathVariable String messageId) {
    final var pseudonymizedMessageId = pseudonymizationTokenGenerator.messageId(messageId);
    log.info("Testability get message with id '{}' that is pseudonymized to '{}'",
        messageId, pseudonymizedMessageId
    );
    return certificateAnalyticsMessageRepository.findByMessageId(pseudonymizedMessageId);
  }

  @GetMapping("/messages/reset")
  public void reset() {
    log.info("Testability reset");
    certificateAnalyticsMessageRepository.clear();
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