package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation.UNIT_CONTACT_INFORMATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK3221_CERTIFICATE_MODEL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK3226_CERTIFICATE_MODEL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK7210_CERTIFICATE_MODEL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK7426_CERTIFICATE_MODEL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK7427_CERTIFICATE_MODEL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK7472_CERTIFICATE_MODEL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK7809_CERTIFICATE_MODEL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK7810_CERTIFICATE_MODEL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.ag114certificateModelBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.ag7804certificateModelBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.fk7804certificateModelBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.SUBJECT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.ALF_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_PHONENUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ZIP_CODE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData.CertificateMetaDataBuilder;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueIcf;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueInteger;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalInvestigation;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.model.Revoked;
import se.inera.intyg.certificateservice.domain.certificate.model.Sent;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateMessageType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;
import se.inera.intyg.certificateservice.domain.common.model.RevokedInformation;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.Subject;

public class TestDataCertificate {

  public static final String EXTERNAL_REF = "externalRef";
  public static final ExternalReference EXTERNAL_REFERENCE = new ExternalReference(EXTERNAL_REF);
  public static final CertificateMetaData CERTIFICATE_META_DATA = metaDataBuilder().build();

  private TestDataCertificate() {

  }

  public static final CertificateId CERTIFICATE_ID = new CertificateId("CERTIFICATE_ID");
  public static final CertificateId PARENT_CERTIFICATE_ID = new CertificateId(
      "PARENT_CERTIFICATE_ID");
  public static final Revision REVISION = new Revision(0L);
  public static final Xml XML = new Xml("xml");
  public static final Recipient RECIPIENT = new Recipient(
      new RecipientId("recipientId"),
      "Recipient",
      "LOGICAL_ADDRESS"
  );
  public static final List<CertificateMessageType> CERTIFICATE_MESSAGE_TYPES = List.of(
      CertificateMessageType.builder()
          .type(MessageType.CONTACT)
          .subject(new Subject(SUBJECT))
          .build()
  );
  public static final Sent SENT = Sent.builder()
      .recipient(RECIPIENT)
      .sentAt(LocalDateTime.now(ZoneId.systemDefault()))
      .sentBy(AJLA_DOKTOR)
      .build();

  private static final String REASON = "INCORRECT_PATIENT";
  private static final String MESSAGE = "REVOKED_MESSAGE";
  public static final RevokedInformation REVOKED_INFORMATION = new RevokedInformation(REASON,
      MESSAGE);
  public static final Revoked REVOKED = Revoked.builder()
      .revokedBy(AJLA_DOKTOR)
      .revokedAt(LocalDateTime.now(ZoneId.systemDefault()))
      .revokedInformation(REVOKED_INFORMATION)
      .build();
  public static final MedicalCertificate FK7210_CERTIFICATE = fk7210CertificateBuilder().build();
  public static final Certificate FK3226_CERTIFICATE = fk3226CertificateBuilder().build();

  public static MedicalCertificate.MedicalCertificateBuilder fk7210CertificateBuilder() {
    return MedicalCertificate.builder()
        .id(CERTIFICATE_ID)
        .revision(REVISION)
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(FK7210_CERTIFICATE_MODEL)
        .xml(XML)
        .signed(LocalDateTime.now(ZoneId.systemDefault()))
        .sent(SENT)
        .revoked(REVOKED)
        .externalReference(EXTERNAL_REFERENCE)
        .elementData(
            List.of(
                ElementData.builder()
                    .id(new ElementId("54"))
                    .value(
                        ElementValueDate.builder()
                            .dateId(new FieldId("54.1"))
                            .date(LocalDate.now())
                            .build()
                    ).build(),
                ElementData.builder()
                    .id(UNIT_CONTACT_INFORMATION)
                    .value(
                        ElementValueUnitContactInformation.builder()
                            .address(ALFA_ALLERGIMOTTAGNINGEN_ADDRESS)
                            .city(ALFA_ALLERGIMOTTAGNINGEN_CITY)
                            .zipCode(ALFA_ALLERGIMOTTAGNINGEN_ZIP_CODE)
                            .phoneNumber(ALFA_ALLERGIMOTTAGNINGEN_PHONENUMBER)
                            .build()
                    )
                    .build()
            )
        )
        .certificateMetaData(CERTIFICATE_META_DATA);
  }

