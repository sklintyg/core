package se.inera.intyg.cts.infrastructure.integration.Intygstjanst;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateExportPageDTO {

  private String careProviderId;
  private int page;
  private int count;
  private long total;
  private long totalRevoked;
  private List<CertificateXmlDTO> certificateXmls;

}
