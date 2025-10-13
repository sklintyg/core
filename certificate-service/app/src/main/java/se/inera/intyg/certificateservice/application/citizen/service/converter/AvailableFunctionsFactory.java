package se.inera.intyg.certificateservice.application.citizen.service.converter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import se.inera.intyg.certificateservice.application.common.dto.AvailableFunctionDTO;
import se.inera.intyg.certificateservice.application.common.dto.AvailableFunctionType;
import se.inera.intyg.certificateservice.application.common.dto.InformationDTO;
import se.inera.intyg.certificateservice.application.common.dto.InformationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

public class AvailableFunctionsFactory {

  public static final String AVAILABLE_FUNCTION_PRINT_NAME = "Intyget kan skrivas ut";
  public static final String SEND_CERTIFICATE_NAME = "Skicka intyg";
  public static final String SEND_CERTIFICATE_TITLE = "Skicka intyg";
  public static final String SEND_CERTIFICATE_BODY =
      "Från den här sidan kan du välja att skicka ditt intyg digitalt till mottagaren. "
          + "Endast mottagare som kan ta emot digitala intyg visas nedan.";

  private AvailableFunctionsFactory() {
    throw new IllegalStateException("Utility class");
  }

  public static List<AvailableFunctionDTO> get(Certificate certificate) {
    if (certificate.certificateModel().citizenAvailableFunctionsProvider() != null) {
      return certificate.certificateModel().citizenAvailableFunctionsProvider().of(certificate)
          .stream()
          .map(AvailableFunctionDTO::toDTO)
          .toList();
    }

    return Stream.of(
            getSendAvailableFunction(certificate),
            getPrintAvailableFunction(certificate)
        ).filter(Optional::isPresent)
        .map(Optional::get)
        .toList();
  }

  private static Optional<AvailableFunctionDTO> getSendAvailableFunction(Certificate certificate) {
    return certificate.isSendActiveForCitizen() ?
        Optional.of(
            AvailableFunctionDTO.builder()
                .title(SEND_CERTIFICATE_TITLE)
                .name(SEND_CERTIFICATE_NAME)
                .type(AvailableFunctionType.SEND_CERTIFICATE)
                .body(SEND_CERTIFICATE_BODY)
                .enabled(true)
                .build()
        )
        : Optional.of(
            AvailableFunctionDTO.builder()
                .title(SEND_CERTIFICATE_TITLE)
                .name(SEND_CERTIFICATE_NAME)
                .type(AvailableFunctionType.SEND_CERTIFICATE)
                .body(SEND_CERTIFICATE_BODY)
                .enabled(false)
                .build()
        );
  }

  private static Optional<AvailableFunctionDTO> getPrintAvailableFunction(Certificate certificate) {
    return Optional.of(
        AvailableFunctionDTO.builder()
            .name(AVAILABLE_FUNCTION_PRINT_NAME)
            .type(AvailableFunctionType.PRINT_CERTIFICATE)
            .information(
                List.of(
                    InformationDTO.builder()
                        .type(InformationType.FILENAME)
                        .text(certificate.fileName())
                        .build()
                )
            )
            .enabled(true)
            .build()
    );
  }
}