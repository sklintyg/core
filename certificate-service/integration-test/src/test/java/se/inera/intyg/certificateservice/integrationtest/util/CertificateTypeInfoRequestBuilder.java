package se.inera.intyg.certificateservice.integrationtest.util;

import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.ALFA_HUDMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.DOKTOR_AJLA;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.TOLVAN_TOLVANSSON;

import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.RoleTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

public class CertificateTypeInfoRequestBuilder {

  private boolean blocked = false;
  private boolean deceased = false;

  public static CertificateTypeInfoRequestBuilder create() {
    return new CertificateTypeInfoRequestBuilder();
  }

  private CertificateTypeInfoRequestBuilder() {

  }

  public CertificateTypeInfoRequestBuilder blocked(boolean blocked) {
    this.blocked = blocked;
    return this;
  }

  public CertificateTypeInfoRequestBuilder deceased(boolean deceased) {
    this.deceased = deceased;
    return this;
  }

  public GetCertificateTypeInfoRequest build() {
    return GetCertificateTypeInfoRequest.builder()
        .user(
            UserDTO.builder()
                .id(DOKTOR_AJLA)
                .blocked(blocked)
                .role(RoleTypeDTO.DOCTOR)
                .build()
        )
        .patient(
            PatientDTO.builder()
                .id(
                    PersonIdDTO.builder()
                        .id(TOLVAN_TOLVANSSON)
                        .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                        .build()
                )
                .protectedPerson(false)
                .deceased(deceased)
                .testIndicated(false)
                .build()
        )
        .careProvider(
            UnitDTO.builder()
                .id(ALFA_REGIONEN)
                .build()
        )
        .unit(
            UnitDTO.builder()
                .id(ALFA_HUDMOTTAGNINGEN)
                .inactive(false)
                .build()
        )
        .careUnit(
            UnitDTO.builder()
                .id(ALFA_MEDICINCENTRUM)
                .build()
        )
        .build();
  }
}
