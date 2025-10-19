package se.inera.intyg.certificateservice.integrationtest.common.util;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATHENA_REACT_ANDERSSON_PERSON_ID_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUnitDTO.ALFA_MEDICINCENTRUM_DTO;

import java.time.LocalDate;
import java.util.List;
import se.inera.intyg.certificateservice.application.certificate.dto.GetSickLeaveCertificatesInternalRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.PersonIdDTO;

public class GetSickLeaveCertificatesInternalRequestBuilder {

  private PersonIdDTO personId = PersonIdDTO.builder()
      .id(ATHENA_REACT_ANDERSSON_PERSON_ID_DTO.getId())
      .type(ATHENA_REACT_ANDERSSON_PERSON_ID_DTO.getType().name())
      .build();
  private List<String> certificateTypes = List.of("ag7804", "ag114");
  private LocalDate signedFrom = LocalDate.now().minusYears(1);
  private LocalDate signedTo = LocalDate.now().plusYears(1);
  private List<String> issuedByUnitIds = List.of(ALFA_MEDICINCENTRUM_DTO.getId());

  private GetSickLeaveCertificatesInternalRequestBuilder() {
  }

  public static GetSickLeaveCertificatesInternalRequestBuilder create() {
    return new GetSickLeaveCertificatesInternalRequestBuilder();
  }

  public GetSickLeaveCertificatesInternalRequestBuilder personId(PersonIdDTO personId) {
    this.personId = personId;
    return this;
  }

  public GetSickLeaveCertificatesInternalRequestBuilder certificateTypes(
      List<String> certificateTypes) {
    this.certificateTypes = certificateTypes;
    return this;
  }

  public GetSickLeaveCertificatesInternalRequestBuilder signedFrom(LocalDate signedFrom) {
    this.signedFrom = signedFrom;
    return this;
  }

  public GetSickLeaveCertificatesInternalRequestBuilder signedTo(LocalDate signedTo) {
    this.signedTo = signedTo;
    return this;
  }

  public GetSickLeaveCertificatesInternalRequestBuilder issuedByUnitIds(
      List<String> issuedByUnitIds) {
    this.issuedByUnitIds = issuedByUnitIds;
    return this;
  }

  public GetSickLeaveCertificatesInternalRequest build() {
    return GetSickLeaveCertificatesInternalRequest.builder()
        .personId(personId)
        .certificateTypes(certificateTypes)
        .signedFrom(signedFrom)
        .signedTo(signedTo)
        .issuedByUnitIds(issuedByUnitIds)
        .build();
  }
}

