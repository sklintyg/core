package se.inera.intyg.certificateservice.integrationtest.fk7472;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.elements.QuestionPeriod.QUESTION_PERIOD_ID;
import static se.inera.intyg.certificateservice.integrationtest.fk7472.FK7472Constants.FK7472;
import static se.inera.intyg.certificateservice.integrationtest.fk7472.FK7472Constants.VERSION;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customUpdateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.getValueDateRangeList;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.updateDateRangeListValue;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDateRange;
import se.inera.intyg.certificateservice.integrationtest.BaseIntegrationIT;

public abstract class UpdateCertificateFK7472IT extends BaseIntegrationIT {

  @Test
  @DisplayName("Om intyget får ett uppdaterat värde för period ska svarsalternativ uppdateras")
  void shallUpdateDataForPeriod() {
    final var testCertificates = testabilityApi.addCertificates(
        customTestabilityCertificateRequest(FK7472, VERSION)
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var expectedData = List.of(
        CertificateDataValueDateRange.builder()
            .id("HALVA")
            .to(LocalDate.now())
            .from(LocalDate.now().minusDays(10))
            .build()
    );
    final var certificate = certificate(testCertificates);

    Objects.requireNonNull(
        certificate.getData().put(
            QUESTION_PERIOD_ID.id(),
            updateDateRangeListValue(certificate, QUESTION_PERIOD_ID.id(), expectedData))
    );

    final var response = api.updateCertificate(
        customUpdateCertificateRequest()
            .certificate(certificate)
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(expectedData, getValueDateRangeList(response, QUESTION_PERIOD_ID.id()));
  }
}
