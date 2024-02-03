package se.inera.intyg.certificateservice.integrationtest.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.athenaReactAnderssonDtoBuilder;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.ALFA_HUDMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.DOKTOR_AJLA;

import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.RoleTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;

public class CreateCertificateRequestBuilder {

  private static final String VERSION = "1.0";
  private static final String TYPE = "fk7211";

  private boolean blocked = false;
  private boolean deceased = false;
  private boolean inactive = false;
  private CertificateModelIdDTO certificateModelId = CertificateModelIdDTO.builder()
      .version(VERSION)
      .type(TYPE)
      .build();

  public static CreateCertificateRequestBuilder create() {
    return new CreateCertificateRequestBuilder();
  }

  private CreateCertificateRequestBuilder() {

  }

  public CreateCertificateRequestBuilder blocked(boolean blocked) {
    this.blocked = blocked;
    return this;
  }

  public CreateCertificateRequestBuilder deceased(boolean deceased) {
    this.deceased = deceased;
    return this;
  }

  public CreateCertificateRequestBuilder inactive(boolean inactive) {
    this.inactive = inactive;
    return this;
  }

  public CreateCertificateRequestBuilder certificateModelId(
      CertificateModelIdDTO certificateModelIdDTO) {
    this.certificateModelId = certificateModelIdDTO;
    return this;
  }

  public CreateCertificateRequest build() {
    return CreateCertificateRequest.builder()
        .user(
            UserDTO.builder()
                .id(DOKTOR_AJLA)
                .blocked(blocked)
                .role(RoleTypeDTO.DOCTOR)
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
                .inactive(inactive)
                .build()
        )
        .careUnit(
            UnitDTO.builder()
                .id(ALFA_MEDICINCENTRUM)
                .build()
        )
        .certificateModelId(
            certificateModelId
        )
        .build();
  }
}
