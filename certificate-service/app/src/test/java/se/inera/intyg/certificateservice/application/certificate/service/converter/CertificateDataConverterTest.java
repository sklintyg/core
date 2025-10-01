package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7210CertificateBuilder;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigDate;
import se.inera.intyg.certificateservice.application.certificate.dto.validation.CertificateDataValidationMandatory;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementVisibilityConfigurationsCheckboxMultipleCode;

@ExtendWith(MockitoExtension.class)
class CertificateDataConverterTest {

  private static final String ID_1 = "id1";
  private static final String ID_2 = "id2";
  private static final String CHILD_ID = "childId";
  private static final ElementId ELEMENT_ID = new ElementId(ID_1);
  private static final ElementSpecification ELEMENT_SPECIFICATION = ElementSpecification.builder()
      .configuration(
          ElementConfigurationDate.builder().build()
      )
      .id(ELEMENT_ID)
      .build();
  private static final List<ElementSpecification> ELEMENT_SPECIFICATIONS = List.of(
      ELEMENT_SPECIFICATION
  );
  private static final String FIELD_ID = "fieldId";

  @Mock
  private CertificateDataDateConfigConverter certificateDataDateConfigConverter;
  @Mock
  private CertificateDataDateConfigConverter certificateDataDateConfigConverterDate;

  @Mock
  private CertificateDataValidationMandatoryConverter certificateDataValidationMandatoryConverter;

  @Mock
  private CertificateDataValueConverterDate certificateDataValueConverterDate;

  private CertificateDataConverter certificateDataConverter;

  private MedicalCertificate.MedicalCertificateBuilder certificateBuilder;

  @BeforeEach
  void setup() {
    certificateBuilder = fk7210CertificateBuilder()
        .elementData(Collections.emptyList());

    certificateDataConverter = new CertificateDataConverter(
        List.of(certificateDataDateConfigConverter, certificateDataDateConfigConverterDate),
        List.of(certificateDataValueConverterDate),
        List.of(certificateDataValidationMandatoryConverter)
    );
  }

  @Nested
  class ShouldConvert {

    @BeforeEach
    void setUp() {
      when(certificateDataDateConfigConverter.getType())
          .thenReturn(ElementType.DATE);
      when(certificateDataValueConverterDate.getType())
          .thenReturn(ElementType.DATE);
      when(certificateDataDateConfigConverter.convert(any(ElementSpecification.class),
          any(MedicalCertificate.class)))
          .thenReturn(
              CertificateDataConfigDate.builder().build()
          );
      when(certificateDataValueConverterDate.convert(any(), any()))
          .thenReturn(
              CertificateDataValueDate.builder().build()
          );
    }


    @Test
    void shallReturnMapOfCertificateDataElements() {
      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(ELEMENT_SPECIFICATIONS)
          .build();

      final var certificate = certificateBuilder
          .certificateModel(certificateModel)
          .build();

      final var result = certificateDataConverter.convert(certificate, false);
      assertInstanceOf(CertificateDataElement.class, result.get(ID_1),
          "Should return map of CertificateDataElementDTO"
      );
    }

    @Test
    void shallConvertSingleElementData() {
      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(ELEMENT_SPECIFICATIONS)
          .build();

      final var certificate = certificateBuilder
          .certificateModel(certificateModel)
          .build();

      final var result = certificateDataConverter.convert(certificate, false);

      assertTrue(result.containsKey(ID_1), "Map should contain the key 'id'");
    }

