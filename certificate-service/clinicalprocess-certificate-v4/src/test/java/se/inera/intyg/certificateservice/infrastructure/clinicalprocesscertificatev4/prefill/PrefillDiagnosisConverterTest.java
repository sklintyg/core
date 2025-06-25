package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemIcd10Se.DIAGNOS_ICD_10_ID;

import jakarta.xml.bind.JAXBContext;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
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
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemIcd10Se;
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
  private final PrefillDiagnosisConverter prefillDiagnosisConverter = new PrefillDiagnosisConverter(
      diagnosisCodeRepository
  );


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
    void shouldReturnPrefillAnswerIfAnswerExists() throws Exception {
      final var prefill = new Forifyllnad();
      final var svar = new Svar();
      final var svar2 = new Svar();

      svar.setId(SPECIFICATION.id().id());
      svar.setInstans(1);
      svar2.setId(SPECIFICATION.id().id());
      svar.setInstans(2);

      final var delsvar = new Delsvar();
      final var delsvar2 = new Delsvar();

      delsvar.setId("%s.2".formatted(SPECIFICATION.id().id()));
      delsvar2.setId("%s.2".formatted(SPECIFICATION.id().id()));

      delsvar.getContent().add(createCVTypeElement(DIAGNOS_CODE, DIAGNOS_DESCRIPTION));
      delsvar2.getContent().add(createCVTypeElement(DIAGNOS_CODE_2, DIAGNOS_DESCRIPTION_2));

      svar.getDelsvar().add(delsvar);
      svar2.getDelsvar().add(delsvar2);

      prefill.getSvar().add(svar);
      prefill.getSvar().add(svar2);

      final var result = prefillDiagnosisConverter.prefillAnswer(SPECIFICATION, prefill);

      final var expected = PrefillAnswer.builder()
          .elementData(EXPECTED_ELEMENT_DATA)
          .build();

      assertEquals(expected, result);
    }

  }

  private static Element createCVTypeElement(String code, String displayName) throws Exception {
    final var cvType = new CVType();
    cvType.setCode(code);
    cvType.setDisplayName(displayName);

    final var factory = new ObjectFactory();
    final var jaxbElement = factory.createCv(cvType);

    final var context = JAXBContext.newInstance(CVType.class);
    final var marshaller = context.createMarshaller();
    final var writer = new StringWriter();
    marshaller.marshal(jaxbElement, writer);

    final var docFactory = DocumentBuilderFactory.newInstance();
    docFactory.setNamespaceAware(true);
    final var doc = docFactory.newDocumentBuilder()
        .parse(new org.xml.sax.InputSource(new StringReader(writer.toString())));
    return doc.getDocumentElement();
  }
}