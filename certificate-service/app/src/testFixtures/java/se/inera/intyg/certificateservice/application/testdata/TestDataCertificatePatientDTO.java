package se.inera.intyg.certificateservice.application.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_FULL_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_STREET;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ZIP_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.DECEASED_FALSE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.PROTECTED_PERSON_FALSE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.TEST_INDICATED_FALSE;

import se.inera.intyg.certificateservice.application.certificate.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.PatientDTO.PatientDTOBuilder;
import se.inera.intyg.certificateservice.application.certificate.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;

public class TestDataCertificatePatientDTO {

  private TestDataCertificatePatientDTO() {
    throw new IllegalStateException("Utility class");
  }

  public static final PatientDTO ATHENA_REACT_ANDERSSON_DTO = athenaReactAnderssonDtoBuilder().build();

  public static PatientDTOBuilder athenaReactAnderssonDtoBuilder() {
    return PatientDTO.builder()
        .deceased(DECEASED_FALSE.value())
        .protectedPerson(PROTECTED_PERSON_FALSE.value())
        .testIndicated(TEST_INDICATED_FALSE.value())
        .personId(
            PersonIdDTO.builder()
                .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER.name())
                .id(ATHENA_REACT_ANDERSSON_ID)
                .build()
        )
        .city(ATHENA_REACT_ANDERSSON_CITY)
        .street(ATHENA_REACT_ANDERSSON_STREET)
        .zipCode(ATHENA_REACT_ANDERSSON_ZIP_CODE)
        .firstName(ATHENA_REACT_ANDERSSON_FIRST_NAME)
        .lastName(ATHENA_REACT_ANDERSSON_LAST_NAME)
        .middleName(ATHENA_REACT_ANDERSSON_MIDDLE_NAME)
        .fullName(ATHENA_REACT_ANDERSSON_FULL_NAME);
  }
}
