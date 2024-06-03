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
@Table(name = "staff_healthcare_professional_licence")
public class HealthcareProfessionalLicenceEmbeddable {

  @Column(name = "healthcare_professional_licence", table = "staff_healthcare_professional_licence")
  private String healthcareProfessionalLicence;
}
