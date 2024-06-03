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
@Table(name = "staff_speciality")
public class SpecialityEmbeddable {

  @Column(name = "speciality", table = "staff_speciality")
  private String speciality;
}
