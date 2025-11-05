package se.inera.intyg.certificateservice.certificate.converter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.certificate.dto.GeneralPrintTextDTO;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateMetadataDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateGeneralPrintText;

@Component
@RequiredArgsConstructor
public class PrintCertificateMetadataConverter {

  private final PrintCertificateUnitInformationConverter printCertificateUnitInformationConverter;

  private static final String APPLICATION_ORIGIN_1177_INTYG = "1177 intyg";
  private static final String APPLICATION_ORIGIN_WEBCERT = "Webcert";

  public PrintCertificateMetadataDTO convert(Certificate certificate,
      boolean isCitizenFormat, String fileName) {
    final var metadata = certificate.getMetadataForPrint();

    return PrintCertificateMetadataDTO.builder()
        .name(certificate.certificateModel().name())
        .typeId(certificate.certificateModel().type().code())
        .version(certificate.certificateModel().id().version().version())
        .signingDate(
            certificate.isDraft()
                ? null
                : certificate.signed().format(DateTimeFormatter.ISO_DATE)
        )
        .certificateId(certificate.id().id())
        .recipientLogo(convertLogo(certificate.certificateModel().recipient().logo()))
        .recipientName(certificate.certificateModel().recipient().name())
        .recipientId(certificate.certificateModel().recipient().id().id())
        .applicationOrigin(isCitizenFormat
            ? APPLICATION_ORIGIN_1177_INTYG
            : APPLICATION_ORIGIN_WEBCERT)
        .personId(metadata.patient().id().idWithDash())
        .description(convertDescription(certificate.certificateModel().description()))
        .issuerName(metadata.issuer().name().fullName())
        .issuingUnit(metadata.issuingUnit().name().name())
        .sentDate(
            (certificate.sent() != null && certificate.sent().sentAt() != null) ? certificate.sent()
                .sentAt().toString() : null)
        .unitInformation(
            printCertificateUnitInformationConverter.convert(certificate))
        .fileName(fileName)
        .canSendElectronically(certificate.certificateModel().recipient().canSendElectronically())
        .generalPrintText(convertGeneralPrintText(certificate.getGeneralPrintText()))
        .build();
  }

  private static GeneralPrintTextDTO convertGeneralPrintText(
      Optional<CertificateGeneralPrintText> text) {
    if (text.isEmpty()) {
      return GeneralPrintTextDTO.builder()
          .build();
    }
    return GeneralPrintTextDTO.builder()
        .leftMarginInfoText(text.get().leftMarginInfoText())
        .draftAlertInfoText(text.get().draftAlertInfoText())
        .build();
  }

  private byte[] convertLogo(String logoPath) {
    final var classloader = getClass().getClassLoader();
    final var inputStream = classloader.getResourceAsStream(logoPath);

    if (inputStream == null) {
      throw new IllegalStateException("Input stream for converting logo is null");
    }

    try {
      final var image = ImageIO.read(inputStream);

      final var byteArrayOutputStream = new ByteArrayOutputStream();
      ImageIO.write(image, "PNG", byteArrayOutputStream);
      return byteArrayOutputStream.toByteArray();
    } catch (IOException e) {
      throw new IllegalStateException("Could not convert logo:", e);
    }
  }

  private String convertDescription(String description) {
    if (description == null || description.isEmpty()) {
      return description;
    }

    if (!description.contains("<LINK:")) {
      return description;
    }

    final var pattern = Pattern.compile("<LINK:(.*?)>");
    final var matcher = pattern.matcher(description);

    return matcher.replaceAll(link ->
        getLinkReplacement(link.group(1))
    );
  }

  private static String getLinkReplacement(String linkId) {
    return switch (linkId) {
      case "transportstyrelsenLink", "transportstyrelsensHemsidaLink" ->
          "Transportstyrelsens hemsida";
      case "forsakringskassanLink" -> "Försäkringskassans hemsida";
      default -> "";
    };
  }
}
