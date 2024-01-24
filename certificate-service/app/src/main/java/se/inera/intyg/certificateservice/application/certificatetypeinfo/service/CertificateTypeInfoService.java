package se.inera.intyg.certificateservice.application.certificatetypeinfo.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateTypeInfoDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;
import se.inera.intyg.certificateservice.repository.CertificateModelRepository;

@Service
@RequiredArgsConstructor
public class CertificateTypeInfoService {

  private final CertificateModelRepository certificateModelRepository;
  private final CertificateTypeInfoConverter certificateTypeInfoConverter;

  public List<CertificateTypeInfoDTO> getActiveCertificateTypeInfos(
      GetCertificateTypeInfoRequest getCertificateInfoRequest) {
    final var certificateModels = certificateModelRepository.findAllActive();
    return certificateModels.stream()
        .map(certificateTypeInfoConverter::convert)
        .toList();
  }
}
