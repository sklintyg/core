package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
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
  @Column(name = "`key`")
  private Long key;

  @ManyToOne
  @JoinColumn(name = "child_certificate_key", referencedColumnName = "`key`")
  private CertificateEntity childCertificate;

  @ManyToOne
  @JoinColumn(name = "parent_certificate_key", referencedColumnName = "`key`")
  private CertificateEntity parentCertificate;

  @Column(name = "created", columnDefinition = "TIMESTAMP")
  private LocalDateTime created;

  @ManyToOne
  @JoinColumn(name = "certificate_relation_type_key")
  private CertificateRelationTypeEntity certificateRelationType;
}
