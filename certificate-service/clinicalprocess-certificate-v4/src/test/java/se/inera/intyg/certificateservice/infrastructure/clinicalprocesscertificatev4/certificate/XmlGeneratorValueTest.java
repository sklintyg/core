package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;

@ExtendWith(MockitoExtension.class)
class XmlGeneratorValueTest {

  private static final String QUESTION_ID_ONE = "QUESTION_ID_ONE";
  private static final String QUESTION_ID_TWO = "QUESTION_ID_TWO";
  private static final String ANSWER_ID_ONE = "ANSWER_ID_ONE";
  private static final String ANSWER_ID_TWO = "ANSWER_ID_TWO";
  private static final String TEXT_VALUE_ONE = "This is the text value One";
  private static final String TEXT_VALUE_TWO = "This is the text value Two";

  @Mock
  private Certificate certificate;
  @Mock
  private CertificateModel certificateModel;
  @Mock
  private XmlGeneratorElementData xmlGeneratorElementDataOne;
  @Mock
  private XmlGeneratorElementData xmlGeneratorElementDataTwo;

  private XmlGeneratorValue xmlGeneratorValue;

  @Test
  void shallNotMapUnitContactInformationToAnswer() {
    final var data = ElementData.builder()
        .value(
            ElementValueUnitContactInformation.builder().build()
        )
        .id(new ElementId(QUESTION_ID_ONE))
        .build();

    doReturn(List.of(data)).when(certificate).elementData();

    xmlGeneratorValue = new XmlGeneratorValue(
        List.of(xmlGeneratorElementDataOne)
    );

    final var response = xmlGeneratorValue.generate(certificate);

    assertEquals(Collections.emptyList(), response);
  }

  @Test
  void shallThrowIfNotConverterFoundForValue() {
    final var data = ElementData.builder()
        .value(
            ElementValueText.builder().build()
        )
        .id(new ElementId(QUESTION_ID_ONE))
        .build();

    doReturn(List.of(data)).when(certificate).elementData();

    xmlGeneratorValue = new XmlGeneratorValue(
        Collections.emptyList()
    );

    assertThrows(IllegalStateException.class,
        () -> xmlGeneratorValue.generate(certificate)
    );
  }

  @Nested
  class TestStandardMapping {

    @BeforeEach
    void setUp() {
      doReturn(ElementValueText.class).when(xmlGeneratorElementDataOne).supports();

      doReturn(certificateModel).when(certificate).certificateModel();
      final var elementSpecificationOne = mock(ElementSpecification.class);
      doReturn(elementSpecificationOne).when(certificateModel)
          .elementSpecification(new ElementId(QUESTION_ID_ONE));

      xmlGeneratorValue = new XmlGeneratorValue(
          List.of(xmlGeneratorElementDataOne)
      );

    }

    @Test
    void shouldMapValue() {
      final var data = ElementData.builder()
          .id(new ElementId(QUESTION_ID_ONE))
          .value(
              ElementValueText.builder()
                  .textId(new FieldId(ANSWER_ID_ONE))
                  .text(TEXT_VALUE_ONE)
                  .build()
          )
          .build();

      doReturn(List.of(data)).when(certificate).elementData();

      final var expectedData = new Svar();
      final var subAnswer = new Delsvar();
      subAnswer.getContent().add(TEXT_VALUE_ONE);
      expectedData.setId(QUESTION_ID_ONE);
      subAnswer.setId(ANSWER_ID_ONE);
      expectedData.getDelsvar().add(subAnswer);

      doReturn(List.of(expectedData)).when(xmlGeneratorElementDataOne).generate(eq(data), any());

      final var response = xmlGeneratorValue.generate(certificate).get(0);

      assertAll(
          () -> assertEquals(expectedData.getId(), response.getId()),
          () -> assertEquals(expectedData.getDelsvar().get(0).getId(),
              response.getDelsvar().get(0).getId()),
          () -> assertEquals(expectedData.getDelsvar().get(0).getContent().get(0),
              response.getDelsvar().get(0).getContent().get(0))
      );
    }

