package se.inera.intyg.certificateservice.integrationtest.util;

import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;

public class ApiRequestUtil {

  public static CertificateTypeInfoRequestBuilder customCertificateTypeInfoRequest() {
    return CertificateTypeInfoRequestBuilder.create();
  }

  public static GetCertificateTypeInfoRequest defaultCertificateTypeInfoRequest() {
    return CertificateTypeInfoRequestBuilder.create().build();
  }
}

