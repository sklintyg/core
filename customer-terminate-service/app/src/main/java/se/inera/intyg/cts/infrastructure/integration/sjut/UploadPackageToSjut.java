package se.inera.intyg.cts.infrastructure.integration.sjut;

import java.io.File;
import java.net.URI;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.service.UploadPackage;
import se.inera.intyg.cts.infrastructure.integration.sjut.dto.PackageMetadata;

@Service
public class UploadPackageToSjut implements UploadPackage {

  private final WebClient webClient;
  private final String scheme;
  private final String baseUrl;
  private final String port;
  private final String certificatesEndpoint;

  public UploadPackageToSjut(WebClient webClient,
      @Value("${integration.sjut.scheme}") String scheme,
      @Value("${integration.sjut.baseurl}") String baseUrl,
      @Value("${integration.sjut.port}") String port,
      @Value("${integration.sjut.upload.endpoint}") String certificatesEndpoint) {
    this.webClient = webClient;
    this.scheme = scheme;
    this.baseUrl = baseUrl;
    this.port = port;
    this.certificatesEndpoint = certificatesEndpoint;
  }

  @Override
  public void uploadPackage(Termination termination, File packageToUpload) {
    final var resource = getResource(packageToUpload);
    final var packageMetadata = getPackageMetadata(termination);

    final var multipartBodyBuilder = new MultipartBodyBuilder();
    multipartBodyBuilder.part("file", resource);
    multipartBodyBuilder.part("metadata", packageMetadata);

    webClient.post()
        .uri(this::getUri)
        .body(BodyInserters.fromMultipartData(multipartBodyBuilder.build()))
        .exchangeToMono(clientResponse ->
        {
          System.out.println(clientResponse);
          if (clientResponse.statusCode() == HttpStatus.OK) {
            return clientResponse.bodyToMono(String.class);
          } else {
            throw new ServiceException(String.format("Could not upload file for termination '%s'",
                termination.terminationId().id()));
          }
        })
        .share()
        .block();
  }

  private Resource getResource(File packageToUpload) {
    return new DefaultResourceLoader().getResource("file:" + packageToUpload.getAbsolutePath());
  }

  private PackageMetadata getPackageMetadata(Termination termination) {
    return new PackageMetadata(
        termination.careProvider().hsaId().id(),
        termination.careProvider().organizationNumber().number(),
        termination.export().organizationRepresentative().personId().id(),
        "intyg",
        "http://localhost:18010/api/v1/receipt/" + termination.terminationId().id()
    );
  }

  private URI getUri(UriBuilder uriBuilder) {
    uriBuilder = uriBuilder
        .scheme(scheme)
        .host(baseUrl)
        .path(certificatesEndpoint);

    if (!port.isBlank()) {
      uriBuilder.port(port);
    }

    return uriBuilder.build();
  }
}
