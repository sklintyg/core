package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.util.List;
import lombok.EqualsAndHashCode;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;

@EqualsAndHashCode
public class DefaultCitizenAvailableFunctionsProvider implements CitizenAvailableFunctionsProvider {

  public static final String AVAILABLE_FUNCTION_PRINT_NAME = "Intyget kan skrivas ut";
  private static final String SEND_CERTIFICATE_NAME = "Skicka intyg";
  private static final String SEND_CERTIFICATE_TITLE = "Skicka intyg";
  private static final String SEND_CERTIFICATE_BODY =
      "Från den här sidan kan du välja att skicka ditt intyg digitalt till mottagaren. "
          + "Endast mottagare som kan ta emot digitala intyg visas nedan.";

  @Override
  public List<CitizenAvailableFunction> of(Certificate certificate) {
    if (certificate.certificateModel().isInactive()) {
      return List.of(createPrintFunction(certificate));
    }

    if (certificate.isReplaced() || certificate.isComplemented()) {
      return List.of();
    }

    return List.of(
        createSendFunction(certificate),
        createPrintFunction(certificate)
    );
  }

  private CitizenAvailableFunction createSendFunction(Certificate certificate) {
    return CitizenAvailableFunction.builder()
        .type(CitizenAvailableFunctionType.SEND_CERTIFICATE)
        .title(SEND_CERTIFICATE_TITLE)
        .name(SEND_CERTIFICATE_NAME)
        .body(SEND_CERTIFICATE_BODY)
        .enabled(certificate.isSendActiveForCitizen())
        .build();
  }

  private CitizenAvailableFunction createPrintFunction(Certificate certificate) {
    return CitizenAvailableFunction.builder()
        .type(CitizenAvailableFunctionType.PRINT_CERTIFICATE)
        .name(AVAILABLE_FUNCTION_PRINT_NAME)
        .enabled(true)
        .information(List.of(
            CitizenAvailableFunctionInformation.builder()
                .type(CitizenAvailableFunctionInformationType.FILENAME)
                .text(certificate.certificateModel().fileName())
                .build()
        ))
        .build();
  }
}