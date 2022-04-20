package se.inera.intyg.cts.infrastructure.integration.Intygstjanst;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;
import se.inera.intyg.cts.domain.model.CertificateText;
import se.inera.intyg.cts.domain.model.CertificateType;
import se.inera.intyg.cts.domain.model.CertificateTypeVersion;
import se.inera.intyg.cts.domain.model.CertificateXML;
import se.inera.intyg.cts.infrastructure.integration.GetCertificateTexts;
import se.inera.intyg.cts.infrastructure.integration.Intygstjanst.dto.CertificateTextDTO;

public class GetCertificateTextsFromIntygstjanst implements GetCertificateTexts {

  private final WebClient webClient;
  private final String baseUrl;
  private final String certificateTextsEndpoint;

  public GetCertificateTextsFromIntygstjanst(WebClient webClient, String baseUrl,
      String certificateTextsEndpoint) {
    this.webClient = webClient;
    this.baseUrl = baseUrl;
    this.certificateTextsEndpoint = certificateTextsEndpoint;
  }

  @Override
  public List<CertificateText> get() {
    final List<CertificateTextDTO> certificateExportPageDTOMono = webClient.get()
        .uri(uriBuilder -> uriBuilder
            .scheme("http")
            .host(baseUrl)
            .port(8180)
            .path(certificateTextsEndpoint)
            .build())
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<List<CertificateTextDTO>>() {
        })
        .share()
        .block();

    return certificateExportPageDTOMono.stream()
        .map(certificateTextDTO -> new CertificateText(
                new CertificateType(certificateTextDTO.type()),
                new CertificateTypeVersion(certificateTextDTO.version()),
                new CertificateXML(certificateTextDTO.xml())
            )
        )
        .collect(Collectors.toList());
  }
}
