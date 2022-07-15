package se.inera.intyg.cts.infrastructure.persistence.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "termination")
@SecondaryTable(name = "export", pkJoinColumns = @PrimaryKeyJoinColumn(name = "termination_id"))
public class TerminationEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;
  @Column(name = "termination_id")
  @Type(type = "org.hibernate.type.UUIDCharType")
  private UUID terminationId;
  @Column(name = "created")
  private LocalDateTime created;
  @Column(name = "creator_hsa_id")
  private String creatorHSAId;
  @Column(name = "creator_name")
  private String creatorName;
  @Column(name = "hsa_id")
  private String hsaId;
  @Column(name = "organization_number")
  private String organizationNumber;
  @Column(name = "person_id")
  private String personId;
  @Column(name = "phone_number")
  private String phoneNumber;
  @Column(name = "status")
  private String status;

  @Embedded
  private ExportEmbeddable export;
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "erase", joinColumns = @JoinColumn(name = "termination_id"))
  private List<EraseEmbeddable> eraseList;

}
