package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
  @Column(name = "event_key")
  private Integer eventKey;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "certificate_key")
  private CertificateEntity certificate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "unit_key")
  private UnitEntity unit;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "provider_key")
  private CareProviderEntity careProvider;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_key")
  private UserEntity user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "session_key")
  private SessionEntity session;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "time_key")
  private TimeEntity time;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "origin_key")
  private OriginEntity origin;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "event_type_key")
  private EventTypeEntity eventType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_key")
  private RoleEntity role;
}
