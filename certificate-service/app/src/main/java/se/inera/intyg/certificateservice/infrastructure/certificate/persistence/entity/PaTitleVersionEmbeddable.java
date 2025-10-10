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
@Table(name = "staff_pa_title_version")
public class PaTitleVersionEmbeddable {

  @Column(name = "code", table = "staff_pa_title_version")
  private String code;
  @Column(name = "description", table = "staff_pa_title_version")
  private String description;
}
