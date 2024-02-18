package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "patient")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PatientEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`key`")
  private Integer key;
  @Column(name = "patient_id", unique = true, updatable = false)
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
  @ManyToOne
  @JoinColumn(name = "patient_id_type_key")
  private PatientIdTypeEntity type;

}
