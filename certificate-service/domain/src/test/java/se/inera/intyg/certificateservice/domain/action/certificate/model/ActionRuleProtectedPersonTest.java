package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.actionEvaluationBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_EMAIL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_PHONENUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_WORKPLACE_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ZIP_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7210CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ANONYMA_REACT_ATTILA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_HUDMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.ALVA_VARDADMINISTRATOR;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.unit.model.Inactive;
import se.inera.intyg.certificateservice.domain.unit.model.SubUnit;
import se.inera.intyg.certificateservice.domain.unit.model.UnitAddress;
import se.inera.intyg.certificateservice.domain.unit.model.UnitContactInfo;
import se.inera.intyg.certificateservice.domain.unit.model.UnitName;
import se.inera.intyg.certificateservice.domain.unit.model.WorkplaceCode;

class ActionRuleProtectedPersonTest {

  private ActionRuleProtectedPerson actionRuleProtectedPerson;

  @BeforeEach
  void setUp() {
    actionRuleProtectedPerson = new ActionRuleProtectedPerson(List.of(Role.DOCTOR));
  }

  @Nested
  class CertificateNotPresentTests {

    @Test
    void shallReturnTrueIfPatientIsProtectedAndUserIsWithinAllowedRoles() {
      final var actionEvaluation = actionEvaluationBuilder()
          .patient(ANONYMA_REACT_ATTILA)
          .build();

      assertTrue(
          actionRuleProtectedPerson.evaluate(Optional.empty(), Optional.of(actionEvaluation))
      );
    }

    @Test
    void shallReturnFalseIfPatientIsProtectedAndUserIsNotWithinAllowedRoles() {
      final var actionEvaluation = actionEvaluationBuilder()
          .patient(ANONYMA_REACT_ATTILA)
          .user(ALVA_VARDADMINISTRATOR)
          .build();

      assertFalse(
          actionRuleProtectedPerson.evaluate(Optional.empty(), Optional.of(actionEvaluation)));
    }
  }

  @Nested
  class CertificatePresentTests {

    @Test
    void shallReturnTrueIfPatientOnCertificateIsProtectedAndUserIsWithinAllowedRolesAndSameUnit() {
      final var actionEvaluation = actionEvaluationBuilder()
          .patient(ANONYMA_REACT_ATTILA)
          .build();

      final var certificate = fk7210CertificateBuilder()
          .certificateMetaData(
              CertificateMetaData.builder()
                  .issuer(AJLA_DOKTOR)
                  .patient(ANONYMA_REACT_ATTILA)
                  .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                  .careUnit(ALFA_MEDICINCENTRUM)
                  .careProvider(ALFA_REGIONEN)
                  .build()
          )
          .build();

      assertTrue(
          actionRuleProtectedPerson.evaluate(Optional.of(certificate),
              Optional.of(actionEvaluation))
      );
    }

    @Test
    void shallReturnFalseIfPatientOnCertificateIsProtectedAndUserIsNotWithinAllowedRoles() {
      final var actionEvaluation = actionEvaluationBuilder()
          .patient(ANONYMA_REACT_ATTILA)
          .user(ALVA_VARDADMINISTRATOR)
          .build();

      final var certificate = fk7210CertificateBuilder()
          .certificateMetaData(
              CertificateMetaData.builder()
                  .issuer(AJLA_DOKTOR)
                  .patient(ANONYMA_REACT_ATTILA)
                  .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                  .careUnit(ALFA_MEDICINCENTRUM)
                  .careProvider(ALFA_REGIONEN)
                  .build()
          )
          .build();

      assertFalse(
          actionRuleProtectedPerson.evaluate(Optional.of(certificate),
              Optional.of(actionEvaluation))
      );
    }

    @Test
    void shallReturnFalseIfPatientOnCertificateIsProtectedAndUserIsWithinAllowedRolesButOtherUnit() {
      final var actionEvaluation = actionEvaluationBuilder()
          .patient(ANONYMA_REACT_ATTILA)
          .subUnit(ALFA_HUDMOTTAGNINGEN)
          .build();

      final var certificate = fk7210CertificateBuilder()
          .certificateMetaData(
              CertificateMetaData.builder()
                  .issuer(AJLA_DOKTOR)
                  .patient(ANONYMA_REACT_ATTILA)
                  .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                  .careUnit(ALFA_MEDICINCENTRUM)
                  .careProvider(ALFA_REGIONEN)
                  .build()
          )
          .build();

      assertFalse(
          actionRuleProtectedPerson.evaluate(Optional.of(certificate),
              Optional.of(actionEvaluation))
      );
    }

    @Test
    void shallReturnTrueIfPatientOnCertificateIsProtectedAndUserIsWithinAllowedRolesButSameCareUnit() {
      final var actionEvaluation = actionEvaluationBuilder()
          .patient(ANONYMA_REACT_ATTILA)
          .subUnit(
              SubUnit.builder()
                  .hsaId(new HsaId(ALFA_MEDICINCENTRUM_ID))
                  .name(new UnitName(ALFA_MEDICINCENTRUM_NAME))
                  .address(
                      UnitAddress.builder()
                          .address(ALFA_MEDICINCENTRUM_ADDRESS)
                          .zipCode(ALFA_MEDICINCENTRUM_ZIP_CODE)
                          .city(ALFA_MEDICINCENTRUM_CITY)
                          .build()
                  )
                  .contactInfo(
                      UnitContactInfo.builder()
                          .email(ALFA_MEDICINCENTRUM_EMAIL)
                          .phoneNumber(ALFA_MEDICINCENTRUM_PHONENUMBER)
                          .build()
                  )
                  .workplaceCode(
                      new WorkplaceCode(ALFA_MEDICINCENTRUM_WORKPLACE_CODE)
                  )
                  .inactive(new Inactive(false))
                  .build()
          )
          .build();

      final var certificate = fk7210CertificateBuilder()
          .certificateMetaData(
              CertificateMetaData.builder()
                  .issuer(AJLA_DOKTOR)
                  .patient(ANONYMA_REACT_ATTILA)
                  .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                  .careUnit(ALFA_MEDICINCENTRUM)
                  .careProvider(ALFA_REGIONEN)
                  .build()
          )
          .build();

      assertTrue(
          actionRuleProtectedPerson.evaluate(Optional.of(certificate),
              Optional.of(actionEvaluation))
      );
    }

  }


  @Test
  void shallReturnErrorMessage() {
    assertEquals(
        "Du saknar behörighet för den begärda åtgärden."
            + " Det krävs särskilda rättigheter eller en specifik befattning"
            + " för att hantera patienter med skyddade personuppgifter.",
        actionRuleProtectedPerson.getReasonForPermissionDenied()
    );
  }
}