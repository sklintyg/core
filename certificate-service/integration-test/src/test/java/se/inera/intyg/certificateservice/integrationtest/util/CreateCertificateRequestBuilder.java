package se.inera.intyg.certificateservice.integrationtest.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.athenaReactAnderssonDtoBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ajlaDoktorDtoBuilder;
import static se.inera.intyg.certificateservice.integrationtest.fk7211.FK7211Constants.ALFA_HUDMOTTAGNINGEN;

import se.inera.intyg.certificateservice.application.certificate.dto.CreateCertificateRequest;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateModelIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.UnitDTO;

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
                .inactive(inactive)
                .build()
        )
        .careUnit(ALFA_MEDICINCENTRUM_DTO)
        .certificateModelId(
            certificateModelId
        )
        .build();
  }
}
