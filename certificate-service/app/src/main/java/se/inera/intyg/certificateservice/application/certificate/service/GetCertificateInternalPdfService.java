package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateInternalPdfResponse;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.pdfboxgenerator.CertificatePdfGenerator;

@Service
@RequiredArgsConstructor
public class GetCertificateInternalPdfService {

  private final CertificateRepository certificateRepository;
  private final CertificatePdfGenerator certificatePdfGenerator;

  public GetCertificateInternalPdfResponse get(String certificateId) {
    final var certificate = certificateRepository.getById(new CertificateId(certificateId));

    final var pdf = certificatePdfGenerator.generate(certificate);

    return GetCertificateInternalPdfResponse.builder()
        .pdfData(pdf.pdfData())
        .fileName(pdf.fileName())
        .build();
  }
}
