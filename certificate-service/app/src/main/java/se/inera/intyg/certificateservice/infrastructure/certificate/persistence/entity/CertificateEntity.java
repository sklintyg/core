package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
  @Column(name = "certificate_id")
  private String certificateId;
  @Column(name = "created")
  private LocalDateTime created;
  @Column(name = "modified")
  private LocalDateTime modified;
  @Column(name = "version")
  private int version;
  @OneToOne
  private CertificateModelEntity certificateModel;
  @ManyToOne
  private PatientEntity patient;
  @ManyToOne
  @JoinColumn(name = "created_by_staff_key", referencedColumnName = "key")
  private StaffEntity createdBy;
  @ManyToOne
  @JoinColumn(name = "issued_by_staff_key", referencedColumnName = "key")
  private StaffEntity issuedBy;
  @ManyToOne
  @JoinColumn(name = "issued_on_unit_key", referencedColumnName = "key")
  private UnitEntity issuedOnUnit;
  @ManyToOne
  @JoinColumn(name = "care_provider_unit_key", referencedColumnName = "key")
  private UnitEntity careProvider;
  @ManyToOne
  @JoinColumn(name = "care_unit_unit_key", referencedColumnName = "key")
  private UnitEntity careUnit;
}
