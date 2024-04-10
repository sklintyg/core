package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK7211_CERTIFICATE_MODEL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModel.FK7443_CERTIFICATE_MODEL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificate.model.Revoked;
import se.inera.intyg.certificateservice.domain.certificate.model.Sent;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
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
        .elementData(Collections.emptyList())
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

  public static Certificate.CertificateBuilder fk443CertificateBuilder() {
    return Certificate.builder()
        .id(CERTIFICATE_ID)
        .revision(REVISION)
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(FK7443_CERTIFICATE_MODEL)
        .xml(XML)
        .sent(SENT)
        .revoked(REVOKED)
        .elementData(Collections.emptyList())
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