  public static MedicalCertificate.MedicalCertificateBuilder fk3226CertificateBuilder() {
    return MedicalCertificate.builder()
        .id(CERTIFICATE_ID)
        .revision(REVISION)
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(FK3226_CERTIFICATE_MODEL)
        .xml(XML)
        .sent(SENT)
        .revoked(REVOKED)
        .externalReference(EXTERNAL_REFERENCE)
        .elementData(
            List.of(
                ElementData.builder()
                    .id(new ElementId("1"))
                    .value(
                        ElementValueDateList.builder()
                            .dateListId(new FieldId("1.1"))
                            .dateList(
                                List.of(
                                    ElementValueDate.builder()
                                        .dateId(new FieldId("undersokningAvPatienten"))
                                        .date(LocalDate.now())
                                        .build()
                                )
                            )
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("52"))
                    .value(
                        ElementValueCode.builder()
                            .codeId(new FieldId("ENDAST_PALLIATIV"))
                            .code("ENDAST_PALLIATIV")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("52.2"))
                    .value(
                        ElementValueDate.builder()
                            .dateId(new FieldId("52.2"))
                            .date(LocalDate.now())
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("53"))
                    .value(
                        ElementValueBoolean.builder()
                            .booleanId(new FieldId("53.1"))
                            .value(true)
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("58"))
                    .value(
                        ElementValueDiagnosisList.builder()
                            .diagnoses(
                                List.of(
                                    ElementValueDiagnosis.builder()
                                        .code("A013")
                                        .description("Paratyfoidfeber C")
                                        .terminology("ICD_10_SE")
                                        .build()
                                )
                            )
                            .build()
                    )
                    .build()
            )
        )
        .certificateMetaData(CERTIFICATE_META_DATA);
  }


  public static final Certificate FK7472_CERTIFICATE = fk7472CertificateBuilder().build();

  public static MedicalCertificate.MedicalCertificateBuilder fk7472CertificateBuilder() {
    return MedicalCertificate.builder()
        .id(CERTIFICATE_ID)
        .revision(REVISION)
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(FK7472_CERTIFICATE_MODEL)
        .xml(XML)
        .sent(SENT)
        .revoked(REVOKED)
        .elementData(
            List.of(
                ElementData.builder()
                    .id(new ElementId("2"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("2.1"))
                            .text("text")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("3"))
                    .value(
                        ElementValueDateRangeList.builder()
                            .dateRangeList(
                                List.of(
                                    DateRange.builder()
                                        .dateRangeId(new FieldId("EN_ATTONDEL"))
                                        .from(LocalDate.now())
                                        .to(LocalDate.now().plusDays(1))
                                        .build()
                                )
                            )
                            .dateRangeListId(new FieldId("3.2"))
                            .build())
                    .build(),
                ElementData.builder()
                    .id(UNIT_CONTACT_INFORMATION)
                    .value(
                        ElementValueUnitContactInformation.builder()
                            .address(ALFA_ALLERGIMOTTAGNINGEN_ADDRESS)
                            .city(ALFA_ALLERGIMOTTAGNINGEN_CITY)
                            .zipCode(ALFA_ALLERGIMOTTAGNINGEN_ZIP_CODE)
                            .phoneNumber(ALFA_ALLERGIMOTTAGNINGEN_PHONENUMBER)
                            .build()
                    )
                    .build()
            )
        )
        .certificateMetaData(
            CertificateMetaData.builder()
                .creator(ALF_DOKTOR)
                .issuer(AJLA_DOKTOR)
                .patient(ATHENA_REACT_ANDERSSON)
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .build()
        );
  }

