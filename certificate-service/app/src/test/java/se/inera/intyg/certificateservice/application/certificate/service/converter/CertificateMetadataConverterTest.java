package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonWebcertUnitDTO.alfaMedicincentrumDtoBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_MESSAGE_TYPES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.EXTERNAL_REF;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.EXTERNAL_REFERENCE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.RECIPIENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7210CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7472CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.fk7210certificateModelBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementSpecification.messageElementSpecificationBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.athenaReactAnderssonBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_DECEASED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_FULL_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_PROTECTED_PERSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_STREET;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_TEST_INDICATED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ZIP_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.ALF_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.ALVA_VARDADMINISTRATOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_FULLNAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_MIDDLE_NAME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateConfirmationModalDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateMessageTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRecipientDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationsDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateSummaryDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ReadyForSign;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.model.Revoked;
import se.inera.intyg.certificateservice.domain.certificate.model.Sent;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateConfirmationModal;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateConfirmationModalProvider;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateSummary;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateSummaryProvider;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateTypeName;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.common.model.PersonIdType;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;
import se.inera.intyg.certificateservice.domain.message.model.Forwarded;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;
import se.inera.intyg.certificateservice.domain.user.model.ResponsibleIssuer;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDate;

@ExtendWith(MockitoExtension.class)
class CertificateMetadataConverterTest {

  private static final String TYPE = "type";
  private static final String VERSION = "2.0";
  private static final CertificateTypeName TYPE_NAME = new CertificateTypeName("typeName");
  private static final String MODEL_NAME = "modelName";
  private static final String MODEL_DESCRIPTION = "modelDescription";
  private static final LocalDateTime CREATED = LocalDateTime.now(ZoneId.systemDefault());
  private static final LocalDateTime SIGNED = LocalDateTime.now(ZoneId.systemDefault());
  private static final LocalDateTime MODIFIED = LocalDateTime.now(ZoneId.systemDefault());
  private static final LocalDate DATE = LocalDate.now().plusDays(1);
  private static final String Q_1 = "q1";
  private static final String ID = "54";
  private static final String EXPRESSION = "$beraknatfodelsedatum";
  private static final String NAME = "Beräknat fodelsedatum";
  private static final Revision REVISION = new Revision(3L);
  private static final Sent SENT = Sent.builder()
      .recipient(RECIPIENT)
      .sentAt(LocalDateTime.now(ZoneId.systemDefault()))
      .sentBy(AJLA_DOKTOR)
      .build();
  private static final String CERTIFICATE_SUMMARY_LABEL = "SummaryLabel";
  private static final String CERTIFICATE_SUMMARY_VALUE = "SummaryValue";
  private static final Forwarded FORWARDED = new Forwarded(true);
  private static final String RESPONSIBLE_ISSUER = "ResponsibleIssuer";
  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder().build();
  private static final CertificateConfirmationModalDTO CONVERTED_MODAL = CertificateConfirmationModalDTO.builder()
      .build();
  private static final LocalDateTime ACTIVE_FROM = LocalDateTime.now().minusDays(1);

  @Mock
  private CertificateMessageTypeConverter certificateMessageTypeConverter;
  @Mock
  private CertificateSummaryProvider certificateSummaryProvider;
  @Mock
  private CertificateConfirmationModalProvider certificateConfirmationModalProvider;
  @Mock
  private CertificateUnitConverter certificateUnitConverter;
  @Mock
  private CertificateConfirmationModalConverter certificateConfirmationModalConverter;
  @InjectMocks
  private CertificateMetadataConverter certificateMetadataConverter;

  private static final String CERTIFICATE_ID = "certificateId";
  private Certificate certificate;
  private MedicalCertificate.MedicalCertificateBuilder certificateBuilder;

  @Nested
  class TestCertificateMetaDataConverter {

