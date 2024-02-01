package se.inera.intyg.certificateservice.integrationtest.util;

import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;

public class ApiRequestUtil {

  public static CertificateTypeInfoRequestBuilder customCertificateTypeInfoRequest() {
    return CertificateTypeInfoRequestBuilder.create();
  }

  public static GetCertificateTypeInfoRequest defaultCertificateTypeInfoRequest() {
    return CertificateTypeInfoRequestBuilder.create().build();
  }

  public static CreateCertificateRequestBuilder customCreateCertificateRequest() {
    return CreateCertificateRequestBuilder.create();
  }

  public static CreateCertificateRequest defaultCreateCertificateRequest() {
    return CreateCertificateRequestBuilder.create().build();
  }
}

