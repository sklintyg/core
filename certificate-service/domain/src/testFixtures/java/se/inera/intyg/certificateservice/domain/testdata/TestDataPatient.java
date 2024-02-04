package se.inera.intyg.certificateservice.domain.testdata;

import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.patient.model.PersonAddress;
import se.inera.intyg.certificateservice.domain.patient.model.PersonId;
import se.inera.intyg.certificateservice.domain.patient.model.PersonIdType;

public class TestDataPatient {

  public static final Patient ATHENA_REACT_ANDERSSON = athenaReactAnderssonBuilder().build();
  public static final Patient ALVE_REACT_ALFREDSSON = alveReactAlfredssonBuilder().build();

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
}
