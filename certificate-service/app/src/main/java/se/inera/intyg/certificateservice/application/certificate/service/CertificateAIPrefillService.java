package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateAIPrefillRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateAIPrefillResponse;

@Service
@RequiredArgsConstructor
public class CertificateAIPrefillService {


  public CertificateAIPrefillResponse prefill(CertificateAIPrefillRequest request,
      String certificateId) {

    return null;
  }
}
