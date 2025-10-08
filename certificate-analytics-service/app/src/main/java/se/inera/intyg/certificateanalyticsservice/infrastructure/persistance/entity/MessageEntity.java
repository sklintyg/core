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
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "dim_message")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`key`")
  private Long key;

  @Column(name = "message_id", length = 22, nullable = false, unique = true)
  private String messageId;

  @Column(name = "message_answer_id", length = 22)
  private String messageAnswerId;

  @Column(name = "message_reminder_id", length = 22)
  private String messageReminderId;

  @Column(name = "message_type", length = 24, nullable = false)
  private String messageType;

  @Column(name = "sent", nullable = false)
  private LocalDateTime sent;

  @Column(name = "last_date_to_answer")
  private LocalDate lastDateToAnswer;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(
      name = "dim_message_question_id",
      joinColumns = @JoinColumn(name = "message_key")
  )
  @Column(name = "question_id")
  private List<String> questionIds;

}
