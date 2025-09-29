package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "time")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`key`")
  private Long key;

  @Column(name = "date", nullable = false)
  private LocalDateTime date;
  @Column(name = "day", nullable = false)
  private Integer day;
  @Column(name = "month", nullable = false)
  private Integer month;
  @Column(name = "year", nullable = false)
  private Integer year;
  @Column(name = "hour", nullable = false)
  private Integer hour;
  @Column(name = "minute", nullable = false)
  private Integer minute;
  @Column(name = "second", nullable = false)
  private Integer second;
}
