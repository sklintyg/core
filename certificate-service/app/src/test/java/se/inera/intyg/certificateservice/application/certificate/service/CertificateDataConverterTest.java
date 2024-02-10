package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigCategory;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidation;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationMandatory;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRule;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

@ExtendWith(MockitoExtension.class)
class CertificateDataConverterTest {

  private static final String ID_1 = "id1";
  private static final String ID_2 = "id2";
  private static final String CHILD_ID = "childId";
  @Mock
  private CertificateDataConfigConverter certificateDataConfigConverter;

  @Mock
  private CertificateDataValueConverter certificateDataValueConverter;

  @Mock
  private CertificateDataValidationConverter certificateDataValidationConverter;

  @InjectMocks
  private CertificateDataConverter certificateDataConverter;


  @Test
  void shallReturnMapOfCertificateDataElements() {
    final var elementId = new ElementId(ID_1);

    final var elementSpecifications = List.of(
        ElementSpecification.builder().id(elementId).build()
    );

    final var certificateModel = CertificateModel.builder()
        .elementSpecifications(elementSpecifications)
        .build();

    final var result = certificateDataConverter.convert(certificateModel, Collections.emptyList());
    assertInstanceOf(CertificateDataElement.class, result.get(ID_1),
        "Should return map of CertificateDataElement"
    );
  }

  @Test
  void shallConvertSingleElementData() {
    final var elementId = new ElementId(ID_1);

    final var elementSpecifications = List.of(
        ElementSpecification.builder().id(elementId).build()
    );

    final var certificateModel = CertificateModel.builder()
        .elementSpecifications(elementSpecifications)
        .build();

    final var result = certificateDataConverter.convert(certificateModel, Collections.emptyList());

    assertTrue(result.containsKey(ID_1), "Map should contain the key 'id'");
  }


  @Test
  void shallConvertMultipleElementData() {
    final var elementId1 = new ElementId(ID_1);
    final var elementId2 = new ElementId(ID_2);

    final var elementSpecifications = List.of(
        ElementSpecification.builder().id(elementId1).build(),
        ElementSpecification.builder().id(elementId2).build()
    );

    final var certificateModel = CertificateModel.builder()
        .elementSpecifications(elementSpecifications)
        .build();

    final var result = certificateDataConverter.convert(certificateModel, Collections.emptyList());

    assertEquals(2, result.size(), "Map should contain two entries");
    assertTrue(result.containsKey(ID_1) && result.containsKey(ID_2),
        "Map should contain keys 'id1' and 'id2'");
  }

  @Test
  void shallMaintainOrderOfElementsBasedOnIndex() {
    final var elementId1 = new ElementId(ID_1);
    final var elementId2 = new ElementId(ID_2);

    final var elementSpecification1 = ElementSpecification.builder()
        .id(elementId1)
        .build();
    final var elementSpecification2 = ElementSpecification.builder()
        .id(elementId2)
        .build();

    final var certificateModel = CertificateModel.builder()
        .elementSpecifications(List.of(elementSpecification1, elementSpecification2))
        .build();

    final var result = certificateDataConverter.convert(certificateModel, Collections.emptyList());
    assertTrue(result.get(ID_1).getIndex() < result.get(ID_2).getIndex(),
        "First element should have a lower index than the second element");
  }

  @Test
  void shallMaintainOrderOfElementsBasedOnIndexWithChildren() {
    final var elementId1 = new ElementId(ID_1);
    final var elementId2 = new ElementId(ID_2);
    final var elementId3 = new ElementId(CHILD_ID);

    final var children = ElementSpecification.builder()
        .id(elementId3)
        .build();

    final var elementSpecification1 = ElementSpecification.builder()
        .id(elementId1)
        .children(List.of(children))
        .build();

    final var elementSpecification2 = ElementSpecification.builder()
        .id(elementId2)
        .build();

    final var certificateModel = CertificateModel.builder()
        .elementSpecifications(List.of(elementSpecification1, elementSpecification2))
        .build();

    final var result = certificateDataConverter.convert(certificateModel, Collections.emptyList());

    assertEquals(1, result.get(ID_1).getIndex());
    assertEquals(2, result.get(CHILD_ID).getIndex());
    assertEquals(3, result.get(ID_2).getIndex());
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
    assertEquals(parentElementId, certificateDataElement.getParent());
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
        .elementSpecifications(
            List.of(parentSpecification)
        )
        .build();

    final var result = certificateDataConverter.convert(certificateModel, elementData);

    final var certificateDataElement = result.get(subQuestionId);
    assertEquals(childElementId, certificateDataElement.getParent());
  }

  @Test
  void shallConvertCertificateDataElementConfig() {
    final var elementId = new ElementId(ID_1);

    final var elementSpecifications = List.of(
        ElementSpecification.builder().id(elementId).build()
    );

    final var certificateModel = CertificateModel.builder()
        .elementSpecifications(elementSpecifications)
        .build();

    doReturn(CertificateDataConfigCategory.builder().build()).when(certificateDataConfigConverter)
        .convert(elementSpecifications.get(0));

    final var result = certificateDataConverter.convert(certificateModel, Collections.emptyList());

    assertNotNull(result.get(ID_1).getConfig(), "CertificateDataElement should contain config");
  }

  @Test
  void shallConvertCertificateDataElementValidation() {
    final var elementId = new ElementId(ID_1);
    final var elementSpecification = ElementSpecification.builder()
        .rules(
            List.of(
                ElementRule.builder().build()
            )
        )
        .id(elementId)
        .build();

    final var elementSpecifications = List.of(
        elementSpecification
    );

    final var certificateModel = CertificateModel.builder()
        .elementSpecifications(elementSpecifications)
        .build();

    doReturn(
        new CertificateDataValidation[]{CertificateDataValidationMandatory.builder().build()})
        .when(certificateDataValidationConverter).convert(elementSpecification.rules());

    final var result = certificateDataConverter.convert(certificateModel, Collections.emptyList());

    assertNotNull(result.get(ID_1).getValidation(),
        "CertificateDataElement should contain validation");
  }

  @Test
  void shallConvertCertificateDataElementValue() {
    final var elementId = new ElementId(ID_1);
    final var elementData = ElementData.builder()
        .id(elementId)
        .value(ElementValueDate.builder().build())
        .build();

    final var elementSpecification = ElementSpecification.builder()
        .id(elementId)
        .build();

    final var elementSpecifications = List.of(
        elementSpecification
    );

    final var certificateModel = CertificateModel.builder()
        .elementSpecifications(elementSpecifications)
        .build();

    doReturn(CertificateDataValueDate.builder().build())
        .when(certificateDataValueConverter).convert(elementSpecification, elementData.value());

    final var result = certificateDataConverter.convert(certificateModel, List.of(elementData));

    assertNotNull(result.get(ID_1).getValue(),
        "CertificateDataElement should contain value");
  }
}
