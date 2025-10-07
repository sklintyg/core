package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "dim_certificate")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`key`")
  private Long key;

  @Column(name = "certificate_id", nullable = false, length = 22)
  private String certificateId;

  @Column(name = "certificate_type", nullable = false, length = 24)
  private String certificateType;

  @Column(name = "certificate_type_version", nullable = false, length = 24)
  private String certificateTypeVersion;

  @ManyToOne
  @JoinColumn(name = "care_provider_key", referencedColumnName = "key")
  private CareProviderEntity careProvider;

  @ManyToOne
  @JoinColumn(name = "unit_key", referencedColumnName = "key")
  private UnitEntity unit;
}
