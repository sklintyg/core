package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226;

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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SchematronPath;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCode;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDate;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateList;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationUnitContactInformation;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateRecipientFactory;

@Component
public class CertificateModelFactoryFK3226 implements CertificateModelFactory {

  private static final FieldId ENDAST_PALLIATIV_FIELD_ID = new FieldId("ENDAST_PALLIATIV");
  private static final FieldId AKUT_LIVSHOTANDE_FIELD_ID = new FieldId("AKUT_LIVSHOTANDE");
  private static final FieldId ANNAT_FIELD_ID = new FieldId("ANNAT");
  private static final FieldId UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID = new FieldId("annat");
  private static final String UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID = "journaluppgifter";
  private static final String UTLATANDE_BASERAT_PA_UNDERSOKNING_AV_PATIENTEN_FIELD_ID = "undersokningAvPatienten";
  private static final ElementId QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_AKUT_LIVSHOTANDE_ID = new ElementId(
      "52.4");
  private static final FieldId QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_AKUT_LIVSHOTANDE_FIELD_ID = new FieldId(
      "52.4");
  private static final ElementId QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_ID = new ElementId(
      "52.5");
  private static final FieldId QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_FIELD_ID = new FieldId(
      "52.5");
  @Value("${certificate.model.fk7472.v1_0.active.from}")
  private LocalDateTime activeFrom;
  private static final String TYPE = "fk3226";
  private static final String VERSION = "1.0";
  private static final String NAME = "Läkarutlåtande för närståendepenning";
  private static final String DETAILED_DESCRIPTION = """
      <b className="iu-fw-heading">Vad är närståendepenning?</b><br>
      <p>Närståendepenning är en ersättning för den som avstår från att förvärvsarbeta för att vara med en patient som är svårt sjuk i lagens mening. I lagen definierar man svårt sjuk som att patientens hälsotillstånd är så nedsatt att det finns ett påtagligt hot mot hens liv på viss tids sikt. Sjukdomstillstånd som på flera års sikt utvecklas till livshotande tillstånd ger däremot inte rätt till närståendepenning.</p>
      <p>Att ge hjälp och stöd till en person som inte har ett livshotande tillstånd kan inte ge rätt till närståendepenning.</p>
      <b className="iu-fw-heading">Vem är närstående?</b><br>
      <p>Till närstående räknas anhöriga, men även andra som har nära relationer med den som är sjuk till exempel vänner eller grannar. Flera närstående kan turas om och få ersätttning för olika dagar eller olika delar av dagar.</p>
      <b className="iu-fw-heading">Ansökan och samtycke</b><br>
      <p>När den närstående som stödjer patienten ansöker om närståendepenning ska hen bifoga blankett Samtycke för närståendepenning. Det gäller i de fall patienten har medicinska förutsättningar för att kunna samtycka till en närståendes stöd.</p>
      """;
  private static final String DESCRIPTION = """
         <b className="iu-fw-heading">Vad är närståendepenning?</b><br>
         <p>Närståendepenning är en ersättning för den som avstår från att förvärvsarbeta för att vara med en patient som är svårt sjuk i lagens mening. I lagen definierar man svårt sjuk som att patientens hälsotillstånd är så nedsatt att det finns ett påtagligt hot mot hens liv på viss tids sikt. Sjukdomstillstånd som på flera års sikt utvecklas till livshotande tillstånd ger däremot inte rätt till närståendepenning.</p>
      """;
  public static final CertificateModelId FK3226_V1_0 = CertificateModelId.builder()
      .type(new CertificateType(TYPE))
      .version(new CertificateVersion(VERSION))
      .build();
  public static final ElementId QUESTION_GRUND_CATEGORY_ID = new ElementId("KAT_1");
  public static final ElementId QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID = new ElementId(
      "52");
  public static final FieldId QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_FIELD_ID = new FieldId(
      "52.1");
  public static final ElementId QUESTION_UTLATANDE_BASERAT_PA_ID = new ElementId("1");
  private static final FieldId QUESTION_UTLATANDE_BASERAT_PA_FIELD_ID = new FieldId("1.1");
  public static final ElementId QUESTION_UTLATANDE_BASERAT_PA_ANNAT_ID = new ElementId("1.3");
  public static final FieldId QUESTION_UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID = new FieldId("1.3");
  private static final ElementId QUESTION_HOT_CATEGORY_ID = new ElementId("KAT_2");
  public static final ElementId QUESTION_NAR_TILLSTANDET_BLEV_AKUT_LIVSHOTANDE_ID = new ElementId(
      "52.3");
  private static final FieldId QUESTION_NAR_TILLSTANDET_BLEV_AKUT_LIVSHOTANDE_FIELD_ID = new FieldId(
      "52.3");
  public static final ElementId QUESTION_NAR_AKTIVA_BEHANDLINGEN_AVSLUTADES_ID = new ElementId(
      "52.2");
  private static final FieldId QUESTION_NAR_AKTIVA_BEHANDLINGEN_AVSLUTADES_FIELD_ID = new FieldId(
      "52.2");
  private static final ElementId QUESTION_SAMTYCKE_CATEGORY_ID = new ElementId("KAT_3");
  public static final SchematronPath SCHEMATRON_PATH = new SchematronPath(
      "fk7472/schematron/lunsp.v1.sch");

