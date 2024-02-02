package se.inera.intyg.certificateservice.integrationtest.util;

import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;

public class ApiRequestUtil {

  public static CertificateTypeInfoRequestBuilder customCertificateTypeInfoRequest() {
    return CertificateTypeInfoRequestBuilder.create();
  }

  public static GetCertificateTypeInfoRequest defaultCertificateTypeInfoRequest() {
    return CertificateTypeInfoRequestBuilder.create().build();
  }

  public static CreateCertificateRequestBuilder customCreateCertificateRequest(String type,
      String version) {
    return CreateCertificateRequestBuilder.create()
        .certificateModelId(
            CertificateModelIdDTO.builder()
                .type(type)
                .version(version)
                .build()
        );
  }

  public static CreateCertificateRequest defaultCreateCertificateRequest(String type,
      String version) {
    return CreateCertificateRequestBuilder.create()
        .certificateModelId(
            CertificateModelIdDTO.builder()
                .type(type)
                .version(version)
                .build()
        )
        .build();
  }
}

