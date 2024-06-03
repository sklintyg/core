package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonWebcertUnitDTO.alfaMedicincentrumDtoBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.EXTERNAL_REF;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.EXTERNAL_REFERENCE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.RECIPIENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7472CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_DECEASED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_FULL_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_PROTECTED_PERSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_STREET;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_TEST_INDICATED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ZIP_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
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
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRecipientDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateRelationsDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateSummaryDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.model.Sent;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateSummary;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateSummaryProvider;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.common.model.PersonIdType;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDate;

@ExtendWith(MockitoExtension.class)
class CertificateMetadataConverterTest {

  private static final String TYPE = "type";
  private static final String VERSION = "version";
  private static final String TYPE_NAME = "typeName";
  private static final String TYPE_DESCRIPTION = "typeDescription";
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
  @Mock
  private CertificateSummaryProvider certificateSummaryProvider;
  @Mock
  private CertificateUnitConverter certificateUnitConverter;
  @InjectMocks
  private CertificateMetadataConverter certificateMetadataConverter;
  private static final String CERTIFICATE_ID = "certificateId";
  private Certificate certificate;
  private Certificate.CertificateBuilder certificateBuilder;

  @BeforeEach
  void setUp() {
    certificateBuilder = Certificate.builder()
        .id(new CertificateId(CERTIFICATE_ID))
        .created(CREATED)
        .revision(REVISION)
        .status(Status.DRAFT)
        .sent(SENT)
        .signed(SIGNED)
        .modified(MODIFIED)
        .certificateModel(
            CertificateModel.builder()
                .id(
                    CertificateModelId.builder()
                        .type(new CertificateType(TYPE))
                        .version(new CertificateVersion(VERSION))
                        .build()
                )
                .name(TYPE_NAME)
                .detailedDescription(TYPE_DESCRIPTION)
                .recipient(RECIPIENT)
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
                .build()
        )
        .certificateMetaData(
            CertificateMetaData.builder()
                .patient(ATHENA_REACT_ANDERSSON)
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .issuer(AJLA_DOKTOR)
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
        .summaryOf(any(Certificate.class));
  }


  @Test
  void shallIncludeCertificateId() {
    assertEquals(CERTIFICATE_ID,
        certificateMetadataConverter.convert(certificate).getId()
    );
  }

  @Test
  void shallIncludeCertificateType() {
    assertEquals(TYPE,
        certificateMetadataConverter.convert(certificate).getType()
    );
  }

  @Test
  void shallIncludeCertificateTypeName() {
    assertEquals(TYPE,
        certificateMetadataConverter.convert(certificate).getTypeName()
    );
  }

  @Test
  void shallIncludeCertificateTypeVersion() {
    assertEquals(VERSION,
        certificateMetadataConverter.convert(certificate)
            .getTypeVersion()
    );
  }

  @Test
  void shallIncludeCertificateName() {
    assertEquals(TYPE_NAME,
        certificateMetadataConverter.convert(certificate).getName()
    );
  }

  @Test
  void shallIncludeCertificateTypeDescription() {
    assertEquals(TYPE_DESCRIPTION,
        certificateMetadataConverter.convert(certificate)
            .getDescription()
    );
  }

  @Test
  void shallIncludeCreated() {
    assertEquals(CREATED,
        certificateMetadataConverter.convert(certificate).getCreated()
    );
  }

  @Test
  void shallIncludeVersion() {
    assertEquals(REVISION.value(),
        certificateMetadataConverter.convert(certificate).getVersion()
    );
  }

  @Test
  void shallIncludeCertificateSummary() {
    final var expectedSummary = CertificateSummaryDTO.builder()
        .label(CERTIFICATE_SUMMARY_LABEL)
        .value(CERTIFICATE_SUMMARY_VALUE)
        .build();

    assertEquals(expectedSummary,
        certificateMetadataConverter.convert(certificate).getSummary()
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
          certificateMetadataConverter.convert(certificate).getPatient()
              .getPersonId()
      );
    }

    @Test
    void shallIncludeFirstName() {
      assertEquals(ATHENA_REACT_ANDERSSON_FIRST_NAME,
          certificateMetadataConverter.convert(certificate).getPatient()
              .getFirstName()
      );
    }

    @Test
    void shallIncludeMiddleName() {
      assertEquals(ATHENA_REACT_ANDERSSON_MIDDLE_NAME,
          certificateMetadataConverter.convert(certificate).getPatient()
              .getMiddleName()
      );
    }

    @Test
    void shallIncludeLastName() {
      assertEquals(ATHENA_REACT_ANDERSSON_LAST_NAME,
          certificateMetadataConverter.convert(certificate).getPatient()
              .getLastName()
      );
    }

    @Test
    void shallIncludeFullName() {
      assertEquals(ATHENA_REACT_ANDERSSON_FULL_NAME,
          certificateMetadataConverter.convert(certificate).getPatient()
              .getFullName()
      );
    }

    @Test
    void shallIncludeStreet() {
      assertEquals(ATHENA_REACT_ANDERSSON_STREET,
          certificateMetadataConverter.convert(certificate).getPatient()
              .getStreet()
      );
    }

    @Test
    void shallIncludeCity() {
      assertEquals(ATHENA_REACT_ANDERSSON_CITY,
          certificateMetadataConverter.convert(certificate).getPatient()
              .getCity()
      );
    }

    @Test
    void shallIncludeZipCode() {
      assertEquals(ATHENA_REACT_ANDERSSON_ZIP_CODE,
          certificateMetadataConverter.convert(certificate).getPatient()
              .getZipCode()
      );
    }

    @Test
    void shallIncludeDeceased() {
      assertEquals(ATHENA_REACT_ANDERSSON_DECEASED.value(),
          certificateMetadataConverter.convert(certificate).getPatient()
              .getDeceased()
      );
    }

    @Test
    void shallIncludeTestIndicated() {
      assertEquals(ATHENA_REACT_ANDERSSON_TEST_INDICATED.value(),
          certificateMetadataConverter.convert(certificate).getPatient()
              .getTestIndicated()
      );
    }

    @Test
    void shallIncludeProtectedPerson() {
      assertEquals(ATHENA_REACT_ANDERSSON_PROTECTED_PERSON.value(),
          certificateMetadataConverter.convert(certificate).getPatient()
              .getProtectedPerson()
      );
    }

    @Test
    void shallIncludeValidForSignTrueIfDraftAndValid() {
      assertTrue(
          certificateMetadataConverter.convert(certificate)
              .isValidForSign()
      );
    }

    @Test
    void shallIncludeValidForSignFalseIfDraftAndInvalid() {
      final var invalidCertificate = certificateBuilder
          .elementData(Collections.emptyList())
          .build();
      assertFalse(
          certificateMetadataConverter.convert(invalidCertificate)
              .isValidForSign()
      );
    }

    @Test
    void shallIncludeValidForSignFalseIfNotDraft() {
      final var invalidCertificate = certificateBuilder
          .status(Status.DELETED_DRAFT)
          .build();
      assertFalse(
          certificateMetadataConverter.convert(invalidCertificate)
              .isValidForSign()
      );
    }

    @Test
    void shallIncludeSigned() {
      assertEquals(SIGNED,
          certificateMetadataConverter.convert(certificate).getSigned()
      );
    }

    @Test
    void shallIncludeModified() {
      assertEquals(MODIFIED,
          certificateMetadataConverter.convert(certificate).getModified()
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
          certificateMetadataConverter.convert(certificate).getUnit()
      );
    }
  }

