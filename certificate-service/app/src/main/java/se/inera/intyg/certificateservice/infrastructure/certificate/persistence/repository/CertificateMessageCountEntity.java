package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

public interface CertificateMessageCountEntity {

  String getCertificateId();

  Integer getComplementsCount();

  Integer getOthersCount();
}
