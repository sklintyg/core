package se.inera.intyg.certificateservice.application.certificatetypeinfo;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateTypeInfoDTO;
import se.inera.intyg.certificateservice.service.GetActiveCertificateTypeInfoService;

@Service
@RequiredArgsConstructor
public class CertificateTypeInfoService {


  private final GetActiveCertificateTypeInfoService getActiveCertificateTypeInfoService;
  private final CertificateTypeInfoConverter certificateTypeInfoConverter;

  public List<CertificateTypeInfoDTO> getActiveCertificateTypeInfos() {
    final var activeCertificateTypeInfos = getActiveCertificateTypeInfoService.get();
    return activeCertificateTypeInfos.stream()
        .map(certificateTypeInfoConverter::convert)
        .toList();
  }
}
