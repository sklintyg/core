package se.inera.intyg.certificateservice.integrationtest.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_REGIONEN_DTO;

import java.util.List;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.patient.dto.PatientCertificatesWithQARequest;

public class GetPatientCertificatesWithQARequestBuilder {

  private List<String> unitIds = null;
  private String careProviderId = ALFA_REGIONEN_DTO.getId();
  private PersonIdDTO personId = ATHENA_REACT_ANDERSSON_DTO.getId();

  public static GetPatientCertificatesWithQARequestBuilder create() {
    return new GetPatientCertificatesWithQARequestBuilder();
  }

  private GetPatientCertificatesWithQARequestBuilder() {

  }

  public GetPatientCertificatesWithQARequestBuilder personId(PersonIdDTO personId) {
    this.personId = personId;
    return this;
  }

  public GetPatientCertificatesWithQARequestBuilder careProviderId(String careProviderId) {
    this.careProviderId = careProviderId;
    return this;
  }

  public GetPatientCertificatesWithQARequestBuilder unitIds(List<String> unitIds) {
    this.unitIds = unitIds;
    return this;
  }

  public PatientCertificatesWithQARequest build() {
    return PatientCertificatesWithQARequest.builder()
        .personId(personId)
        .careProviderId(careProviderId)
        .unitIds(unitIds)
        .build();
  }
}
