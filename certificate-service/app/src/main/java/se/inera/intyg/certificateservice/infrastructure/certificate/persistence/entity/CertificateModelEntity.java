package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "certificate_model",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"type", "version"})}
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificateModelEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "key")
  private int key;
  @Column(name = "type")
  private String type;
  @Column(name = "version")
  private String version;
  @Column(name = "name")
  private String name;
}
