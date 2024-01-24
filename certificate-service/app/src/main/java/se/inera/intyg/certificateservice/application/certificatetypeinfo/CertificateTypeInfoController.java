package se.inera.intyg.certificateservice.application.certificatetypeinfo;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateTypeInfoDTO;

@RestController("/api/certificatetypeinfo")
public class CertificateTypeInfoController {

  @PostMapping
  List<CertificateTypeInfoDTO> findActiveCertificateTypeInfos() {
    return null;
  }
}
