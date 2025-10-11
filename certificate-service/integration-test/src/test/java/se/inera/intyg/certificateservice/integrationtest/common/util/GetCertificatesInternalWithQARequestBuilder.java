package se.inera.intyg.certificateservice.integrationtest.common.util;

import java.util.Collections;
import java.util.List;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificatesWithQAInternalRequest;

public class GetCertificatesInternalWithQARequestBuilder {

  private List<String> certificateIds = Collections.emptyList();

  public static GetCertificatesInternalWithQARequestBuilder create() {
    return new GetCertificatesInternalWithQARequestBuilder();
  }

  private GetCertificatesInternalWithQARequestBuilder() {

  }

  public GetCertificatesInternalWithQARequestBuilder certificateIds(List<String> unitIds) {
    this.certificateIds = unitIds;
    return this;
  }

  public CertificatesWithQAInternalRequest build() {
    return CertificatesWithQAInternalRequest.builder()
        .certificateIds(certificateIds)
        .build();
  }
}
