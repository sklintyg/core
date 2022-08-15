package se.inera.intyg.css.application.service;

import java.util.List;
import org.springframework.stereotype.Service;
import se.inera.intyg.css.application.dto.CertificateExportPageDTO;
import se.inera.intyg.css.application.dto.CertificateTextDTO;
import se.inera.intyg.css.infrastructure.persistence.IntygstjanstRepository;

@Service
public class IntygstjanstService {

  private final IntygstjanstRepository intygstjanstRepository;

  public IntygstjanstService(IntygstjanstRepository intygstjanstRepository) {
    this.intygstjanstRepository = intygstjanstRepository;
  }

  public CertificateExportPageDTO getCertificateExportPage(String careProviderId, int size,
      int page) {
    final var certificateXmlDTOS = intygstjanstRepository.get(careProviderId, size, size * page);
    return new CertificateExportPageDTO(
        careProviderId,
        page,
        certificateXmlDTOS.size(),
        intygstjanstRepository.count(careProviderId),
        intygstjanstRepository.countRevoked(careProviderId),
        certificateXmlDTOS
    );
  }

  public List<CertificateTextDTO> getCertificateTexts() {
    return intygstjanstRepository.getCertificateTexts();
  }

  public void eraseCareProvider(String careProviderId) {
    intygstjanstRepository.eraseCareProvider(careProviderId);
  }
}
