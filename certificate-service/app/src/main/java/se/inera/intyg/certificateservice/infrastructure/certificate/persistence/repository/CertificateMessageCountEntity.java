package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

public interface CertificateMessageCountEntity {

  String getCertificateId();

  int getComplementsCount();

  int getOthersCount();
}
