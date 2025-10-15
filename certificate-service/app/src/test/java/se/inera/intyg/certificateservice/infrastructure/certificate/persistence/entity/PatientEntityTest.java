package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.testdata.TestDataPatientEntity.ATHENA_REACT_ANDERSSON_ENTITY;
import static se.inera.intyg.certificateservice.application.testdata.TestDataPatientEntity.athenaReactAnderssonEntityBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ALVE_REACT_ALFREDSSON_ID;

import org.junit.jupiter.api.Test;

class PatientEntityTest {

  @Test
  void shallUpdateFields() {
    var target = athenaReactAnderssonEntityBuilder().build();
    var originalId = target.getId();
    var originalKey = target.getKey();

    var source = athenaReactAnderssonEntityBuilder()
        .firstName("NewFirst")
        .middleName("NewMiddle")
        .lastName("NewLast")
        .protectedPerson(!ATHENA_REACT_ANDERSSON_ENTITY.isProtectedPerson())
        .deceased(!ATHENA_REACT_ANDERSSON_ENTITY.isDeceased())
        .testIndicated(!ATHENA_REACT_ANDERSSON_ENTITY.isTestIndicated())
        .type(new PatientIdTypeEntity())
        .build();

    target.updateWith(source);

    assertEquals("NewFirst", target.getFirstName());
    assertEquals("NewMiddle", target.getMiddleName());
    assertEquals("NewLast", target.getLastName());
    assertEquals(!ATHENA_REACT_ANDERSSON_ENTITY.isProtectedPerson(), target.isProtectedPerson());
    assertEquals(!ATHENA_REACT_ANDERSSON_ENTITY.isDeceased(), target.isDeceased());
    assertEquals(!ATHENA_REACT_ANDERSSON_ENTITY.isTestIndicated(), target.isTestIndicated());
    assertSame(source.getType(), target.getType(), "type should be replaced");
    assertEquals(originalId, target.getId(), "id must remain unchanged");
    assertEquals(originalKey, target.getKey(), "key must remain unchanged");
  }

  @Test
  void shallThrowExceptionWhenSourceIsNull() {
    assertThrows(IllegalArgumentException.class,
        () -> ATHENA_REACT_ANDERSSON_ENTITY.updateWith(null));
  }

  @Test
  void shallNotFindDiffWhenEntityHasSameValues() {
    assertFalse(ATHENA_REACT_ANDERSSON_ENTITY.hasDiff(athenaReactAnderssonEntityBuilder().build()));
  }

  @Test
  void shallFindDiffWhenProtectedPersonDiffers() {
    assertTrue(ATHENA_REACT_ANDERSSON_ENTITY.hasDiff(athenaReactAnderssonEntityBuilder()
        .protectedPerson(!ATHENA_REACT_ANDERSSON_ENTITY.isProtectedPerson()).build()));
  }

  @Test
  void shallFindDiffWhenDeceasedDiffers() {
    assertTrue(ATHENA_REACT_ANDERSSON_ENTITY.hasDiff(athenaReactAnderssonEntityBuilder()
        .deceased(!ATHENA_REACT_ANDERSSON_ENTITY.isDeceased()).build()));
  }

  @Test
  void shallFindDiffWhenTestIndicatedDiffers() {
    assertTrue(ATHENA_REACT_ANDERSSON_ENTITY.hasDiff(athenaReactAnderssonEntityBuilder()
        .testIndicated(!ATHENA_REACT_ANDERSSON_ENTITY.isTestIndicated()).build()));
  }

  @Test
  void shallFindDiffWhenFirstNameDiffers() {
    assertTrue(ATHENA_REACT_ANDERSSON_ENTITY.hasDiff(athenaReactAnderssonEntityBuilder()
        .firstName("DifferentFirst").build()));
  }

  @Test
  void shallFindDiffWhenMiddleNameDiffers() {
    assertTrue(ATHENA_REACT_ANDERSSON_ENTITY.hasDiff(athenaReactAnderssonEntityBuilder()
        .middleName("DifferentMiddle").build()));
  }

  @Test
  void shallFindDiffWhenLastNameDiffers() {
    assertTrue(ATHENA_REACT_ANDERSSON_ENTITY.hasDiff(athenaReactAnderssonEntityBuilder()
        .lastName("DifferentLast").build()));
  }

  @Test
  void shallFindDiffWhenTypeDiffers() {
    assertTrue(ATHENA_REACT_ANDERSSON_ENTITY.hasDiff(athenaReactAnderssonEntityBuilder()
        .type(new PatientIdTypeEntity()).build()));
  }

  @Test
  void shallFindDiffWhenAllFieldsDiffer() {
    var allDiff = PatientEntity.builder()
        .id(ATHENA_REACT_ANDERSSON_ENTITY.getId())
        .firstName("F")
        .middleName("M")
        .lastName("L")
        .protectedPerson(!ATHENA_REACT_ANDERSSON_ENTITY.isProtectedPerson())
        .deceased(!ATHENA_REACT_ANDERSSON_ENTITY.isDeceased())
        .testIndicated(!ATHENA_REACT_ANDERSSON_ENTITY.isTestIndicated())
        .type(new PatientIdTypeEntity())
        .build();

    assertTrue(ATHENA_REACT_ANDERSSON_ENTITY.hasDiff(allDiff));
  }

  @Test
  void shallThrowExceptionWhenOtherIsNull() {
    assertThrows(IllegalArgumentException.class,
        () -> ATHENA_REACT_ANDERSSON_ENTITY.hasDiff(null));
  }

  @Test
  void shallThrowExceptionWhenComparingPatientsWithDifferentIds() {
    var patientWithDifferentId = athenaReactAnderssonEntityBuilder()
        .id(ALVE_REACT_ALFREDSSON_ID)
        .build();

    assertThrows(IllegalArgumentException.class,
        () -> ATHENA_REACT_ANDERSSON_ENTITY.hasDiff(patientWithDifferentId));
  }

}
