package se.inera.intyg.certificateservice.integrationtest.util;

import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.RoleTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

public class CertificateTypeInfoRequestBuilder {

  private static final String DOKTOR_AJLA = "TSTNMT2321000156-DRAA";
  private static final String TOLVAN_TOLVANSSON = "191212121212";
  private static final String ALFA_REGIONEN = "TSTNMT2321000156-ALFA";
  private static final String ALFA_HUDMOTTAGNINGEN = "TSTNMT2321000156-ALHM";
  private static final String ALFA_MEDICINCENTRUM = "TSTNMT2321000156-ALMC";

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