  @Nested
  class CareUnitConvert {

    @Test
    void shallIncludeId() {
      assertEquals(ALFA_MEDICINCENTRUM_ID,
          certificateMetadataConverter.convert(certificate).getCareUnit()
              .getUnitId()
      );
    }

    @Test
    void shallIncludeName() {
      assertEquals(ALFA_MEDICINCENTRUM_NAME,
          certificateMetadataConverter.convert(certificate).getCareUnit()
              .getUnitName()
      );
    }
  }

  @Nested
  class CareProviderConvert {

    @Test
    void shallIncludeId() {
      assertEquals(ALFA_REGIONEN_ID,
          certificateMetadataConverter.convert(certificate)
              .getCareProvider().getUnitId()
      );
    }

    @Test
    void shallIncludeName() {
      assertEquals(ALFA_REGIONEN_NAME,
          certificateMetadataConverter.convert(certificate)
              .getCareProvider().getUnitName()
      );
    }
  }

  @Nested
  class IssuedByConvert {

    @Test
    void shallIncludeId() {
      assertEquals(AJLA_DOCTOR_HSA_ID,
          certificateMetadataConverter.convert(certificate).getIssuedBy()
              .getPersonId()
      );
    }

    @Test
    void shallIncludeFirstName() {
      assertEquals(AJLA_DOCTOR_FIRST_NAME,
          certificateMetadataConverter.convert(certificate).getIssuedBy()
              .getFirstName()
      );
    }

