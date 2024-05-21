package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Table(name = "message_complement")
public class MessageComplementEmbeddable {

  @Column(name = "elementId", table = "message_complement")
  private String elementId;
  @Column(name = "fieldId", table = "message_complement")
  private String fieldId;
  @Column(name = "content", table = "message_complement")
  private String content;
}
