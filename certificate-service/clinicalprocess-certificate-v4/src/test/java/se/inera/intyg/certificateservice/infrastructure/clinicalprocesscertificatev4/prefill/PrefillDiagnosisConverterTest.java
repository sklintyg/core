package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemIcd10Se.DIAGNOS_ICD_10_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemIcd10Se.ICD_10_SE_CODE_SYSTEM;
import static se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.TestMarshaller.getElement;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.w3c.dom.Element;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisListItem;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.Diagnosis;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.DiagnosisCode;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.DiagnosisDescription;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemIcd10Se;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter.PrefillDiagnosisConverter;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@ExtendWith(MockitoExtension.class)
class PrefillDiagnosisConverterTest {

  private static final ElementId ELEMENT_ID = new ElementId("1");
  private static final FieldId DIAGNOS_FIELD_ID = new FieldId("F1");
  private static final FieldId FIELD_ID = new FieldId("F");
  private static final FieldId DIAGNOS_2_FIELD_ID = new FieldId("F2");
  private static final String DIAGNOS_CODE = "code1";
  private static final String DIAGNOS_CODE_2 = "code2";

  private static final String DIAGNOS_DESCRIPTION = "diagnos_description";
  private static final String DIAGNOS_DESCRIPTION_2 = "diagnos_description_2";

  private static final ElementSpecification SPECIFICATION = ElementSpecification.builder()
      .id(ELEMENT_ID)
      .configuration(
          ElementConfigurationDiagnosis.builder()
              .id(FIELD_ID)
              .terminology(
                  List.of(CodeSystemIcd10Se.terminology())
              )
              .list(
                  List.of(
                      new ElementDiagnosisListItem(DIAGNOS_FIELD_ID),
                      new ElementDiagnosisListItem(DIAGNOS_2_FIELD_ID)
                  )
              )
              .build()
      )
      .build();
  private static final ElementData EXPECTED_ELEMENT_DATA = ElementData.builder()
      .id(ELEMENT_ID)
      .value(
          ElementValueDiagnosisList.builder()
              .diagnoses(
                  List.of(
                      ElementValueDiagnosis.builder()
                          .id(DIAGNOS_FIELD_ID)
                          .code(DIAGNOS_CODE)
                          .description(DIAGNOS_DESCRIPTION)
                          .terminology(DIAGNOS_ICD_10_ID)
                          .build(),
                      ElementValueDiagnosis.builder()
                          .id(DIAGNOS_2_FIELD_ID)
                          .code(DIAGNOS_CODE_2)
                          .description(DIAGNOS_DESCRIPTION_2)
                          .terminology(DIAGNOS_ICD_10_ID)
                          .build()
                  )
              )
              .build()
      ).build();

  @Mock
  private DiagnosisCodeRepository diagnosisCodeRepository;

  private PrefillDiagnosisConverter prefillDiagnosisConverter;

  @BeforeEach
  void setUp() {
    prefillDiagnosisConverter = new PrefillDiagnosisConverter(
        diagnosisCodeRepository
    );
  }

  @Test
  void shouldReturnSupportsDiagnosis() {
    assertEquals(ElementConfigurationDiagnosis.class,
        prefillDiagnosisConverter.supports());
  }

  @Nested
  class PrefillAnswerWithForifyllnad {

    @Test
    void shouldReturnNullIfNoAnswerExists() {
      Forifyllnad prefill = new Forifyllnad();

      PrefillAnswer result = prefillDiagnosisConverter.prefillAnswer(
          SPECIFICATION,
          prefill
      );

      assertNull(result);
    }

    @Test
    void shouldReturnPrefillAnswerWithWrongNumberOfSubAnswers() {
      final var prefill = new Forifyllnad();
      final var svar = new Svar();

      svar.setId(SPECIFICATION.id().id());
      svar.setInstans(1);

      prefill.getSvar().add(svar);

      final var result = prefillDiagnosisConverter.prefillAnswer(SPECIFICATION, prefill);

      assertEquals(
          PrefillErrorType.WRONG_NUMBER_OF_ANSWERS,
          result.getErrors().getLast().type()
      );
    }

