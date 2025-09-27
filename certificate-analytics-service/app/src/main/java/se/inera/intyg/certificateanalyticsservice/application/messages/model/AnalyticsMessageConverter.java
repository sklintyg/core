package se.inera.intyg.certificateanalyticsservice.application.messages.model;

public interface AnalyticsMessageConverter {

  boolean canConvert(String type, String schemaVersion);

  CertificateAnalyticsMessage convert(String message);
}
