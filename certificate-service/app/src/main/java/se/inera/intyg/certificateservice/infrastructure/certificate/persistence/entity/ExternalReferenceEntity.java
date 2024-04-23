package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "external_reference")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExternalReferenceEntity {

  @Id
  @Column(name = "`key`")
  private Long key;

  @OneToOne
  @MapsId
  @JoinColumn(name = "`key`")
  private CertificateEntity certificate;

  @Column(name = "reference")
  private String reference;
}
