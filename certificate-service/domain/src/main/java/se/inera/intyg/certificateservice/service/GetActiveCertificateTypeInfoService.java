package se.inera.intyg.certificateservice.service;

import java.util.List;
import se.inera.intyg.certificateservice.model.CertificateTypeInfo;
import se.inera.intyg.certificateservice.repository.CertificateTypeInfoRepository;

public class GetActiveCertificateTypeInfoService {

  private final CertificateTypeInfoRepository certificateTypeInfoRepository;

  public GetActiveCertificateTypeInfoService(
      CertificateTypeInfoRepository certificateTypeInfoRepository) {
    this.certificateTypeInfoRepository = certificateTypeInfoRepository;
  }

  public List<CertificateTypeInfo> get() {
    return null;
  }
}
