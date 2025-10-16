package se.inera.intyg.certificateservice.integrationtest.fk7472;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.elements.QuestionPeriod.QUESTION_PERIOD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.elements.QuestionSymptom.QUESTION_SYMPTOM_ID;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customValidateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.updateDateRangeListValue;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.updateTextValue;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.updateUnit;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.validationErrors;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDateRange;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class ValidateFK7472IT extends BaseIntegrationIT {

  @Test
  @DisplayName("Om utkastet innehåller korrekta värden skall utkastet vara klar för signering")
  void shallReturnEmptyListOfErrorsIfDateIsCorrect() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var expectedText = "Ett nytt exempel på ett svar.";
    final var certificate = certificate(testCertificates);

    Objects.requireNonNull(
        certificate.getData().put(
            QUESTION_SYMPTOM_ID.id(),
            updateTextValue(certificate, QUESTION_SYMPTOM_ID.id(), expectedText))
    );

    final var response = api().validateCertificate(
        customValidateCertificateRequest()
            .certificate(certificate)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(Collections.emptyList(), validationErrors(response));
  }

  @Test
  @DisplayName("Om utkastet innehåller 'symtom' längre än 318 tecken ska ett valideringsmeddelande visas.")
  void shallReturnListOfErrorsIfSymtomIsLongerThanLimit() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var expectedText = "Ett nytt exempel på ett svar. Ett nytt exempel på ett svar."
        + "Ett nytt exempel på ett svar.  Ett nytt exempel på ett svar."
        + "Ett nytt exempel på ett svar. Ett nytt exempel på ett svar."
        + "Ett nytt exempel på ett svar. Ett nytt exempel på ett svar."
        + "Ett nytt exempel på ett svar. Ett nytt exempel på ett svar."
        + "Ett nytt exempel på ett svar. Ett nytt exempel på ett svar."
        + "Ett nytt exempel på ett svar. Ett nytt exempel på ett svar."
        + "Ett nytt exempel på ett svar. Ett nytt exempel på ett svar."
        + "Ett nytt exempel på ett svar. Ett nytt exempel på ett svar."
        + "Ett nytt exempel på ett svar. Ett nytt exempel på ett svar."
        + "Ett nytt exempel på ett svar. Ett nytt exempel på ett svar.";
    final var certificate = certificate(testCertificates);

    Objects.requireNonNull(
        certificate.getData().put(
            QUESTION_SYMPTOM_ID.id(),
            updateTextValue(certificate, QUESTION_SYMPTOM_ID.id(), expectedText))
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
                .contains("Ange en text som inte är längre än"),
            () -> "Expect to contain 'Ange en text som inte är längre än' but was '%s'"
                .formatted(validationErrors(response).get(0))
        )
    );
  }

  @Test
  @DisplayName("Om utkastet saknar 'symtom' skall valideringsfel returneras")
  void shallReturnListOfErrorsIfTextOfSymtomIsMissing() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var certificate = certificate(testCertificates);

    Objects.requireNonNull(
        certificate.getData().put(
            QUESTION_SYMPTOM_ID.id(),
            updateTextValue(certificate, QUESTION_SYMPTOM_ID.id(), null))
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
                .contains("Ange ett svar."),
            () -> "Expect to contain 'Ange ett svar.' but was '%s'"
                .formatted(validationErrors(response).get(0))
        )
    );
  }

  @Test
  @DisplayName("Om utkastet saknar 'period' skall valideringsfel returneras")
  void shallReturnListOfErrorsIfDateRangeListOfPeriodIsMissing() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var certificate = certificate(testCertificates);

    Objects.requireNonNull(
        certificate.getData().put(
            QUESTION_PERIOD_ID.id(),
            updateDateRangeListValue(certificate, QUESTION_PERIOD_ID.id(),
                Collections.emptyList()))
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
                .contains("Välj minst ett alternativ."),
            () -> "Expect to contain 'Välj minst ett alternativ.' but was '%s'"
                .formatted(validationErrors(response).get(0))
        )
    );
  }

  @Test
  @DisplayName("Om utkastet har en icke komplett 'period' skall valideringsfel returneras")
  void shallReturnListOfErrorsIfDateRangeListOfPeriodIsNotComplete() {
    final var testCertificates = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var certificate = certificate(testCertificates);

    Objects.requireNonNull(
        certificate.getData().put(
            QUESTION_PERIOD_ID.id(),
            updateDateRangeListValue(certificate, QUESTION_PERIOD_ID.id(),
                List.of(
                    CertificateDataValueDateRange.builder()
                        .id("HALVA")
                        .to(LocalDate.now())
                        .build()
                )
            ))
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
                .contains("Ange ett datum"),
            () -> "Expect to contain 'Ange ett datum.' but was '%s'"
                .formatted(validationErrors(response).get(0))
        ),
        () -> assertTrue(validationErrors(response).get(0).getField()
                .contains("HALVA.from"),
            () -> "Expect field to contain 'HALVA.from' but was '%s'"
                .formatted(validationErrors(response).get(0))
        )
    );
  }

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
            QUESTION_SYMPTOM_ID.id(),
            updateTextValue(certificate, QUESTION_SYMPTOM_ID.id(), "Text")
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
}