    @Test
    void shallConvertMultipleElementData() {
      final var elementId1 = new ElementId(ID_1);
      final var elementId2 = new ElementId(ID_2);

      final var elementSpecifications = List.of(
          ElementSpecification.builder()
              .id(elementId1)
              .configuration(
                  ElementConfigurationDate.builder().build()
              )
              .build(),
          ElementSpecification.builder()
              .id(elementId2)
              .configuration(
                  ElementConfigurationDate.builder().build()
              )
              .build()
      );

      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(elementSpecifications)
          .build();

      final var certificate = certificateBuilder
          .certificateModel(certificateModel)
          .build();

      final var result = certificateDataConverter.convert(certificate, false);

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
          .configuration(
              ElementConfigurationDate.builder().build()
          )
          .build();
      final var elementSpecification2 = ElementSpecification.builder()
          .id(elementId2)
          .configuration(
              ElementConfigurationDate.builder().build()
          )
          .build();

      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(List.of(elementSpecification1, elementSpecification2))
          .build();

      final var certificate = certificateBuilder
          .certificateModel(certificateModel)
          .build();

      final var result = certificateDataConverter.convert(certificate, false);

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
          .configuration(
              ElementConfigurationDate.builder().build()
          )
          .build();

      final var elementSpecification1 = ElementSpecification.builder()
          .id(elementId1)
          .configuration(
              ElementConfigurationDate.builder().build()
          )
          .children(List.of(children))
          .build();

      final var elementSpecification2 = ElementSpecification.builder()
          .id(elementId2)
          .configuration(
              ElementConfigurationDate.builder().build()
          )
          .build();

      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(List.of(elementSpecification1, elementSpecification2))
          .build();

      final var certificate = certificateBuilder
          .certificateModel(certificateModel)
          .build();

      final var result = certificateDataConverter.convert(certificate, false);

      assertEquals(1, result.get(ID_1).getIndex());
      assertEquals(2, result.get(CHILD_ID).getIndex());
      assertEquals(3, result.get(ID_2).getIndex());
    }

    @Test
    void shallSetParentAttributeToNullWhenNoParentExists() {
      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(List.of(
              ElementSpecification.builder()
                  .configuration(
                      ElementConfigurationDate.builder().build()
                  )
                  .id(new ElementId(ID_1))
                  .build()
          ))
          .build();

      final var certificate = certificateBuilder
          .certificateModel(certificateModel)
          .build();

      final var result = certificateDataConverter.convert(certificate, false);

      final var certificateDataElement = result.get(ID_1);
      assertNull(certificateDataElement.getParent(),
          "Parent attribute should be null when no parent exists.");
    }

    @Test
    void shallSetParentAttributeCorrectlyWhenParentExists() {
      final var parentElementId = "parent1";
      final var childElementId = "child1";

      final var childSpecification = ElementSpecification.builder()
          .id(new ElementId(childElementId))
          .configuration(
              ElementConfigurationDate.builder().build()
          )
          .build();

      final var parentSpecification = ElementSpecification.builder()
          .id(new ElementId(parentElementId))
          .configuration(
              ElementConfigurationDate.builder().build()
          )
          .children(List.of(childSpecification))
          .build();

      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(List.of(parentSpecification))
          .build();

      final var certificate = certificateBuilder
          .certificateModel(certificateModel)
          .build();

      final var result = certificateDataConverter.convert(certificate, false);

      final var certificateDataElement = result.get(childElementId);
      assertEquals(parentElementId, certificateDataElement.getParent());
    }

    @Test
    void shallSetParentAttributeCorrectlyWhenParentExistsWithSubQuestion() {
      final var parentElementId = "parent1";
      final var childElementId = "child1";
      final var subQuestionId = "child2";

      final var subChildSpecification = ElementSpecification.builder()
          .id(new ElementId(subQuestionId))
          .configuration(
              ElementConfigurationDate.builder().build()
          )
          .children(List.of())
          .build();

      final var childSpecification = ElementSpecification.builder()
          .id(new ElementId(childElementId))
          .configuration(
              ElementConfigurationDate.builder().build()
          )
          .children(List.of(subChildSpecification))
          .build();

      final var parentSpecification = ElementSpecification.builder()
          .id(new ElementId(parentElementId))
          .configuration(
              ElementConfigurationDate.builder().build()
          )
          .children(List.of(childSpecification))
          .build();

      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(
              List.of(parentSpecification)
          )
          .build();

      final var certificate = certificateBuilder
          .certificateModel(certificateModel)
          .build();

      final var result = certificateDataConverter.convert(certificate, false);

      final var certificateDataElement = result.get(subQuestionId);
      assertEquals(childElementId, certificateDataElement.getParent());
    }

