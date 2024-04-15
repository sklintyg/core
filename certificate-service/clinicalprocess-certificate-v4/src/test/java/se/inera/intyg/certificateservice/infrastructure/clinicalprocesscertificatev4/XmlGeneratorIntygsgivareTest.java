package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import jakarta.xml.bind.JAXBElement;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

class XmlGeneratorIntygsgivareTest {

  private static final String LAKARE = "Läkare";
  private static final String BARNMOSRKA = "Barnmorska";
  private static final String SJUKSKOTERSKA = "Sjuksköterska";
  private static final Role DOCTOR = Role.DOCTOR;
  private static final Role PRIVATE_DOCTOR = Role.PRIVATE_DOCTOR;
  private static final Role NURSE = Role.NURSE;
  private static final Role MIDWIFE = Role.MIDWIFE;
  private static final String INTYGSGIVARE_QUESTION_ID = "1.2";
  private static final String CODE_SYSTEM = "KV_FKMU_0008";

  private XmlGeneratorIntygsgivare xmlGeneratorIntygsgivare;

  @BeforeEach
  void setUp() {
    xmlGeneratorIntygsgivare = new XmlGeneratorIntygsgivare();
  }

  @Test
  void shouldIncludeQuestionId() {
    final var response = xmlGeneratorIntygsgivare.generate(DOCTOR);
    assertEquals(INTYGSGIVARE_QUESTION_ID, response.getId());
  }

  @Test
  void shouldReturnJAXBElement() {
    final var response = xmlGeneratorIntygsgivare.generate(DOCTOR);
    assertEquals(JAXBElement.class, response.getContent().get(0).getClass());
  }

  @Test
  void shouldContainCVType() {
    final var cvType = getCVType(xmlGeneratorIntygsgivare.generate(DOCTOR));
    assertEquals(CVType.class, Objects.requireNonNull(cvType).getClass());
  }

  @Test
  void shouldIncludeCodeSystem() {
    final var cvType = getCVType(xmlGeneratorIntygsgivare.generate(DOCTOR));
    assertEquals(CODE_SYSTEM, Objects.requireNonNull(cvType).getCodeSystem());
  }

  @Test
  void shouldReturnCodeLAKAREFOrRoleDoctor() {
    final var cvType = getCVType(xmlGeneratorIntygsgivare.generate(DOCTOR));
    assertEquals(LAKARE.toUpperCase(), Objects.requireNonNull(cvType).getCode());
    assertEquals(LAKARE, Objects.requireNonNull(cvType).getDisplayName());
  }

  @Test
  void shouldReturnCodeLAKAREForRolePrivateDoctor() {
    final var cvType = getCVType(xmlGeneratorIntygsgivare.generate(PRIVATE_DOCTOR));
    assertEquals(LAKARE.toUpperCase(), Objects.requireNonNull(cvType).getCode());
    assertEquals(LAKARE, Objects.requireNonNull(cvType).getDisplayName());
  }

  @Test
  void shouldReturnCodeSJUKSKOTERSKAForRoleNurse() {
    final var cvType = getCVType(xmlGeneratorIntygsgivare.generate(NURSE));
    assertEquals(SJUKSKOTERSKA.toUpperCase(), Objects.requireNonNull(cvType).getCode());
    assertEquals(SJUKSKOTERSKA, Objects.requireNonNull(cvType).getDisplayName());
  }

  @Test
  void shouldReturnCodeBARNMORSKAForRoleMidwife() {
    final var cvType = getCVType(xmlGeneratorIntygsgivare.generate(MIDWIFE));
    assertEquals(BARNMOSRKA.toUpperCase(), Objects.requireNonNull(cvType).getCode());
    assertEquals(BARNMOSRKA, Objects.requireNonNull(cvType).getDisplayName());
  }

  @Test
  void shouldThrowExceptionForUnknownRole() {
    assertThrows(IllegalStateException.class, () ->
        xmlGeneratorIntygsgivare.generate(Role.CARE_ADMIN));
  }

  private CVType getCVType(Delsvar answer) {
    final var content = answer.getContent().get(0);
    if (content instanceof JAXBElement<?> jaxbElement) {
      if (jaxbElement.getValue() instanceof CVType) {
        return (CVType) jaxbElement.getValue();
      }
    }
    return null;
  }
}
