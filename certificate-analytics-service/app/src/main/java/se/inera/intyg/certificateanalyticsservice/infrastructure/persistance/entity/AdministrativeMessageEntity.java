package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "administrative_message")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdministrativeMessageEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`key`")
  private Long key;

  @Column(name = "administrative_message_id", length = 22, nullable = false)
  private String administrativeMessageId;

  @Column(name = "answer_id", length = 22)
  private String answerId;

  @Column(name = "reminder_id", length = 22)
  private String reminderId;

  @ManyToOne
  @JoinColumn(name = "administrative_message_type_key", referencedColumnName = "key", nullable = false)
  private AdministrativeMessageTypeEntity messageType;

  @Column(name = "sent")
  private LocalDateTime sent;

  @Column(name = "last_date_to_answer")
  private LocalDate lastDateToAnswer;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(
      name = "administrative_message_question",
      joinColumns = @JoinColumn(name = "administrative_message_key")
  )
  @Column(name = "question_id", length = 5)
  private List<String> questionId;

  @ManyToOne
  @JoinColumn(name = "administrative_message_sender_key", referencedColumnName = "key", nullable = false)
  private AdministrativeMessageSenderEntity sender;

  @ManyToOne
  @JoinColumn(name = "administrative_message_recipient_key", referencedColumnName = "key", nullable = false)
  private AdministrativeMessageRecipientEntity recipient;
}
