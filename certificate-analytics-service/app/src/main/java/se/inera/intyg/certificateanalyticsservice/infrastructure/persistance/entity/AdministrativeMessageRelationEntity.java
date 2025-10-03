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
@Table(name = "administrative_message_relation")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdministrativeMessageRelationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "key")
  private Long key;

  @ManyToOne
  @JoinColumn(name = "administrative_message_key", referencedColumnName = "key", nullable = false)
  private AdministrativeMessageEntity administrativeMessage;

  @ManyToOne
  @JoinColumn(name = "relation_type_key", referencedColumnName = "key", nullable = false)
  private AdministrativeMessageRelationTypeEntity relationType;

  @Column(name = "relation_id", nullable = false, unique = true)
  private String relationId;
}
