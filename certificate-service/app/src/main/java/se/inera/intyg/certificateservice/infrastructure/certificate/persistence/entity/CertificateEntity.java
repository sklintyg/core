package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "certificate")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`key`")
  private Long key;
  @Column(name = "certificate_id", unique = true)
  private String certificateId;
  @ManyToOne
  @JoinColumn(name = "certificate_status_key")
  private CertificateStatusEntity status;
  @Column(name = "created")
  private LocalDateTime created;
  @Column(name = "modified")
  private LocalDateTime modified;
  @Column(name = "signed")
  private LocalDateTime signed;
  @Column(name = "ready_for_sign")
  private LocalDateTime readyForSign;
  @Column(name = "sent")
  private LocalDateTime sent;
  @Column(name = "revoked")
  private LocalDateTime revoked;
  @Column(name = "locked")
  private LocalDateTime locked;
  @Column(name = "revision")
  private Long revision;
  @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  private CertificateModelEntity certificateModel;
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinColumn(name = "patient_key", referencedColumnName = "`key`", nullable = false)
  private PatientEntity patient;
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinColumn(name = "created_by_staff_key", referencedColumnName = "`key`", nullable = false)
  private StaffEntity createdBy;
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinColumn(name = "issued_by_staff_key", referencedColumnName = "`key`", nullable = false)
  private StaffEntity issuedBy;
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinColumn(name = "sent_by_staff_key", referencedColumnName = "`key`")
  private StaffEntity sentBy;
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinColumn(name = "ready_for_sign_by_staff_key", referencedColumnName = "`key`")
  private StaffEntity readyForSignBy;
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinColumn(name = "issued_on_unit_key", referencedColumnName = "`key`", nullable = false)
  private UnitEntity issuedOnUnit;
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinColumn(name = "care_provider_unit_key", referencedColumnName = "`key`", nullable = false)
  private UnitEntity careProvider;
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinColumn(name = "care_unit_unit_key", referencedColumnName = "`key`", nullable = false)
  private UnitEntity careUnit;
  @OneToOne(mappedBy = "certificate", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private CertificateDataEntity data;
  @OneToOne(mappedBy = "certificate", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private CertificateXmlEntity xml;
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinColumn(name = "revoked_by_staff_key", referencedColumnName = "`key`")
  private StaffEntity revokedBy;
  @ManyToOne
  @JoinColumn(name = "revoked_reason_key")
  private RevokedReasonEntity revokedReason;
  @Column(name = "revoked_message")
  private String revokedMessage;
  @OneToOne(mappedBy = "certificate", cascade = CascadeType.ALL)
  @PrimaryKeyJoinColumn
  private ExternalReferenceEntity externalReference;
  @Column(name = "forwarded")
  private Boolean forwarded;
  @Column(name = "placeholder")
  private Boolean placeholder;

  public boolean isPlaceHolder() {
    return Boolean.TRUE.equals(placeholder);
  }
}