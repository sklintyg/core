package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.util.Objects;
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

  @Version
  private Long version;

  public void updateWith(PatientEntity source) {
    if (source == null) {
      throw new IllegalArgumentException("Cannot update PatientEntity with null");
    }

    this.firstName = source.getFirstName();
    this.middleName = source.getMiddleName();
    this.lastName = source.getLastName();
    this.protectedPerson = source.isProtectedPerson();
    this.deceased = source.isDeceased();
    this.testIndicated = source.isTestIndicated();
    this.type = source.getType();
  }

  public boolean hasDiff(PatientEntity other) {
    if (other == null) {
      throw new IllegalArgumentException("Cannot compare patientEntity with null");
    }

    if (!Objects.equals(this.id, other.getId())) {
      throw new IllegalArgumentException("Cannot compare patientEntity with different IDs");
    }

    return !(this.protectedPerson == other.isProtectedPerson()
        && this.deceased == other.isDeceased()
        && this.testIndicated == other.isTestIndicated()
        && Objects.equals(this.firstName, other.getFirstName())
        && Objects.equals(this.middleName, other.getMiddleName())
        && Objects.equals(this.lastName, other.getLastName())
        && Objects.equals(this.type, other.getType()));
  }
}
