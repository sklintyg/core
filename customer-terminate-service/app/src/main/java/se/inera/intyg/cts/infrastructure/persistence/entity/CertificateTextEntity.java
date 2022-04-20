package se.inera.intyg.cts.infrastructure.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "certificate_text")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateTextEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "certificate_type")
  private String certificateType;
  @Column(name = "certificate_type_version")
  private String certificateTypeVersion;
  @Column(name = "xml")
  private String xml;
  @ManyToOne(fetch = FetchType.LAZY)
  private TerminationEntity termination;

}
