package se.inera.intyg.certificateservice.integrationtest.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.athenaReactAnderssonDtoBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ajlaDoktorDtoBuilder;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.ALFA_HUDMOTTAGNINGEN;

import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.GetCertificateTypeInfoRequest;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;

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
            ajlaDoktorDtoBuilder()
                .blocked(blocked)
                .build()
        )
        .patient(
            athenaReactAnderssonDtoBuilder()
                .deceased(deceased)
                .build()
        )
        .careProvider(ALFA_REGIONEN_DTO)
        .unit(
            UnitDTO.builder()
                .id(ALFA_HUDMOTTAGNINGEN)
                .inactive(false)
                .build()
        )
        .careUnit(ALFA_MEDICINCENTRUM_DTO)
        .build();
  }
}
