package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemIcd10Se.ICD_10_SE_CODE_SYSTEM;
import static se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.TestMarshaller.getElement;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.w3c.dom.Element;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisListItem;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisTerminology;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.Diagnosis;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.DiagnosisCode;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.DiagnosisDescription;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill.converter.PrefillCustomDiagnosisListConverter;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ObjectFactory;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

@ExtendWith(MockitoExtension.class)
class PrefillCustomDiagnosisListConverterTest {

  private static final ElementId ELEMENT_ID = new ElementId("4");
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
              .terminology(List.of(
                  new ElementDiagnosisTerminology("ICD-10-SE", "ICD-10-SE", "1.2.752.116.1.1.1",
                      List.of("1.2.752.116.1.1.1", "1.2.752.116.1.1.1.1.8",
                          "1.2.752.116.1.1.1.1.3"))))
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
                          .terminology("ICD-10-SE")
                          .build(),
                      ElementValueDiagnosis.builder()
                          .id(DIAGNOS_2_FIELD_ID)
                          .code(DIAGNOS_CODE_2)
                          .description(DIAGNOS_DESCRIPTION_2)
                          .terminology("ICD-10-SE")
                          .build()
                  )
              )
              .build()
      ).build();

  private static final ElementData EXPECTED_ELEMENT_DATA_MIN = ElementData.builder()
      .id(ELEMENT_ID)
      .value(
          ElementValueDiagnosisList.builder()
              .diagnoses(
                  List.of(
                      ElementValueDiagnosis.builder()
                          .id(DIAGNOS_FIELD_ID)
                          .code(DIAGNOS_CODE)
                          .description(DIAGNOS_DESCRIPTION)
                          .terminology("ICD-10-SE")
                          .build()
                  )
              )
              .build()
      ).build();

  @Mock
  private DiagnosisCodeRepository diagnosisCodeRepository;

  @InjectMocks
  private PrefillCustomDiagnosisListConverter converter;

  @Test
  void shouldReturnNullIfNoAnswerExists() {
    final var prefill = new Forifyllnad();
    final var result = converter.prefillAnswer(SPECIFICATION, prefill);
    assertNull(result);
  }

  @Test
  void shouldReturnPrefillAnswerWithWrongNumberOfSubAnswers() {
    final var prefill = new Forifyllnad();
    final var svar = new Svar();
    svar.setId(SPECIFICATION.id().id());
    prefill.getSvar().add(svar);
    final var expectedErrors = List.of(
        PrefillError.wrongNumberOfSubAnswers(
            SPECIFICATION.id().id(), 1, 0)
    );

    final var result = converter.prefillAnswer(SPECIFICATION, prefill);

    assertEquals(expectedErrors, result.getErrors());
  }

  @Test
  void shouldReturnPrefillAnswerWithInvalidDiagnosisCode() {
    final var prefill = getPrefill();
    doReturn(Optional.empty()).when(diagnosisCodeRepository)
        .findByCode(new DiagnosisCode(DIAGNOS_CODE));
    final var expectedErrors = List.of(
        PrefillError.invalidDiagnosisCode(DIAGNOS_CODE),
        PrefillError.invalidDiagnosisCode(DIAGNOS_CODE_2)
    );

    final var result = converter.prefillAnswer(SPECIFICATION, prefill);

    assertEquals(expectedErrors, result.getErrors());
  }

  @Test
  void shouldReturnElementDataIfOneAnswerHasInvalidFormat() {
    final var prefill = getPrefill();
    doReturn(Optional.empty()).when(diagnosisCodeRepository)
        .findByCode(new DiagnosisCode(DIAGNOS_CODE_2));
    doReturn(Optional.of(Diagnosis.builder().build())).when(diagnosisCodeRepository)
        .findByCode(new DiagnosisCode(DIAGNOS_CODE));

    final var result = converter.prefillAnswer(SPECIFICATION, prefill);

    assertAll(
        () -> assertEquals(EXPECTED_ELEMENT_DATA_MIN, result.getElementData()),
        () -> assertEquals(1, result.getErrors().size()),
        () -> assertEquals(PrefillErrorType.INVALID_DIAGNOSIS_CODE,
            result.getErrors().getFirst().type())
    );
  }

  @Test
  void shouldReturnPrefillAnswerIfAnswerExists() {
    final var prefill = getPrefill();
    doReturn(Optional.of(Diagnosis.builder().build())).when(diagnosisCodeRepository)
        .findByCode(new DiagnosisCode(DIAGNOS_CODE));
    doReturn(Optional.of(Diagnosis.builder().build())).when(diagnosisCodeRepository)
        .findByCode(new DiagnosisCode(DIAGNOS_CODE_2));

    final var result = converter.prefillAnswer(SPECIFICATION, prefill);

    assertEquals(EXPECTED_ELEMENT_DATA, result.getElementData());
  }

  @Test
  void shouldReturnPrefillAnswerWithDescriptionFromDiagnosisRepository() {
    final var expectedDescription = "expectedDescription_code2";
    final var prefill = getPrefillMissingDescription();

    doReturn(Optional.of(Diagnosis.builder().build()))
        .when(diagnosisCodeRepository)
        .findByCode(new DiagnosisCode(DIAGNOS_CODE));

    doReturn(Optional.of(Diagnosis.builder().build()))
        .when(diagnosisCodeRepository)
        .findByCode(new DiagnosisCode(DIAGNOS_CODE_2));

    doReturn(Diagnosis.builder()
        .description(new DiagnosisDescription(expectedDescription))
        .build())
        .when(diagnosisCodeRepository)
        .getByCode(new DiagnosisCode(DIAGNOS_CODE_2));
    final var result = converter.prefillAnswer(SPECIFICATION, prefill);

    final var value = (ElementValueDiagnosisList) result.getElementData().value();

    assertEquals(expectedDescription,
        value.diagnoses().stream()
            .filter(d -> DIAGNOS_CODE_2.equals(d.code()))
            .findFirst()
            .get()
            .description());
  }


  @Test
  void shouldMapEquivalentCodeSystemsOids() {

    final var prefill = new Forifyllnad();
    final var svar = new Svar();
    svar.setId(SPECIFICATION.id().id());

    final var delsvar1Description = new Delsvar();
    delsvar1Description.setId("4.1");
    final var delsvar1Code = new Delsvar();
    delsvar1Code.setId("4.2");
    final var delsvar2Description = new Delsvar();
    delsvar2Description.setId("4.3");
    final var delsvar2Code = new Delsvar();
    delsvar2Code.setId("4.4");

    delsvar1Description.getContent().add(DIAGNOS_DESCRIPTION);
    delsvar1Code.getContent()
        .add(createCVTypeElement(DIAGNOS_CODE, "1.2.752.116.1.1.1.1.8"));

    delsvar2Description.getContent().add(DIAGNOS_DESCRIPTION_2);
    delsvar2Code.getContent()
        .add(createCVTypeElement(DIAGNOS_CODE_2, "1.2.752.116.1.1.1.1.3"));

    svar.getDelsvar().add(delsvar1Description);
    svar.getDelsvar().add(delsvar1Code);

    svar.getDelsvar().add(delsvar2Description);
    svar.getDelsvar().add(delsvar2Code);

    prefill.getSvar().add(svar);

    doReturn(Optional.of(Diagnosis.builder().build())).when(diagnosisCodeRepository)
        .findByCode(new DiagnosisCode(DIAGNOS_CODE));
    doReturn(Optional.of(Diagnosis.builder().build())).when(diagnosisCodeRepository)
        .findByCode(new DiagnosisCode(DIAGNOS_CODE_2));

    final var result = converter.prefillAnswer(SPECIFICATION, prefill);

    assertEquals(EXPECTED_ELEMENT_DATA, result.getElementData());
  }

  private static Forifyllnad getPrefill() {
    final var prefill = new Forifyllnad();
    final var svar = new Svar();
    svar.setId(SPECIFICATION.id().id());

    final var delsvar1Description = new Delsvar();
    delsvar1Description.setId("4.1");
    final var delsvar1Code = new Delsvar();
    delsvar1Code.setId("4.2");
    final var delsvar2Description = new Delsvar();
    delsvar2Description.setId("4.3");
    final var delsvar2Code = new Delsvar();
    delsvar2Code.setId("4.4");

    delsvar1Description.getContent().add(DIAGNOS_DESCRIPTION);
    delsvar1Code.getContent().add(createCVTypeElement(DIAGNOS_CODE));

    delsvar2Description.getContent().add(DIAGNOS_DESCRIPTION_2);
    delsvar2Code.getContent().add(createCVTypeElement(DIAGNOS_CODE_2));

    svar.getDelsvar().add(delsvar1Description);
    svar.getDelsvar().add(delsvar1Code);

    svar.getDelsvar().add(delsvar2Description);
    svar.getDelsvar().add(delsvar2Code);

    prefill.getSvar().add(svar);
    return prefill;
  }

  private static Forifyllnad getPrefillMissingDescription() {
    final var prefill = new Forifyllnad();
    final var svar = new Svar();
    svar.setId(SPECIFICATION.id().id());

    final var delsvar1Description = new Delsvar();
    delsvar1Description.setId("4.1");
    final var delsvar1Code = new Delsvar();
    delsvar1Code.setId("4.2");
    final var delsvar2Code = new Delsvar();
    delsvar2Code.setId("4.4");

    delsvar1Description.getContent().add(DIAGNOS_DESCRIPTION);
    delsvar1Code.getContent().add(createCVTypeElement(DIAGNOS_CODE));

    delsvar2Code.getContent().add(createCVTypeElement(DIAGNOS_CODE_2));

    svar.getDelsvar().add(delsvar1Description);
    svar.getDelsvar().add(delsvar1Code);

    svar.getDelsvar().add(delsvar2Code);

    prefill.getSvar().add(svar);
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

