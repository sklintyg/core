package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

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
@Table(name = "patient_version")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientVersionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`key`")
  private Integer key;
  @Column(name = "patient_id", nullable = false)
  private String id;
  @Column(name = "protected_person")
  private boolean protectedPerson;
  @Column(name = "deceased")
  private boolean deceased;
  @Column(name = "test_indicated")
  private boolean testIndicated;
  @Column(name = "first_name")
  private String firstName;
  @Column(name = "middle_name")
  private String middleName;
  @Column(name = "last_name")
  private String lastName;
  @Column(name = "valid_from")
  private LocalDateTime validFrom;
  @Column(name = "valid_to", nullable = false)
  private LocalDateTime validTo;
  @ManyToOne
  @JoinColumn(name = "patient_id_type_key")
  private PatientIdTypeEntity type;
  @ManyToOne
  @JoinColumn(name = "patient_key", referencedColumnName = "`key`", nullable = false)
  private PatientEntity patient;
}
