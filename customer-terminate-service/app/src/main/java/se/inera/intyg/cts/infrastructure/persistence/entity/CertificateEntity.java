package se.inera.intyg.cts.infrastructure.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "certificate")
public class CertificateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "certificate_id")
  private String certificateId;
  @Column(name = "revoked")
  private boolean revoked;
  @Column(name = "xml")
  private String xml;
  @ManyToOne(fetch = FetchType.LAZY)
  private TerminationEntity termination;

  public CertificateEntity() {
  }

  public CertificateEntity(String certificateId, String xml, boolean revoked,
      TerminationEntity termination) {
    this.id = id;
    this.certificateId = certificateId;
    this.xml = xml;
    this.revoked = revoked;
    this.termination = termination;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCertificateId() {
    return certificateId;
  }

  public void setCertificateId(String certificateId) {
    this.certificateId = certificateId;
  }

  public String getXml() {
    return xml;
  }

  public void setXml(String xml) {
    this.xml = xml;
  }

  public boolean isRevoked() {
    return revoked;
  }

  public void setRevoked(boolean revoked) {
    this.revoked = revoked;
  }

  public TerminationEntity getTermination() {
    return termination;
  }

  public void setTermination(
      TerminationEntity termination) {
    this.termination = termination;
  }

  @Override
  public String toString() {
    return "CertificateEntity{" +
        "id=" + id +
        ", certificateId='" + certificateId + '\'' +
        ", xml='" + xml + '\'' +
        ", revoked='" + revoked + '\'' +
        ", termination=" + termination +
        '}';
  }
}
