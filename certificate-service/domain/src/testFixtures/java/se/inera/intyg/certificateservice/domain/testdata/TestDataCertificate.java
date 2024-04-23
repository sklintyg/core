package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK7211_CERTIFICATE_MODEL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK7443_CERTIFICATE_MODEL;
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
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.model.Revoked;
import se.inera.intyg.certificateservice.domain.certificate.model.Sent;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Recipient;
import se.inera.intyg.certificateservice.domain.common.model.RecipientId;
import se.inera.intyg.certificateservice.domain.common.model.RevokedInformation;

public class TestDataCertificate {

  private TestDataCertificate() {

  }

  public static final CertificateId CERTIFICATE_ID = new CertificateId("CERTIFICATE_ID");
  public static final Revision REVISION = new Revision(0L);
  public static final Xml XML = new Xml("xml");
  public static final Recipient RECIPIENT = new Recipient(
      new RecipientId("recipientId"),
      "Recipient"
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
  public static final Certificate FK7211_CERTIFICATE = fk7211CertificateBuilder().build();

  public static Certificate.CertificateBuilder fk7211CertificateBuilder() {
    return Certificate.builder()
        .id(CERTIFICATE_ID)
        .revision(REVISION)
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(FK7211_CERTIFICATE_MODEL)
        .xml(XML)
        .sent(SENT)
        .revoked(REVOKED)
        .elementData(
            List.of(
                ElementData.builder()
                    .id(new ElementId("1"))
                    .value(
                        ElementValueDate.builder()
                            .dateId(new FieldId("1.1"))
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


  public static final Certificate FK443_CERTIFICATE = fk7443CertificateBuilder().build();

  public static Certificate.CertificateBuilder fk7443CertificateBuilder() {
    return Certificate.builder()
        .id(CERTIFICATE_ID)
        .revision(REVISION)
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(FK7443_CERTIFICATE_MODEL)
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
                                        .dateRangeId(new FieldId("EN_ATTANDEL"))
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
}
