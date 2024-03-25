package se.inera.intyg.certificateservice.application.testdata;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCertdificateDataEntity.CERTIFICATE_DATA_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCertificateStatusEntity.STATUS_SIGNED_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCertificateXmlEntity.CERTIFICATE_XML_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataPatientEntity.ATHENA_REACT_ANDERSSON_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataStaffEntity.AJLA_DOKTOR_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataUnitEntity.ALFA_ALLERGIMOTTAGNINGEN_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataUnitEntity.ALFA_MEDICINCENTRUM_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataUnitEntity.ALFA_REGIONEN_ENTITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateConstants.CERTIFICATE_ID;

import java.time.LocalDateTime;
import java.time.ZoneId;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateEntity;

public class TestDataCertificateEntity {

  public static final CertificateEntity CERTIFICATE_ENTITY = certificateEntityBuilder().build();

  public static CertificateEntity.CertificateEntityBuilder certificateEntityBuilder() {
    return CertificateEntity.builder()
        .certificateId(CERTIFICATE_ID)
        .status(STATUS_SIGNED_ENTITY)
        .created(LocalDateTime.now().minusDays(1))
        .modified(LocalDateTime.now())
        .signed(LocalDateTime.now())
        .revision(1L)
        .careProvider(ALFA_REGIONEN_ENTITY)
        .careUnit(ALFA_MEDICINCENTRUM_ENTITY)
        .issuedOnUnit(ALFA_ALLERGIMOTTAGNINGEN_ENTITY)
        .issuedBy(AJLA_DOKTOR_ENTITY)
        .patient(ATHENA_REACT_ANDERSSON_ENTITY)
        .data(CERTIFICATE_DATA_ENTITY)
        .xml(CERTIFICATE_XML_ENTITY)
        .sent(LocalDateTime.now(ZoneId.systemDefault()))
        .sentBy(AJLA_DOKTOR_ENTITY);

  }

}
