package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.DATE_ELEMENT_CONFIGURATION_MAX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.DATE_ELEMENT_CONFIGURATION_MIN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.DATE_ELEMENT_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.DATE_ELEMENT_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.DATE_ELEMENT_RULE_EXPRESSION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.DATE_ELEMENT_VALUE_ID;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRule;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementValidationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;

public class TestDataElementSpecification {

  public static final ElementSpecification DATE_ELEMENT_SPECIFICATION = dateElementSpecificationBuilder().build();

  public static ElementSpecification.ElementSpecificationBuilder dateElementSpecificationBuilder() {
    return ElementSpecification.builder()
        .id(new ElementId(DATE_ELEMENT_ID))
        .configuration(
            ElementConfigurationDate.builder()
                .name(DATE_ELEMENT_NAME)
                .id(DATE_ELEMENT_VALUE_ID)
                .minDate(DATE_ELEMENT_CONFIGURATION_MIN)
                .maxDate(DATE_ELEMENT_CONFIGURATION_MAX)
                .build()
        )
        .rules(
            List.of(
                ElementRule.builder()
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
}
