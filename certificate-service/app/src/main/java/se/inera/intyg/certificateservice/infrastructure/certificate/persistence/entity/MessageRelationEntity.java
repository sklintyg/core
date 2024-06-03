package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import jakarta.persistence.CascadeType;
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
@Table(name = "message_relation")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageRelationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`key`")
  private Long key;

  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinColumn(name = "child_message_key", referencedColumnName = "`key`")
  private MessageEntity childMessage;

  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinColumn(name = "parent_message_key", referencedColumnName = "`key`")
  private MessageEntity parentMessage;

  @ManyToOne
  @JoinColumn(name = "message_relation_type_key")
  private MessageRelationTypeEntity messageRelationType;
}
