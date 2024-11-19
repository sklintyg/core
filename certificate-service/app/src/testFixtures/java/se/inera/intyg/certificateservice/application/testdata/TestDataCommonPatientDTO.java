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
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ANONYMA_REACT_ATTILA_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ANONYMA_REACT_ATTILA_DECEASED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ANONYMA_REACT_ATTILA_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ANONYMA_REACT_ATTILA_FULL_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ANONYMA_REACT_ATTILA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ANONYMA_REACT_ATTILA_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ANONYMA_REACT_ATTILA_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ANONYMA_REACT_ATTILA_PROTECTED_PERSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ANONYMA_REACT_ATTILA_STREET;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ANONYMA_REACT_ATTILA_TEST_INDICATED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ANONYMA_REACT_ATTILA_ZIP_CODE;
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
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATLAS_REACT_ABRAHAMSSON_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATLAS_REACT_ABRAHAMSSON_DECEASED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATLAS_REACT_ABRAHAMSSON_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATLAS_REACT_ABRAHAMSSON_FULL_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATLAS_REACT_ABRAHAMSSON_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATLAS_REACT_ABRAHAMSSON_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATLAS_REACT_ABRAHAMSSON_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATLAS_REACT_ABRAHAMSSON_PROTECTED_PERSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATLAS_REACT_ABRAHAMSSON_STREET;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATLAS_REACT_ABRAHAMSSON_TEST_INDICATED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATLAS_REACT_ABRAHAMSSON_ZIP_CODE;

import se.inera.intyg.certificateservice.application.common.dto.PatientDTO;
import se.inera.intyg.certificateservice.application.common.dto.PatientDTO.PatientDTOBuilder;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;

public class TestDataCommonPatientDTO {

  private TestDataCommonPatientDTO() {
    throw new IllegalStateException("Utility class");
  }

  public static final PatientDTO ATHENA_REACT_ANDERSSON_DTO = athenaReactAnderssonDtoBuilder().build();
  public static final PersonIdDTO ATHENA_REACT_ANDERSSON_PERSON_ID_DTO = PersonIdDTO.builder()
      .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
      .id(ATHENA_REACT_ANDERSSON_ID)
      .build();
  public static final PatientDTO ALVE_REACT_ALFREDSSON_DTO = alveReactAlfredssonDtoBuilder().build();
  public static final PatientDTO ATLAS_REACT_ABRAHAMSSON_DTO = atlasReactAbrahamssonDtoBuilder().build();
  public static final PatientDTO ANONYMA_REACT_ATTILA_DTO = anonymaReactAttilaDtoBuilder().build();

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

  public static PatientDTOBuilder atlasReactAbrahamssonDtoBuilder() {
    return PatientDTO.builder()
        .id(
            PersonIdDTO.builder()
                .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                .id(ATLAS_REACT_ABRAHAMSSON_ID)
                .build()
        )
        .city(ATLAS_REACT_ABRAHAMSSON_CITY)
        .street(ATLAS_REACT_ABRAHAMSSON_STREET)
        .zipCode(ATLAS_REACT_ABRAHAMSSON_ZIP_CODE)
        .firstName(ATLAS_REACT_ABRAHAMSSON_FIRST_NAME)
        .lastName(ATLAS_REACT_ABRAHAMSSON_LAST_NAME)
        .middleName(ATLAS_REACT_ABRAHAMSSON_MIDDLE_NAME)
        .fullName(ATLAS_REACT_ABRAHAMSSON_FULL_NAME)
        .deceased(ATLAS_REACT_ABRAHAMSSON_DECEASED.value())
        .protectedPerson(ATLAS_REACT_ABRAHAMSSON_PROTECTED_PERSON.value())
        .testIndicated(ATLAS_REACT_ABRAHAMSSON_TEST_INDICATED.value());
  }

  public static PatientDTOBuilder anonymaReactAttilaDtoBuilder() {
    return PatientDTO.builder()
        .id(
            PersonIdDTO.builder()
                .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
                .id(ANONYMA_REACT_ATTILA_ID)
                .build()
        )
        .city(ANONYMA_REACT_ATTILA_CITY)
        .street(ANONYMA_REACT_ATTILA_STREET)
        .zipCode(ANONYMA_REACT_ATTILA_ZIP_CODE)
        .firstName(ANONYMA_REACT_ATTILA_FIRST_NAME)
        .lastName(ANONYMA_REACT_ATTILA_LAST_NAME)
        .middleName(ANONYMA_REACT_ATTILA_MIDDLE_NAME)
        .fullName(ANONYMA_REACT_ATTILA_FULL_NAME)
        .deceased(ANONYMA_REACT_ATTILA_DECEASED.value())
        .protectedPerson(ANONYMA_REACT_ATTILA_PROTECTED_PERSON.value())
        .testIndicated(ANONYMA_REACT_ATTILA_TEST_INDICATED.value());
  }
}
