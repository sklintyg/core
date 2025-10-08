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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "unit")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"key", "version"})
public class UnitEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`key`")
  private int key;
  @Column(name = "hsa_id", unique = true)
  private String hsaId;
  @Column(name = "name")
  private String name;
  @Column(name = "address")
  private String address;
  @Column(name = "zip_code")
  private String zipCode;
  @Column(name = "city")
  private String city;
  @Column(name = "phone_number")
  private String phoneNumber;
  @Column(name = "email")
  private String email;
  @Column(name = "workplace_code")
  private String workplaceCode;
  @ManyToOne
  @JoinColumn(name = "unit_type_key")
  private UnitTypeEntity type;

  @Version
  private Long version;

  public void updateWith(UnitEntity newUnitEntity) {
    this.setName(newUnitEntity.getName());
    this.setAddress(newUnitEntity.getAddress());
    this.setZipCode(newUnitEntity.getZipCode());
    this.setCity(newUnitEntity.getCity());
    this.setPhoneNumber(newUnitEntity.getPhoneNumber());
    this.setEmail(newUnitEntity.getEmail());
    this.setWorkplaceCode(newUnitEntity.getWorkplaceCode());
    this.setType(newUnitEntity.getType());
  }

}
