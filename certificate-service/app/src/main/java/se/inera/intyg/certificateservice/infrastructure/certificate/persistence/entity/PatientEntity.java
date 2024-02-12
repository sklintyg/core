package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "patient")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientEntity {

  @NaturalId
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`key`", updatable = false)
  private Integer key;
  @Id
  @Column(name = "patient_id", unique = true, updatable = false)
  private String id;
  @ManyToOne
  @JoinColumn(name = "patient_id_type_key")
  private PatientIdTypeEntity type;
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient")
  private List<CertificateEntity> certificates;

}
