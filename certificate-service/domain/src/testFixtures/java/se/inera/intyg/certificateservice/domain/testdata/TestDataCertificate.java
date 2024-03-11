package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7211_CODE_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7211_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7211_VERSION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;

import java.time.LocalDateTime;
import java.time.ZoneId;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.Revision;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;

public class TestDataCertificate {

  private TestDataCertificate() {

  }

  public static final CertificateId CERTIFICATE_ID = new CertificateId("certificateId");
  public static final Revision REVISION = new Revision(0L);
  public static final Certificate FK7211_CERTIFICATE = fk7211CertificateBuilder().build();

  public static Certificate.CertificateBuilder fk7211CertificateBuilder() {
    return Certificate.builder()
        .id(CERTIFICATE_ID)
        .revision(REVISION)
        .created(LocalDateTime.now(ZoneId.systemDefault()))
        .certificateModel(
            CertificateModel.builder()
                .id(
                    CertificateModelId.builder()
                        .type(FK7211_TYPE)
                        .version(FK7211_VERSION)
                        .build()
                )
                .type(FK7211_CODE_TYPE)
                .build()
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
