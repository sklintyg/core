package se.inera.intyg.css.testability.service;

import jakarta.mail.Address;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import se.inera.intyg.css.testability.configuration.MailServerConfig;

@Service
public record EmailTestabilityService(MailServerConfig mailServerConfig) {

  public List<Map<String, List<String>>> getAllEmails() {
    final var greenMail = mailServerConfig.getGreenMail();
    return Arrays.stream(greenMail.getReceivedMessages()).map(this::getMailProperties)
        .toList();
  }

  public List<Map<String, List<String>>> getEmailsForAddress(String emailAddress) {
    final var greenMail = mailServerConfig.getGreenMail();
    return Arrays.stream(greenMail.getReceivedMessagesForDomain(emailAddress))
        .map(this::getMailProperties).toList();
  }

  public void deleteEmails() {
    mailServerConfig.restartGreenMail();
  }

  private Map<String, List<String>> getMailProperties(MimeMessage mimeMessage) {
    try {
      final var subject = List.of(mimeMessage.getSubject());
      final var content = List.of(mimeMessage.getContent().toString());
      final var senders = Arrays.stream(mimeMessage.getFrom()).map(Address::toString).toList();
      final var recipents = Arrays.stream(mimeMessage.getAllRecipients()).map(Address::toString)
          .toList();

      return Map.of(
          "subject", subject,
          "senders", senders,
          "recipients", recipents,
          "content", content);

    } catch (MessagingException | IOException e) {
      return Map.of("subject", Collections.emptyList(), "senders", Collections.emptyList(),
          "recipients", Collections.emptyList(), "content", Collections.emptyList());
    }
  }
}
