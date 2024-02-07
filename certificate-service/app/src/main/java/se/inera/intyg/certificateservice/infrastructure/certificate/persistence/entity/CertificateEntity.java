package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "certificate")
@Data
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
  @OneToOne
  private PatientEntity patient;
  @OneToOne(mappedBy = "created_by_staff_key")
  private StaffEntity createdBy;
  @OneToOne(mappedBy = "issued_by_staff_key")
  private StaffEntity issuedBy;
  @OneToOne(mappedBy = "issued_on_unit_key")
  private UnitEntity issuedOnUnit;
  @OneToOne(mappedBy = "care_provider_unit_key")
  private UnitEntity careProvider;
  @OneToOne(mappedBy = "care_unit_unit_key")
  private UnitEntity careUnit;
}