  public static MedicalCertificate.MedicalCertificateBuilder fk7809CertificateBuilder() {
    return MedicalCertificate.builder()
        .id(CERTIFICATE_ID)
        .revision(REVISION)
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(FK7809_CERTIFICATE_MODEL)
        .xml(XML)
        .sent(SENT)
        .revoked(REVOKED)
        .externalReference(EXTERNAL_REFERENCE)
        .elementData(
            List.of(
                ElementData.builder()
                    .id(new ElementId("1"))
                    .value(
                        ElementValueDateList.builder()
                            .dateListId(new FieldId("1.1"))
                            .dateList(
                                List.of(
                                    ElementValueDate.builder()
                                        .dateId(new FieldId("undersokningAvPatienten"))
                                        .date(LocalDate.now())
                                        .build()
                                )
                            )
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("3"))
                    .value(
                        ElementValueBoolean.builder()
                            .booleanId(new FieldId("3.1"))
                            .value(true)
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("4"))
                    .value(
                        ElementValueMedicalInvestigationList.builder()
                            .id(new FieldId("52.2"))
                            .list(
                                List.of(
                                    MedicalInvestigation.builder()
                                        .id(new FieldId("medicalInvestigation1"))
                                        .informationSource(
                                            ElementValueText.builder()
                                                .textId(new FieldId(
                                                    "medicalInvestigation1_INFORMATION_SOURCE"))
                                                .text("Example text")
                                                .build()
                                        )
                                        .investigationType(
                                            ElementValueCode.builder()
                                                .codeId(new FieldId(
                                                    "medicalInvestigation1_INVESTIGATION_TYPE"))
                                                .code("LOGOPED")
                                                .build()
                                        )
                                        .date(
                                            ElementValueDate.builder()
                                                .dateId(new FieldId("medicalInvestigation1_DATE"))
                                                .date(LocalDate.now())
                                                .build())
                                        .build()
                                )
                            )
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("58"))
                    .value(
                        ElementValueDiagnosisList.builder()
                            .diagnoses(
                                List.of(
                                    ElementValueDiagnosis.builder()
                                        .code("A013")
                                        .description("Paratyfoidfeber C")
                                        .terminology("ICD_10_SE")
                                        .build()
                                )
                            )
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("5"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("5.1"))
                            .text("TEXT")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("9"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("9.1"))
                            .text("TEXT")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("51"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("51.1"))
                            .text("TEXT")
                            .build()
                    )
                    .build()
            )
        )
        .certificateMetaData(CERTIFICATE_META_DATA);
  }

  public static final Certificate FK7427_CERTIFICATE = fk7427CertificateBuilder().build();

  public static MedicalCertificate.MedicalCertificateBuilder fk7427CertificateBuilder() {
    return MedicalCertificate.builder()
        .id(CERTIFICATE_ID)
        .revision(REVISION)
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(FK7427_CERTIFICATE_MODEL)
        .xml(XML)
        .sent(SENT)
        .revoked(REVOKED)
        .externalReference(EXTERNAL_REFERENCE)
        .elementData(
            List.of(
                ElementData.builder()
                    .id(new ElementId("1"))
                    .value(
                        ElementValueDateList.builder()
                            .dateListId(new FieldId("1.1"))
                            .dateList(
                                List.of(
                                    ElementValueDate.builder()
                                        .dateId(new FieldId("fysisktMote"))
                                        .date(LocalDate.now())
                                        .build()
                                )
                            )
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("1.3"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("1.3"))
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("58"))
                    .value(
                        ElementValueDiagnosisList.builder()
                            .diagnoses(
                                List.of(
                                    ElementValueDiagnosis.builder()
                                        .code("A013")
                                        .description("Paratyfoidfeber C")
                                        .terminology("ICD_10_SE")
                                        .build()
                                )
                            )
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("55"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("55.1"))
                            .text("Barnets symtom")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("59"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("59.1"))
                            .text("Barnets aktuella hälsotillstånd")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("62"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("62.5"))
                            .text("Stort behov av vård och tillsyn")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("62.6"))
                    .value(
                        ElementValueDateRange.builder()
                            .id(new FieldId("62.6"))
                            .fromDate(LocalDate.now())
                            .toDate(LocalDate.now().plusDays(14))
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("62.1"))
                    .value(
                        ElementValueBoolean.builder()
                            .booleanId(new FieldId("62.1"))
                            .value(true)
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("62.2"))
                    .value(
                        ElementValueDateRange.builder()
                            .id(new FieldId("62.2"))
                            .fromDate(LocalDate.now())
                            .toDate(LocalDate.now().plusDays(14))
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("19"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("19.1"))
                            .build()
                    )
                    .build()
            )
        )
        .certificateMetaData(CERTIFICATE_META_DATA);
  }

