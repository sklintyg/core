package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity;

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
@Table(name = "fact_event")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`key`")
  private Long key;

  @Column(name = "id", unique = true, nullable = false, length = 22)
  private String messageId;

  @ManyToOne
  @JoinColumn(name = "certificate_key", referencedColumnName = "key")
  private CertificateEntity certificate;

  @ManyToOne
  @JoinColumn(name = "parent_relation_certificate_key", referencedColumnName = "key")
  private CertificateEntity parentRelationCertificate;

  @ManyToOne
  @JoinColumn(name = "parent_relation_type_key", referencedColumnName = "key")
  private RelationTypeEntity parentRelationType;

  @ManyToOne
  @JoinColumn(name = "certificate_unit_key", referencedColumnName = "key")
  private UnitEntity certificateUnit;

  @ManyToOne
  @JoinColumn(name = "certificate_care_provider_key", referencedColumnName = "key")
  private CareProviderEntity certificateCareProvider;

  @ManyToOne
  @JoinColumn(name = "message_key", referencedColumnName = "key")
  private MessageEntity message;

  @ManyToOne
  @JoinColumn(name = "patient_key", referencedColumnName = "key")
  private PatientEntity patient;

  @ManyToOne
  @JoinColumn(name = "unit_key", referencedColumnName = "key")
  private UnitEntity unit;

  @ManyToOne
  @JoinColumn(name = "care_provider_key", referencedColumnName = "key")
  private CareProviderEntity careProvider;

  @ManyToOne
  @JoinColumn(name = "user_key", referencedColumnName = "key")
  private UserEntity user;

  @ManyToOne
  @JoinColumn(name = "session_key", referencedColumnName = "key")
  private SessionEntity session;

  @Column(name = "timestamp", nullable = false)
  private LocalDateTime timestamp;

  @ManyToOne
  @JoinColumn(name = "origin_key", referencedColumnName = "key")
  private OriginEntity origin;

  @ManyToOne
  @JoinColumn(name = "event_type_key", referencedColumnName = "key")
  private EventTypeEntity eventType;

  @ManyToOne
  @JoinColumn(name = "role_key", referencedColumnName = "key")
  private RoleEntity role;

  @ManyToOne
  @JoinColumn(name = "sender_party_key", referencedColumnName = "key")
  private PartyEntity sender;

  @ManyToOne
  @JoinColumn(name = "recipient_party_key", referencedColumnName = "key")
  private PartyEntity recipient;

  @Column(name = "message_complement_question_ids_count")
  private Integer messageComplementQuestionIdsCount;

}