    @Test
    void shallIncludeLastMiddle() {
      assertEquals(AJLA_DOCTOR_MIDDLE_NAME,
          certificateMetadataConverter.convert(certificate).getIssuedBy()
              .getMiddleName()
      );
    }

    @Test
    void shallIncludeLastName() {
      assertEquals(AJLA_DOCTOR_LAST_NAME,
          certificateMetadataConverter.convert(certificate).getIssuedBy()
              .getLastName()
      );
    }

    @Test
    void shallIncludeFullName() {
      assertEquals(AJLA_DOCTOR_FULLNAME,
          certificateMetadataConverter.convert(certificate).getIssuedBy()
              .getFullName()
      );
    }
  }

  @Test
  void shallIncludeExternalSetExternalReference() {
    assertEquals(EXTERNAL_REF,
        certificateMetadataConverter.convert(certificate)
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
                  certificate
              )
              .getStatus()
      );
    }

    @Test
    void shallConvertDeletedDraftToUnsigned() {
      assertEquals(
          CertificateStatusTypeDTO.UNSIGNED,
          certificateMetadataConverter.convert(
                  certificateBuilder.status(Status.DELETED_DRAFT).build())
              .getStatus()
      );
    }

    @Test
    void shallConvertSignedToSigned() {
      assertEquals(
          CertificateStatusTypeDTO.SIGNED,
          certificateMetadataConverter.convert(certificateBuilder.status(Status.SIGNED).build())
              .getStatus()
      );
    }

    @Test
    void shallConvertRevokedToRevoked() {
      assertEquals(
          CertificateStatusTypeDTO.REVOKED,
          certificateMetadataConverter.convert(
              certificateBuilder.status(Status.REVOKED).build()).getStatus()
      );
    }
  }

  @Nested
  class TestCertificateRecipient {

    @Test
    void shallSetSentTrueIfSentNotNull() {
      assertTrue(
          certificateMetadataConverter.convert(certificate).isSent()
      );
    }

    @Test
    void shallSetSentFalseIfSentNull() {
      final var certificate = certificateBuilder.sent(null).build();
      assertFalse(
          certificateMetadataConverter.convert(certificate).isSent()
      );
    }

    @Test
    void shallIncludeSentToIfSentNotNull() {
      assertEquals(RECIPIENT.name(),
          certificateMetadataConverter.convert(certificate).getSentTo()
      );
    }

    @Test
    void shallExcludeSentToIfSentNotNull() {
      final var certificate = certificateBuilder.sent(null).build();

      assertNull(
          certificateMetadataConverter.convert(certificate).getSentTo()
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
          certificateMetadataConverter.convert(certificate).getRecipient()
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
          certificateMetadataConverter.convert(certificate).getRecipient()
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
          certificateMetadataConverter.convert(replacedCertificate).getRelations()
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
          certificateMetadataConverter.convert(replacedCertificate).getRelations()
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
          certificateMetadataConverter.convert(replacedCertificate).getRelations()
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
          certificateMetadataConverter.convert(replacedCertificate).getRelations()
      );
    }
  }
}