    @Test
    void shallConvertCertificateDataElementConfig() {
      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(ELEMENT_SPECIFICATIONS)
          .build();

      final var certificate = certificateBuilder
          .certificateModel(certificateModel)
          .build();

      final var result = certificateDataConverter.convert(certificate, false);

      assertNotNull(result.get(ID_1).getConfig(),
          "CertificateDataElementDTO should contain config");
    }

    @Test
    void shallConvertCertificateDataElementValidation() {
      final var elementId = new ElementId(ID_1);
      final var elementSpecification = ElementSpecification.builder()
          .configuration(
              ElementConfigurationDate.builder().build()
          )
          .rules(
              List.of(
                  ElementRuleExpression.builder()
                      .type(ElementRuleType.MANDATORY)
                      .build()
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

      doReturn(CertificateDataValidationMandatory.builder().build())
          .when(certificateDataValidationMandatoryConverter)
          .convert(any(ElementRuleExpression.class));
      when(certificateDataValidationMandatoryConverter.getType())
          .thenReturn(ElementRuleType.MANDATORY);

      final var certificate = certificateBuilder
          .certificateModel(certificateModel)
          .build();

      final var result = certificateDataConverter.convert(certificate, false);

      assertNotNull(result.get(ID_1).getValidation(),
          "CertificateDataElementDTO should contain validation");
    }

    @Test
    void shallConvertCertificateDataElementValidationToEmptyArrayIfRulesIsEmpty() {
      final var elementId = new ElementId(ID_1);
      final var elementSpecification = ElementSpecification.builder()
          .configuration(
              ElementConfigurationDate.builder().build()
          )
          .id(elementId)
          .build();

      final var elementSpecifications = List.of(
          elementSpecification
      );

      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(elementSpecifications)
          .build();

      final var certificate = certificateBuilder
          .certificateModel(certificateModel)
          .build();

      final var result = certificateDataConverter.convert(certificate, false);

      assertEquals(0, result.get(ID_1).getValidation().length,
          "CertificateDataElementDTO should contain empty validation");
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
          .configuration(
              ElementConfigurationDate.builder().build()
          )
          .build();

      final var elementSpecifications = List.of(
          elementSpecification
      );

      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(elementSpecifications)
          .build();

      final var certificate = certificateBuilder
          .certificateModel(certificateModel)
          .elementData(List.of(elementData))
          .build();

      final var result = certificateDataConverter.convert(certificate, false);

      assertNotNull(result.get(ID_1).getValue(),
          "CertificateDataElementDTO should contain value");
    }

  }

  @Test
  void shallThrowIfNoConverterFoundForElementValue() {
    final var elementId = new ElementId(ID_1);
    final var elementData = ElementData.builder()
        .id(elementId)
        .value(ElementValueDate.builder().build())
        .build();

    final var elementSpecification = ElementSpecification.builder()
        .id(elementId)
        .configuration(
            ElementConfigurationTextArea.builder().build()
        )
        .build();

    final var elementSpecifications = List.of(
        elementSpecification
    );

    final var certificateModel = CertificateModel.builder()
        .elementSpecifications(elementSpecifications)
        .build();

    final var elementDataList = List.of(elementData);

    final var certificate = certificateBuilder
        .certificateModel(certificateModel)
        .elementData(elementDataList)
        .build();

    when(certificateDataDateConfigConverter.getType())
        .thenReturn(ElementType.TEXT_AREA);
    when(certificateDataValueConverterDate.getType())
        .thenReturn(ElementType.DATE);
    when(certificateDataDateConfigConverter.convert(any(ElementSpecification.class),
        eq(certificate)))
        .thenReturn(
            CertificateDataConfigDate.builder().build()
        );

    final var illegalStateException = assertThrows(IllegalStateException.class,
        () -> certificateDataConverter.convert(certificate, false));

    assertTrue(illegalStateException.getMessage().contains("Could not find value converter"),
        "Message was %s".formatted(illegalStateException.getMessage())
    );
  }

  @Test
  void shallReturnNullIfCategoryConfiguration() {
    final var elementId = new ElementId(ID_1);
    final var elementData = ElementData.builder()
        .id(elementId)
        .value(ElementValueDate.builder().build())
        .build();

    final var elementSpecification = ElementSpecification.builder()
        .id(elementId)
        .configuration(
            ElementConfigurationCategory.builder().build()
        )
        .build();

    final var elementSpecifications = List.of(
        elementSpecification
    );

    final var certificateModel = CertificateModel.builder()
        .elementSpecifications(elementSpecifications)
        .build();

    final var certificate = certificateBuilder
        .certificateModel(certificateModel)
        .elementData(List.of(elementData))
        .build();

    when(certificateDataDateConfigConverter.getType())
        .thenReturn(ElementType.CATEGORY);

    when(certificateDataDateConfigConverter.convert(any(ElementSpecification.class),
        eq(certificate)))
        .thenReturn(
            CertificateDataConfigDate.builder().build()
        );

    final var result = certificateDataConverter.convert(certificate, false);

    assertNull(result.get(ID_1).getValue(),
        "CertificateDataElementDTO should be null if category");
  }

  @Test
  void shallReturnNullIfMessageConfiguration() {
    final var elementId = new ElementId(ID_1);
    final var elementData = ElementData.builder()
        .id(elementId)
        .value(ElementValueDate.builder().build())
        .build();

    final var elementSpecification = ElementSpecification.builder()
        .id(elementId)
        .configuration(
            ElementConfigurationMessage.builder().build()
        )
        .build();

    final var elementSpecifications = List.of(
        elementSpecification
    );

    final var certificateModel = CertificateModel.builder()
        .elementSpecifications(elementSpecifications)
        .build();

    final var certificate = certificateBuilder
        .certificateModel(certificateModel)
        .elementData(List.of(elementData))
        .build();

    when(certificateDataDateConfigConverter.getType())
        .thenReturn(ElementType.MESSAGE);

    when(certificateDataDateConfigConverter.convert(any(ElementSpecification.class),
        eq(certificate)))
        .thenReturn(
            CertificateDataConfigDate.builder().build()
        );

    final var result = certificateDataConverter.convert(certificate, false);

    assertNull(result.get(ID_1).getValue(),
        "CertificateDataElementDTO should be null if message configuration");
  }

  @Test
  void shallReturnNullIfCertificateIsSignedWithoutVisibilityConfigurationAndElementDataIsNull() {
    final var elementId = new ElementId(ID_1);
    final var elementIdWithoutValue = new ElementId(ID_2);

    final var elementSpecification = ElementSpecification.builder()
        .id(elementId)
        .configuration(
            ElementConfigurationCategory.builder().build()
        )
        .children(
            List.of(
                ElementSpecification.builder()
                    .id(elementIdWithoutValue)
                    .configuration(
                        ElementConfigurationDate.builder().build()
                    )
                    .build()
            )
        )
        .build();

    final var elementSpecifications = List.of(
        elementSpecification
    );

    final var certificateModel = CertificateModel.builder()
        .elementSpecifications(elementSpecifications)
        .build();

    final var certificate = certificateBuilder
        .certificateModel(certificateModel)
        .status(Status.SIGNED)
        .build();

    final var result = certificateDataConverter.convert(certificate, false);

    assertNull(result.get(ID_2),
        "Should not include CertificateDataElementDTO if certificate is signed and missing element data");
  }

  @Test
  void shallReturnElementIfCertificateIsSignedWithVisibilityConfigurationAndElementDataIsNull() {
    final var elementId = new ElementId(ID_1);
    final var elementIdWithoutValue = new ElementId(ID_2);

    final var elementSpecification = ElementSpecification.builder()
        .id(elementId)
        .configuration(
            ElementConfigurationCategory.builder().build()
        )
        .children(
            List.of(
                ElementSpecification.builder()
                    .id(elementIdWithoutValue)
                    .visibilityConfiguration(
                        ElementVisibilityConfigurationsCheckboxMultipleCode.builder().build()
                    )
                    .configuration(
                        ElementConfigurationDate.builder().build()
                    )
                    .build()
            )
        )
        .build();

    final var elementSpecifications = List.of(
        elementSpecification
    );

    final var certificateModel = CertificateModel.builder()
        .elementSpecifications(elementSpecifications)
        .build();

    final var certificate = certificateBuilder
        .certificateModel(certificateModel)
        .status(Status.SIGNED)
        .build();

    when(certificateDataDateConfigConverter.getType())
        .thenReturn(ElementType.CATEGORY);
    when(certificateDataDateConfigConverterDate.getType())
        .thenReturn(ElementType.DATE);

    when(certificateDataDateConfigConverter.convert(any(ElementSpecification.class),
        eq(certificate)))
        .thenReturn(
            CertificateDataConfigDate.builder().build()
        );

    when(certificateDataDateConfigConverterDate.convert(any(ElementSpecification.class),
        eq(certificate)))
        .thenReturn(
            CertificateDataConfigDate.builder().build()
        );

    when(certificateDataValueConverterDate.getType())
        .thenReturn(ElementType.DATE);

    when(certificateDataValueConverterDate.convert(any(), any()))
        .thenReturn(
            CertificateDataValueDate.builder().build()
        );

    final var result = certificateDataConverter.convert(certificate, false);

    assertNotNull(result.get(ID_2),
        "Should include CertificateDataElementDTO if certificate is signed and missing element data if visibility configuration is present");
  }

  @Test
  void shallReturnCertificateElementIfCertificateIsDraftAndElementDataIsNull() {
    final var elementId = new ElementId(ID_1);
    final var elementIdWithoutValue = new ElementId(ID_2);

    final var elementSpecification = ElementSpecification.builder()
        .id(elementId)
        .configuration(
            ElementConfigurationCategory.builder().build()
        )
        .children(
            List.of(
                ElementSpecification.builder()
                    .id(elementIdWithoutValue)
                    .configuration(
                        ElementConfigurationDate.builder().build()
                    )
                    .build()
            )
        )
        .build();

    final var elementSpecifications = List.of(
        elementSpecification
    );

    final var certificateModel = CertificateModel.builder()
        .elementSpecifications(elementSpecifications)
        .build();

    final var certificate = certificateBuilder
        .certificateModel(certificateModel)
        .status(Status.DRAFT)
        .build();

    when(certificateDataDateConfigConverter.getType())
        .thenReturn(ElementType.CATEGORY);
    when(certificateDataDateConfigConverterDate.getType())
        .thenReturn(ElementType.DATE);

    when(certificateDataDateConfigConverter.convert(any(ElementSpecification.class),
        eq(certificate)))
        .thenReturn(
            CertificateDataConfigDate.builder().build()
        );

    when(certificateDataDateConfigConverterDate.convert(any(ElementSpecification.class),
        eq(certificate)))
        .thenReturn(
            CertificateDataConfigDate.builder().build()
        );

    when(certificateDataValueConverterDate.getType())
        .thenReturn(ElementType.DATE);

    when(certificateDataValueConverterDate.convert(any(), any()))
        .thenReturn(
            CertificateDataValueDate.builder().build()
        );

    final var result = certificateDataConverter.convert(certificate, false);

    assertNotNull(result.get(ID_2),
        "Should include CertificateDataElementDTO if certificate is draft and missing element data");
  }

  @Test
  void shallNotConvertSpecificationsOfIssuingUnitTypeConfiguration() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationUnitContactInformation.builder().build()
        )
        .build();