    @Test
    void shouldMapEmptyIfNoValue() {
      final var data = ElementData.builder()
          .id(new ElementId(QUESTION_ID_ONE))
          .value(
              ElementValueText.builder()
                  .textId(new FieldId(ANSWER_ID_ONE))
                  .build()
          )
          .build();

      doReturn(List.of(data)).when(certificate).elementData();

      final var response = xmlGeneratorValue.generate(certificate);

      assertEquals(Collections.emptyList(), response);
    }
  }

  @Nested
  class TestCustomMapping {

    @BeforeEach
    void setUp() {
      doReturn(ElementValueText.class).when(xmlGeneratorElementDataOne).supports();
      doReturn(ElementValueDateList.class).when(xmlGeneratorElementDataTwo).supports();

      xmlGeneratorValue = new XmlGeneratorValue(
          List.of(xmlGeneratorElementDataOne, xmlGeneratorElementDataTwo)
      );
    }

    @Test
    void shouldMergeDelsvarWhenCustomMappingOnElementId() {
      final var dataOne = ElementData.builder()
          .id(new ElementId(QUESTION_ID_ONE))
          .value(
              ElementValueText.builder()
                  .textId(new FieldId(ANSWER_ID_ONE))
                  .text(TEXT_VALUE_ONE)
                  .build()
          )
          .build();
      final var dataTwo = ElementData.builder()
          .id(new ElementId(QUESTION_ID_TWO))
          .value(
              ElementValueDateList.builder()
                  .build()
          )
          .build();

      doReturn(List.of(dataOne, dataTwo)).when(certificate).elementData();
      doReturn(certificateModel).when(certificate).certificateModel();
      final var elementSpecificationOne = mock(ElementSpecification.class);
      final var elementSpecificationTwo = mock(ElementSpecification.class);
      doReturn(elementSpecificationOne).when(certificateModel)
          .elementSpecification(new ElementId(QUESTION_ID_ONE));
      doReturn(elementSpecificationTwo).when(certificateModel)
          .elementSpecification(new ElementId(QUESTION_ID_TWO));
      final var elementMapping = new ElementMapping(new ElementId(QUESTION_ID_ONE), null);
      doReturn(null).when(elementSpecificationOne).mapping();
      doReturn(elementMapping).when(elementSpecificationTwo).mapping();

      final var expectedData = new Svar();
      final var subAnswerOne = new Delsvar();
      final var subAnswerTwo = new Delsvar();
      expectedData.setId(QUESTION_ID_ONE);
      subAnswerOne.setId(ANSWER_ID_ONE);
      subAnswerOne.getContent().add(TEXT_VALUE_ONE);
      expectedData.getDelsvar().add(subAnswerOne);
      subAnswerTwo.setId(ANSWER_ID_TWO);
      subAnswerTwo.getContent().add(TEXT_VALUE_TWO);
      expectedData.getDelsvar().add(subAnswerTwo);

      final var answerOne = new Svar();
      answerOne.setId(QUESTION_ID_ONE);
      answerOne.getDelsvar().add(subAnswerOne);
      final var answerTwo = new Svar();
      answerTwo.setId(QUESTION_ID_TWO);
      answerTwo.getDelsvar().add(subAnswerTwo);

      doReturn(List.of(answerOne)).when(xmlGeneratorElementDataOne).generate(eq(dataOne), any());
      doReturn(List.of(answerTwo)).when(xmlGeneratorElementDataTwo).generate(eq(dataTwo), any());

      final var response = xmlGeneratorValue.generate(certificate);

      assertAll(
          () -> assertEquals(expectedData.getId(), response.get(0).getId()),
          () -> assertEquals(expectedData.getDelsvar().get(0).getId(),
              response.get(0).getDelsvar().get(0).getId()),
          () -> assertEquals(expectedData.getDelsvar().get(0).getContent().get(0),
              response.get(0).getDelsvar().get(0).getContent().get(0)),
          () -> assertEquals(expectedData.getDelsvar().get(1).getId(),
              response.get(0).getDelsvar().get(1).getId()),
          () -> assertEquals(expectedData.getDelsvar().get(1).getContent().get(0),
              response.get(0).getDelsvar().get(1).getContent().get(0))
      );
    }

