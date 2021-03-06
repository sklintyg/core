package se.inera.intyg.cts.infrastructure.persistence.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Table(name = "export")
public class ExportEmbeddable {

  @Column(name = "total", table = "export")
  private int total;
  @Column(name = "revoked", table = "export")
  private int revoked;
  @Column(name = "password", table = "export")
  private String password;
  @Column(name = "receipt_time", table = "export")
  private LocalDateTime receiptTime;

}
