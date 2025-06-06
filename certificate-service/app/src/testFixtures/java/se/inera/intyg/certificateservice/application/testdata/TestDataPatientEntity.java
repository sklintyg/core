package se.inera.intyg.certificateservice.application.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_DECEASED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_PROTECTED_PERSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_TEST_INDICATED;

import se.inera.intyg.certificateservice.domain.common.model.PersonIdType;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientIdTypeEntity;

public class TestDataPatientEntity {

  private TestDataPatientEntity() {
    throw new IllegalStateException("Utility class");
  }

  public static final PatientEntity ATHENA_REACT_ANDERSSON_ENTITY = athenaReactAnderssonEntityBuilder().build();

  public static PatientEntity.PatientEntityBuilder athenaReactAnderssonEntityBuilder() {
    return PatientEntity.builder()
        .id(ATHENA_REACT_ANDERSSON_ID)
        .protectedPerson(ATHENA_REACT_ANDERSSON_PROTECTED_PERSON.value())
        .testIndicated(ATHENA_REACT_ANDERSSON_TEST_INDICATED.value())
        .deceased(ATHENA_REACT_ANDERSSON_DECEASED.value())
        .type(PatientIdTypeEntity.builder()
            .type(PersonIdType.PERSONAL_IDENTITY_NUMBER.name())
            .build())
        .firstName(ATHENA_REACT_ANDERSSON_FIRST_NAME)
        .middleName(ATHENA_REACT_ANDERSSON_MIDDLE_NAME)
        .lastName(ATHENA_REACT_ANDERSSON_LAST_NAME);
  }

}
