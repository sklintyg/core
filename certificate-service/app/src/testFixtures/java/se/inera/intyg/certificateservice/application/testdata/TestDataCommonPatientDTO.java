package se.inera.intyg.certificateservice.application.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ALVE_REACT_ALFREDSSON_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ALVE_REACT_ALFREDSSON_DECEASED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ALVE_REACT_ALFREDSSON_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ALVE_REACT_ALFREDSSON_FULL_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ALVE_REACT_ALFREDSSON_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ALVE_REACT_ALFREDSSON_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ALVE_REACT_ALFREDSSON_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ALVE_REACT_ALFREDSSON_PROTECTED_PERSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ALVE_REACT_ALFREDSSON_STREET;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ALVE_REACT_ALFREDSSON_TEST_INDICATED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ALVE_REACT_ALFREDSSON_ZIP_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_DECEASED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_FULL_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_PROTECTED_PERSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_STREET;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_TEST_INDICATED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ZIP_CODE;

import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO.PatientDTOBuilder;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;

public class TestDataCommonPatientDTO {

  public static final PatientDTO ATHENA_REACT_ANDERSSON_DTO = athenaReactAnderssonDtoBuilder().build();
  public static final PatientDTO ALVE_REACT_ALFREDSSON_DTO = alveReactAlfredssonDtoBuilder().build();

  public static PatientDTOBuilder athenaReactAnderssonDtoBuilder() {
    return PatientDTO.builder()
        .id(
            PersonIdDTO.builder()
                .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                .id(ATHENA_REACT_ANDERSSON_ID)
                .build()
        )
        .city(ATHENA_REACT_ANDERSSON_CITY)
        .street(ATHENA_REACT_ANDERSSON_STREET)
        .zipCode(ATHENA_REACT_ANDERSSON_ZIP_CODE)
        .firstName(ATHENA_REACT_ANDERSSON_FIRST_NAME)
        .lastName(ATHENA_REACT_ANDERSSON_LAST_NAME)
        .middleName(ATHENA_REACT_ANDERSSON_MIDDLE_NAME)
        .fullName(ATHENA_REACT_ANDERSSON_FULL_NAME)
        .deceased(ATHENA_REACT_ANDERSSON_DECEASED.value())
        .protectedPerson(ATHENA_REACT_ANDERSSON_PROTECTED_PERSON.value())
        .testIndicated(ATHENA_REACT_ANDERSSON_TEST_INDICATED.value());
  }

  public static PatientDTOBuilder alveReactAlfredssonDtoBuilder() {
    return PatientDTO.builder()
        .id(
            PersonIdDTO.builder()
                .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                .id(ALVE_REACT_ALFREDSSON_ID)
                .build()
        )
        .city(ALVE_REACT_ALFREDSSON_CITY)
        .street(ALVE_REACT_ALFREDSSON_STREET)
        .zipCode(ALVE_REACT_ALFREDSSON_ZIP_CODE)
        .firstName(ALVE_REACT_ALFREDSSON_FIRST_NAME)
        .lastName(ALVE_REACT_ALFREDSSON_LAST_NAME)
        .middleName(ALVE_REACT_ALFREDSSON_MIDDLE_NAME)
        .fullName(ALVE_REACT_ALFREDSSON_FULL_NAME)
        .deceased(ALVE_REACT_ALFREDSSON_DECEASED.value())
        .protectedPerson(ALVE_REACT_ALFREDSSON_PROTECTED_PERSON.value())
        .testIndicated(ALVE_REACT_ALFREDSSON_TEST_INDICATED.value());
  }
}
