package se.inera.intyg.certificateservice.certificate.converter;

import java.util.Optional;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateMetadataDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

@Component
public class PrintCertificateMetadataConverter {

  private static final String APPLICATION_ORIGIN_1177_INTYG = "1177 intyg";
  private static final String APPLICATION_ORIGIN_WEBCERT = "Webcert";

  public Optional<PrintCertificateMetadataDTO> convert(Certificate certificate,
      boolean isCitizenFormat) {
    return Optional.of(PrintCertificateMetadataDTO.builder()
        .name(certificate.certificateModel().name())
        .typeId(certificate.certificateModel().type().code())
        .version(certificate.certificateModel().id().version().version())
        .signingDate(certificate.signed().toString())
        .certificateId(certificate.id().id())
//      .recipientLogo(certificate.certificateModel().recipient.logoPath())
        .recipientName(certificate.certificateModel().recipient().name())
        .applicationOrigin(isCitizenFormat
            ? APPLICATION_ORIGIN_1177_INTYG
            : APPLICATION_ORIGIN_WEBCERT)
        .personId(certificate.certificateMetaData().patient().id().idWithDash())
        .description(certificate.certificateModel().detailedDescription())
        .build());
  }

  private byte[] convertLogo(String logoPath) {
    //Get file from path
    //Convert to byteArray
    return null;
  }
}
