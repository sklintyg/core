package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customValidateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultCreateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.updateBooleanValue;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.updateDateValue;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.updateTextValue;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.updateUnit;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.validationErrors;

import java.time.LocalDate;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class ValidateCertificateIT extends BaseIntegrationIT {

  @Test
  @DisplayName("Om utkastet saknar 'Vårdenhetens adress' skall valideringsfel returneras")
  void shallReturnListOfErrorsIfMissingUnitContactAddress() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var certificate = updateUnit(
        testCertificates,
        certificate(testCertificates).getMetadata().getUnit()
            .withAddress("")
    );

    Objects.requireNonNull(
        certificate.getData().put(
            element().id(),
            updateValue(certificate, element().id(), value())
        )
    );

    final var response = api().validateCertificate(
        customValidateCertificateRequest()
            .certificate(certificate)
            .build(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertEquals(1, validationErrors(response).size(),
            () -> "Wrong number of errors '%s'".formatted(validationErrors(response))
        ),
        () -> assertTrue(validationErrors(response).get(0).getText()
                .contains("Ange postadress."),
            () -> "Expect to contain 'Ange postadress.' but was '%s'"
                .formatted(validationErrors(response).get(0))
        )
    );
  }

  @Test
  @DisplayName("Om utkastet saknar 'Vårdenhetens postnummer' skall valideringsfel returneras")
  void shallReturnListOfErrorsIfMissingUnitContactZipCode() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var certificate = updateUnit(
        testCertificates,
        certificate(testCertificates).getMetadata().getUnit()
            .withZipCode("")
    );

    Objects.requireNonNull(
        certificate.getData().put(
            element().id(),
            updateValue(certificate, element().id(), value())
        )
    );

    final var response = api().validateCertificate(
        customValidateCertificateRequest()
            .certificate(certificate)
            .build(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertEquals(1, validationErrors(response).size(),
            () -> "Wrong number of errors '%s'".formatted(validationErrors(response))
        ),
        () -> assertTrue(validationErrors(response).get(0).getText()
                .contains("Ange postnummer."),
            () -> "Expect to contain 'Ange postnummer.' but was '%s'"
                .formatted(validationErrors(response).get(0))
        )
    );
  }

  @Test
  @DisplayName("Om utkastet saknar 'Vårdenhetens postort' skall valideringsfel returneras")
  void shallReturnListOfErrorsIfMissingUnitContactCity() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var certificate = updateUnit(
        testCertificates,
        certificate(testCertificates).getMetadata().getUnit()
            .withCity("")
    );

    Objects.requireNonNull(
        certificate.getData().put(
            element().id(),
            updateValue(certificate, element().id(), value())
        )
    );

    final var response = api().validateCertificate(
        customValidateCertificateRequest()
            .certificate(certificate)
            .build(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertEquals(1, validationErrors(response).size(),
            () -> "Wrong number of errors '%s'".formatted(validationErrors(response))
        ),
        () -> assertTrue(validationErrors(response).get(0).getText()
                .contains("Ange postort."),
            () -> "Expect to contain 'Ange postort.' but was '%s'"
                .formatted(validationErrors(response).get(0))
        )
    );
  }

  @Test
  @DisplayName("Om utkastet saknar 'Vårdenhetens telefonnummer' skall valideringsfel returneras")
  void shallReturnListOfErrorsIfMissingUnitContactPhoneNumber() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var certificate = updateUnit(
        testCertificates,
        certificate(testCertificates).getMetadata().getUnit()
            .withPhoneNumber("")
    );

    Objects.requireNonNull(
        certificate.getData().put(
            element().id(),
            updateValue(certificate, element().id(), value())
        )
    );

    final var response = api().validateCertificate(
        customValidateCertificateRequest()
            .certificate(certificate)
            .build(),
        certificateId(testCertificates)
    );

    assertAll(
        () -> assertEquals(1, validationErrors(response).size(),
            () -> "Wrong number of errors '%s'".formatted(validationErrors(response))
        ),
        () -> assertTrue(validationErrors(response).get(0).getText()
                .contains("Ange telefonnummer."),
            () -> "Expect to contain 'Ange telefonnummer.' but was '%s'"
                .formatted(validationErrors(response).get(0))
        )
    );
  }

  @Test
  @DisplayName("Om utkastet saknar värden skall valideringsfel returneras")
  void shallReturnListOfErrorsIfEmptyCertificate() {

    final var certificate = api().createCertificate(defaultCreateCertificateRequest(
        type(), typeVersion()
    ));

    final var response = api().validateCertificate(
        customValidateCertificateRequest()
            .certificate(certificate.getBody().getCertificate())
            .build(),
        certificate.getBody().getCertificate().getMetadata().getId()
    );

    assertFalse(response.getBody().getValidationErrors().isEmpty());
  }

  private CertificateDataElement updateValue(CertificateDTO certificate, String id,
      Object expectedValue) {
    if (expectedValue instanceof String expectedText) {
      return updateTextValue(certificate, id, expectedText);
    }
    if (expectedValue instanceof LocalDate expectedDate) {
      return updateDateValue(certificate, id, expectedDate);
    }
    if (expectedValue instanceof Boolean expectedBoolean) {
      return updateBooleanValue(certificate, id, expectedBoolean);
    }

    throw new IllegalStateException("No update function available for type %s"
        .formatted(expectedValue.getClass()));
  }
}
