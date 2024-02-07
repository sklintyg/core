package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "unit_relation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitRelationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "key")
  private int key;
  @OneToOne(mappedBy = "child_unit_key")
  private UnitEntity parent;
  @OneToOne(mappedBy = "parent_unit_key")
  private UnitEntity child;
}
