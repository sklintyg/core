package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "message")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`key`")
  private Integer key;
  @Column(name = "message_id", unique = true, updatable = false)
  private String id;
  @Column(name = "reference")
  private String reference;
  @Column(name = "subject")
  private String subject;
  @Column(name = "content")
  private String content;
  @Column(name = "author")
  private String author;
  @Column(name = "created")
  private LocalDateTime created;
  @Column(name = "modified")
  private LocalDateTime modified;
  @Column(name = "sent")
  private LocalDateTime sent;
  @Column(name = "forwarded")
  private boolean forwarded;
  @Column(name = "lastDateToReply")
  private LocalDate lastDateToReply;
  @ManyToOne
  @JoinColumn(name = "message_status_key")
  private MessageStatusEntity status;
  @ManyToOne
  @JoinColumn(name = "message_type_key")
  private MessageTypeEntity messageType;
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "message_contact_info", joinColumns = @JoinColumn(name = "message_key"))
  private List<MessageContactInfoEmbeddable> contactInfo;
  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "message_complement", joinColumns = @JoinColumn(name = "message_key"))
  private List<MessageComplementEmbeddable> complements;
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinColumn(name = "certificate_key", referencedColumnName = "`key`")
  private CertificateEntity certificate;
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
  @JoinColumn(name = "authored_by_staff_key", referencedColumnName = "`key`")
  private StaffEntity authoredByStaff;
}
