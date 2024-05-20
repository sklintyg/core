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
@Table(name = "message_contact_info")
public class MessageContactInfoEmbeddable {

  @Column(name = "info", table = "message_contact_info")
  private String info;
}