    final var elementSpecifications = List.of(
        elementSpecification
    );

    final var certificateModel = CertificateModel.builder()
        .elementSpecifications(elementSpecifications)
        .build();

    final var certificate = certificateBuilder
        .certificateModel(certificateModel)
        .build();

    final var result = certificateDataConverter.convert(certificate, false);

    assertTrue(result.isEmpty(),
        "Should not convert ElementConfigurationMetaData");
  }

  @Test
  void shallNotConvertSpecificationsOfMessageConfigurationIfNotUnsigned() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationMessage.builder().build()
        )
        .build();

    final var elementSpecifications = List.of(
        elementSpecification
    );

    final var certificateModel = CertificateModel.builder()
        .elementSpecifications(elementSpecifications)
        .build();

    final var certificate = certificateBuilder
        .status(Status.SIGNED)
        .certificateModel(certificateModel)
        .build();

    final var result = certificateDataConverter.convert(certificate, false);

    assertTrue(result.isEmpty(),
        "Should not convert message");
  }

  @Nested
  class ConverterForCitizenCertificate {

    @BeforeEach
    void setUp() {
      when(certificateDataDateConfigConverter.getType())
          .thenReturn(ElementType.DATE);
      when(certificateDataValueConverterDate.getType())
          .thenReturn(ElementType.DATE);
      when(certificateDataDateConfigConverter.convert(any(ElementSpecification.class),
          any(MedicalCertificate.class)))
          .thenReturn(
              CertificateDataConfigDate.builder().build()
          );
      when(certificateDataValueConverterDate.convert(any(), any()))
          .thenReturn(
              CertificateDataValueDate.builder().build()
          );
    }

    @Test
    void shallRemoveChildElementSpecificationIfIncludeForCitizenIsFalseAndCertificateIsForCitizen() {
      final var elementId1 = new ElementId(ID_1);
      final var elementId2 = new ElementId(ID_2);

      final var elementSpecifications = List.of(
          ElementSpecification.builder()
              .id(elementId1)
              .includeForCitizen(true)
              .configuration(
                  ElementConfigurationDate.builder().build()
              )
              .children(List.of(ElementSpecification.builder()
                  .includeForCitizen(false)
                  .id(elementId2)
                  .configuration(
                      ElementConfigurationDate.builder().build()
                  )
                  .build()))
              .build()
      );

      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(elementSpecifications)
          .build();

      final var certificate = certificateBuilder
          .certificateModel(certificateModel)
          .build();

      final var result = certificateDataConverter.convert(certificate, true);

      assertEquals(1, result.size(), "Map should contain one entry");
      assertTrue(result.containsKey(ID_1),
          "Map should only contain key 'id1'");
    }

    @Test
    void shallRemoveParentElementSpecificationIfIncludeForCitizenIsFalseAndCertificateIsForCitizen() {
      final var elementId1 = new ElementId(ID_1);
      final var elementId2 = new ElementId(ID_2);

      final var elementSpecifications = List.of(
          ElementSpecification.builder()
              .id(elementId1)
              .includeForCitizen(true)
              .configuration(
                  ElementConfigurationDate.builder().build()
              )
              .build(),
          ElementSpecification.builder()
              .includeForCitizen(false)
              .id(elementId2)
              .configuration(
                  ElementConfigurationDate.builder().build()
              )
              .build()
      );

      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(elementSpecifications)
          .build();

      final var certificate = certificateBuilder
          .certificateModel(certificateModel)
          .build();

      final var result = certificateDataConverter.convert(certificate, true);

      assertEquals(1, result.size(), "Map should contain one entry");
      assertTrue(result.containsKey(ID_1),
          "Map should only contain key 'id1'");
    }

    @Test
    void shallNotRemoveElementSpecificationIfCertificateIsForCare() {
      final var elementId1 = new ElementId(ID_1);
      final var elementId2 = new ElementId(ID_2);

      final var elementSpecifications = List.of(
          ElementSpecification.builder()
              .id(elementId1)
              .includeForCitizen(true)
              .configuration(
                  ElementConfigurationDate.builder().build()
              )
              .build(),
          ElementSpecification.builder()
              .id(elementId2)
              .includeForCitizen(false)
              .configuration(
                  ElementConfigurationDate.builder().build()
              )
              .build()
      );

      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(elementSpecifications)
          .build();

      final var certificate = certificateBuilder
          .certificateModel(certificateModel)
          .build();

      final var result = certificateDataConverter.convert(certificate, false);

      assertEquals(2, result.size(), "Map should contain two entries");
      assertTrue(result.containsKey(ID_1) && result.containsKey(ID_2),
          "Map should contain keys 'id1' and 'id2' ");
    }

  }

  @Nested
  class FilterCategoriesWithChildrenWithoutElementValueTests {

    @Test
    void shallNotReturnCertificateElementIfCertificateIsSignedAndElementIsCategoryWhenChildrenElementDataIsNull() {
      final var elementId = new ElementId(ID_1);
      final var elementIdWithoutValue = new ElementId(ID_2);

      final var elementSpecification = ElementSpecification.builder()
          .id(elementId)
          .configuration(
              ElementConfigurationCategory.builder().build()
          )
          .children(
              List.of(
                  ElementSpecification.builder()
                      .id(elementIdWithoutValue)
                      .configuration(
                          ElementConfigurationDate.builder().build()
                      )
                      .build()
              )
          )
          .build();

      final var elementSpecifications = List.of(
          elementSpecification
      );

      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(elementSpecifications)
          .build();

      final var certificate = certificateBuilder
          .certificateModel(certificateModel)
          .status(Status.SIGNED)
          .build();

      final var result = certificateDataConverter.convert(certificate, false);

      assertNull(result.get(ID_2),
          "Should not include CertificateDataElementDTO if certificate is signed and no element value found for children");
      assertTrue(result.isEmpty(), "Map should not contain any entries");
    }

    @Test
    void shallReturnCertificateElementIfCertificateIsDraftAndElementIsCategoryWhenChildrenElementDataIsNull() {
      final var elementId = new ElementId(ID_1);
      final var elementIdWithoutValue = new ElementId(ID_2);

      final var elementSpecification = ElementSpecification.builder()
          .id(elementId)
          .configuration(
              ElementConfigurationCategory.builder()
                  .build()
          )
          .children(
              List.of(
                  ElementSpecification.builder()
                      .id(elementIdWithoutValue)
                      .configuration(
                          ElementConfigurationDate.builder().build()
                      )
                      .build()
              )
          )
          .build();

      final var elementSpecifications = List.of(
          elementSpecification
      );

      final var certificateModel = CertificateModel.builder()
          .elementSpecifications(elementSpecifications)
          .build();

      final var certificate = certificateBuilder
          .certificateModel(certificateModel)
          .status(Status.DRAFT)
          .build();

      when(certificateDataDateConfigConverter.getType())
          .thenReturn(ElementType.CATEGORY);
      when(certificateDataDateConfigConverterDate.getType())
          .thenReturn(ElementType.DATE);

      when(certificateDataDateConfigConverter.convert(any(ElementSpecification.class),
          eq(certificate)))
          .thenReturn(
              CertificateDataConfigDate.builder().build()
          );

      when(certificateDataDateConfigConverterDate.convert(any(ElementSpecification.class),
          eq(certificate)))
          .thenReturn(
              CertificateDataConfigDate.builder().build()
          );

      when(certificateDataValueConverterDate.getType())
          .thenReturn(ElementType.DATE);

      when(certificateDataValueConverterDate.convert(any(), any()))
          .thenReturn(
              CertificateDataValueDate.builder().build()
          );

      final var result = certificateDataConverter.convert(certificate, false);

      assertNotNull(result.get(ID_2),
          "Should include CertificateDataElementDTO if certificate is draft and no element value found for children");
      assertEquals(2, result.size(), "Map should contain two entries");
    }
  }
}