package se.inera.intyg.cts.infrastructure.integration.Intygstjanst;

import java.util.stream.Collectors;
import org.springframework.web.reactive.function.client.WebClient;
import se.inera.intyg.cts.domain.model.Certificate;
import se.inera.intyg.cts.domain.model.CertificateBatch;
import se.inera.intyg.cts.domain.model.CertificateId;
import se.inera.intyg.cts.domain.model.CertificateSummary;
import se.inera.intyg.cts.domain.model.CertificateXML;
import se.inera.intyg.cts.infrastructure.integration.GetCertificateBatch;

public class GetCertificateBatchFromIntygstjanst implements GetCertificateBatch {

  @Override
  public CertificateBatch get(String careProvider, int limit, int offset) {
    System.out.println(
        String.format("limit = %s offset = %s page = %s", limit, offset, offset / limit));

    final var webClient = WebClient.create("http://localhost:18000");
//    final var webClient = WebClient.create("http://it.localtest.me:8180/inera-certificate");
    final var certificateExportPageDTOMono = webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/api-intygstjanst/v1/certificates/{careProvider}")
//            .path("/internalapi/v1/certificates/{careProvider}")
            .queryParam("size", limit)
            .queryParam("page", (int) offset / limit)
            .build(careProvider))
        .retrieve()
        .bodyToMono(CertificateExportPageDTO.class)
        .share()
        .block();

    return new CertificateBatch(
        new CertificateSummary(
            (int) certificateExportPageDTOMono.getTotal(),
            (int) certificateExportPageDTOMono.getTotalRevoked()
        ),
        certificateExportPageDTOMono.getCertificateXmls().stream()
            .map(certificateXmlDTO -> new Certificate(
                new CertificateId(certificateXmlDTO.getId()),
                certificateXmlDTO.isRevoked(),
                new CertificateXML(certificateXmlDTO.getXml())
            ))
            .collect(Collectors.toList())
    );
  }
}
