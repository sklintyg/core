package se.inera.intyg.cts.infrastructure.persistence.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "termination")
public class TerminationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "termination_id")
  @Type(type = "org.hibernate.type.UUIDCharType")
  private UUID terminationId;
  @Column(name = "created")
  private LocalDateTime created;
  @Column(name = "creator_hsa_id")
  private String creatorHSAId;
  @Column(name = "creator_name")
  private String creatorName;
  @Column(name = "hsa_id")
  private String hsaId;
  @Column(name = "organizational_number")
  private String organizationalNumber;
  @Column(name = "person_id")
  private String personId;
  @Column(name = "phone_number")
  private String phoneNumber;
  @Column(name = "status")
  private String status;

  protected TerminationEntity() {
  }

  public TerminationEntity(UUID terminationId, LocalDateTime created, String creatorHSAId,
      String creatorName, String hsaId, String organizationalNumber, String personId,
      String phoneNumber, String status) {
    this.terminationId = terminationId;
    this.created = created;
    this.creatorHSAId = creatorHSAId;
    this.creatorName = creatorName;
    this.hsaId = hsaId;
    this.organizationalNumber = organizationalNumber;
    this.personId = personId;
    this.phoneNumber = phoneNumber;
    this.status = status;
  }

  @Override
  public String toString() {
    return "TerminationEntity{" +
        "id=" + id +
        ", terminationId='" + terminationId + '\'' +
        ", created=" + created +
        ", creatorHSAId='" + creatorHSAId + '\'' +
        ", creatorName='" + creatorName + '\'' +
        ", hsaId='" + hsaId + '\'' +
        ", organizationalNumber='" + organizationalNumber + '\'' +
        ", personId='" + personId + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        ", status='" + status + '\'' +
        '}';
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UUID getTerminationId() {
    return terminationId;
  }

  public void setTerminationId(UUID terminationId) {
    this.terminationId = terminationId;
  }

  public LocalDateTime getCreated() {
    return created;
  }

  public void setCreated(LocalDateTime created) {
    this.created = created;
  }

  public String getCreatorHSAId() {
    return creatorHSAId;
  }

  public void setCreatorHSAId(String creatorHSAId) {
    this.creatorHSAId = creatorHSAId;
  }

  public String getCreatorName() {
    return creatorName;
  }

  public void setCreatorName(String creatorName) {
    this.creatorName = creatorName;
  }

  public String getHsaId() {
    return hsaId;
  }

  public void setHsaId(String hsaId) {
    this.hsaId = hsaId;
  }

  public String getOrganizationalNumber() {
    return organizationalNumber;
  }

  public void setOrganizationalNumber(String organizationalNumber) {
    this.organizationalNumber = organizationalNumber;
  }

  public String getPersonId() {
    return personId;
  }

  public void setPersonId(String organizationalRepresentative) {
    this.personId = organizationalRepresentative;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