    @Test
    void shouldMergeDelsvarWhenCustomMappingOnElementIdAndFieldId() {
      final var dataOne = ElementData.builder()
          .id(new ElementId(QUESTION_ID_ONE))
          .value(
              ElementValueText.builder()
                  .textId(new FieldId(ANSWER_ID_ONE))
                  .text(TEXT_VALUE_ONE)
                  .build()
          )
          .build();
      final var dataTwo = ElementData.builder()
          .id(new ElementId(QUESTION_ID_TWO))
          .value(
              ElementValueDateList.builder()
                  .build()
          )
          .build();

      doReturn(List.of(dataOne, dataTwo)).when(certificate).elementData();
      doReturn(certificateModel).when(certificate).certificateModel();
      final var elementSpecificationOne = mock(ElementSpecification.class);
      final var elementSpecificationTwo = mock(ElementSpecification.class);
      doReturn(elementSpecificationOne).when(certificateModel)
          .elementSpecification(new ElementId(QUESTION_ID_ONE));
      doReturn(elementSpecificationTwo).when(certificateModel)
          .elementSpecification(new ElementId(QUESTION_ID_TWO));
      final var elementMapping = new ElementMapping(
          new ElementId(QUESTION_ID_ONE),
          new Code("Code2", "CodeSystem", "DisplayName2")
      );
      doReturn(null).when(elementSpecificationOne).mapping();
      doReturn(elementMapping).when(elementSpecificationTwo).mapping();

      final var expectedDataOne = new Svar();
      final var answerOneSubAnswerOne = new Delsvar();
      final var answerOneSubAnswerCodeOne = new CVType();
      expectedDataOne.setId(QUESTION_ID_ONE);
      expectedDataOne.setInstans(1);
      answerOneSubAnswerOne.setId(ANSWER_ID_ONE);
      answerOneSubAnswerCodeOne.setCode("Code1");
      answerOneSubAnswerCodeOne.setCodeSystem("CodeSystem");
      answerOneSubAnswerCodeOne.setDisplayName("DisplayName1");
      answerOneSubAnswerOne.getContent().add(
          answerOneSubAnswerCodeOne
      );
      expectedDataOne.getDelsvar().add(answerOneSubAnswerOne);

      final var expectedDataTwo = new Svar();
      final var answerTwoSubAnswerOne = new Delsvar();
      final var answerTwoSubAnswerCodeOne = new CVType();
      final var answerTwoSubAnswerTwo = new Delsvar();
      expectedDataTwo.setId(QUESTION_ID_ONE);
      expectedDataTwo.setInstans(2);
      answerTwoSubAnswerOne.setId(ANSWER_ID_ONE);
      answerTwoSubAnswerCodeOne.setCode("Code2");
      answerTwoSubAnswerCodeOne.setCodeSystem("CodeSystem");
      answerTwoSubAnswerCodeOne.setDisplayName("DisplayName2");
      answerTwoSubAnswerOne.getContent().add(
          answerTwoSubAnswerCodeOne
      );
      expectedDataTwo.getDelsvar().add(answerTwoSubAnswerOne);
      answerTwoSubAnswerTwo.setId(ANSWER_ID_TWO);
      answerTwoSubAnswerTwo.getContent().add(TEXT_VALUE_TWO);
      expectedDataTwo.getDelsvar().add(answerTwoSubAnswerTwo);

      final var answerOneInstanceOne = new Svar();
      answerOneInstanceOne.setId(QUESTION_ID_ONE);
      answerOneInstanceOne.setInstans(1);
      answerOneInstanceOne.getDelsvar().add(answerOneSubAnswerOne);
      final var answerOneInstanceTwo = new Svar();
      answerOneInstanceTwo.setId(QUESTION_ID_ONE);
      answerOneInstanceTwo.setInstans(2);
      answerOneInstanceTwo.getDelsvar().add(answerTwoSubAnswerOne);
      final var answerTwo = new Svar();
      answerTwo.setId(QUESTION_ID_TWO);
      answerTwo.getDelsvar().add(answerTwoSubAnswerTwo);

      doReturn(List.of(answerOneInstanceOne, answerOneInstanceTwo))
          .when(xmlGeneratorElementDataOne)
          .generate(eq(dataOne), any());
      doReturn(List.of(answerTwo)).when(xmlGeneratorElementDataTwo).generate(eq(dataTwo), any());
      final var response = xmlGeneratorValue.generate(certificate);

      assertAll(
          () -> assertEquals(expectedDataOne.getId(), response.get(0).getId()),
          () -> assertEquals(expectedDataOne.getInstans(), response.get(0).getInstans()),
          () -> assertEquals(expectedDataOne.getDelsvar().get(0).getId(),
              response.get(0).getDelsvar().get(0).getId()),
          () -> assertEquals(expectedDataOne.getDelsvar().get(0).getContent().get(0),
              response.get(0).getDelsvar().get(0).getContent().get(0)),
          () -> assertEquals(expectedDataTwo.getId(), response.get(1).getId()),
          () -> assertEquals(expectedDataTwo.getDelsvar().get(0).getId(),
              response.get(1).getDelsvar().get(0).getId()),
          () -> assertEquals(expectedDataTwo.getDelsvar().get(0).getContent().get(0),
              response.get(1).getDelsvar().get(0).getContent().get(0)),
          () -> assertEquals(expectedDataTwo.getDelsvar().get(1).getId(),
              response.get(1).getDelsvar().get(1).getId()),
          () -> assertEquals(expectedDataTwo.getDelsvar().get(1).getContent().get(0),
              response.get(1).getDelsvar().get(1).getContent().get(0))
      );
    }

