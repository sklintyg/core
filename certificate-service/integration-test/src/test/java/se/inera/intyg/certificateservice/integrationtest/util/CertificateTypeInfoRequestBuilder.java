package se.inera.intyg.certificateservice.integrationtest.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.athenaReactAnderssonDtoBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ajlaDoktorDtoBuilder;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.ALFA_HUDMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.ALFA_REGIONEN;

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
