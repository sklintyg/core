package se.inera.intyg.certificateservice.application.certificatetypeinfo;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateTypeInfoDTO;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.service.CertificateTypeInfoService;

@RequiredArgsConstructor
@RestController("/api/certificatetypeinfo")
public class CertificateTypeInfoController {

  private final CertificateTypeInfoService certificateTypeInfoService;

  @PostMapping
  List<CertificateTypeInfoDTO> findActiveCertificateTypeInfos(
      GetCertificateTypeInfoRequest getCertificateTypeInfoRequest) {
    return certificateTypeInfoService.getActiveCertificateTypeInfos(getCertificateTypeInfoRequest);
  }
}
