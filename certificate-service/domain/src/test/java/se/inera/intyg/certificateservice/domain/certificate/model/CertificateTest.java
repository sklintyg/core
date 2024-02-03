package se.inera.intyg.certificateservice.domain.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.patient.model.Deceased;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.patient.model.PersonAddress;
import se.inera.intyg.certificateservice.domain.patient.model.PersonId;
import se.inera.intyg.certificateservice.domain.patient.model.PersonIdType;
import se.inera.intyg.certificateservice.domain.patient.model.ProtectedPerson;
import se.inera.intyg.certificateservice.domain.patient.model.TestIndicated;
import se.inera.intyg.certificateservice.domain.user.model.User;

@ExtendWith(MockitoExtension.class)
class CertificateTest {

  private static final String DEFAULT_VALUE = "defaultCertificateId";
  private static final String EXPECTED_VALUE = "defaultId";
  private static final LocalDateTime CREATED = LocalDateTime.now(ZoneId.systemDefault());
  private Certificate certificate;
  private ActionEvaluation.ActionEvaluationBuilder actionEvaluationBuilder;

  @BeforeEach
  void setUp() {
    certificate = Certificate.builder()
        .id(new CertificateId(DEFAULT_VALUE))
        .certificateModel(mock(CertificateModel.class))
        .created(CREATED)
        .certificateMetaData(
            CertificateMetaData.builder()
                .patient(
                    Patient.builder()
                        .id(
                            PersonId.builder()
                                .type(PersonIdType.PERSONAL_IDENTITY_NUMBER)
                                .id(EXPECTED_VALUE)
                                .build()
                        )
                        .name(
                            Name.builder()
                                .firstName(DEFAULT_VALUE)
                                .middleName(DEFAULT_VALUE)
                                .lastName(DEFAULT_VALUE)
                                .build()
                        )
                        .deceased(new Deceased(false))
                        .address(
                            PersonAddress.builder()
                                .city(DEFAULT_VALUE)
                                .street(DEFAULT_VALUE)
                                .zipCode(DEFAULT_VALUE)
                                .build()
                        )
                        .protectedPerson(new ProtectedPerson(false))
                        .testIndicated(new TestIndicated(false))
                        .build()
                )
                .issuer(
                    Staff.builder()
                        .hsaId(new HsaId(DEFAULT_VALUE))
                        .build()
                )
                .issuingUnit(
                    SubUnit.builder()
                        .hsaId(new HsaId(DEFAULT_VALUE))
                        .name(new UnitName(DEFAULT_VALUE))
                        .address(
                            UnitAddress.builder()
                                .address(DEFAULT_VALUE)
                                .zipCode(DEFAULT_VALUE)
                                .city(DEFAULT_VALUE)
                                .build()
                        )
                        .contactInfo(
                            UnitContactInfo.builder()
                                .phoneNumber(DEFAULT_VALUE)
                                .email(DEFAULT_VALUE)
                                .build()
                        )
                        .inactive(new Inactive(false))
                        .build()
                )
                .careUnit(
                    CareUnit.builder()
                        .hsaId(new HsaId(DEFAULT_VALUE))
                        .name(new UnitName(DEFAULT_VALUE))
                        .address(
                            UnitAddress.builder()
                                .address(DEFAULT_VALUE)
                                .zipCode(DEFAULT_VALUE)
                                .city(DEFAULT_VALUE)
                                .build()
                        )
                        .contactInfo(
                            UnitContactInfo.builder()
                                .phoneNumber(DEFAULT_VALUE)
                                .email(DEFAULT_VALUE)
                                .build()
                        )
                        .inactive(new Inactive(false))
                        .build()
                )
                .careProvider(
                    CareProvider.builder()
                        .hsaId(new HsaId(DEFAULT_VALUE))
                        .name(new UnitName(DEFAULT_VALUE))
                        .address(
                            UnitAddress.builder()
                                .address(DEFAULT_VALUE)
                                .zipCode(DEFAULT_VALUE)
                                .city(DEFAULT_VALUE)
                                .build()
                        )
                        .contactInfo(
                            UnitContactInfo.builder()
                                .phoneNumber(DEFAULT_VALUE)
                                .email(DEFAULT_VALUE)
                                .build()
                        )
                        .build()
                ).build()
        )
        .build();

    actionEvaluationBuilder = ActionEvaluation.builder()
        .patient(
            Patient.builder()
                .id(
                    PersonId.builder()
                        .type(PersonIdType.PERSONAL_IDENTITY_NUMBER)
                        .id(EXPECTED_VALUE)
                        .build()
                )
                .name(
                    Name.builder()
                        .firstName(DEFAULT_VALUE)
                        .middleName(DEFAULT_VALUE)
                        .lastName(DEFAULT_VALUE)
                        .build()
                )
                .deceased(new Deceased(false))
                .address(
                    PersonAddress.builder()
                        .city(DEFAULT_VALUE)
                        .street(DEFAULT_VALUE)
                        .zipCode(DEFAULT_VALUE)
                        .build()
                )
                .protectedPerson(new ProtectedPerson(false))
                .testIndicated(new TestIndicated(false))
                .build()
        )
        .user(
            User.builder()
                .hsaId(new HsaId(DEFAULT_VALUE))
                .name(
                    Name.builder()
                        .lastName(DEFAULT_VALUE)
                        .build()
                )
                .build()
        )
        .subUnit(
            SubUnit.builder()
                .hsaId(new HsaId(DEFAULT_VALUE))
                .name(new UnitName(DEFAULT_VALUE))
                .address(
                    UnitAddress.builder()
                        .address(DEFAULT_VALUE)
                        .zipCode(DEFAULT_VALUE)
                        .city(DEFAULT_VALUE)
                        .build()
                )
                .contactInfo(
                    UnitContactInfo.builder()
                        .phoneNumber(DEFAULT_VALUE)
                        .email(DEFAULT_VALUE)
                        .build()
                )
                .inactive(new Inactive(false))
                .build()
        )
        .careUnit(
            CareUnit.builder()
                .hsaId(new HsaId(DEFAULT_VALUE))
                .name(new UnitName(DEFAULT_VALUE))
                .address(
                    UnitAddress.builder()
                        .address(DEFAULT_VALUE)
                        .zipCode(DEFAULT_VALUE)
                        .city(DEFAULT_VALUE)
                        .build()
                )
                .contactInfo(
                    UnitContactInfo.builder()
                        .phoneNumber(DEFAULT_VALUE)
                        .email(DEFAULT_VALUE)
                        .build()
                )
                .inactive(new Inactive(false))
                .build()
        )
        .careProvider(
            CareProvider.builder()
                .hsaId(new HsaId(DEFAULT_VALUE))
                .name(new UnitName(DEFAULT_VALUE))
                .address(
                    UnitAddress.builder()
                        .address(DEFAULT_VALUE)
                        .zipCode(DEFAULT_VALUE)
                        .city(DEFAULT_VALUE)
                        .build()
                )
                .contactInfo(
                    UnitContactInfo.builder()
                        .phoneNumber(DEFAULT_VALUE)
                        .email(DEFAULT_VALUE)
                        .build()
                )
                .build()
        );
  }

