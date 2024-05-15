package se.inera.intyg.certificateservice.domain.testdata;

import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.common.model.PersonIdType;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.patient.model.PersonAddress;

public class TestDataPatient {

  public static final Patient ATHENA_REACT_ANDERSSON = athenaReactAnderssonBuilder().build();
  public static final Patient ALVE_REACT_ALFREDSSON = alveReactAlfredssonBuilder().build();
  public static final Patient ATLAS_REACT_ABRAHAMSSON = atlasReactAbrahamssonBuilder().build();
  public static final Patient ANONYMA_REACT_ATTILA = anonymaReactAttilaBuilder().build();

  public static Patient.PatientBuilder athenaReactAnderssonBuilder() {
    return Patient.builder()
        .id(
            PersonId.builder()
                .id(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID)
                .type(PersonIdType.PERSONAL_IDENTITY_NUMBER)
                .build()
        )
        .name(
            Name.builder()
                .firstName(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_FIRST_NAME)
                .middleName(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_MIDDLE_NAME)
                .lastName(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_LAST_NAME)
                .build()
        )
        .address(
            PersonAddress.builder()
                .street(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_STREET)
                .city(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_CITY)
                .zipCode(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ZIP_CODE)
                .build()
        )
        .testIndicated(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_TEST_INDICATED)
        .deceased(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_DECEASED)
        .protectedPerson(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_PROTECTED_PERSON);
  }

  public static Patient.PatientBuilder alveReactAlfredssonBuilder() {
    return Patient.builder()
        .id(
            PersonId.builder()
                .id(TestDataPatientConstants.ALVE_REACT_ALFREDSSON_ID)
                .type(PersonIdType.PERSONAL_IDENTITY_NUMBER)
                .build()
        )
        .name(
            Name.builder()
                .firstName(TestDataPatientConstants.ALVE_REACT_ALFREDSSON_FIRST_NAME)
                .middleName(TestDataPatientConstants.ALVE_REACT_ALFREDSSON_MIDDLE_NAME)
                .lastName(TestDataPatientConstants.ALVE_REACT_ALFREDSSON_LAST_NAME)
                .build()
        )
        .address(
            PersonAddress.builder()
                .street(TestDataPatientConstants.ALVE_REACT_ALFREDSSON_STREET)
                .city(TestDataPatientConstants.ALVE_REACT_ALFREDSSON_CITY)
                .zipCode(TestDataPatientConstants.ALVE_REACT_ALFREDSSON_ZIP_CODE)
                .build()
        )
        .testIndicated(TestDataPatientConstants.ALVE_REACT_ALFREDSSON_TEST_INDICATED)
        .deceased(TestDataPatientConstants.ALVE_REACT_ALFREDSSON_DECEASED)
        .protectedPerson(TestDataPatientConstants.ALVE_REACT_ALFREDSSON_PROTECTED_PERSON);
  }

  public static Patient.PatientBuilder atlasReactAbrahamssonBuilder() {
    return Patient.builder()
        .id(
            PersonId.builder()
                .id(TestDataPatientConstants.ATLAS_REACT_ABRAHAMSSON_ID)
                .type(PersonIdType.PERSONAL_IDENTITY_NUMBER)
                .build()
        )
        .name(
            Name.builder()
                .firstName(TestDataPatientConstants.ATLAS_REACT_ABRAHAMSSON_FIRST_NAME)
                .middleName(TestDataPatientConstants.ATLAS_REACT_ABRAHAMSSON_MIDDLE_NAME)
                .lastName(TestDataPatientConstants.ATLAS_REACT_ABRAHAMSSON_LAST_NAME)
                .build()
        )
        .address(
            PersonAddress.builder()
                .street(TestDataPatientConstants.ATLAS_REACT_ABRAHAMSSON_STREET)
                .city(TestDataPatientConstants.ATLAS_REACT_ABRAHAMSSON_CITY)
                .zipCode(TestDataPatientConstants.ATLAS_REACT_ABRAHAMSSON_ZIP_CODE)
                .build()
        )
        .testIndicated(TestDataPatientConstants.ATLAS_REACT_ABRAHAMSSON_TEST_INDICATED)
        .deceased(TestDataPatientConstants.ATLAS_REACT_ABRAHAMSSON_DECEASED)
        .protectedPerson(TestDataPatientConstants.ATLAS_REACT_ABRAHAMSSON_PROTECTED_PERSON);
  }

  public static Patient.PatientBuilder anonymaReactAttilaBuilder() {
    return Patient.builder()
        .id(
            PersonId.builder()
                .id(TestDataPatientConstants.ANONYMA_REACT_ATTILA_ID)
                .type(PersonIdType.PERSONAL_IDENTITY_NUMBER)
                .build()
        )
        .name(
            Name.builder()
                .firstName(TestDataPatientConstants.ANONYMA_REACT_ATTILA_FIRST_NAME)
                .middleName(TestDataPatientConstants.ANONYMA_REACT_ATTILA_MIDDLE_NAME)
                .lastName(TestDataPatientConstants.ANONYMA_REACT_ATTILA_LAST_NAME)
                .build()
        )
        .address(
            PersonAddress.builder()
                .street(TestDataPatientConstants.ANONYMA_REACT_ATTILA_STREET)
                .city(TestDataPatientConstants.ANONYMA_REACT_ATTILA_CITY)
                .zipCode(TestDataPatientConstants.ANONYMA_REACT_ATTILA_ZIP_CODE)
                .build()
        )
        .testIndicated(TestDataPatientConstants.ANONYMA_REACT_ATTILA_TEST_INDICATED)
        .deceased(TestDataPatientConstants.ANONYMA_REACT_ATTILA_DECEASED)
        .protectedPerson(TestDataPatientConstants.ANONYMA_REACT_ATTILA_PROTECTED_PERSON);
  }
}
