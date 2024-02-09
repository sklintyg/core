package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

@ExtendWith(MockitoExtension.class)
class CertificateDataConverterTest {

  private static final String DATE_ID = "dateId";
  private static final String DATE_TEST = "Date";
  private static final String ID = "id";
  private static final String ID_1 = "id1";
  private static final String ID_2 = "id2";
  @Mock
  private CertificateDataConfigConverter certificateDataConfigConverter;

  @Mock
  private CertificateDataValueConverter certificateDataValueConverter;

  @Mock
  private CertificateDataValidationConverter certificateDataValidationConverter;

  @InjectMocks
  private CertificateDataConverter certificateDataConverter;

  @Test
  void shallConvertSingleElementData() {
    final var elementId = new ElementId(ID_1);

    final var elementData = ElementData.builder()
        .id(elementId)
        .value(ElementValueDate.builder().date(LocalDate.now()).build())
        .build();

    final var elementSpecifications = List.of(
        ElementSpecification.builder().id(elementId).build()
    );

    final var certificateModel = CertificateModel.builder()
        .elementSpecifications(elementSpecifications)
        .build();

    final var result = certificateDataConverter.convert(certificateModel, List.of(elementData));

    assertTrue(result.containsKey(ID_1), "Map should contain the key 'id'");
  }


  @Test
  void shallConvertMultipleElementData() {
    final var elementId1 = new ElementId(ID_1);
    final var elementId2 = new ElementId(ID_2);

    final var elementData1 = ElementData.builder()
        .id(elementId1)
        .build();

    final var elementData2 = ElementData.builder()
        .id(elementId2)
        .build();

    final var elementSpecifications = List.of(
        ElementSpecification.builder().id(elementId1).build(),
        ElementSpecification.builder().id(elementId2).build()
    );

    final var certificateModel = CertificateModel.builder()
        .elementSpecifications(elementSpecifications)
        .build();

    final var result = certificateDataConverter.convert(certificateModel,
        List.of(elementData1, elementData2));

    assertEquals(2, result.size(), "Map should contain two entries");
    assertTrue(result.containsKey(ID_1) && result.containsKey(ID_2),
        "Map should contain keys 'id1' and 'id2'");
  }

  @Test
  void shallMaintainOrderOfElementsBasedOnIndex() {
    final var elementId1 = new ElementId(ID_1);
    final var elementId2 = new ElementId(ID_2);

    final var elementData1 = ElementData.builder()
        .id(elementId1)
        .build();
    final var elementData2 = ElementData.builder()
        .id(elementId2)
        .build();

    final var elementSpecification1 = ElementSpecification.builder()
        .id(elementId1)
        .build();
    final var elementSpecification2 = ElementSpecification.builder()
        .id(elementId2)
        .build();

    final var certificateModel = CertificateModel.builder()
        .elementSpecifications(List.of(elementSpecification1, elementSpecification2))
        .build();

    final var result = certificateDataConverter.convert(certificateModel,
        List.of(elementData1, elementData2));
    assertTrue(result.get(ID_1).getIndex() < result.get(ID_2).getIndex(),
        "First element should have a lower index than the second element");
  }

  @Test
  void shallSetParentAttributeToNullWhenNoParentExists() {
    final var elementData = List.of(
        ElementData.builder()
            .id(new ElementId(ID_1))
            .build()
    );
    final var certificateModel = CertificateModel.builder()
        .elementSpecifications(List.of(
            ElementSpecification.builder()
                .id(new ElementId(ID_1))
                .children(Collections.emptyList())
                .build()
        ))
        .build();

    final var result = certificateDataConverter.convert(certificateModel, elementData);

    final var certificateDataElement = result.get(ID_1);
    assertNull(certificateDataElement.getParent(),
        "Parent attribute should be null when no parent exists.");
  }

  @Test
  void shallSetParentAttributeCorrectlyWhenParentExists() {
    final var parentElementId = "parent1";
    final var childElementId = "child1";
    final var elementData = List.of(
        ElementData.builder()
            .id(new ElementId(childElementId))
            .build()
    );

    final var childSpecification = ElementSpecification.builder()
        .id(new ElementId(childElementId))
        .build();

    final var parentSpecification = ElementSpecification.builder()
        .id(new ElementId(parentElementId))
        .children(List.of(childSpecification))
        .build();

    final var certificateModel = CertificateModel.builder()
        .elementSpecifications(List.of(parentSpecification))
        .build();

    final var result = certificateDataConverter.convert(certificateModel, elementData);

    final var certificateDataElement = result.get(childElementId);
    assertEquals(parentElementId, certificateDataElement.getParent(),
        "Parent attribute should be correctly set when parent exists.");
  }

  @Test
  void shallSetParentAttributeCorrectlyWhenParentExistsWithSubQuestion() {
    final var parentElementId = "parent1";
    final var childElementId = "child1";
    final var subQuestionId = "child2";
    final var elementData = List.of(
        ElementData.builder()
            .id(new ElementId(childElementId))
            .build(),
        ElementData.builder()
            .id(new ElementId(subQuestionId))
            .build()
    );

    final var subChildSpecification = ElementSpecification.builder()
        .id(new ElementId(subQuestionId))
        .children(List.of())
        .build();

    final var childSpecification = ElementSpecification.builder()
        .id(new ElementId(childElementId))
        .children(List.of(subChildSpecification))
        .build();

    final var parentSpecification = ElementSpecification.builder()
        .id(new ElementId(parentElementId))
        .children(List.of(childSpecification))
        .build();

    final var certificateModel = CertificateModel.builder()
        .elementSpecifications(List.of(parentSpecification))
        .build();

    final var result = certificateDataConverter.convert(certificateModel, elementData);

    final var certificateDataElement = result.get(subQuestionId);
    assertEquals(childElementId, certificateDataElement.getParent(),
        "Parent attribute should be correctly set when parent exists.");
  }
}
