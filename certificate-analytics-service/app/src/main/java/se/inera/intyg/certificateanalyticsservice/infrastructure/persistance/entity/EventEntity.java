package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "event")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`key`")
  private Long key;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "certificate_key", referencedColumnName = "key")
  private CertificateEntity certificate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "unit_key", referencedColumnName = "key")
  private UnitEntity unit;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "provider_key", referencedColumnName = "key")
  private CareProviderEntity careProvider;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_key", referencedColumnName = "key")
  private UserEntity user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "session_key", referencedColumnName = "key")
  private SessionEntity session;

  @Column(name = "timestamp", nullable = false)
  private LocalDateTime timestamp;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "origin_key", referencedColumnName = "key")
  private OriginEntity origin;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "event_type_key", referencedColumnName = "key")
  private EventTypeEntity eventType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_key", referencedColumnName = "key")
  private RoleEntity role;
}
