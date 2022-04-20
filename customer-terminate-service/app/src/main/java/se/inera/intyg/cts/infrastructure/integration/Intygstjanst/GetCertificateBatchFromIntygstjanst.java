package se.inera.intyg.cts.infrastructure.integration.Intygstjanst;

import java.util.stream.Collectors;
import org.springframework.web.reactive.function.client.WebClient;
import se.inera.intyg.cts.domain.model.Certificate;
import se.inera.intyg.cts.domain.model.CertificateBatch;
import se.inera.intyg.cts.domain.model.CertificateId;
import se.inera.intyg.cts.domain.model.CertificateSummary;
import se.inera.intyg.cts.domain.model.CertificateXML;
import se.inera.intyg.cts.infrastructure.integration.GetCertificateBatch;
import se.inera.intyg.cts.infrastructure.integration.Intygstjanst.dto.CertificateExportPageDTO;

public class GetCertificateBatchFromIntygstjanst implements GetCertificateBatch {

  private final WebClient webClient;
  private final String baseUrl;
  private final String certificatesEndpoint;

  public GetCertificateBatchFromIntygstjanst(WebClient webClient, String baseUrl,
      String certificatesEndpoint) {
    this.webClient = webClient;
    this.baseUrl = baseUrl;
    this.certificatesEndpoint = certificatesEndpoint;
  }

  @Override
  public CertificateBatch get(String careProvider, int limit, int offset) {
    final var certificateExportPageDTOMono = webClient.get()
        .uri(uriBuilder -> uriBuilder
            .scheme("http")
            .host(baseUrl)
            .port(8180)
            .path(certificatesEndpoint + "/{careProvider}")
            .queryParam("size", limit)
            .queryParam("page", offset / limit)
            .build(careProvider))
        .retrieve()
        .bodyToMono(CertificateExportPageDTO.class)
        .share()
        .block();

    return new CertificateBatch(
        new CertificateSummary(
            (int) certificateExportPageDTOMono.total(),
            (int) certificateExportPageDTOMono.totalRevoked()
        ),
        certificateExportPageDTOMono.certificateXmls().stream()
            .map(certificateXmlDTO -> new Certificate(
                new CertificateId(certificateXmlDTO.id()),
                certificateXmlDTO.revoked(),
                new CertificateXML(certificateXmlDTO.xml())
            ))
            .collect(Collectors.toList())
    );
  }
}
