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
  @Column(name = "certificate_id", unique = true)
  private String certificateId;
  @Column(name = "created")
  private LocalDateTime created;
  @Column(name = "modified")
  private LocalDateTime modified;
  @Column(name = "version")
  private int version;
  @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  private CertificateModelEntity certificateModel;
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  private PatientEntity patient;
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinColumn(name = "created_by_staff_key", referencedColumnName = "key", nullable = false)
  private StaffEntity createdBy;
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinColumn(name = "issued_by_staff_key", referencedColumnName = "key", nullable = false)
  private StaffEntity issuedBy;
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinColumn(name = "issued_on_unit_key", referencedColumnName = "key", nullable = false)
  private UnitEntity issuedOnUnit;
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
  @JoinColumn(name = "care_provider_unit_key", referencedColumnName = "key", nullable = false)
  private UnitEntity careProvider;
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
  @JoinColumn(name = "care_unit_unit_key", referencedColumnName = "key", nullable = false)
  private UnitEntity careUnit;
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "certificate_data_key", referencedColumnName = "key")
  private CertificateDataEntity data;
}