    @Test
    void shouldReturnPrefillAnswerWithInvalidDiagnosisCode() {
      final var prefill = new Forifyllnad();
      final var svar = new Svar();
      final var svar2 = new Svar();

      svar.setId(SPECIFICATION.id().id());
      svar.setInstans(1);
      svar2.setId(SPECIFICATION.id().id());
      svar.setInstans(2);

      final var delsvar1Code = new Delsvar();
      final var delsvar1Description = new Delsvar();
      final var delsvar2Code = new Delsvar();
      final var delsvar2Description = new Delsvar();

      delsvar1Code.setId("%s.2".formatted(SPECIFICATION.id().id()));
      delsvar2Code.setId("%s.2".formatted(SPECIFICATION.id().id()));
      delsvar1Description.setId("%s.1".formatted(SPECIFICATION.id().id()));
      delsvar2Description.setId("%s.1".formatted(SPECIFICATION.id().id()));

      delsvar1Code.getContent().add(createCVTypeElement(DIAGNOS_CODE));
      delsvar2Code.getContent().add(createCVTypeElement(DIAGNOS_CODE_2));
      delsvar1Description.getContent().add(DIAGNOS_DESCRIPTION);
      delsvar2Description.getContent().add(DIAGNOS_DESCRIPTION_2);

      svar.getDelsvar().add(delsvar1Code);
      svar.getDelsvar().add(delsvar1Description);
      svar2.getDelsvar().add(delsvar2Code);
      svar2.getDelsvar().add(delsvar2Description);

      prefill.getSvar().add(svar);
      prefill.getSvar().add(svar2);

      doReturn(Optional.empty())
          .when(diagnosisCodeRepository).findByCode(new DiagnosisCode(DIAGNOS_CODE));

      final var result = prefillDiagnosisConverter.prefillAnswer(SPECIFICATION, prefill);

      assertEquals(
          PrefillErrorType.INVALID_DIAGNOSIS_CODE,
          result.getErrors().getFirst().type()
      );
    }

    @Test
    void shouldReturnElementDataIfOneAnswerHasInvalidFormat() {
      final var prefill = getPrefill();
      final var invalidAnswer = new Svar();

      invalidAnswer.setId(SPECIFICATION.id().id());

      final var subAnswerCode = new Delsvar();
      final var subAnswerDescription = new Delsvar();

      subAnswerCode.setId("%s.2".formatted(SPECIFICATION.id().id()));
      subAnswerDescription.setId("%s.1".formatted(SPECIFICATION.id().id()));

      subAnswerCode.getContent().add(createCVTypeElement("unknown code"));
      subAnswerDescription.getContent().add(DIAGNOS_DESCRIPTION);

      invalidAnswer.getDelsvar().add(subAnswerCode);
      invalidAnswer.getDelsvar().add(subAnswerDescription);
      prefill.getSvar().add(invalidAnswer);

      doReturn(Optional.of(Diagnosis.builder().build()))
          .when(diagnosisCodeRepository).findByCode(new DiagnosisCode(DIAGNOS_CODE));
      doReturn(Optional.of(Diagnosis.builder().build()))
          .when(diagnosisCodeRepository).findByCode(new DiagnosisCode(DIAGNOS_CODE_2));
      final var result = prefillDiagnosisConverter.prefillAnswer(SPECIFICATION, prefill);

      assertAll(
          () -> assertEquals(EXPECTED_ELEMENT_DATA, result.getElementData()),
          () -> assertEquals(1, result.getErrors().size()),
          () -> assertEquals(PrefillErrorType.INVALID_DIAGNOSIS_CODE,
              result.getErrors().getFirst().type())
      );
    }

    @Test
    void shouldReturnPrefillAnswerIfAnswerExists() {
      final var prefill = getPrefill();

      doReturn(Optional.of(Diagnosis.builder().build()))
          .when(diagnosisCodeRepository).findByCode(new DiagnosisCode(DIAGNOS_CODE));

      doReturn(Optional.of(Diagnosis.builder().build()))
          .when(diagnosisCodeRepository).findByCode(new DiagnosisCode(DIAGNOS_CODE_2));

      final var result = prefillDiagnosisConverter.prefillAnswer(SPECIFICATION, prefill);

      final var expected = PrefillAnswer.builder()
          .elementData(EXPECTED_ELEMENT_DATA)
          .build();

      assertEquals(expected, result);
    }

    @Test
    void shouldReturnPrefillAnswerWithDescriptionFromDiagnosisRepository() {
      final var expectedDescription = "expectedDescription";

      final var prefill = new Forifyllnad();
      final var svar = new Svar();

      svar.setId(SPECIFICATION.id().id());
      svar.setInstans(1);

      final var delsvar1Code = new Delsvar();

      delsvar1Code.setId("%s.2".formatted(SPECIFICATION.id().id()));

      delsvar1Code.getContent().add(createCVTypeElement(DIAGNOS_CODE));

      svar.getDelsvar().add(delsvar1Code);

      prefill.getSvar().add(svar);

      doReturn(Optional.of(Diagnosis.builder().build()))
          .when(diagnosisCodeRepository).findByCode(new DiagnosisCode(DIAGNOS_CODE));

      doReturn(
          Diagnosis.builder()
              .description(
                  new DiagnosisDescription(expectedDescription)
              )
              .build()
      ).when(diagnosisCodeRepository).getByCode(new DiagnosisCode(DIAGNOS_CODE));

      final var result = prefillDiagnosisConverter.prefillAnswer(SPECIFICATION, prefill);

      final var value = (ElementValueDiagnosisList) result.getElementData().value();
      assertEquals(expectedDescription, value.diagnoses().getFirst().description());
    }
  }

