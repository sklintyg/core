package se.inera.intyg.certificateservice.certificate.converter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateMetadataDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

@Component
public class PrintCertificateMetadataConverter {

  private static final String APPLICATION_ORIGIN_1177_INTYG = "1177 intyg";
  private static final String APPLICATION_ORIGIN_WEBCERT = "Webcert";

  public PrintCertificateMetadataDTO convert(Certificate certificate,
      boolean isCitizenFormat, String fileName) {
    return PrintCertificateMetadataDTO.builder()
        .name(certificate.certificateModel().name())
        .typeId(certificate.certificateModel().type().code())
        .version(certificate.certificateModel().id().version().version())
        .signingDate(certificate.signed().toString())
        .certificateId(certificate.id().id())
        .recipientLogo(convertLogo(certificate.certificateModel().recipient().logo()))
        .recipientName(certificate.certificateModel().recipient().name())
        .applicationOrigin(isCitizenFormat
            ? APPLICATION_ORIGIN_1177_INTYG
            : APPLICATION_ORIGIN_WEBCERT)
        .personId(certificate.certificateMetaData().patient().id().idWithDash())
        .description(certificate.certificateModel().detailedDescription())
        .fileName(fileName)
        .build();
  }

  private byte[] convertLogo(String logoPath) {
    final var classloader = getClass().getClassLoader();
    final var inputStream = classloader.getResourceAsStream(logoPath);

    try {
      final var image = ImageIO.read(inputStream);

      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      ImageIO.write(image, "PNG", byteArrayOutputStream);
      return byteArrayOutputStream.toByteArray();
    } catch (IOException e) {
      throw new IllegalStateException("Could not convert logo:", e);
    }
  }

}
