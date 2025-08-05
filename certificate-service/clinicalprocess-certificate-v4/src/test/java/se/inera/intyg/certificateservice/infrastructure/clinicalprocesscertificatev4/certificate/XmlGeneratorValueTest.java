package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.certificate.model.CustomMapperId.CODE_LIST_TO_BOOLEAN;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
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
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ObjectFactory;
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
  private XmlGeneratorElementValue xmlGeneratorElementValueOne;
  @Mock
  private XmlGeneratorCustomMapper xmlGeneratorCustomMapper;
  @Mock
  private XmlGeneratorElementValue xmlGeneratorElementValueTwo;

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
        List.of(xmlGeneratorElementValueOne),
        List.of(xmlGeneratorCustomMapper)
    );

    final var response = xmlGeneratorValue.generate(certificate);

    assertEquals(Collections.emptyList(), response);
  }

  @Test
  void shallThrowIfNotConverterFoundForValue() {
    doReturn(certificateModel).when(certificate).certificateModel();
    final var elementSpecificationOne = mock(ElementSpecification.class);
    when(certificateModel.elementSpecification(new ElementId(QUESTION_ID_ONE))).thenReturn(
        elementSpecificationOne);
    when(elementSpecificationOne.includeInXml()).thenReturn(true);

    final var data = ElementData.builder()
        .value(
            ElementValueText.builder().build()
        )
        .id(new ElementId(QUESTION_ID_ONE))
        .build();

    doReturn(List.of(data)).when(certificate).elementData();

    xmlGeneratorValue = new XmlGeneratorValue(
        Collections.emptyList(),
        Collections.emptyList()
    );

    assertThrows(IllegalStateException.class,
        () -> xmlGeneratorValue.generate(certificate)
    );
  }

  @Test
  void shouldNotMapValueIfNotIncludedInXml() {
    doReturn(ElementValueText.class).when(xmlGeneratorElementValueOne).supports();

    doReturn(certificateModel).when(certificate).certificateModel();
    final var elementSpecificationOne = mock(ElementSpecification.class);
    when(elementSpecificationOne.includeInXml()).thenReturn(false);
    doReturn(elementSpecificationOne).when(certificateModel)
        .elementSpecification(new ElementId(QUESTION_ID_ONE));

    xmlGeneratorValue = new XmlGeneratorValue(
        List.of(xmlGeneratorElementValueOne),
        Collections.emptyList()
    );

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

    verify(xmlGeneratorElementValueOne, times(0)).generate(eq(data), any());
    final var response = xmlGeneratorValue.generate(certificate);
    assertEquals(Collections.emptyList(), response);
  }

  @Nested
  class TestStandardXmlMapping {

    @BeforeEach
    void setUp() {
      doReturn(ElementValueText.class).when(xmlGeneratorElementValueOne).supports();

      doReturn(certificateModel).when(certificate).certificateModel();
      final var elementSpecificationOne = mock(ElementSpecification.class);
      when(elementSpecificationOne.includeInXml()).thenReturn(true);
      doReturn(elementSpecificationOne).when(certificateModel)
          .elementSpecification(new ElementId(QUESTION_ID_ONE));

      xmlGeneratorValue = new XmlGeneratorValue(
          List.of(xmlGeneratorElementValueOne),
          Collections.emptyList()
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

      doReturn(List.of(expectedData)).when(xmlGeneratorElementValueOne).generate(eq(data), any());

      final var response = xmlGeneratorValue.generate(certificate).get(0);

      assertAll(
          () -> assertEquals(expectedData.getId(), response.getId()),
          () -> assertEquals(expectedData.getDelsvar().get(0).getId(),
              response.getDelsvar().get(0).getId()),
          () -> assertEquals(expectedData.getDelsvar().get(0).getContent().get(0),
              response.getDelsvar().get(0).getContent().get(0))
      );
    }
  }

  @Nested
  class TestCustomXmlMapping {

    @BeforeEach
    void setUp() {
      doReturn(ElementValueText.class).when(xmlGeneratorElementValueOne).supports();
      doReturn(ElementValueDateList.class).when(xmlGeneratorElementValueTwo).supports();

      xmlGeneratorValue = new XmlGeneratorValue(
          List.of(xmlGeneratorElementValueOne, xmlGeneratorElementValueTwo),
          List.of(xmlGeneratorCustomMapper)
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

      final var elementMapping = new ElementMapping(new ElementId(QUESTION_ID_TWO), null);
      final var elementSpecification = mock(ElementSpecification.class);

      doReturn(certificateModel).when(certificate).certificateModel();
      doReturn(true).when(elementSpecification).includeInXml();
      doReturn(elementSpecification).when(certificateModel)
          .elementSpecification(new ElementId(QUESTION_ID_ONE));
      doReturn(elementMapping).when(elementSpecification).mapping();
      doReturn(List.of(data)).when(certificate).elementData();

      final var response = xmlGeneratorValue.generate(certificate);

      assertEquals(Collections.emptyList(), response);
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
      when(elementSpecificationOne.includeInXml()).thenReturn(true);
      final var elementSpecificationTwo = mock(ElementSpecification.class);
      when(elementSpecificationTwo.includeInXml()).thenReturn(true);
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

      doReturn(List.of(answerOne)).when(xmlGeneratorElementValueOne).generate(eq(dataOne), any());
      doReturn(List.of(answerTwo)).when(xmlGeneratorElementValueTwo).generate(eq(dataTwo), any());

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
    void shouldMergeDelsvarWhenCustomMappingOnElementIdAndElementDataIsNotSorted() {
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

      doReturn(List.of(dataTwo, dataOne)).when(certificate).elementData();
      doReturn(certificateModel).when(certificate).certificateModel();
      final var elementSpecificationOne = mock(ElementSpecification.class);
      when(elementSpecificationOne.includeInXml()).thenReturn(true);
      final var elementSpecificationTwo = mock(ElementSpecification.class);
      when(elementSpecificationTwo.includeInXml()).thenReturn(true);
      doReturn(elementSpecificationOne).when(certificateModel)
          .elementSpecification(new ElementId(QUESTION_ID_ONE));
      doReturn(elementSpecificationTwo).when(certificateModel)
          .elementSpecification(new ElementId(QUESTION_ID_TWO));
      doReturn(-1).when(certificateModel)
          .compare(new ElementId(QUESTION_ID_ONE), new ElementId(QUESTION_ID_TWO));
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

      doReturn(List.of(answerOne)).when(xmlGeneratorElementValueOne).generate(eq(dataOne), any());
      doReturn(List.of(answerTwo)).when(xmlGeneratorElementValueTwo).generate(eq(dataTwo), any());

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
      when(elementSpecificationOne.includeInXml()).thenReturn(true);
      final var elementSpecificationTwo = mock(ElementSpecification.class);
      when(elementSpecificationTwo.includeInXml()).thenReturn(true);
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
          new ObjectFactory().createCv(answerTwoSubAnswerCodeOne)
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
          .when(xmlGeneratorElementValueOne)
          .generate(eq(dataOne), any());
      doReturn(List.of(answerTwo)).when(xmlGeneratorElementValueTwo).generate(eq(dataTwo), any());
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
    void shouldProvideEmptySvarIfCustomMappingMissing() {
      final var dataOne = ElementData.builder()
          .id(new ElementId(QUESTION_ID_ONE))
          .value(
              ElementValueText.builder()
                  .textId(new FieldId(ANSWER_ID_ONE))
                  .text(TEXT_VALUE_ONE)
                  .build()
          )
          .build();

      doReturn(List.of(dataOne)).when(certificate).elementData();
      doReturn(certificateModel).when(certificate).certificateModel();
      final var elementSpecificationOne = mock(ElementSpecification.class);
      when(elementSpecificationOne.includeInXml()).thenReturn(true);
      doReturn(elementSpecificationOne).when(certificateModel)
          .elementSpecification(new ElementId(QUESTION_ID_ONE));
      final var elementMapping = new ElementMapping(new ElementId("MISSING_QUESTION_ID"), null);
      doReturn(elementMapping).when(elementSpecificationOne).mapping();

      final var expectedData = new Svar();
      final var subAnswerOne = new Delsvar();
      expectedData.setId("MISSING_QUESTION_ID");
      subAnswerOne.setId(ANSWER_ID_ONE);
      subAnswerOne.getContent().add(TEXT_VALUE_ONE);
      expectedData.getDelsvar().add(subAnswerOne);

      final var answerOne = new Svar();
      answerOne.setId(QUESTION_ID_ONE);
      answerOne.getDelsvar().add(subAnswerOne);

      doReturn(List.of(answerOne)).when(xmlGeneratorElementValueOne).generate(eq(dataOne), any());

      final var response = xmlGeneratorValue.generate(certificate);

      assertAll(
          () -> assertEquals(expectedData.getId(), response.get(0).getId()),
          () -> assertEquals(expectedData.getDelsvar().get(0).getId(),
              response.get(0).getDelsvar().get(0).getId()),
          () -> assertEquals(expectedData.getDelsvar().get(0).getContent().get(0),
              response.get(0).getDelsvar().get(0).getContent().get(0))
      );
    }
  }

  @Nested
  class TestCustomElementValueMapper {

    @BeforeEach
    void setUp() {
      doReturn(CODE_LIST_TO_BOOLEAN).when(xmlGeneratorCustomMapper).id();
      xmlGeneratorValue = new XmlGeneratorValue(
          List.of(xmlGeneratorElementValueOne),
          List.of(xmlGeneratorCustomMapper)
      );
    }

    @Test
    void shallUseElementValueMapperToGenerateConverter() {
      final var dataOne = ElementData.builder()
          .id(new ElementId(QUESTION_ID_ONE))
          .value(
              ElementValueCodeList.builder()
                  .list(Collections.emptyList())
                  .build()
          )
          .build();

      doReturn(List.of(dataOne)).when(certificate).elementData();
      doReturn(certificateModel).when(certificate).certificateModel();
      final var elementSpecificationOne = mock(ElementSpecification.class);
      when(elementSpecificationOne.includeInXml()).thenReturn(true);
      doReturn(elementSpecificationOne).when(certificateModel)
          .elementSpecification(new ElementId(QUESTION_ID_ONE));
      final var elementMapping = new ElementMapping(CODE_LIST_TO_BOOLEAN);
      doReturn(Optional.of(elementMapping)).when(elementSpecificationOne).getMapping();

      final var expectedData = new Svar();
      final var subAnswerOne = new Delsvar();
      expectedData.setId(QUESTION_ID_ONE);
      subAnswerOne.setId(ANSWER_ID_ONE);
      subAnswerOne.getContent().add(TEXT_VALUE_ONE);
      expectedData.getDelsvar().add(subAnswerOne);

      final var answerOne = new Svar();
      answerOne.setId(QUESTION_ID_ONE);
      answerOne.getDelsvar().add(subAnswerOne);

      doReturn(List.of(answerOne)).when(xmlGeneratorCustomMapper).generate(eq(dataOne), any());

      final var response = xmlGeneratorValue.generate(certificate);

      assertAll(
          () -> assertEquals(expectedData.getId(), response.getFirst().getId()),
          () -> assertEquals(expectedData.getDelsvar().getFirst().getId(),
              response.getFirst().getDelsvar().getFirst().getId()),
          () -> assertEquals(expectedData.getDelsvar().getFirst().getContent().getFirst(),
              response.getFirst().getDelsvar().getFirst().getContent().getFirst())
      );
    }
  }
}