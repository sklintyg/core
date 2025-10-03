package se.inera.intyg.certificateanalyticsservice.infrastructure.pseudonymization;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PseudonymizationTokenGenerator {

  @Value("${pseudonymization.key}")
  private byte[] key;

  @Value("${pseudonymization.context}")
  private String context;

  private static final String FIELD_MESSAGE_ID = "messageId";
  private static final String FIELD_STAFF_ID = "staffId";
  private static final String FIELD_SESSION_ID = "sessionId";
  private static final String FIELD_CERTIFICATE_ID = "certificateId";
  private static final String FIELD_PATIENT_ID = "patientId";
  private static final String FIELD_ADMINISTRATIVE_MESSAGE_ID = "administrativeMessageId";
  private static final String FIELD_ADMINISTRATIVE_MESSAGE_ANSWER_ID = "administrativeMessageAnswerId";
  private static final String FIELD_ADMINISTRATIVE_MESSAGE_REMINDER_ID = "administrativeMessageReminderId";

  private static final String HMAC_SHA_256 = "HmacSHA256";
  private static final String INPUT_TEMPLATE = "%s|%s|%s";
  private static final int TOKEN_LENGTH = 16;

  public String messageId(String messageId) {
    return messageId == null ? null : token(
        FIELD_MESSAGE_ID,
        normalize(messageId)
    );
  }

  public String staffId(String staffId) {
    return staffId == null ? null : token(
        FIELD_STAFF_ID,
        normalize(staffId)
    );
  }

  public String sessionId(String sessionId) {
    return sessionId == null ? null : token(
        FIELD_SESSION_ID,
        normalize(sessionId)
    );
  }

  public String certificateId(String certificateId) {
    return certificateId == null ? null : token(
        FIELD_CERTIFICATE_ID,
        normalize(certificateId)
    );
  }

  public String parentCertificateId(String certificateId) {
    return certificateId == null ? null : token(
        FIELD_CERTIFICATE_ID,
        normalize(certificateId)
    );
  }

  public String patientId(String patientId) {
    return patientId == null ? null : token(
        FIELD_PATIENT_ID,
        normalize(patientId)
    );
  }

  public String administrativeMessageId(String administrativeMessageId) {
    return administrativeMessageId == null ? null : token(
        FIELD_ADMINISTRATIVE_MESSAGE_ID,
        normalize(administrativeMessageId)
    );
  }

  public String administrativeMessageAnswerId(String administrativeMessageAnswerId) {
    return administrativeMessageAnswerId == null ? null : token(
        FIELD_ADMINISTRATIVE_MESSAGE_ANSWER_ID,
        normalize(administrativeMessageAnswerId)
    );
  }

  public String administrativeMessageReminderId(String administrativeMessageReminderId) {
    return administrativeMessageReminderId == null ? null : token(
        FIELD_ADMINISTRATIVE_MESSAGE_REMINDER_ID,
        normalize(administrativeMessageReminderId)
    );
  }

  private String normalize(String value) {
    return value == null ? null : value.trim().toLowerCase();
  }

  private String token(String field, String normalizedValue) {
    try {
      final var input = INPUT_TEMPLATE.formatted(field, context, normalizedValue);
      final var mac = Mac.getInstance(HMAC_SHA_256);
      mac.init(new SecretKeySpec(key, HMAC_SHA_256));
      final var hash = mac.doFinal(input.getBytes(StandardCharsets.UTF_8));
      final var hash16Bytes = Arrays.copyOf(hash, TOKEN_LENGTH);
      return Base64.getUrlEncoder().withoutPadding().encodeToString(hash16Bytes);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }
}