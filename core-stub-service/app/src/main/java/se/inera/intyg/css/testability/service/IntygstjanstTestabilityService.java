package se.inera.intyg.css.testability.service;

import java.util.List;
import org.springframework.stereotype.Service;
import se.inera.intyg.css.application.dto.CertificateTextDTO;
import se.inera.intyg.css.infrastructure.persistence.IntygstjanstRepository;
import se.inera.intyg.css.testability.dto.IntygstjanstCertificatesDTO;

@Service
public class IntygstjanstTestabilityService {

  private final IntygstjanstRepository intygstjanstRepository;

  public IntygstjanstTestabilityService(IntygstjanstRepository intygstjanstRepository) {
    this.intygstjanstRepository = intygstjanstRepository;
  }

  public void setCertificates(String careProvider,
      IntygstjanstCertificatesDTO intygstjanstCertificatesDTO) {
    intygstjanstRepository.store(careProvider, intygstjanstCertificatesDTO.certificates());
  }

  public void deleteCertificates(String careProvider) {
    intygstjanstRepository.remove(careProvider);
  }

  public void setCertificateTexts(List<CertificateTextDTO> certificateTexts) {
    intygstjanstRepository.storeCertificateTexts(certificateTexts);
  }

  public void deleteCertificateTexts() {
    intygstjanstRepository.removeCertificateTexts();
  }
}