  public static final String PDF_FK_3226_PDF = "fk7472/pdf/fk7472_v1.pdf";
  public static final String PDF_NO_ADDRESS_FK_7472_PDF = "fk7472/pdf/fk7472_v1_no_address.pdf";

  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(FK3226_V1_0)
        .type(
            new Code(
                "LUNSP",
                "b64ea353-e8f6-4832-b563-fc7d46f29548",
                NAME
            )
        )
        .name(NAME)
        .description(DESCRIPTION)
        .detailedDescription(DETAILED_DESCRIPTION)
        .activeFrom(activeFrom)
        .availableForCitizen(false)
        .rolesWithAccess(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE,
            Role.CARE_ADMIN))
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
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.REPLACE)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.REPLACE_CONTINUE)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.RENEW)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.SEND_AFTER_SIGN)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.MESSAGES)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.MESSAGES_ADMINISTRATIVE)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.RECEIVE_COMPLEMENT)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.COMPLEMENT)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.CANNOT_COMPLEMENT)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.FORWARD_MESSAGE)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.HANDLE_COMPLEMENT)
                    .build()
            )
        )
        .elementSpecifications(
            List.of(
                categoryGrund(
                    questionUtlatandeBaseratPa(),
                    questionUtlatandeBaseratPaAnnat()
                ),
                categoryHot(
                    questionPatientBehandlingOchVardsituation(),
                    questionNarAktivaBehandlingenAvslutades(),
                    questionNarTillstandetBlevAkutLivshotande(),
                    questionPatagligtHotMotPatientensLivAkutLivshotande(),
                    questionUppskattaHurLangeTillstandetKommerVaraLivshotande()
                ),
                categorySamtycke(),
                issuingUnitContactInfo()
            )
        )
        .pdfTemplatePath(PDF_FK_3226_PDF)
        .pdfNoAddressTemplatePath(PDF_NO_ADDRESS_FK_7472_PDF)
        .schematronPath(SCHEMATRON_PATH)
        .build();
  }

  private static ElementSpecification questionUppskattaHurLangeTillstandetKommerVaraLivshotande() {
    return ElementSpecification.builder()
        .id(QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_FIELD_ID)
                .name("Kan du uppskatta hur länge tillståndet kommer vara livshotande?")
                .selectedText("Ja")
                .unselectedText("Nej")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_ID,
                    QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_FIELD_ID
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID,
                    AKUT_LIVSHOTANDE_FIELD_ID
                )
            )
        )
        .build();
  }

  private static ElementSpecification questionPatagligtHotMotPatientensLivAkutLivshotande() {
    return ElementSpecification.builder()
        .id(QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_AKUT_LIVSHOTANDE_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_AKUT_LIVSHOTANDE_FIELD_ID)
                .name(
                    "Beskriv på vilket sätt  sjukdomstillståndet utgör ett påtagligt hot mot patientens liv")
                .description(
                    "Ange om möjligt hur länge hotet mot livet kvarstår när patienten får vård enligt den vårdplan som gäller.")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_AKUT_LIVSHOTANDE_ID,
                    QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_AKUT_LIVSHOTANDE_FIELD_ID
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID,
                    AKUT_LIVSHOTANDE_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit(4000)
                    .build()
            )
        )
        .build();
  }

  private static ElementSpecification questionNarAktivaBehandlingenAvslutades() {
    return ElementSpecification.builder()
        .id(QUESTION_NAR_AKTIVA_BEHANDLINGEN_AVSLUTADES_ID)
        .configuration(
            ElementConfigurationDate.builder()
                .name("Ange när den aktiva behandlingen avslutades")
                .id(QUESTION_NAR_AKTIVA_BEHANDLINGEN_AVSLUTADES_FIELD_ID)
                .max(Period.ofDays(0))
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_NAR_AKTIVA_BEHANDLINGEN_AVSLUTADES_ID,
                    QUESTION_NAR_AKTIVA_BEHANDLINGEN_AVSLUTADES_FIELD_ID
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID,
                    ENDAST_PALLIATIV_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationDate.builder()
                    .mandatory(true)
                    .max(Period.ofDays(0))
                    .build()
            )
        )
        .build();
  }

  private static ElementSpecification questionNarTillstandetBlevAkutLivshotande() {
    return ElementSpecification.builder()
        .id(QUESTION_NAR_TILLSTANDET_BLEV_AKUT_LIVSHOTANDE_ID)
        .configuration(
            ElementConfigurationDate.builder()
                .name("Ange när tillståndet blev akut livshotande")
                .id(QUESTION_NAR_TILLSTANDET_BLEV_AKUT_LIVSHOTANDE_FIELD_ID)
                .max(Period.ofDays(0))
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_NAR_TILLSTANDET_BLEV_AKUT_LIVSHOTANDE_ID,
                    QUESTION_NAR_TILLSTANDET_BLEV_AKUT_LIVSHOTANDE_FIELD_ID
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID,
                    AKUT_LIVSHOTANDE_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationDate.builder()
                    .mandatory(true)
                    .max(Period.ofDays(0))
                    .build()
            )
        )
        .build();
  }

  private static ElementSpecification questionPatientBehandlingOchVardsituation() {
    final var radioMultipleCodes = List.of(
        new ElementConfigurationCode(ENDAST_PALLIATIV_FIELD_ID,
            "Endast palliativ vård ges och all aktiv behandling mot sjukdomstillståndet har avslutats"),
        new ElementConfigurationCode(AKUT_LIVSHOTANDE_FIELD_ID,
            "Akut livshotande tillstånd (till exempel vård på intensivvårdsavdelning)"),
        new ElementConfigurationCode(ANNAT_FIELD_ID, "Annat")
    );

    return ElementSpecification.builder()
        .id(QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID)
        .configuration(
            ElementConfigurationRadioMultipleCode.builder()
                .id(QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_FIELD_ID)
                .name("Patientens behandling och vårdsituation")
                .elementLayout(ElementLayout.ROWS)
                .list(radioMultipleCodes)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID,
                    radioMultipleCodes.stream().map(ElementConfigurationCode::id).toList()
                )
            )
        )
        .validations(
            List.of(
                ElementValidationCode.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .build();
  }

  private static ElementSpecification categoryGrund(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_GRUND_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Grund för medicinskt underlag")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }

  private static ElementSpecification questionUtlatandeBaseratPa(ElementSpecification... children) {
    final var checkboxDates = List.of(
        new CheckboxDate(
            new FieldId(UTLATANDE_BASERAT_PA_UNDERSOKNING_AV_PATIENTEN_FIELD_ID),
            CodeSystemKvFkmu0001.UNDERSOKNING.displayName(),
            CodeSystemKvFkmu0001.UNDERSOKNING
        ),
        new CheckboxDate(
            new FieldId(UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID),
            CodeSystemKvFkmu0001.JOURNALUPGIFTER.displayName(),
            CodeSystemKvFkmu0001.JOURNALUPGIFTER
        ),
        new CheckboxDate(
            UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID,
            CodeSystemKvFkmu0001.ANNAT.displayName(),
            CodeSystemKvFkmu0001.ANNAT
        )
    );

    return ElementSpecification.builder()
        .id(QUESTION_UTLATANDE_BASERAT_PA_ID)
        .configuration(
            ElementConfigurationCheckboxMultipleDate.builder()
                .id(QUESTION_UTLATANDE_BASERAT_PA_FIELD_ID)
                .name("Utlåtandet är baserat på")
                .dates(checkboxDates)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_UTLATANDE_BASERAT_PA_ID,
                    checkboxDates.stream().map(CheckboxDate::id).toList()
                )
            )
        )
        .validations(
            List.of(
                ElementValidationDateList.builder()
                    .mandatory(true)
                    .max(Period.ofDays(0))
                    .build()
            )
        )
        .children(List.of(children))
        .build();
  }

  private static ElementSpecification questionUtlatandeBaseratPaAnnat() {
    return ElementSpecification.builder()
        .id(QUESTION_UTLATANDE_BASERAT_PA_ANNAT_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .name(
                    "Ange vad annat är och motivera vid behov varför du inte baserar utlåtandet på en undersökning eller på journaluppgifter")
                .id(QUESTION_UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_UTLATANDE_BASERAT_PA_ANNAT_ID,
                    QUESTION_UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_UTLATANDE_BASERAT_PA_ANNAT_ID,
                    (short) 4000),
                CertificateElementRuleFactory.show(
                    QUESTION_UTLATANDE_BASERAT_PA_ID,
                    UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(true)
                    .limit(4000)
                    .build()
            )
        )
        .mapping(
            new ElementMapping(
                QUESTION_UTLATANDE_BASERAT_PA_ID,
                new Code("annat", "codeSystem", "annat")
            )
        )
        .build();
  }

  private static ElementSpecification categoryHot(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_HOT_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Påtagligt hot mot patientens liv")
                .description("""
                    Ange på vilket sätt hälsotillståndet utgör ett påtagligt hot mot patientens liv i nuläget eller på någon tids sikt. Hälsotillstånd som på flera års sikt kan utvecklas till livshotande tillstånd ger däremot inte rätt till närståendepenning.
                                        \s
                    Hälsotillståndet kan utgöra ett påtagligt hot även om det finns hopp om att det förbättras.
                                        \s
                    <b>Särskilda regler för vissa HIV-smittade </b>
                    Ange om patienten blivit hiv-smittad på något av följande sätt.
                                        \s
                    1.   Patienten har blivit smittad när hen fick blod- eller blodprodukter, och smittades när hen behandlades av den svenska hälso- och sjukvården.
                    2.   Patienten har blivit smittad av nuvarande eller före detta make, maka, sambo eller registrerade partner, och den personen smittades när hen behandlades av den svenska hälso- och sjukvården.
                    """)
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }

  private static ElementSpecification categorySamtycke(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_SAMTYCKE_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Samtycke för närståendes stöd")
                .build()
        )
        .children(
            List.of(children)
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