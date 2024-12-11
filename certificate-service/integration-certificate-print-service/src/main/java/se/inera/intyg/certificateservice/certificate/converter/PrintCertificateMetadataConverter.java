package se.inera.intyg.certificateservice.certificate.converter;

import java.util.Optional;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateMetadataDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

@Component
public class PrintCertificateMetadataConverter {

  public Optional<PrintCertificateMetadataDTO> convert(Certificate certificate) {
    return Optional.of(PrintCertificateMetadataDTO.builder()
        .name(certificate.certificateModel().name())
        .typeId(certificate.certificateModel().type().code())
        .version(certificate.certificateModel().id().version().version())
        .signingDate(certificate.signed().toString())
        .certificateId(certificate.id().id())
//      .recipientLogo()
        .recipientName(certificate.certificateModel().recipient().name())
//      .applicationOrigin()
        .personId(certificate.certificateMetaData().patient().id().idWithDash())
        .description(certificate.certificateModel().detailedDescription())
        .build());

  }

}
