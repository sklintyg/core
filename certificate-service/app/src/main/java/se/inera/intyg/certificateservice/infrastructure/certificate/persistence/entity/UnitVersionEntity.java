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
@Table(name = "unit_version")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UnitVersionEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`key`")
  private int key;
  @Column(name = "hsa_id", nullable = false)
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
  @Column(name = "valid_from")
  private LocalDateTime validFrom;
  @Column(name = "valid_to", nullable = false)
  private LocalDateTime validTo;
  @ManyToOne
  @JoinColumn(name = "unit_type_key")
  private UnitTypeEntity type;
  @ManyToOne
  @JoinColumn(name = "unit_key", referencedColumnName = "`key`", nullable = false)
  private UnitEntity unit;
}
