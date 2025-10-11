package se.inera.intyg.certificateservice.integrationtest.fk3226;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionDiagnos.DIAGNOSIS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionDiagnos.DIAGNOS_1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionForutsattningarForAttLamnaSkriftligtSamtycke.FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionForutsattningarForAttLamnaSkriftligtSamtycke.FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionPatientensBehandlingOchVardsituation.QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionPatientensBehandlingOchVardsituation.QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionUtlatandeBaseratPa.QUESTION_UTLATANDE_BASERAT_PA_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionUtlatandeBaseratPa.QUESTION_UTLATANDE_BASERAT_PA_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionUtlatandeBaseratPa.UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customUpdateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.getValueBoolean;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.getValueCode;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.getValueDateList;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.getValueDiagnosisList;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.updateValue;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueBoolean;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueCode;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDateList;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDiagnosis;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDiagnosisList;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0009;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class UpdateCertificateFK3226IT extends BaseIntegrationIT {

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
            .id(QUESTION_UTLATANDE_BASERAT_PA_FIELD_ID.value())
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
            QUESTION_UTLATANDE_BASERAT_PA_ID.id(),
            updateValue(certificate, QUESTION_UTLATANDE_BASERAT_PA_ID.id(), expectedData))
    );

    final var response = api().updateCertificate(
        customUpdateCertificateRequest()
            .certificate(certificate)
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build(),
        certificateId(testCertificates)
    );

    assertEquals(expectedData, getValueDateList(response, QUESTION_UTLATANDE_BASERAT_PA_ID.id()));
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
  @DisplayName("Om intyget får ett uppdaterat värde för behandling ska intyget uppdateras")
  void shallUpdateDataForTreatment() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var expectedData =
        CertificateDataValueCode.builder()
            .id(QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_FIELD_ID.value())
            .code(CodeSystemKvFkmu0009.ENDAST_PALLIATIV.code())
            .build();
    final var certificate = certificate(testCertificates);

    Objects.requireNonNull(
        certificate.getData().put(
            QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID.id(),
            updateValue(certificate, QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID.id(),
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
        getValueCode(response, QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID.id()));
  }

  @Test
  @DisplayName("Om intyget får ett uppdaterat värde för samtycke ska intyget uppdateras")
  void shallUpdateDataForConsent() {
    final var testCertificates = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion())
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );

    final var expectedData =
        CertificateDataValueBoolean.builder()
            .id(FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_FIELD_ID.value())
            .selected(true)
            .build();
    final var certificate = certificate(testCertificates);

    Objects.requireNonNull(
        certificate.getData().put(
            FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_ID.id(),
            updateValue(certificate, FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_ID.id(),
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
        getValueBoolean(response, FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_ID.id()));
  }
}
