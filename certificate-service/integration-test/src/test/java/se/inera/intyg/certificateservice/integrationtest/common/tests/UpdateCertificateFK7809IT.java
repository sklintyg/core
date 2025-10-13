package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionAktivitetsbegransningar.QUESTION_AKTIVITETSBEGRANSNINGAR_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionBaseratPaAnnatMedicinsktUnderlag.QUESTION_BASERAT_PA_ANNAT_UNDERLAG_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionBaseratPaAnnatMedicinsktUnderlag.QUESTION_BASERAT_PA_ANNAT_UNDERLAG_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionDiagnos.DIAGNOSIS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionDiagnos.DIAGNOS_1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionDiagnosHistorik.DIAGNOSIS_MOTIVATION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATNING_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionFunktionsnedsattning.FUNKTIONSNEDSATTNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionGrundForMedicinsktUnderlag.QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionGrundForMedicinsktUnderlag.QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionGrundForMedicinsktUnderlag.UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionOvrigt.QUESTION_OVRIGT_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionPagaendeOchPlaneradeBehandlingar.QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionPrognos.QUESTION_PROGNOS_ID;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customUpdateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.getValueBoolean;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.getValueCodeList;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.getValueDateList;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.getValueDiagnosisList;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.getValueText;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.updateTextValue;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.updateValue;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueBoolean;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueCode;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueCodeList;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDateList;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDiagnosis;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDiagnosisList;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemFunktionsnedsattning;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class UpdateCertificateFK7809IT extends BaseIntegrationIT {

  @Test
  @DisplayName("Om intyget får ett uppdaterat värde för grund för medicinskt underlag ska intyget uppdateras")
  void shallUpdateDataForMedicalExamination() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var expectedData =
        CertificateDataValueDateList.builder()
            .id(QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_FIELD_ID.value())
            .list(List.of(
                CertificateDataValueDate.builder()
                    .id(UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID)
                    .date(LocalDate.now())
                    .build()
            ))
            .build();
    final var certificate = certificate(testCertificates);

    Objects.requireNonNull(
        certificate.getData().put(
            QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID.id(),
            updateValue(certificate, QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID.id(), expectedData))
    );

    final var response = api().updateCertificate(
        customUpdateCertificateRequest()
            .certificate(certificate)
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(expectedData,
        getValueDateList(response, QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID.id()));
  }

  @Test
  @DisplayName("Om intyget får ett uppdaterat värde för annat underlag ska intyget uppdateras")
  void shallUpdateDataForOtherTreatments() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var expectedData =
        CertificateDataValueBoolean.builder()
            .id(QUESTION_BASERAT_PA_ANNAT_UNDERLAG_FIELD_ID.value())
            .selected(true)
            .build();
    final var certificate = certificate(testCertificates);

    Objects.requireNonNull(
        certificate.getData().put(
            QUESTION_BASERAT_PA_ANNAT_UNDERLAG_ID.id(),
            updateValue(certificate, QUESTION_BASERAT_PA_ANNAT_UNDERLAG_ID.id(),
                expectedData))
    );

    final var response = api().updateCertificate(
        customUpdateCertificateRequest()
            .certificate(certificate)
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(expectedData,
        getValueBoolean(response, QUESTION_BASERAT_PA_ANNAT_UNDERLAG_ID.id()));
  }

  @Test
  @DisplayName("Om intyget får ett uppdaterat värde för diagnos ska intyget uppdateras")
  void shallUpdateDataForDiagnosis() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var expectedData =
        CertificateDataValueDiagnosisList.builder()
            .list(List.of(
                CertificateDataValueDiagnosis.builder()
                    .id(DIAGNOS_1.value())
                    .code("A20")
                    .description("Diagnosis")
                    .terminology("ICD-10")
                    .build()
            ))
            .build();
    final var certificate = certificate(testCertificates);

    Objects.requireNonNull(
        certificate.getData().put(
            DIAGNOSIS_ID.id(),
            updateValue(certificate, DIAGNOSIS_ID.id(), expectedData))
    );

    final var response = api().updateCertificate(
        customUpdateCertificateRequest()
            .certificate(certificate)
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(expectedData, getValueDiagnosisList(response, DIAGNOSIS_ID.id()));
  }

  @Test
  @DisplayName("Om intyget får ett uppdaterat värde för diagnoshistorik ska intyget uppdateras")
  void shallUpdateDataForDiagnosisHistory() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );
    final var text = "history";
    final var certificate = certificate(testCertificates);

    Objects.requireNonNull(
        certificate.getData().put(
            DIAGNOSIS_MOTIVATION_ID.id(),
            updateTextValue(certificate, DIAGNOSIS_MOTIVATION_ID.id(),
                text))
    );

    final var response = api().updateCertificate(
        customUpdateCertificateRequest()
            .certificate(certificate)
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(text, getValueText(response, DIAGNOSIS_MOTIVATION_ID.id()));
  }

  @Test
  @DisplayName("Om intyget får ett uppdaterat värde för typ av funktionsnedsättning ska intyget uppdateras")
  void shallUpdateDataForDisabilityCode() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var expectedData =
        CertificateDataValueCodeList.builder()
            .id(FUNKTIONSNEDSATNING_FIELD_ID.value())
            .list(List.of(
                CertificateDataValueCode.builder()
                    .id(FUNKTIONSNEDSATNING_FIELD_ID.value())
                    .code(CodeSystemFunktionsnedsattning.HORSELFUNKTION.code())
                    .build()))
            .build();
    final var certificate = certificate(testCertificates);

    Objects.requireNonNull(
        certificate.getData().put(
            FUNKTIONSNEDSATTNING_ID.id(),
            updateValue(certificate, FUNKTIONSNEDSATTNING_ID.id(),
                expectedData))
    );

    final var response = api().updateCertificate(
        customUpdateCertificateRequest()
            .certificate(certificate)
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(expectedData,
        getValueCodeList(response, FUNKTIONSNEDSATTNING_ID.id()));
  }

  @Test
  @DisplayName("Om intyget får ett uppdaterat värde för aktivitetsbegränsningar ska intyget uppdateras")
  void shallUpdateDataForActivityLimitations() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );
    final var text = "limitations";
    final var certificate = certificate(testCertificates);

    Objects.requireNonNull(
        certificate.getData().put(
            QUESTION_AKTIVITETSBEGRANSNINGAR_ID.id(),
            updateTextValue(certificate, QUESTION_AKTIVITETSBEGRANSNINGAR_ID.id(),
                text))
    );

    final var response = api().updateCertificate(
        customUpdateCertificateRequest()
            .certificate(certificate)
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(text, getValueText(response, QUESTION_AKTIVITETSBEGRANSNINGAR_ID.id()));
  }

  @Test
  @DisplayName("Om intyget får ett uppdaterat värde för medicinsk behandling ska intyget uppdateras")
  void shallUpdateDataForMedicalTreatment() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );
    final var text = "medical treatment";
    final var certificate = certificate(testCertificates);

    Objects.requireNonNull(
        certificate.getData().put(
            QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_ID.id(),
            updateTextValue(certificate, QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_ID.id(),
                text))
    );

    final var response = api().updateCertificate(
        customUpdateCertificateRequest()
            .certificate(certificate)
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(text, getValueText(response, QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_ID.id()));
  }

  @Test
  @DisplayName("Om intyget får ett uppdaterat värde för prognos ska intyget uppdateras")
  void shallUpdateDataForPrognosis() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );
    final var text = "prognosis";
    final var certificate = certificate(testCertificates);

    Objects.requireNonNull(
        certificate.getData().put(
            QUESTION_PROGNOS_ID.id(),
            updateTextValue(certificate, QUESTION_PROGNOS_ID.id(),
                text))
    );

    final var response = api().updateCertificate(
        customUpdateCertificateRequest()
            .certificate(certificate)
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(text, getValueText(response, QUESTION_PROGNOS_ID.id()));
  }

  @Test
  @DisplayName("Om intyget får ett uppdaterat värde för övrigt ska intyget uppdateras")
  void shallUpdateDataForOther() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );
    final var text = "other";
    final var certificate = certificate(testCertificates);

    Objects.requireNonNull(
        certificate.getData().put(
            QUESTION_OVRIGT_ID.id(),
            updateTextValue(certificate, QUESTION_OVRIGT_ID.id(),
                text))
    );

    final var response = api().updateCertificate(
        customUpdateCertificateRequest()
            .certificate(certificate)
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(text, getValueText(response, QUESTION_OVRIGT_ID.id()));
  }
}
