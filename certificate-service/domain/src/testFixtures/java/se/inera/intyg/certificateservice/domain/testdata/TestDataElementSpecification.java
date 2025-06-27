package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.CATEGORY_ELEMENT_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.CATEGORY_ELEMENT_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.DATE_ELEMENT_CONFIGURATION_MAX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.DATE_ELEMENT_CONFIGURATION_MIN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.DATE_ELEMENT_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.DATE_ELEMENT_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.DATE_ELEMENT_RULE_EXPRESSION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.DATE_ELEMENT_VALUE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.ISSUING_UNIT_ELEMENT_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.MESSAGE_ELEMENT_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.MESSAGE_ELEMENT_NAME;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleExpression;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDate;

public class TestDataElementSpecification {

  public static final ElementSpecification DATE_ELEMENT_SPECIFICATION = dateElementSpecificationBuilder().build();
  public static final ElementSpecification CATEGORY_ELEMENT_SPECIFICATION = categoryElementSpecificationBuilder().build();
  public static final ElementSpecification MESSAGE_ELEMENT_SPECIFICATION = messageElementSpecificationBuilder().build();
  public static final ElementSpecification ISSUING_UNIT_ELEMENT_SPECIFICATION = issuingUnitElementSpecificationBuilder().build();


  public static ElementSpecification.ElementSpecificationBuilder dateElementSpecificationBuilder() {
    return ElementSpecification.builder()
        .id(new ElementId(DATE_ELEMENT_ID))
        .configuration(
            ElementConfigurationDate.builder()
                .name(DATE_ELEMENT_NAME)
                .id(new FieldId(DATE_ELEMENT_VALUE_ID))
                .min(DATE_ELEMENT_CONFIGURATION_MIN)
                .max(DATE_ELEMENT_CONFIGURATION_MAX)
                .build()
        )
        .rules(
            List.of(
                ElementRuleExpression.builder()
                    .id(new ElementId(DATE_ELEMENT_ID))
                    .type(ElementRuleType.MANDATORY)
                    .expression(new RuleExpression(DATE_ELEMENT_RULE_EXPRESSION))
                    .build()
            )
        )
        .validations(
            List.of(
                ElementValidationDate.builder().build()
            )
        );
  }

  public static ElementSpecification.ElementSpecificationBuilder categoryElementSpecificationBuilder() {
    return ElementSpecification.builder()
        .id(new ElementId(CATEGORY_ELEMENT_ID))
        .configuration(
            ElementConfigurationCategory.builder()
                .name(CATEGORY_ELEMENT_NAME)
                .build()
        );
  }

  public static ElementSpecification.ElementSpecificationBuilder messageElementSpecificationBuilder() {
    return ElementSpecification.builder()
        .id(new ElementId(MESSAGE_ELEMENT_ID))
        .configuration(
            ElementConfigurationMessage.builder()
                .name(MESSAGE_ELEMENT_NAME)
                .build()
        );
  }

  public static ElementSpecification.ElementSpecificationBuilder issuingUnitElementSpecificationBuilder() {
    return ElementSpecification.builder()
        .id(new ElementId(ISSUING_UNIT_ELEMENT_ID))
        .configuration(
            ElementConfigurationUnitContactInformation.builder()
                .build()
        );
  }
}
