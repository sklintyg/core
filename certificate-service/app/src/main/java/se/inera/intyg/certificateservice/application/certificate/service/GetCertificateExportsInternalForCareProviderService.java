package se.inera.intyg.certificateservice.application.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.ExportCertificateInternalRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ExportCertificateInternalResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ExportInternalResponse;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificate.repository.CertificateRepository;
import se.inera.intyg.certificateservice.domain.certificate.service.XmlGenerator;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;

@Service
@RequiredArgsConstructor
public class GetCertificateExportsInternalForCareProviderService {

  private final CertificateRepository certificateRepository;
  private final XmlGenerator xmlGenerator;

  public ExportInternalResponse get(ExportCertificateInternalRequest request, String careProviderId) {
    final var certificateExportPage = certificateRepository.getExportByCareProviderId(
        new HsaId(careProviderId),
        request.getPage(),
        request.getSize()
    );

    final var exportedCertificates = certificateExportPage.certificates().stream()
        .map(certificate ->
            ExportCertificateInternalResponse.builder()
                .certificateId(certificate.id().id())
                .xml(getXml(certificate).base64())
                .revoked(certificate.revoked() != null)
                .build()
        )
        .toList();

    return ExportInternalResponse.builder()
        .exports(exportedCertificates)
        .build();
  }

    private Xml getXml(Certificate certificate) {
        return certificate.xml() != null ? certificate.xml()
            : xmlGenerator.generate(certificate, false);
    }
}