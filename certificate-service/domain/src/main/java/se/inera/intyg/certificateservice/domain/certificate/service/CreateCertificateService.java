package se.inera.intyg.certificateservice.domain.certificate.service;

import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateModelRepository;

@RequiredArgsConstructor
public class CreateCertificateService {

  private final CertificateModelRepository certificateModelRepository;
  private final CertificateRepository certificateRepository;

  public Certificate create(CertificateModelId certificateModelId,
      ActionEvaluation actionEvaluation) {
    // Hämtar certificatemodel
    // Validera ifall man får skapa utkast.
    // Skapar cerificate från repository
    // Fyller metadata från actionEvaluation
    // Sparar i repository
    // Returnerar
    return null;
  }
}
