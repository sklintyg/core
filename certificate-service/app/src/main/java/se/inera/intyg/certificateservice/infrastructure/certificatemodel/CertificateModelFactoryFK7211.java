package se.inera.intyg.certificateservice.infrastructure.certificatemodel;

import static se.inera.intyg.certificateservice.application.common.CertificateConstants.ISSUING_UNIT;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationIssuingUnit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRule;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementValidationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;

@Component
public class CertificateModelFactoryFK7211 implements CertificateModelFactory {

  @Value("${certificate.model.fk7211.v1_0.active.from}")
  private LocalDateTime activeFrom;
  private static final String FK_7211 = "fk7211";
  private static final String VERSION = "1.0";
  private static final String NAME = "Intyg om graviditet";
  private static final String DESCRIPTION = "Intyg om graviditet "
      + "Ungefär i vecka 20 får du ett intyg om graviditet av barnmorskan. Intyget anger "
      + "också datum för beräknad förlossning. Intyget skickar du till Försäkringskassan, "
      + "som ger besked om kommande föräldrapenning.";

  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(new CertificateType(FK_7211))
                .version(new CertificateVersion(VERSION))
                .build()
        )
        .name(NAME)
        .description(DESCRIPTION)
        .activeFrom(activeFrom)
        .certificateActionSpecifications(
            List.of(
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.CREATE)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.READ)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.UPDATE)
                    .build()
            )
        )
        .elementSpecifications(
            List.of(
                categoryBeraknatNedkomstdatum(
                    questionBeraknatNedkomstdatum()
                ),
                issuingUnitContactInfo()
            )
        )
        .build();
  }

  private static ElementSpecification categoryBeraknatNedkomstdatum(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(new ElementId("KAT_1"))
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Beräknat nedkomstdatum")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }

  private static ElementSpecification questionBeraknatNedkomstdatum() {
    return ElementSpecification.builder()
        .id(new ElementId("FRG_1"))
        .configuration(
            ElementConfigurationDate.builder()
                .name("Beräknat nedkomstdatum")
                .id("beraknatnedkomstdatum")
                .minDate(LocalDate.now().minus(Period.ofDays(0)))
                .maxDate(LocalDate.now().plus(Period.ofYears(1)))
                .build()
        )
        .rules(
            List.of(
                ElementRule.builder()
                    .id(new ElementId("FRG_1"))
                    .type(ElementRuleType.MANDATORY)
                    .expression(
                        new RuleExpression("$beraknatnedkomstdatum")
                    )
                    .build()
            )
        )
        .validations(
            List.of(
                ElementValidationDate.builder()
                    .mandatory(true)
                    .min(Period.ofDays(0))
                    .max(Period.ofYears(1))
                    .build()
            )
        )
        .build();
  }

  private static ElementSpecification issuingUnitContactInfo() {
    return ElementSpecification.builder()
        .id(new ElementId(ISSUING_UNIT))
        .configuration(
            ElementConfigurationIssuingUnit.builder()
                .build()
        )
        .build();
  }

}