    @BeforeEach
    void setUp() {
      final var model = CertificateModel.builder()
          .id(
              CertificateModelId.builder()
                  .type(new CertificateType(TYPE))
                  .version(new CertificateVersion(VERSION))
                  .build()
          )
          .activeFrom(ACTIVE_FROM)
          .typeName(TYPE_NAME)
          .name(MODEL_NAME)
          .detailedDescription(MODEL_DESCRIPTION)
          .recipient(RECIPIENT)
          .messageTypes(CERTIFICATE_MESSAGE_TYPES)
          .elementSpecifications(
              List.of(
                  ElementSpecification.builder()
                      .id(new ElementId(Q_1))
                      .configuration(
                          ElementConfigurationCategory.builder()
                              .name(NAME)
                              .build()
                      )
                      .children(
                          List.of(
                              ElementSpecification.builder()
                                  .id(new ElementId(ID))
                                  .configuration(
                                      ElementConfigurationDate.builder()
                                          .id(new FieldId(ID))
                                          .name(NAME)
                                          .min(Period.ofDays(0))
                                          .max(Period.ofYears(1))
                                          .build()
                                  )
                                  .rules(
                                      List.of(
                                          ElementRuleExpression.builder()
                                              .id(new ElementId(ID))
                                              .type(ElementRuleType.MANDATORY)
                                              .expression(
                                                  new RuleExpression(EXPRESSION))
                                              .build()
                                      )
                                  )
                                  .validations(
                                      List.of(
                                          ElementValidationDate.builder()
                                              .mandatory(true)
                                              .min(Period.ofDays(0))
                                              .max(Period.ofYears(1))
                                              .build()
                                      )
                                  )
                                  .build()
                          )
                      )
                      .build()
              )
          )
          .summaryProvider(certificateSummaryProvider)
          .confirmationModalProvider(certificateConfirmationModalProvider)
          .availableForCitizen(true)
          .build();

      final var certificateModel = model.withVersions(List.of(model));

      certificateBuilder = MedicalCertificate.builder()
          .id(new CertificateId(CERTIFICATE_ID))
          .created(CREATED)
          .revision(REVISION)
          .status(Status.DRAFT)
          .sent(SENT)
          .signed(SIGNED)
          .modified(MODIFIED)
          .forwarded(FORWARDED)
          .revoked(
              Revoked.builder()
                  .revokedAt(LocalDateTime.now())
                  .revokedBy(ALVA_VARDADMINISTRATOR)
                  .build()
          )
          .readyForSign(
              ReadyForSign.builder()
                  .readyForSignAt(LocalDateTime.now())
                  .readyForSignBy(Staff.builder().build())
                  .build()
          )
          .certificateModel(certificateModel)
          .certificateMetaData(
              CertificateMetaData.builder()
                  .patient(
                      athenaReactAnderssonBuilder()
                          .id(
                              PersonId.builder()
                                  .id(ATHENA_REACT_ANDERSSON_ID_WITHOUT_DASH)
                                  .type(PersonIdType.PERSONAL_IDENTITY_NUMBER)
                                  .build()
                          )
                          .build()
                  )
                  .creator(ALF_DOKTOR)
                  .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                  .careUnit(ALFA_MEDICINCENTRUM)
                  .careProvider(ALFA_REGIONEN)
                  .issuer(AJLA_DOKTOR)
                  .responsibleIssuer(new ResponsibleIssuer(RESPONSIBLE_ISSUER))
                  .build()
          )
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId(Q_1))
                      .value(null)
                      .build(),
                  ElementData.builder()
                      .id(new ElementId(ID))
                      .value(
                          ElementValueDate.builder()
                              .date(DATE)
                              .build()
                      )
                      .build()
              )
          )
          .externalReference(
              EXTERNAL_REFERENCE
          );
      certificate = certificateBuilder.build();

      final var certificateSummary = CertificateSummary.builder()
          .label(CERTIFICATE_SUMMARY_LABEL)
          .value(CERTIFICATE_SUMMARY_VALUE)
          .build();
      doReturn(certificateSummary).when(certificateSummaryProvider)
          .summaryOf(any(MedicalCertificate.class));

      final var confirmationModal = CertificateConfirmationModal.builder().build();
      doReturn(confirmationModal).when(certificateConfirmationModalProvider)
          .of(any(MedicalCertificate.class), any(ActionEvaluation.class));
      when(certificateConfirmationModalConverter.convert(confirmationModal))
          .thenReturn(CONVERTED_MODAL);
    }

    @Test
    void shallIncludeConfirmationModal() {
      assertEquals(CONVERTED_MODAL,
          certificateMetadataConverter.convert(certificate, ACTION_EVALUATION)
              .getConfirmationModal()
      );
    }

    @Test
    void shallIncludeRevoked() {
      assertEquals(certificate.revoked().revokedAt(),
          certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getRevokedAt()
      );
    }

    @Test
    void shallIncludeRevokedBy() {
      final var response = certificateMetadataConverter.convert(certificate, ACTION_EVALUATION);
      assertAll(
          () -> assertEquals(certificate.revoked().revokedBy().hsaId().id(),
              response.getRevokedBy().getPersonId()
          ),
          () -> assertEquals(certificate.revoked().revokedBy().name().fullName(),
              response.getRevokedBy().getFullName()
          ),
          () -> assertEquals(certificate.revoked().revokedBy().name().firstName(),
              response.getRevokedBy().getFirstName()
          ),
          () -> assertEquals(certificate.revoked().revokedBy().name().middleName(),
              response.getRevokedBy().getMiddleName()
          ),
          () -> assertEquals(certificate.revoked().revokedBy().name().lastName(),
              response.getRevokedBy().getLastName()
          )
      );
    }

    @Test
    void shallIncludeCreatedBy() {
      final var response = certificateMetadataConverter.convert(certificate, ACTION_EVALUATION);
      assertAll(
          () -> assertEquals(certificate.certificateMetaData().creator().hsaId().id(),
              response.getCreatedBy().getPersonId()
          ),
          () -> assertEquals(certificate.certificateMetaData().creator().name().fullName(),
              response.getCreatedBy().getFullName()
          ),
          () -> assertEquals(certificate.certificateMetaData().creator().name().firstName(),
              response.getCreatedBy().getFirstName()
          ),
          () -> assertEquals(certificate.certificateMetaData().creator().name().middleName(),
              response.getCreatedBy().getMiddleName()
          ),
          () -> assertEquals(certificate.certificateMetaData().creator().name().lastName(),
              response.getCreatedBy().getLastName()
          )
      );
    }

    @Test
    void shallIncludeCertificateId() {
      assertEquals(CERTIFICATE_ID,
          certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getId()
      );
    }

    @Test
    void shallIncludeCertificateType() {
      assertEquals(TYPE,
          certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getType()
      );
    }

    @Test
    void shallIncludeCertificateTypeName() {
      assertEquals(TYPE_NAME.name(),
          certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getTypeName()
      );
    }

    @Test
    void shallIncludeCertificateTypeVersion() {
      assertEquals(VERSION,
          certificateMetadataConverter.convert(certificate, ACTION_EVALUATION)
              .getTypeVersion()
      );
    }

    @Test
    void shallIncludeCertificateName() {
      assertEquals(MODEL_NAME,
          certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getName()
      );
    }

    @Test
    void shallIncludeCertificateTypeDescription() {
      assertEquals(MODEL_DESCRIPTION,
          certificateMetadataConverter.convert(certificate, ACTION_EVALUATION)
              .getDescription()
      );
    }

    @Test
    void shallIncludeCreated() {
      assertEquals(CREATED,
          certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getCreated()
      );
    }

    @Test
    void shallIncludeVersion() {
      assertEquals(REVISION.value(),
          certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getVersion()
      );
    }

    @Test
    void shallIncludeCertificateSummary() {
      final var expectedSummary = CertificateSummaryDTO.builder()
          .label(CERTIFICATE_SUMMARY_LABEL)
          .value(CERTIFICATE_SUMMARY_VALUE)
          .build();

      assertEquals(expectedSummary,
          certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getSummary()
      );
    }

    @Test
    void shallIncludeResponsibleIssuer() {
      assertEquals(RESPONSIBLE_ISSUER,
          certificateMetadataConverter.convert(certificate, ACTION_EVALUATION)
              .getResponsibleHospName()
      );
    }

    @Test
    void shallIncludeTestCertificate() {
      assertEquals(ATHENA_REACT_ANDERSSON_TEST_INDICATED.value(),
          certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).isTestCertificate()
      );
    }

    @Test
    void shallIncludeIsLatestMajorVersion() {
      assertTrue(certificateMetadataConverter.convert(certificate, ACTION_EVALUATION)
          .isLatestMajorVersion()
      );
    }

    @Test
    void shallIncludeIsInactiveCertificateType() {
      assertFalse(certificateMetadataConverter.convert(certificate, ACTION_EVALUATION)
          .isInactiveCertificateType()
      );
    }

    @Nested
    class PatientConvert {

      @Test
      void shallIncludeId() {
        final var expectedId = PersonIdDTO.builder()
            .id(ATHENA_REACT_ANDERSSON_ID)
            .type(PersonIdType.PERSONAL_IDENTITY_NUMBER.name())
            .build();

        assertEquals(expectedId,
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getPatient()
                .getPersonId()
        );
      }

      @Test
      void shallIncludeFirstName() {
        assertEquals(ATHENA_REACT_ANDERSSON_FIRST_NAME,
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getPatient()
                .getFirstName()
        );
      }

      @Test
      void shallIncludeMiddleName() {
        assertEquals(ATHENA_REACT_ANDERSSON_MIDDLE_NAME,
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getPatient()
                .getMiddleName()
        );
      }

      @Test
      void shallIncludeLastName() {
        assertEquals(ATHENA_REACT_ANDERSSON_LAST_NAME,
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getPatient()
                .getLastName()
        );
      }

      @Test
      void shallIncludeFullName() {
        assertEquals(ATHENA_REACT_ANDERSSON_FULL_NAME,
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getPatient()
                .getFullName()
        );
      }

      @Test
      void shallIncludeStreet() {
        assertEquals(ATHENA_REACT_ANDERSSON_STREET,
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getPatient()
                .getStreet()
        );
      }

      @Test
      void shallIncludeCity() {
        assertEquals(ATHENA_REACT_ANDERSSON_CITY,
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getPatient()
                .getCity()
        );
      }

      @Test
      void shallIncludeZipCode() {
        assertEquals(ATHENA_REACT_ANDERSSON_ZIP_CODE,
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getPatient()
                .getZipCode()
        );
      }

      @Test
      void shallIncludeDeceased() {
        assertEquals(ATHENA_REACT_ANDERSSON_DECEASED.value(),
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getPatient()
                .getDeceased()
        );
      }

      @Test
      void shallIncludeTestIndicated() {
        assertEquals(ATHENA_REACT_ANDERSSON_TEST_INDICATED.value(),
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getPatient()
                .getTestIndicated()
        );
      }

      @Test
      void shallIncludeProtectedPerson() {
        assertEquals(ATHENA_REACT_ANDERSSON_PROTECTED_PERSON.value(),
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getPatient()
                .getProtectedPerson()
        );
      }

      @Test
      void shallIncludeValidForSignTrueIfDraftAndValid() {
        assertTrue(
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION)
                .isValidForSign()
        );
      }

      @Test
      void shallIncludeValidForSignFalseIfDraftAndInvalid() {
        final var invalidCertificate = certificateBuilder
            .elementData(Collections.emptyList())
            .build();
        assertFalse(
            certificateMetadataConverter.convert(invalidCertificate, ACTION_EVALUATION)
                .isValidForSign()
        );
      }

      @Test
      void shallIncludeValidForSignFalseIfNotDraft() {
        final var invalidCertificate = certificateBuilder
            .status(Status.DELETED_DRAFT)
            .build();
        assertFalse(
            certificateMetadataConverter.convert(invalidCertificate, ACTION_EVALUATION)
                .isValidForSign()
        );
      }

      @Test
      void shallIncludeSigned() {
        assertEquals(SIGNED,
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getSigned()
        );
      }

      @Test
      void shallIncludeModified() {
        assertEquals(MODIFIED,
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getModified()
        );
      }
    }

    @Nested
    class UnitConvert {

      @Test
      void shallIncludeUnit() {
        final var expectedUnit = alfaMedicincentrumDtoBuilder().build();
        doReturn(expectedUnit).when(certificateUnitConverter).convert(
            certificate.certificateMetaData().issuingUnit(),
            Optional.empty()
        );
        assertEquals(expectedUnit,
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getUnit()
        );
      }
    }

    @Nested
    class CareUnitConvert {

      @Test
      void shallIncludeId() {
        assertEquals(ALFA_MEDICINCENTRUM_ID,
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getCareUnit()
                .getUnitId()
        );
      }

      @Test
      void shallIncludeName() {
        assertEquals(ALFA_MEDICINCENTRUM_NAME,
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getCareUnit()
                .getUnitName()
        );
      }

      @Test
      void shallIncludeAvailableToCitizen() {
        assertTrue(certificateMetadataConverter.convert(certificate, ACTION_EVALUATION)
            .isAvailableForCitizen());
      }
    }

    @Nested
    class CareProviderConvert {

      @Test
      void shallIncludeId() {
        assertEquals(ALFA_REGIONEN_ID,
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION)
                .getCareProvider().getUnitId()
        );
      }

      @Test
      void shallIncludeName() {
        assertEquals(ALFA_REGIONEN_NAME,
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION)
                .getCareProvider().getUnitName()
        );
      }
    }

    @Nested
    class IssuedByConvert {

      @Test
      void shallIncludeId() {
        assertEquals(AJLA_DOCTOR_HSA_ID,
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getIssuedBy()
                .getPersonId()
        );
      }

      @Test
      void shallIncludeFirstName() {
        assertEquals(AJLA_DOCTOR_FIRST_NAME,
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getIssuedBy()
                .getFirstName()
        );
      }

      @Test
      void shallIncludeLastMiddle() {
        assertEquals(AJLA_DOCTOR_MIDDLE_NAME,
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getIssuedBy()
                .getMiddleName()
        );
      }

      @Test
      void shallIncludeLastName() {
        assertEquals(AJLA_DOCTOR_LAST_NAME,
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getIssuedBy()
                .getLastName()
        );
      }

      @Test
      void shallIncludeFullName() {
        assertEquals(AJLA_DOCTOR_FULLNAME,
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getIssuedBy()
                .getFullName()
        );
      }
    }

    @Test
    void shallIncludeExternalSetExternalReference() {
      assertEquals(EXTERNAL_REF,
          certificateMetadataConverter.convert(certificate, ACTION_EVALUATION)
              .getExternalReference()
      );
    }


    @Nested
    class StatusConvert {

      @Test
      void shallConvertDraftToUnsigned() {
        assertEquals(
            CertificateStatusTypeDTO.UNSIGNED,
            certificateMetadataConverter.convert(
                    certificate, ACTION_EVALUATION
                )
                .getStatus()
        );
      }

      @Test
      void shallConvertDeletedDraftToUnsigned() {
        assertEquals(
            CertificateStatusTypeDTO.UNSIGNED,
            certificateMetadataConverter.convert(
                    certificateBuilder.status(Status.DELETED_DRAFT).build(), ACTION_EVALUATION)
                .getStatus()
        );
      }

      @Test
      void shallConvertSignedToSigned() {
        assertEquals(
            CertificateStatusTypeDTO.SIGNED,
            certificateMetadataConverter.convert(certificateBuilder.status(Status.SIGNED).build(),
                    ACTION_EVALUATION)
                .getStatus()
        );
      }

      @Test
      void shallConvertRevokedToRevoked() {
        assertEquals(
            CertificateStatusTypeDTO.REVOKED,
            certificateMetadataConverter.convert(
                certificateBuilder.status(Status.REVOKED).build(), ACTION_EVALUATION).getStatus()
        );
      }
    }

    @Nested
    class TestCertificateRecipient {

      @Test
      void shallSetSentTrueIfSentNotNull() {
        assertTrue(
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).isSent()
        );
      }

      @Test
      void shallSetSentFalseIfSentNull() {
        final var certificate = certificateBuilder.sent(null).build();
        assertFalse(
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).isSent()
        );
      }

      @Test
      void shallIncludeSentToIfSentNotNull() {
        assertEquals(RECIPIENT.name(),
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getSentTo()
        );
      }

      @Test
      void shallExcludeSentToIfSentNotNull() {
        final var certificate = certificateBuilder.sent(null).build();

        assertNull(
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getSentTo()
        );
      }

      @Test
      void shallIncludeRecipientWithSentDateTimeIfSentNotNull() {
        final var expectedCertificateRecipient = CertificateRecipientDTO.builder()
            .id(RECIPIENT.id().id())
            .name(RECIPIENT.name())
            .sent(SENT.sentAt())
            .build();
        assertEquals(expectedCertificateRecipient,
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getRecipient()
        );
      }

      @Test
      void shallIncludeRecipientWithoutSentDateIfSentIsNull() {
        final var expectedCertificateRecipient = CertificateRecipientDTO.builder()
            .id(RECIPIENT.id().id())
            .name(RECIPIENT.name())
            .build();

        final var certificate = certificateBuilder.sent(null).build();

        assertEquals(expectedCertificateRecipient,
            certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getRecipient()
        );
      }
    }

    @Nested
    class TestCertificateRelation {

      private Relation.RelationBuilder relationBuilder;
      private Relation relation;

      @BeforeEach
      void setUp() {
        final var certificate = fk7472CertificateBuilder()
            .id(new CertificateId(CERTIFICATE_ID))
            .status(Status.DRAFT)
            .build();

        relationBuilder = Relation.builder()
            .certificate(certificate)
            .created(LocalDateTime.now(ZoneId.systemDefault()))
            .type(RelationType.REPLACE);

        relation = relationBuilder.build();
      }

      @Test
      void shallIncludeParentRelationIfReplacingCertificate() {
        final var expectedValue = CertificateRelationsDTO.builder()
            .parent(
                CertificateRelationDTO.builder()
                    .certificateId(CERTIFICATE_ID)
                    .type(CertificateRelationTypeDTO.REPLACED)
                    .created(relation.created())
                    .status(CertificateStatusTypeDTO.UNSIGNED)
                    .build()
            )
            .children(Collections.emptyList())
            .build();

        final var replacedCertificate = certificateBuilder
            .parent(relation)
            .build();

        assertEquals(expectedValue,
            certificateMetadataConverter.convert(replacedCertificate, ACTION_EVALUATION)
                .getRelations()
        );
      }

      @Test
      void shallIncludeChildRelationIfReplaced() {
        final var expectedValue = CertificateRelationsDTO.builder()
            .children(
                List.of(
                    CertificateRelationDTO.builder()
                        .certificateId(CERTIFICATE_ID)
                        .type(CertificateRelationTypeDTO.REPLACED)
                        .created(relation.created())
                        .status(CertificateStatusTypeDTO.UNSIGNED)
                        .build()
                )
            )
            .build();

        final var replacedCertificate = certificateBuilder
            .children(List.of(relation))
            .build();

        assertEquals(expectedValue,
            certificateMetadataConverter.convert(replacedCertificate, ACTION_EVALUATION)
                .getRelations()
        );
      }

      @Test
      void shallNotIncludeChildRelationIfReplacedIsRevoked() {
        final var expectedValue = CertificateRelationsDTO.builder()
            .children(
                Collections.emptyList()
            )
            .build();

        final var replacedCertificate = certificateBuilder
            .children(
                List.of(
                    relationBuilder
                        .certificate(
                            fk7472CertificateBuilder()
                                .id(new CertificateId(CERTIFICATE_ID))
                                .status(Status.REVOKED)
                                .build()
                        )
                        .build()
                )
            )
            .build();

        assertEquals(expectedValue,
            certificateMetadataConverter.convert(replacedCertificate, ACTION_EVALUATION)
                .getRelations()
        );
      }

      @Test
      void shallNotIncludeChildRelationThatIsNull() {
        final var expectedValue = CertificateRelationsDTO.builder()
            .children(
                Collections.emptyList()
            )
            .build();

        final var replacedCertificate = certificateBuilder
            .children(
                Collections.singletonList(null)
            )
            .build();

        assertEquals(expectedValue,
            certificateMetadataConverter.convert(replacedCertificate, ACTION_EVALUATION)
                .getRelations()
        );
      }
    }

    @Test
    void shallIncludeMessageTypes() {
      final var certificateMessageTypeDTO = CertificateMessageTypeDTO.builder().build();
      final var expectedMessageTypes = List.of(certificateMessageTypeDTO);
      doReturn(certificateMessageTypeDTO).when(certificateMessageTypeConverter)
          .convert(CERTIFICATE_MESSAGE_TYPES.getFirst());

      assertEquals(expectedMessageTypes,
          certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getMessageTypes()
      );
    }

    @Test
    void shallIncludeForwarded() {
      assertEquals(FORWARDED.value(),
          certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).isForwarded()
      );
    }

    @Test
    void shallIncludeReadyForSign() {
      assertEquals(certificate.readyForSign().readyForSignAt(),
          certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getReadyForSign()
      );
    }
  }

  @Nested
  class TestCertificateMetaDataConverterRecipientWhenLogicalAddressMissing {

    @Test
    void shallNotReturnRecipientIfLogicalAddressIsNull() {

      final var recipientWithoutLogicalAddress = new Recipient(
          new RecipientId("RecipientId"),
          "Name",
          null,
          "test/logo.png",
          "General Name");

      final var certificate = fk7210CertificateBuilder()
          .certificateModel(
              fk7210certificateModelBuilder()
                  .elementSpecifications(List.of(messageElementSpecificationBuilder().build()))
                  .recipient(recipientWithoutLogicalAddress)
                  .build()
          )
          .build();

      assertNull(
          certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getRecipient());
    }

    @Test
    void shallNotReturnRecipientIfLogicalAddressIsEmpty() {

      final var recipientWithoutLogicalAddress = new Recipient(
          new RecipientId("RecipientId"),
          "Name",
          "",
          "test/logo.png",
          "General Name");

      final var certificate = fk7210CertificateBuilder()
          .certificateModel(
              fk7210certificateModelBuilder()
                  .elementSpecifications(List.of(messageElementSpecificationBuilder().build()))
                  .recipient(recipientWithoutLogicalAddress)
                  .build()
          )
          .build();

      assertNull(
          certificateMetadataConverter.convert(certificate, ACTION_EVALUATION).getRecipient());
    }

  }
}