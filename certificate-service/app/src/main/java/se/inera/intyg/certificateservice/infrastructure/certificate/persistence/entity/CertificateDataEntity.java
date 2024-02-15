package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.nio.charset.StandardCharsets;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "certificate_data")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificateDataEntity {

  @Id
  @Column(name = "`key`")
  private Long key;

  @OneToOne
  @MapsId
  @JoinColumn(name = "`key`")
  private CertificateEntity certificate;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "data")
  private byte[] data;

  public CertificateDataEntity(String data) {
    this.data = toBytes(data);
  }

  public void setData(String document) {
    this.data = toBytes(document);
  }

  public String getData() {
    return fromBytes(this.data);
  }

  private byte[] toBytes(String data) {
    if (data == null) {
      return new byte[0];
    }
    return data.getBytes(StandardCharsets.UTF_8);
  }

  private String fromBytes(byte[] bytes) {
    return new String(bytes, StandardCharsets.UTF_8);
  }

}
