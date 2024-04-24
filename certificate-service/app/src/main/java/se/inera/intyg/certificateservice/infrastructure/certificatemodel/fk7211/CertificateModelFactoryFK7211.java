package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7211;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation.UNIT_CONTACT_INFORMATION;

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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SchematronPath;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDate;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationUnitContactInformation;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateRecipientFactory;

@Component
public class CertificateModelFactoryFK7211 implements CertificateModelFactory {

  @Value("${certificate.model.fk7211.v1_0.active.from}")
  private LocalDateTime activeFrom;
  private static final String FK_7211 = "fk7211";
  private static final String VERSION = "1.0";
  private static final String NAME = "Intyg om graviditet";
  private static final String DESCRIPTION = """
      När en patient är gravid ska hen få ett intyg om graviditet av hälso- och sjukvården. Intyget behövs om föräldern begär ersättning från Försäkringskassan innan barnet är fött.
            
      Patienten ska:
      Be barnmorskan, läkaren eller sjuksköterskan om ett intyg om graviditet
      Barnmorskan, läkaren eller sjuksköterskan ska:
      Skriva ett intyg om graviditet och skicka in intyget om graviditet till Försäkringskassan digitalt.""";
  public static final CertificateModelId FK7211_V1_0 = CertificateModelId.builder()
      .type(new CertificateType(FK_7211))
      .version(new CertificateVersion(VERSION))
      .build();
  public static final ElementId QUESTION_BERAKNAT_NEDKOMSTDATUM_CATEGORY_ID = new ElementId(
      "KAT_1");
  public static final ElementId QUESTION_BERAKNAT_NEDKOMSTDATUM_ID = new ElementId("1");
  private static final FieldId QUESTION_BERAKNAT_NEDKOMSTDATUM_FIELD_ID = new FieldId("1.1");
  public static final String PDF_FK_7211_PDF = "fk7211/pdf/fk7211_v1.pdf";
  public static final SchematronPath SCHEMATRON_PATH = new SchematronPath(
      "fk7211/schematron/igrav.v1.sch");


  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(FK7211_V1_0)
        .type(
            new Code(
                "IGRAV",
                "b64ea353-e8f6-4832-b563-fc7d46f29548",
                NAME
            )
        )
        .name(NAME)
        .description(DESCRIPTION)
        .activeFrom(activeFrom)
        .availableForCitizen(true)
        .recipient(CertificateRecipientFactory.fkassa())
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
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.DELETE)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.SIGN)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.SEND)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.PRINT)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.REVOKE)
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
        .pdfTemplatePath(PDF_FK_7211_PDF)
        .schematronPath(SCHEMATRON_PATH)
        .build();
  }

  private static ElementSpecification categoryBeraknatNedkomstdatum(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_BERAKNAT_NEDKOMSTDATUM_CATEGORY_ID)
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
        .id(QUESTION_BERAKNAT_NEDKOMSTDATUM_ID)
        .configuration(
            ElementConfigurationDate.builder()
                .name("Beräknat nedkomstdatum")
                .id(QUESTION_BERAKNAT_NEDKOMSTDATUM_FIELD_ID)
                .min(Period.ofDays(0))
                .max(Period.ofYears(1))
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_BERAKNAT_NEDKOMSTDATUM_ID,
                    QUESTION_BERAKNAT_NEDKOMSTDATUM_FIELD_ID
                )
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
        .id(UNIT_CONTACT_INFORMATION)
        .configuration(
            ElementConfigurationUnitContactInformation.builder().build()
        )
        .validations(
            List.of(
                ElementValidationUnitContactInformation.builder().build()
            )
        )
        .build();
  }
}