  public static MedicalCertificate.MedicalCertificateBuilder fk7426CertificateBuilder() {
    return MedicalCertificate.builder()
        .id(CERTIFICATE_ID)
        .revision(REVISION)
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(FK7426_CERTIFICATE_MODEL)
        .xml(XML)
        .sent(SENT)
        .revoked(REVOKED)
        .externalReference(EXTERNAL_REFERENCE)
        .elementData(
            List.of(
                ElementData.builder()
                    .id(new ElementId("1"))
                    .value(
                        ElementValueDateList.builder()
                            .dateListId(new FieldId("1.1"))
                            .dateList(
                                List.of(
                                    ElementValueDate.builder()
                                        .dateId(new FieldId("fysisktMote"))
                                        .date(LocalDate.now())
                                        .build()
                                )
                            )
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(UNIT_CONTACT_INFORMATION)
                    .value(
                        ElementValueUnitContactInformation.builder()
                            .address(ALFA_ALLERGIMOTTAGNINGEN_ADDRESS)
                            .city(ALFA_ALLERGIMOTTAGNINGEN_CITY)
                            .zipCode(ALFA_ALLERGIMOTTAGNINGEN_ZIP_CODE)
                            .phoneNumber(ALFA_ALLERGIMOTTAGNINGEN_PHONENUMBER)
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("58"))
                    .value(
                        ElementValueDiagnosisList.builder()
                            .diagnoses(
                                List.of(
                                    ElementValueDiagnosis.builder()
                                        .code("A013")
                                        .description("Paratyfoidfeber C")
                                        .terminology("ICD_10_SE")
                                        .build()
                                )
                            )
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("55"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("55.1"))
                            .text("Example symptom description")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("71"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("71.1"))
                            .text("Example health condition")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("72"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("72.1"))
                            .text("Additional health details")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("60"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("60.1"))
                            .text("Care and supervision needs")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("19"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("19.1"))
                            .text("Additional notes")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("20"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("20.1"))
                            .text("Further observations")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("61"))
                    .value(
                        ElementValueDateRange.builder()
                            .id(new FieldId("61.1"))
                            .fromDate(LocalDate.now())
                            .toDate(LocalDate.now().plusDays(7))
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("61.2"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("61.2"))
                            .text("Date range description")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("62"))
                    .value(
                        ElementValueBoolean.builder()
                            .booleanId(new FieldId("62.1"))
                            .value(true)
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("62.2"))
                    .value(
                        ElementValueDateRange.builder()
                            .id(new FieldId("62.2"))
                            .fromDate(LocalDate.now())
                            .toDate(LocalDate.now().plusDays(14))
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("62.3"))
                    .value(
                        ElementValueBoolean.builder()
                            .booleanId(new FieldId("62.3"))
                            .value(false)
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("62.4"))
                    .value(
                        ElementValueDateRange.builder()
                            .id(new FieldId("62.4"))
                            .fromDate(LocalDate.now().minusDays(3))
                            .toDate(LocalDate.now().plusDays(10))
                            .build()
                    )
                    .build()
            )
        )
        .certificateMetaData(CERTIFICATE_META_DATA);
  }


  public static MedicalCertificate.MedicalCertificateBuilder fk3221CertificateBuilder() {
    return MedicalCertificate.builder()
        .id(CERTIFICATE_ID)
        .revision(REVISION)
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(FK3221_CERTIFICATE_MODEL)
        .xml(XML)
        .sent(SENT)
        .revoked(REVOKED)
        .externalReference(EXTERNAL_REFERENCE)
        .elementData(
            List.of(
                ElementData.builder()
                    .id(new ElementId("1"))
                    .value(
                        ElementValueDateList.builder()
                            .dateListId(new FieldId("1.1"))
                            .dateList(
                                List.of(
                                    ElementValueDate.builder()
                                        .dateId(new FieldId("fysisktMote"))
                                        .date(LocalDate.now())
                                        .build()
                                )
                            )
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("3"))
                    .value(
                        ElementValueBoolean.builder()
                            .booleanId(new FieldId("3.1"))
                            .value(true)
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("4"))
                    .value(
                        ElementValueMedicalInvestigationList.builder()
                            .id(new FieldId("4.1"))
                            .list(
                                List.of(
                                    MedicalInvestigation.builder()
                                        .id(new FieldId("medicalInvestigation1"))
                                        .informationSource(
                                            ElementValueText.builder()
                                                .textId(new FieldId(
                                                    "medicalInvestigation1_INFORMATION_SOURCE"))
                                                .text("Example text")
                                                .build()
                                        )
                                        .investigationType(
                                            ElementValueCode.builder()
                                                .codeId(new FieldId(
                                                    "medicalInvestigation1_INVESTIGATION_TYPE"))
                                                .code("LOGOPED")
                                                .build()
                                        )
                                        .date(
                                            ElementValueDate.builder()
                                                .dateId(new FieldId("medicalInvestigation1_DATE"))
                                                .date(LocalDate.now())
                                                .build())
                                        .build()
                                )
                            )
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("58"))
                    .value(
                        ElementValueDiagnosisList.builder()
                            .diagnoses(
                                List.of(
                                    ElementValueDiagnosis.builder()
                                        .code("A013")
                                        .description("Paratyfoidfeber C")
                                        .terminology("ICD_10_SE")
                                        .build()
                                )
                            )
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("5"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("5.1"))
                            .text("TEXT")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("8"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("8.1"))
                            .text("TEXT")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("17"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("17.1"))
                            .text("TEXT")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("39"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("39.2"))
                            .text("TEXT")
                            .build()
                    )
                    .build()
            )
        )
        .certificateMetaData(CERTIFICATE_META_DATA);
  }

  public static final Certificate FK7810_CERTIFICATE = fk7810CertificateBuilder().build();

  public static MedicalCertificate.MedicalCertificateBuilder fk7810CertificateBuilder() {
    return MedicalCertificate.builder()
        .id(CERTIFICATE_ID)
        .revision(REVISION)
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(FK7810_CERTIFICATE_MODEL)
        .xml(XML)
        .sent(SENT)
        .revoked(REVOKED)
        .externalReference(EXTERNAL_REFERENCE)
        .elementData(
            List.of(
                ElementData.builder()
                    .id(new ElementId("1"))
                    .value(
                        ElementValueDateList.builder()
                            .dateListId(new FieldId("1.1"))
                            .dateList(
                                List.of(
                                    ElementValueDate.builder()
                                        .dateId(new FieldId("fysisktMote"))
                                        .date(LocalDate.now())
                                        .build()
                                )
                            )
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("2"))
                    .value(
                        ElementValueCode.builder()
                            .codeId(new FieldId("MINDRE_AN_ETT_AR"))
                            .code("MINDRE_AN_ETT_AR")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("3"))
                    .value(
                        ElementValueBoolean.builder()
                            .booleanId(new FieldId("3.1"))
                            .value(true)
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("4"))
                    .value(
                        ElementValueMedicalInvestigationList.builder()
                            .id(new FieldId("4.1"))
                            .list(
                                List.of(
                                    MedicalInvestigation.builder()
                                        .id(new FieldId("medicalInvestigation1"))
                                        .informationSource(
                                            ElementValueText.builder()
                                                .textId(new FieldId(
                                                    "medicalInvestigation1_INFORMATION_SOURCE"))
                                                .text("Example text")
                                                .build()
                                        )
                                        .investigationType(
                                            ElementValueCode.builder()
                                                .codeId(new FieldId(
                                                    "medicalInvestigation1_INVESTIGATION_TYPE"))
                                                .code("LOGOPED")
                                                .build()
                                        )
                                        .date(
                                            ElementValueDate.builder()
                                                .dateId(new FieldId("medicalInvestigation1_DATE"))
                                                .date(LocalDate.now())
                                                .build())
                                        .build()
                                )
                            )
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("58"))
                    .value(
                        ElementValueDiagnosisList.builder()
                            .diagnoses(
                                List.of(
                                    ElementValueDiagnosis.builder()
                                        .code("A013")
                                        .description("Paratyfoidfeber C")
                                        .terminology("ICD_10_SE")
                                        .build()
                                )
                            )
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("5"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("5.1"))
                            .text("TEXT")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("8"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("8.1"))
                            .text("TEXT")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("65"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("65.1"))
                            .text("TEXT")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("39"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("39.2"))
                            .text("TEXT")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("70"))
                    .value(
                        ElementValueBoolean.builder()
                            .booleanId(new FieldId("70.1"))
                            .value(true)
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("70.2"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("70.2"))
                            .text("TEXT")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("70.3"))
                    .value(
                        ElementValueBoolean.builder()
                            .booleanId(new FieldId("70.3"))
                            .value(true)
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("70.4"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("70.4"))
                            .text("TEXT")
                            .build()
                    )
                    .build()
            )
        )
        .certificateMetaData(CERTIFICATE_META_DATA);
  }

  public static final Certificate FK7804_CERTIFICATE = fk7804CertificateBuilder().build();

  public static MedicalCertificate.MedicalCertificateBuilder fk7804CertificateBuilder() {
    return MedicalCertificate.builder()
        .id(CERTIFICATE_ID)
        .revision(REVISION)
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(fk7804certificateModelBuilder().build())
        .xml(XML)
        .sent(SENT)
        .revoked(REVOKED)
        .externalReference(EXTERNAL_REFERENCE)
        .elementData(
            List.of(
                ElementData.builder()
                    .id(new ElementId("1"))
                    .value(
                        ElementValueDateList.builder()
                            .dateListId(new FieldId("1.1"))
                            .dateList(
                                List.of(
                                    ElementValueDate.builder()
                                        .dateId(new FieldId("FYSISKUNDERSOKNING"))
                                        .date(LocalDate.now())
                                        .build()
                                )
                            )
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("6"))
                    .value(
                        ElementValueDiagnosisList.builder()
                            .diagnoses(
                                List.of(
                                    ElementValueDiagnosis.builder()
                                        .code("A013")
                                        .description("Paratyfoidfeber C")
                                        .terminology("ICD_10_SE")
                                        .build()
                                )
                            )
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("35"))
                    .value(
                        ElementValueIcf.builder()
                            .id(new FieldId("35.1"))
                            .text("Funktionsnedsättning")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("17"))
                    .value(
                        ElementValueIcf.builder()
                            .id(new FieldId("17.1"))
                            .text("Aktivitetsbegräsning")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("27"))
                    .value(
                        ElementValueBoolean.builder()
                            .booleanId(new FieldId("27.1"))
                            .value(false)
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("28"))
                    .value(
                        ElementValueCodeList.builder()
                            .id(new FieldId("28.1"))
                            .list(List.of(
                                    ElementValueCode.builder()
                                        .code("NUVARANDE_ARBETE")
                                        .codeId(new FieldId("NUVARANDE_ARBETE"))
                                        .build()
                                )
                            ).build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("29"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("29.1"))
                            .text("Butikssäljare, ansvarar för kassa och varuplock.")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("19"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("19.1"))
                            .text("Rehabövningar under 10 dagar hemifrån.")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("32"))
                    .value(
                        ElementValueDateRangeList.builder()
                            .dateRangeListId(new FieldId("32.1"))
                            .dateRangeList(List.of(
                                DateRange.builder()
                                    .dateRangeId(new FieldId(("EN_FJARDEDEL")))
                                    .from(LocalDate.now())
                                    .to(LocalDate.now().plusDays(30))
                                    .build()
                            ))
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("33"))
                    .value(
                        ElementValueBoolean.builder()
                            .booleanId(new FieldId("33.1"))
                            .value(true)
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("33.2"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("33.2"))
                            .text("Behöver regelbundna vilopauser och kan inte arbeta heltid.")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("34"))
                    .value(
                        ElementValueBoolean.builder()
                            .booleanId(new FieldId("34.1"))
                            .value(false)
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("37"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("37.1"))
                            .text(
                                "Patienten har långvariga besvär och tidigare behandlingar har haft begränsad effekt.")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("39"))
                    .value(
                        ElementValueCode.builder()
                            .code("ATER_X_ANTAL_MANADER")
                            .codeId(new FieldId("ATER_X_ANTAL_MANADER"))
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("39.4"))
                    .value(
                        ElementValueInteger.builder()
                            .integerId(new FieldId("39.4"))
                            .value(6)
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("44"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("44.1"))
                            .text(
                                "Arbetsanpassning med höj- och sänkbart skrivbord och flexibla arbetstider.")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("25"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("25.1"))
                            .text(
                                "Patienten har svårt att ta sig till arbetsplatsen med kollektivtrafik.")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("26"))
                    .value(
                        ElementValueBoolean.builder()
                            .booleanId(new FieldId("26.1"))
                            .value(true)
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("26.2"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("26.2"))
                            .text("Behöver diskutera kompletterande intyg med handläggare.")
                            .build()
                    )
                    .build()
            )
        )
        .certificateMetaData(CERTIFICATE_META_DATA);
  }

  public static CertificateMetaDataBuilder metaDataBuilder() {
    return CertificateMetaData.builder()
        .issuer(AJLA_DOKTOR)
        .creator(ALF_DOKTOR)
        .patient(ATHENA_REACT_ANDERSSON)
        .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
        .careUnit(ALFA_MEDICINCENTRUM)
        .careProvider(ALFA_REGIONEN);
  }

  public static final Certificate AG7804_CERTIFICATE = ag7804CertificateBuilder().build();

  public static MedicalCertificate.MedicalCertificateBuilder ag7804CertificateBuilder() {
    return MedicalCertificate.builder()
        .id(CERTIFICATE_ID)
        .revision(REVISION)
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(
            ag7804certificateModelBuilder().build()
        )
        .xml(XML)
        .externalReference(EXTERNAL_REFERENCE)
        .elementData(
            List.of(
                ElementData.builder()
                    .id(new ElementId("1"))
                    .value(
                        ElementValueDateList.builder()
                            .dateListId(new FieldId("1.1"))
                            .dateList(
                                List.of(
                                    ElementValueDate.builder()
                                        .dateId(new FieldId("FYSISKUNDERSOKNING"))
                                        .date(LocalDate.now())
                                        .build()
                                )
                            )
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("6"))
                    .value(
                        ElementValueDiagnosisList.builder()
                            .diagnoses(
                                List.of(
                                    ElementValueDiagnosis.builder()
                                        .code("A013")
                                        .description("Paratyfoidfeber C")
                                        .terminology("ICD_10_SE")
                                        .build()
                                )
                            )
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("35"))
                    .value(
                        ElementValueIcf.builder()
                            .id(new FieldId("35.1"))
                            .text("Funktionsnedsättning")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("17"))
                    .value(
                        ElementValueIcf.builder()
                            .id(new FieldId("17.1"))
                            .text("Aktivitetsbegräsning")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("27"))
                    .value(
                        ElementValueBoolean.builder()
                            .booleanId(new FieldId("27.1"))
                            .value(false)
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("28"))
                    .value(
                        ElementValueCodeList.builder()
                            .id(new FieldId("28.1"))
                            .list(List.of(
                                    ElementValueCode.builder()
                                        .code("NUVARANDE_ARBETE")
                                        .codeId(new FieldId("NUVARANDE_ARBETE"))
                                        .build()
                                )
                            ).build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("29"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("29.1"))
                            .text("Butikssäljare, ansvarar för kassa och varuplock.")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("19"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("19.1"))
                            .text("Rehabövningar under 10 dagar hemifrån.")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("32"))
                    .value(
                        ElementValueDateRangeList.builder()
                            .dateRangeListId(new FieldId("32.1"))
                            .dateRangeList(List.of(
                                DateRange.builder()
                                    .dateRangeId(new FieldId(("EN_FJARDEDEL")))
                                    .from(LocalDate.now())
                                    .to(LocalDate.now().plusDays(30))
                                    .build()
                            ))
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("33"))
                    .value(
                        ElementValueBoolean.builder()
                            .booleanId(new FieldId("33.1"))
                            .value(true)
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("33.2"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("33.2"))
                            .text("Behöver regelbundna vilopauser och kan inte arbeta heltid.")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("34"))
                    .value(
                        ElementValueBoolean.builder()
                            .booleanId(new FieldId("34.1"))
                            .value(false)
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("37"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("37.1"))
                            .text(
                                "Patienten har långvariga besvär och tidigare behandlingar har haft begränsad effekt.")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("39"))
                    .value(
                        ElementValueCode.builder()
                            .code("ATER_X_ANTAL_MANADER")
                            .codeId(new FieldId("ATER_X_ANTAL_MANADER"))
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("39.4"))
                    .value(
                        ElementValueInteger.builder()
                            .integerId(new FieldId("39.4"))
                            .value(6)
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("44"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("44.1"))
                            .text(
                                "Arbetsanpassning med höj- och sänkbart skrivbord och flexibla arbetstider.")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("25"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("25.1"))
                            .text(
                                "Patienten har svårt att ta sig till arbetsplatsen med kollektivtrafik.")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("26"))
                    .value(
                        ElementValueBoolean.builder()
                            .booleanId(new FieldId("26.1"))
                            .value(true)
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("26.2"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("26.2"))
                            .text("Behöver diskutera kompletterande intyg med handläggare.")
                            .build()
                    )
                    .build()
            )
        )
        .certificateMetaData(
            CertificateMetaData.builder()
                .issuer(AJLA_DOKTOR)
                .creator(ALF_DOKTOR)
                .patient(ATHENA_REACT_ANDERSSON)
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .build()
        );
  }

  public static final MedicalCertificate AG114_CERTIFICATE = ag114CertificateBuilder().build();

  public static MedicalCertificate.MedicalCertificateBuilder ag114CertificateBuilder() {
    return MedicalCertificate.builder()
        .id(new CertificateId("AG114_CERTIFICATE_ID"))
        .revision(REVISION)
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(ag114certificateModelBuilder().build())
        .xml(XML)
        .sent(SENT)
        .revoked(REVOKED)
        .externalReference(EXTERNAL_REFERENCE)
        .elementData(
            List.of(
                ElementData.builder()
                    .id(new ElementId("1"))
                    .value(
                        ElementValueText.builder()
                            .textId(new FieldId("1.1"))
                            .text("Svarstext för sysselsättning")
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("4"))
                    .value(
                        ElementValueDiagnosisList.builder()
                            .diagnoses(
                                List.of(
                                    ElementValueDiagnosis.builder()
                                        .code("A013")
                                        .description("Paratyfoidfeber C")
                                        .terminology("ICD_10_SE")
                                        .build()
                                )
                            )
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("7"))
                    .value(
                        ElementValueInteger.builder()
                            .integerId(new FieldId("7.1"))
                            .value(70)
                            .build()
                    )
                    .build(),
                ElementData.builder()
                    .id(new ElementId("7.2"))
                    .value(
                        ElementValueDateRange.builder()
                            .id(new FieldId("7.2"))
                            .fromDate(LocalDate.now())
                            .toDate(LocalDate.now().plusDays(30))
                            .build()
                    )
                    .build()
            )
        )
        .certificateMetaData(
            CertificateMetaData.builder()
                .issuer(AJLA_DOKTOR)
                .creator(ALF_DOKTOR)
                .patient(ATHENA_REACT_ANDERSSON)
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .build()
        );
  }
}