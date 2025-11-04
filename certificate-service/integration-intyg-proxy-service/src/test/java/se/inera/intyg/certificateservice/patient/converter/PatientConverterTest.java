package se.inera.intyg.certificateservice.patient.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.athenaReactAnderssonBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.common.model.PersonIdType;
import se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants;
import se.inera.intyg.certificateservice.patient.dto.PersonDTO;

class PatientConverterTest {

  private PatientConverter patientConverter;

  @BeforeEach
  void setUp() {
    patientConverter = new PatientConverter();
  }

  @Test
  void shouldConvertPatient() {
    final var personDTO = PersonDTO.builder()
        .personnummer(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID)
        .fornamn(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_FIRST_NAME)
        .mellannamn(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_MIDDLE_NAME)
        .efternamn(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_LAST_NAME)
        .postadress(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_STREET)
        .postnummer(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ZIP_CODE)
        .postort(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_CITY)
        .testIndicator(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_TEST_INDICATED.value())
        .avliden(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_DECEASED.value())
        .sekretessmarkering(
            TestDataPatientConstants.ATHENA_REACT_ANDERSSON_PROTECTED_PERSON.value())
        .build();

    final var result = patientConverter.convert(personDTO);
    assertEquals(ATHENA_REACT_ANDERSSON, result);
  }

  @Test
  void shouldConvertPatientWithCoordinationNumber() {
    final var personId = "19121260-1212";
    final var expectedPatient = athenaReactAnderssonBuilder()
        .id(
            PersonId.builder()
                .id(personId)
                .type(PersonIdType.COORDINATION_NUMBER)
                .build()
        )
        .build();

    final var personDTO = PersonDTO.builder()
        .personnummer(personId)
        .fornamn(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_FIRST_NAME)
        .mellannamn(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_MIDDLE_NAME)
        .efternamn(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_LAST_NAME)
        .postadress(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_STREET)
        .postnummer(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ZIP_CODE)
        .postort(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_CITY)
        .testIndicator(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_TEST_INDICATED.value())
        .avliden(TestDataPatientConstants.ATHENA_REACT_ANDERSSON_DECEASED.value())
        .sekretessmarkering(
            TestDataPatientConstants.ATHENA_REACT_ANDERSSON_PROTECTED_PERSON.value())
        .build();

    final var result = patientConverter.convert(personDTO);
    assertEquals(expectedPatient, result);
  }
}