  @Test
  void shouldMapEquivalentCodeSystemsOids() {

    final var prefill = new Forifyllnad();
    final var svar = new Svar();
    final var svar2 = new Svar();

    svar.setId(SPECIFICATION.id().id());
    svar.setInstans(1);
    svar2.setId(SPECIFICATION.id().id());
    svar2.setInstans(2);

    final var delsvar1Code = new Delsvar();
    final var delsvar1Description = new Delsvar();
    final var delsvar2Code = new Delsvar();
    final var delsvar2Description = new Delsvar();

    delsvar1Code.setId("%s.2".formatted(SPECIFICATION.id().id()));
    delsvar2Code.setId("%s.2".formatted(SPECIFICATION.id().id()));
    delsvar1Description.setId("%s.1".formatted(SPECIFICATION.id().id()));
    delsvar2Description.setId("%s.1".formatted(SPECIFICATION.id().id()));

    delsvar1Code.getContent()
        .add(createCVTypeElement(DIAGNOS_CODE, "1.2.752.116.1.1.1.1.8"));
    delsvar2Code.getContent()
        .add(createCVTypeElement(DIAGNOS_CODE_2, "1.2.752.116.1.1.1.1.3"));
    delsvar1Description.getContent().add(DIAGNOS_DESCRIPTION);
    delsvar2Description.getContent().add(DIAGNOS_DESCRIPTION_2);

    svar.getDelsvar().add(delsvar1Code);
    svar.getDelsvar().add(delsvar1Description);
    svar2.getDelsvar().add(delsvar2Code);
    svar2.getDelsvar().add(delsvar2Description);

    prefill.getSvar().add(svar);
    prefill.getSvar().add(svar2);

    doReturn(Optional.of(Diagnosis.builder().build()))
        .when(diagnosisCodeRepository).findByCode(new DiagnosisCode(DIAGNOS_CODE));

    doReturn(Optional.of(Diagnosis.builder().build()))
        .when(diagnosisCodeRepository).findByCode(new DiagnosisCode(DIAGNOS_CODE_2));

    final var result = prefillDiagnosisConverter.prefillAnswer(SPECIFICATION, prefill);

    final var expected = PrefillAnswer.builder()
        .elementData(EXPECTED_ELEMENT_DATA)
        .build();

    assertEquals(expected, result);
  }

  private static Forifyllnad getPrefill() {
    final var prefill = new Forifyllnad();
    final var svar = new Svar();
    final var svar2 = new Svar();

    svar.setId(SPECIFICATION.id().id());
    svar.setInstans(1);
    svar2.setId(SPECIFICATION.id().id());
    svar2.setInstans(2);

    final var delsvar1Code = new Delsvar();
    final var delsvar1Description = new Delsvar();
    final var delsvar2Code = new Delsvar();
    final var delsvar2Description = new Delsvar();

    delsvar1Code.setId("%s.2".formatted(SPECIFICATION.id().id()));
    delsvar2Code.setId("%s.2".formatted(SPECIFICATION.id().id()));
    delsvar1Description.setId("%s.1".formatted(SPECIFICATION.id().id()));
    delsvar2Description.setId("%s.1".formatted(SPECIFICATION.id().id()));

    delsvar1Code.getContent().add(createCVTypeElement(DIAGNOS_CODE));
    delsvar2Code.getContent().add(createCVTypeElement(DIAGNOS_CODE_2));
    delsvar1Description.getContent().add(DIAGNOS_DESCRIPTION);
    delsvar2Description.getContent().add(DIAGNOS_DESCRIPTION_2);

    svar.getDelsvar().add(delsvar1Code);
    svar.getDelsvar().add(delsvar1Description);
    svar2.getDelsvar().add(delsvar2Code);
    svar2.getDelsvar().add(delsvar2Description);

    prefill.getSvar().add(svar);
    prefill.getSvar().add(svar2);
    return prefill;
  }

  private static Element createCVTypeElement(String code) {
    return createCVTypeElement(code, ICD_10_SE_CODE_SYSTEM);
  }

  private static Element createCVTypeElement(String code, String codeSystem) {
    final var cvType = new CVType();
    cvType.setCode(code);
    cvType.setDisplayName("displayName");
    cvType.setCodeSystem(codeSystem);
    final var factory = new ObjectFactory();
    return getElement(cvType, factory::createCv);
  }
}