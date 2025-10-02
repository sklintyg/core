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
@Table(name = "certificate_relation")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificateRelationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "key")
  private Long key;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "parent_certificate_key", referencedColumnName = "key", nullable = false)
  private CertificateEntity parentCertificate;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "child_certificate_key", referencedColumnName = "key", nullable = false)
  private CertificateEntity childCertificate;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "certificate_relation_type_key", referencedColumnName = "key", nullable = false)
  private CertificateRelationTypeEntity relationType;
}