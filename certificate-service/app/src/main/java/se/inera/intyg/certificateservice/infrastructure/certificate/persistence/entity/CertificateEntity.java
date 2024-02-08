package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
  @Column(name = "key")
  private Long key;
  @Column(name = "certificate_id")
  private String certificateId;
  @Column(name = "created")
  private LocalDateTime created;
  @Column(name = "modified")
  private LocalDateTime modified;
  @Column(name = "version")
  private int version;
  @Column(name = "certificate_model_key")
  private int certificateModelKey;
  @Column(name = "patient_key")
  private int patientKey;
  @Column(name = "created_by_staff_key")
  private int createdBy;
  @Column(name = "issued_by_staff_key")
  private int issuedBy;
  @Column(name = "issued_on_unit_key")
  private int issuedOnUnit;
  @Column(name = "care_provider_unit_key")
  private int careProvider;
  @Column(name = "care_unit_unit_key")
  private int careUnit;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "certificate_data_key", referencedColumnName = "key")
  private CertificateDataEntity data;
}
