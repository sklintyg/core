package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ts8071;

import static se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType.SEND;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionRuleLimitedCertificateFunctionality;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenAvailableFunction;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenAvailableFunctionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CitizenAvailableFunctionsProvider;
import se.inera.intyg.certificateservice.domain.certificatemodel.repository.CertificateActionConfigurationRepository;

@RequiredArgsConstructor
public class TS8071CitizenAvailableFunctionsProvider implements CitizenAvailableFunctionsProvider {

  private final CertificateActionConfigurationRepository certificateActionConfigurationRepository;

  private static final String OLD_VERSION_LIMITED_FUNCTIONALITY_INFO_TITLE = "Detta intyg är av en äldre version";
  private static final String OLD_VERSION_LIMITED_FUNCTIONALITY_INFO_NAME = "Presentera informationsruta";
  private static final String OLD_VERSION_LIMITED_FUNCTIONALITY_INFO_BODY = "Intyget kan inte längre skickas digitalt till mottagaren.";
  private static final String AVAILABLE_FUNCTION_PRINT_NAME = "Intyget kan skrivas ut";
  private static final String SEND_CERTIFICATE_NAME = "Skicka intyg";
  private static final String SEND_CERTIFICATE_TITLE = "Skicka intyg";
  private static final String SEND_CERTIFICATE_BODY =
      "Från den här sidan kan du välja att skicka ditt intyg digitalt till mottagaren. "
          + "Endast mottagare som kan ta emot digitala intyg visas nedan.";


  @Override
  public List<CitizenAvailableFunction> of(Certificate certificate) {
    final var citizenAvailableFunctions = new ArrayList<CitizenAvailableFunction>();
    final var actionRuleLimitedCertificateFunctionality = new ActionRuleLimitedCertificateFunctionality(
        certificateActionConfigurationRepository,
        SEND);

    if (certificate.isReplaced() || certificate.isComplemented()) {
      return List.of();
    }

    if (certificateHasFullFunctionality(certificate, actionRuleLimitedCertificateFunctionality)) {
      citizenAvailableFunctions.add(
          CitizenAvailableFunction.builder()
              .type(CitizenAvailableFunctionType.SEND_CERTIFICATE)
              .enabled(certificate.sent() == null)
              .title(SEND_CERTIFICATE_TITLE)
              .name(SEND_CERTIFICATE_NAME)
              .body(SEND_CERTIFICATE_BODY)
              .build()
      );
    } else {
      citizenAvailableFunctions.add(
          CitizenAvailableFunction.builder()
              .type(CitizenAvailableFunctionType.ATTENTION)
              .enabled(true)
              .title(OLD_VERSION_LIMITED_FUNCTIONALITY_INFO_TITLE)
              .name(OLD_VERSION_LIMITED_FUNCTIONALITY_INFO_NAME)
              .body(OLD_VERSION_LIMITED_FUNCTIONALITY_INFO_BODY)
              .build()
      );
    }
    citizenAvailableFunctions.add(
        CitizenAvailableFunction.builder()
            .type(CitizenAvailableFunctionType.PRINT_CERTIFICATE)
            .enabled(true)
            .name(AVAILABLE_FUNCTION_PRINT_NAME)
            .build()
    );

    return citizenAvailableFunctions;
  }

  private boolean certificateHasFullFunctionality(Certificate certificate,
      ActionRuleLimitedCertificateFunctionality actionRuleLimitedCertificateFunctionality) {
    return actionRuleLimitedCertificateFunctionality.evaluate(Optional.of(certificate),
        Optional.empty());
  }
}