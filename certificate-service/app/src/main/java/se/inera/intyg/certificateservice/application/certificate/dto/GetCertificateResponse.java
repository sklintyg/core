package se.inera.intyg.certificateservice.application.certificate.dto;

import lombok.Builder;
import lombok.Value;


@Value
@Builder
public class GetCertificateResponse {

  CertificateDTO certificate;
}