  @Nested
  class UpdateMetaData {

    @Nested
    class UpdatePatient {

      @BeforeEach
      void setUp() {
        actionEvaluationBuilder
            .patient(
                Patient.builder()
                    .id(
                        PersonId.builder()
                            .id(EXPECTED_VALUE)
                            .type(PersonIdType.PERSONAL_IDENTITY_NUMBER)
                            .build()
                    )
                    .name(
                        Name.builder()
                            .firstName(EXPECTED_VALUE)
                            .middleName(EXPECTED_VALUE)
                            .lastName(EXPECTED_VALUE)
                            .build()
                    )
                    .deceased(new Deceased(true))
                    .address(
                        PersonAddress.builder()
                            .city(EXPECTED_VALUE)
                            .street(EXPECTED_VALUE)
                            .zipCode(EXPECTED_VALUE)
                            .build()
                    )
                    .protectedPerson(new ProtectedPerson(true))
                    .testIndicated(new TestIndicated(true))
                    .build()
            )
            .build();
      }

      @Test
      void shallUpdatePatientIdType() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(PersonIdType.PERSONAL_IDENTITY_NUMBER,
            certificate.certificateMetaData().getPatient().getId().getType());
      }

      @Test
      void shallUpdatePatientIdId() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getPatient().getId().getId());
      }

      @Test
      void shallUpdatePatientFirstName() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getPatient().getName().getFirstName());
      }

      @Test
      void shallUpdatePatientMiddleName() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getPatient().getName().getMiddleName());
      }

      @Test
      void shallUpdatePatientLastName() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getPatient().getName().getLastName());
      }

      @Test
      void shallUpdatePatientAddressCity() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getPatient().getAddress().getCity());
      }

      @Test
      void shallUpdatePatientAddressZipCode() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getPatient().getAddress().getZipCode());
      }

      @Test
      void shallUpdatePatientAddressStreet() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getPatient().getAddress().getStreet());
      }

      @Test
      void shallUpdatePatientProtectedPerson() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertTrue(certificate.certificateMetaData().getPatient().getProtectedPerson()
            .value());
      }

      @Test
      void shallUpdatePatientTestIndicated() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertTrue(certificate.certificateMetaData().getPatient().getTestIndicated()
            .value());
      }

      @Test
      void shallUpdatePatientDeceased() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertTrue(certificate.certificateMetaData().getPatient().getDeceased()
            .value());
      }

      @Test
      void shallNotUpdatePatientIfMissing() {
        final var patientBeforeUpdate = certificate.certificateMetaData().getPatient();

        final var actionEvaluation = actionEvaluationBuilder
            .patient(null)
            .build();

        certificate.updateMetadata(actionEvaluation);

        assertEquals(patientBeforeUpdate, certificate.certificateMetaData().getPatient());
      }
    }

    @Nested
    class UpdateIssuer {

      @BeforeEach
      void setUp() {
        actionEvaluationBuilder
            .user(
                User.builder()
                    .hsaId(new HsaId(EXPECTED_VALUE))
                    .name(
                        Name.builder()
                            .lastName(EXPECTED_VALUE)
                            .build()
                    )
                    .blocked(new Blocked(true))
                    .build()
            )
            .build();
      }

      @Test
      void shallUpdateIssuerHsaId() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getIssuer().getHsaId().id());
      }

      @Test
      void shallUpdateIssuerName() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getIssuer().getName().getLastName());
      }

      @Test
      void shallUpdateIssuerBlocked() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertTrue(certificate.certificateMetaData().getIssuer().getBlocked().value());
      }
    }

    @Nested
    class UpdateCareUnit {

      @BeforeEach
      void setUp() {
        actionEvaluationBuilder
            .careUnit(
                CareUnit.builder()
                    .hsaId(new HsaId(EXPECTED_VALUE))
                    .name(new UnitName(EXPECTED_VALUE))
                    .address(
                        UnitAddress.builder()
                            .address(EXPECTED_VALUE)
                            .zipCode(EXPECTED_VALUE)
                            .city(EXPECTED_VALUE)
                            .build()
                    )
                    .contactInfo(
                        UnitContactInfo.builder()
                            .email(EXPECTED_VALUE)
                            .phoneNumber(EXPECTED_VALUE)
                            .build()
                    )
                    .inactive(new Inactive(true))
                    .build()
            )
            .build();
      }

      @Test
      void shallUpdateCareUnitHsaId() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getCareUnit().getHsaId().id());
      }

      @Test
      void shallUpdateCareUnitName() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getCareUnit().getName().name());
      }

      @Test
      void shallUpdateCareUnitAddress() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getCareUnit().getAddress().getAddress());
      }

      @Test
      void shallUpdateCareUnitCity() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getCareUnit().getAddress().getCity());
      }

      @Test
      void shallUpdateCareUnitZipCode() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getCareUnit().getAddress().getZipCode());
      }

      @Test
      void shallUpdateCareUnitEmail() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getCareUnit().getContactInfo().getEmail());
      }

      @Test
      void shallUpdateCareUnitPhoneNumber() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getCareUnit().getContactInfo()
                .getPhoneNumber());
      }

      @Test
      void shallUpdateCareUnitInactive() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertTrue(certificate.certificateMetaData().getCareUnit().getInactive().value());
      }
    }

    @Nested
    class UpdateCareProvider {

      @BeforeEach
      void setUp() {
        actionEvaluationBuilder
            .careProvider(
                CareProvider.builder()
                    .hsaId(new HsaId(EXPECTED_VALUE))
                    .name(new UnitName(EXPECTED_VALUE))
                    .address(
                        UnitAddress.builder()
                            .address(EXPECTED_VALUE)
                            .zipCode(EXPECTED_VALUE)
                            .city(EXPECTED_VALUE)
                            .build()
                    )
                    .contactInfo(
                        UnitContactInfo.builder()
                            .email(EXPECTED_VALUE)
                            .phoneNumber(EXPECTED_VALUE)
                            .build()
                    )
                    .build()
            )
            .build();
      }

      @Test
      void shallUpdateCareUnitHsaId() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getCareProvider().getHsaId().id());
      }

      @Test
      void shallUpdateCareUnitName() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getCareProvider().getName().name());
      }

      @Test
      void shallUpdateCareUnitAddress() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getCareProvider().getAddress().getAddress());
      }

      @Test
      void shallUpdateCareUnitCity() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getCareProvider().getAddress().getCity());
      }

      @Test
      void shallUpdateCareUnitZipCode() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getCareProvider().getAddress().getZipCode());
      }

      @Test
      void shallUpdateCareUnitEmail() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getCareProvider().getContactInfo().getEmail());
      }

      @Test
      void shallUpdateCareUnitPhoneNumber() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getCareProvider().getContactInfo()
                .getPhoneNumber());
      }
    }

    @Nested
    class UpdateIssuingUnit {

      @BeforeEach
      void setUp() {
        actionEvaluationBuilder
            .subUnit(
                SubUnit.builder()
                    .hsaId(new HsaId(EXPECTED_VALUE))
                    .name(new UnitName(EXPECTED_VALUE))
                    .address(
                        UnitAddress.builder()
                            .address(EXPECTED_VALUE)
                            .zipCode(EXPECTED_VALUE)
                            .city(EXPECTED_VALUE)
                            .build()
                    )
                    .contactInfo(
                        UnitContactInfo.builder()
                            .email(EXPECTED_VALUE)
                            .phoneNumber(EXPECTED_VALUE)
                            .build()
                    )
                    .inactive(new Inactive(true))
                    .build()
            )
            .build();
      }

      @Test
      void shallUpdateCareUnitHsaId() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getIssuingUnit().getHsaId().id());
      }

      @Test
      void shallUpdateCareUnitName() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getIssuingUnit().getName().name());
      }

      @Test
      void shallUpdateCareUnitAddress() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getIssuingUnit().getAddress().getAddress());
      }

      @Test
      void shallUpdateCareUnitCity() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getIssuingUnit().getAddress().getCity());
      }

      @Test
      void shallUpdateCareUnitZipCode() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getIssuingUnit().getAddress().getZipCode());
      }

      @Test
      void shallUpdateCareUnitEmail() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getIssuingUnit().getContactInfo().getEmail());
      }

      @Test
      void shallUpdateCareUnitPhoneNumber() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertEquals(EXPECTED_VALUE,
            certificate.certificateMetaData().getIssuingUnit().getContactInfo()
                .getPhoneNumber());
      }

      @Test
      void shallUpdateCareUnitInactive() {
        certificate.updateMetadata(actionEvaluationBuilder.build());

        assertTrue(certificate.certificateMetaData().getIssuingUnit().getInactive().value());
      }
    }
  }

  @Nested
  class TestActions {

    @Test
    void shallReturnActionIfEvaluateTrue() {
      final var actionEvaluation = ActionEvaluation.builder().build();
      final var certificateAction = mock(CertificateAction.class);
      final var expectedActions = List.of(certificateAction);

      doReturn(expectedActions).when(certificate.certificateModel()).actions();

      doReturn(true).when(certificateAction).evaluate(Optional.of(certificate), actionEvaluation);

      final var actualActions = certificate.actions(actionEvaluation);

      assertEquals(expectedActions, actualActions);
    }

    @Test
    void shallNotReturnActionIfEvaluateFalse() {
      final var actionEvaluation = ActionEvaluation.builder().build();
      final var certificateAction = mock(CertificateAction.class);
      final var expectedActions = Collections.emptyList();

      doReturn(List.of(certificateAction)).when(certificate.certificateModel()).actions();

      doReturn(false).when(certificateAction).evaluate(Optional.of(certificate), actionEvaluation);

      final var actualActions = certificate.actions(actionEvaluation);

      assertEquals(expectedActions, actualActions);
    }
  }

  @Nested
  class TestAllowTo {

    @Test
    void shallReturnTrueIfExistsAndEvaluateTrue() {
      final var actionEvaluation = ActionEvaluation.builder().build();
      final var certificateAction = mock(CertificateAction.class);
      final var actions = List.of(certificateAction);

      doReturn(actions).when(certificate.certificateModel()).actions();

      doReturn(CertificateActionType.READ).when(certificateAction).getType();
      doReturn(true).when(certificateAction).evaluate(Optional.of(certificate), actionEvaluation);

      assertTrue(
          certificate.allowTo(CertificateActionType.READ, actionEvaluation),
          "Expected allowTo to return 'true'"
      );
    }

    @Test
    void shallReturnFalseIfExistsAndEvaluateFalse() {
      final var actionEvaluation = ActionEvaluation.builder().build();
      final var certificateAction = mock(CertificateAction.class);
      final var actions = List.of(certificateAction);

      doReturn(actions).when(certificate.certificateModel()).actions();

      doReturn(CertificateActionType.READ).when(certificateAction).getType();
      doReturn(false).when(certificateAction).evaluate(Optional.of(certificate), actionEvaluation);

      assertFalse(
          certificate.allowTo(CertificateActionType.READ, actionEvaluation),
          "Expected allowTo to return 'false'"
      );
    }

    @Test
    void shallReturnFalseIfNotExists() {
      final var actionEvaluation = ActionEvaluation.builder().build();
      final var certificateAction = mock(CertificateAction.class);
      final var actions = List.of(certificateAction);

      doReturn(actions).when(certificate.certificateModel()).actions();

      doReturn(CertificateActionType.CREATE).when(certificateAction).getType();

      assertFalse(
          certificate.allowTo(CertificateActionType.READ, actionEvaluation),
          "Expected allowTo to return 'false'"
      );
    }
  }
}
