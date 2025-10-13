package se.inera.intyg.certificateservice.integrationtest.common.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ANONYMA_REACT_ATTILA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_ALLERGIMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_HUDMOTTAGNINGEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_VARDCENTRAL_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingQuestionMessageBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customUnitStatisticsRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultUnitStatisticsRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.UnitStatisticsUtil.unitStatistics;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateResponse;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseIntegrationIT;

public abstract class UnitStatisticsIT extends BaseIntegrationIT {

  @Test
  @DisplayName("Skall returnera antalet utkast utfärdade på en enhet")
  void shallReturnCountOfAllDraftsOnUnit() {
    testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion()),
        defaultTestablilityCertificateRequest(type(), typeVersion()),
        defaultTestablilityCertificateRequest(type(), typeVersion())
    );

    final var response = api().getUnitStatistics(
        defaultUnitStatisticsRequest()
    );

    final var unitStatistics = unitStatistics(response);
    final var statisticsOnUnit = unitStatistics.getOrDefault(ALFA_ALLERGIMOTTAGNINGEN_ID, null);
    assertEquals(3, statisticsOnUnit.getDraftCount());
  }

  @Test
  @DisplayName("Skall returnera antalet utkast utfärdade på flera enheter")
  void shallReturnCountOfAllDraftsOnUnits() {
    testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.UNSIGNED)
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );
    testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.UNSIGNED)
            .unit(ALFA_HUDMOTTAGNINGEN_DTO)
            .build()
    );
    testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.UNSIGNED)
            .unit(ALFA_VARDCENTRAL_DTO)
            .build()
    );

    final var availableUnitIds = List.of(
        ALFA_MEDICINCENTRUM_DTO.getId(),
        ALFA_HUDMOTTAGNINGEN_DTO.getId(),
        ALFA_VARDCENTRAL_DTO.getId()
    );

    final var response = api().getUnitStatistics(
        customUnitStatisticsRequest()
            .availableUnitIds(availableUnitIds)
            .build()
    );

    final var unitStatistics = unitStatistics(response);

    availableUnitIds.forEach(
        unitId -> assertEquals(1, unitStatistics.getOrDefault(unitId, null).getDraftCount())
    );
  }


  @Test
  @DisplayName("Skall returnera antalet ohanterade frågor på en enhet")
  void shallReturnCountOfAllUnhandledQuestionsOnUnit() {
    if (!canReceiveQuestions()) {
      return;
    }

    final var testCertificate1 = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(
            type(),
            typeVersion(),
            CertificateStatusTypeDTO.SIGNED
        )
    );
    final var testCertificate2 = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(
            type(),
            typeVersion(),
            CertificateStatusTypeDTO.SIGNED
        )
    );
    final var testCertificate3 = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(
            type(),
            typeVersion(),
            CertificateStatusTypeDTO.SIGNED
        )
    );

    sendCertificate(testCertificate1, ALFA_ALLERGIMOTTAGNINGEN_DTO);
    sendCertificate(testCertificate2, ALFA_ALLERGIMOTTAGNINGEN_DTO);
    sendCertificate(testCertificate3, ALFA_ALLERGIMOTTAGNINGEN_DTO);

    api().receiveMessage(
        incomingQuestionMessageBuilder()
            .id("testCertificate1")
            .certificateId(certificateId(testCertificate1))
            .build()
    );
    api().receiveMessage(
        incomingQuestionMessageBuilder()
            .id("testCertificate2")
            .certificateId(certificateId(testCertificate2))
            .build()
    );
    api().receiveMessage(
        incomingQuestionMessageBuilder()
            .id("testCertificate3")
            .certificateId(certificateId(testCertificate3))
            .build()
    );

    final var response = api().getUnitStatistics(
        defaultUnitStatisticsRequest()
    );

    final var unitStatistics = unitStatistics(response);
    final var statisticsOnUnit = unitStatistics.getOrDefault(ALFA_ALLERGIMOTTAGNINGEN_ID, null);
    assertEquals(3, statisticsOnUnit.getUnhandledMessageCount());
  }

  @Test
  @DisplayName("Skall returnera antalet ohanterade frågor på flera enheter")
  void shallReturnCountOfAllUnhandledQuestionsOnUnits() {
    if (!canReceiveQuestions()) {
      return;
    }

    final var testCertificate1 = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.SIGNED)
            .unit(ALFA_MEDICINCENTRUM_DTO)
            .build()
    );
    final var testCertificate2 = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.SIGNED)
            .unit(ALFA_VARDCENTRAL_DTO)
            .build()
    );
    final var testCertificate3 = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion(),
            CertificateStatusTypeDTO.SIGNED)
            .unit(ALFA_HUDMOTTAGNINGEN_DTO)
            .build()
    );

    sendCertificate(testCertificate1, ALFA_MEDICINCENTRUM_DTO);
    sendCertificate(testCertificate2, ALFA_VARDCENTRAL_DTO);
    sendCertificate(testCertificate3, ALFA_HUDMOTTAGNINGEN_DTO);

    api().receiveMessage(
        incomingQuestionMessageBuilder()
            .id("testCertificate1")
            .certificateId(certificateId(testCertificate1))
            .build()
    );
    api().receiveMessage(
        incomingQuestionMessageBuilder()
            .id("testCertificate2")
            .certificateId(certificateId(testCertificate2))
            .build()
    );
    api().receiveMessage(
        incomingQuestionMessageBuilder()
            .id("testCertificate3")
            .certificateId(certificateId(testCertificate3))
            .build()
    );

    final var availableUnitIds = List.of(
        ALFA_MEDICINCENTRUM_DTO.getId(),
        ALFA_VARDCENTRAL_DTO.getId(),
        ALFA_HUDMOTTAGNINGEN_DTO.getId()
    );

    final var response = api().getUnitStatistics(
        customUnitStatisticsRequest()
            .availableUnitIds(availableUnitIds)
            .build()
    );

    final var unitStatistics = unitStatistics(response);
    availableUnitIds.forEach(
        unitId -> assertEquals(1, unitStatistics.getOrDefault(unitId, null)
            .getUnhandledMessageCount())
    );
  }

  private void sendCertificate(List<CreateCertificateResponse> createCertificateResponses,
      UnitDTO unitDTO) {
    api().sendCertificate(
        customSendCertificateRequest()
            .unit(unitDTO)
            .build(),
        certificateId(createCertificateResponses)
    );
  }

  @ParameterizedTest
  @DisplayName("Skall returnera antal utkast som är skapade på patient med skyddade personuppgifter")
  @MethodSource("rolesNoAccessToProtectedPerson")
  void shallReturnCountOfDraftsExcludingPatientIsProtectedPerson(UserDTO userDTO) {
    testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion()),
        defaultTestablilityCertificateRequest(type(), typeVersion()),
        customTestabilityCertificateRequest(type(), typeVersion())
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    final var response = api().getUnitStatistics(
        customUnitStatisticsRequest()
            .user(userDTO)
            .build()
    );

    final var unitStatistics = unitStatistics(response);
    final var statisticsOnUnit = unitStatistics.getOrDefault(ALFA_ALLERGIMOTTAGNINGEN_ID, null);
    assertEquals(2, statisticsOnUnit.getDraftCount());
  }

  @ParameterizedTest
  @DisplayName("Skall returnera antal utkast som är skapade på patient med skyddade personuppgifter")
  @MethodSource("rolesAccessToProtectedPerson")
  void shallReturnCountOfDraftsIncludingPatientIsProtectedPerson(UserDTO userDTO) {
    testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(type(), typeVersion()),
        defaultTestablilityCertificateRequest(type(), typeVersion()),
        customTestabilityCertificateRequest(type(), typeVersion())
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    final var response = api().getUnitStatistics(
        customUnitStatisticsRequest()
            .user(userDTO)
            .build()
    );

    final var unitStatistics = unitStatistics(response);
    final var statisticsOnUnit = unitStatistics.getOrDefault(ALFA_ALLERGIMOTTAGNINGEN_ID, null);
    assertEquals(3, statisticsOnUnit.getDraftCount());
  }

  @ParameterizedTest
  @DisplayName("Skall returnera antal ej hanterade ärenden som är skapade på patient med skyddade personuppgifter")
  @MethodSource("rolesNoAccessToProtectedPerson")
  void shallReturnCountOfUnhandledMessagesExcludingPatientIsProtectedPerson(UserDTO userDTO) {
    if (!canReceiveQuestions()) {
      return;
    }

    final var testCertificate1 = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(
            type(),
            typeVersion(),
            CertificateStatusTypeDTO.SIGNED
        )
    );
    final var testCertificate2 = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(
            type(),
            typeVersion(),
            CertificateStatusTypeDTO.SIGNED
        )
    );
    final var testCertificate3 = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion(), CertificateStatusTypeDTO.SIGNED)
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    sendCertificate(testCertificate1, ALFA_ALLERGIMOTTAGNINGEN_DTO);
    sendCertificate(testCertificate2, ALFA_ALLERGIMOTTAGNINGEN_DTO);
    sendCertificate(testCertificate3, ALFA_ALLERGIMOTTAGNINGEN_DTO);

    api().receiveMessage(
        incomingQuestionMessageBuilder()
            .id("testCertificate1")
            .certificateId(certificateId(testCertificate1))
            .build()
    );
    api().receiveMessage(
        incomingQuestionMessageBuilder()
            .id("testCertificate2")
            .certificateId(certificateId(testCertificate2))
            .build()
    );
    api().receiveMessage(
        incomingQuestionMessageBuilder()
            .id("testCertificate3")
            .certificateId(certificateId(testCertificate3))
            .personId(ANONYMA_REACT_ATTILA_DTO.getId())
            .build()
    );

    final var response = api().getUnitStatistics(
        customUnitStatisticsRequest()
            .user(userDTO)
            .build()
    );

    final var unitStatistics = unitStatistics(response);
    final var statisticsOnUnit = unitStatistics.getOrDefault(ALFA_ALLERGIMOTTAGNINGEN_ID, null);
    assertEquals(2, statisticsOnUnit.getUnhandledMessageCount());
  }

  @ParameterizedTest
  @DisplayName("Skall returnera antal ej hanterade ärenden som är skapade på patient med skyddade personuppgifter")
  @MethodSource("rolesAccessToProtectedPerson")
  void shallReturnCountOfUnhandledMessagesIncludingPatientIsProtectedPerson(UserDTO userDTO) {
    if (!canReceiveQuestions()) {
      return;
    }

    final var testCertificate1 = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(
            type(),
            typeVersion(),
            CertificateStatusTypeDTO.SIGNED
        )
    );
    final var testCertificate2 = testabilityApi().addCertificates(
        defaultTestablilityCertificateRequest(
            type(),
            typeVersion(),
            CertificateStatusTypeDTO.SIGNED
        )
    );
    final var testCertificate3 = testabilityApi().addCertificates(
        customTestabilityCertificateRequest(type(), typeVersion(), CertificateStatusTypeDTO.SIGNED)
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    sendCertificate(testCertificate1, ALFA_ALLERGIMOTTAGNINGEN_DTO);
    sendCertificate(testCertificate2, ALFA_ALLERGIMOTTAGNINGEN_DTO);
    sendCertificate(testCertificate3, ALFA_ALLERGIMOTTAGNINGEN_DTO);

    api().receiveMessage(
        incomingQuestionMessageBuilder()
            .id("testCertificate1")
            .certificateId(certificateId(testCertificate1))
            .build()
    );
    api().receiveMessage(
        incomingQuestionMessageBuilder()
            .id("testCertificate2")
            .certificateId(certificateId(testCertificate2))
            .build()
    );
    api().receiveMessage(
        incomingQuestionMessageBuilder()
            .id("testCertificate3")
            .certificateId(certificateId(testCertificate3))
            .personId(ANONYMA_REACT_ATTILA_DTO.getId())
            .build()
    );

    final var response = api().getUnitStatistics(
        customUnitStatisticsRequest()
            .user(userDTO)
            .build()
    );

    final var unitStatistics = unitStatistics(response);
    final var statisticsOnUnit = unitStatistics.getOrDefault(ALFA_ALLERGIMOTTAGNINGEN_ID, null);
    assertEquals(3, statisticsOnUnit.getUnhandledMessageCount());
  }
}