    @Test
    void shouldThrowIfCustomMappingCannotBeMerged() {
      final var dataOne = ElementData.builder()
          .id(new ElementId(QUESTION_ID_ONE))
          .value(
              ElementValueText.builder()
                  .textId(new FieldId(ANSWER_ID_ONE))
                  .text(TEXT_VALUE_ONE)
                  .build()
          )
          .build();
      final var dataTwo = ElementData.builder()
          .id(new ElementId(QUESTION_ID_TWO))
          .value(
              ElementValueDateList.builder()
                  .build()
          )
          .build();

      doReturn(List.of(dataOne, dataTwo)).when(certificate).elementData();
      doReturn(certificateModel).when(certificate).certificateModel();
      final var elementSpecificationOne = mock(ElementSpecification.class);
      final var elementSpecificationTwo = mock(ElementSpecification.class);
      doReturn(elementSpecificationOne).when(certificateModel)
          .elementSpecification(new ElementId(QUESTION_ID_ONE));
      doReturn(elementSpecificationTwo).when(certificateModel)
          .elementSpecification(new ElementId(QUESTION_ID_TWO));
      final var elementMapping = new ElementMapping(new ElementId("MISSING_QUESTION_ID"), null);
      doReturn(null).when(elementSpecificationOne).mapping();
      doReturn(elementMapping).when(elementSpecificationTwo).mapping();

      final var expectedData = new Svar();
      final var subAnswerOne = new Delsvar();
      final var subAnswerTwo = new Delsvar();
      expectedData.setId(QUESTION_ID_ONE);
      subAnswerOne.setId(ANSWER_ID_ONE);
      subAnswerOne.getContent().add(TEXT_VALUE_ONE);
      expectedData.getDelsvar().add(subAnswerOne);
      subAnswerTwo.setId(ANSWER_ID_TWO);
      subAnswerTwo.getContent().add(TEXT_VALUE_TWO);
      expectedData.getDelsvar().add(subAnswerTwo);

      final var answerOne = new Svar();
      answerOne.setId(QUESTION_ID_ONE);
      answerOne.getDelsvar().add(subAnswerOne);
      final var answerTwo = new Svar();
      answerTwo.setId(QUESTION_ID_TWO);
      answerTwo.getDelsvar().add(subAnswerTwo);

      doReturn(List.of(answerOne)).when(xmlGeneratorElementDataOne).generate(eq(dataOne), any());
      doReturn(List.of(answerTwo)).when(xmlGeneratorElementDataTwo).generate(eq(dataTwo), any());

      assertThrows(IllegalStateException.class,
          () -> xmlGeneratorValue.generate(certificate)
      );
    }
  }
}
