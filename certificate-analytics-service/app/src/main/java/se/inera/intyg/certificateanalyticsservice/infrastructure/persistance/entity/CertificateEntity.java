package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

  @Column(name = "certificate_id", nullable = false, length = 36)
  private String certificateId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "certificate_type_key", nullable = false, referencedColumnName = "key")
  private CertificateTypeEntity certificateType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "care_provider_key", referencedColumnName = "key")
  private CareProviderEntity careProvider;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "unit_key", referencedColumnName = "key")
  private UnitEntity unit;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "patient_key", referencedColumnName = "key")
  private PatientEntity patient;
}
