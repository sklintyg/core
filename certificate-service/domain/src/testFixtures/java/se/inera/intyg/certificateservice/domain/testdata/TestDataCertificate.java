package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK3226_CERTIFICATE_MODEL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK7210_CERTIFICATE_MODEL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK7472_CERTIFICATE_MODEL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK7809_CERTIFICATE_MODEL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.SUBJECT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
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
  public static final Certificate FK7210_CERTIFICATE = fk7210CertificateBuilder().build();
  public static final Certificate FK3226_CERTIFICATE = fk3226CertificateBuilder().build();

  public static Certificate.CertificateBuilder fk7210CertificateBuilder() {
    return Certificate.builder()
        .id(CERTIFICATE_ID)
        .revision(REVISION)
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(FK7210_CERTIFICATE_MODEL)
        .xml(XML)
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
                    ).build()
            )
        )
        .certificateMetaData(
            CertificateMetaData.builder()
                .issuer(AJLA_DOKTOR)
                .patient(ATHENA_REACT_ANDERSSON)
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .build()
        );
  }

  public static Certificate.CertificateBuilder fk3226CertificateBuilder() {
    return Certificate.builder()
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
        .certificateMetaData(
            CertificateMetaData.builder()
                .issuer(AJLA_DOKTOR)
                .patient(ATHENA_REACT_ANDERSSON)
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .build()
        );
  }


  public static final Certificate FK7472_CERTIFICATE = fk7472CertificateBuilder().build();

  public static Certificate.CertificateBuilder fk7472CertificateBuilder() {
    return Certificate.builder()
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
                    .build()
            )
        )
        .certificateMetaData(
            CertificateMetaData.builder()
                .issuer(AJLA_DOKTOR)
                .patient(ATHENA_REACT_ANDERSSON)
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .build()
        );
  }

  public static Certificate.CertificateBuilder fk7809CertificateBuilder() {
    return Certificate.builder()
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
                            .textId(new FieldId("5"))
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
                            .textId(new FieldId("51"))
                            .text("TEXT")
                            .build()
                    )
                    .build()
            )
        )
        .certificateMetaData(
            CertificateMetaData.builder()
                .issuer(AJLA_DOKTOR)
                .patient(ATHENA_REACT_ANDERSSON)
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .build()
        );
  }
}
