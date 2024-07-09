package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation.UNIT_CONTACT_INFORMATION;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateMessageType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisListItem;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMessageLevel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SchematronPath;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.Subject;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCode;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDate;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateList;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDiagnosis;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationUnitContactInformation;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateRecipientFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CodeSystemKvFkmu0001;

@Component
@RequiredArgsConstructor
public class CertificateModelFactoryFK3226 implements CertificateModelFactory {

  private final DiagnosisCodeRepository diagnosisCodeRepositoy;
  public static final FieldId ENDAST_PALLIATIV_FIELD_ID = new FieldId("ENDAST_PALLIATIV");
  public static final FieldId AKUT_LIVSHOTANDE_FIELD_ID = new FieldId("AKUT_LIVSHOTANDE");
  private static final FieldId ANNAT_FIELD_ID = new FieldId("ANNAT");
  public static final FieldId UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID = new FieldId("annat");
  public static final String UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID = "journaluppgifter";
  public static final String UTLATANDE_BASERAT_PA_UNDERSOKNING_AV_PATIENTEN_FIELD_ID = "undersokningAvPatienten";
  public static final ElementId QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_AKUT_LIVSHOTANDE_ID = new ElementId(
      "52.4");
  private static final FieldId QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_AKUT_LIVSHOTANDE_FIELD_ID = new FieldId(
      "52.4");
  public static final ElementId QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_ID = new ElementId(
      "52.5");
  private static final FieldId QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_FIELD_ID = new FieldId(
      "52.5");
  public static final ElementId QUESTION_TILLSTANDET_UPPSKATTAS_LIVSHOTANDE_TILL_OCH_MED_ID = new ElementId(
      "52.6");
  private static final FieldId QUESTION_TILLSTANDET_UPPSKATTAS_LIVSHOTANDE_TILL_OCH_MED_FIELD_ID = new FieldId(
      "52.6");
  private static final ElementId QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_ANNAT_ID = new ElementId(
      "52.7");
  private static final FieldId QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_ANNAT_FIELD_ID = new FieldId(
      "52.7");
  public static final ElementId FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_ID = new ElementId(
      "53");
  private static final FieldId FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_FIELD_ID = new FieldId(
      "53.1");
  private static final FieldId DIAGNOSIS_FIELD_ID = new FieldId(
      "58.1");
  public static final ElementId DIAGNOSIS_ID = new ElementId("58");
  public static final FieldId DIAGNOS_1 = new FieldId("huvuddiagnos");
  public static final FieldId DIAGNOS_2 = new FieldId("diagnos2");
  public static final FieldId DIAGNOS_3 = new FieldId("diagnos3");
  public static final FieldId DIAGNOS_4 = new FieldId("diagnos4");
  public static final FieldId DIAGNOS_5 = new FieldId("diagnos5");
  private static final short DIAGNOSIS_CODE_LIMIT = (short) 81;
  @Value("${certificate.model.fk3226.v1_0.active.from}")
  private LocalDateTime activeFrom;
  private static final String TYPE = "fk3226";
  private static final String VERSION = "1.0";
  private static final String NAME = "Läkarutlåtande för närståendepenning";
  private static final String DETAILED_DESCRIPTION = """
      <b className="iu-fw-heading">Vad är närståendepenning?</b><br>
      <p>Närståendepenning är en ersättning för den som avstår från att förvärvsarbeta för att vara med en patient som är svårt sjuk i lagens mening. I lagen definierar man svårt sjuk som att patientens hälsotillstånd är så nedsatt att det finns ett påtagligt hot mot hens liv på viss tids sikt. Sjukdomstillstånd som på flera års sikt utvecklas till livshotande tillstånd ger däremot inte rätt till närståendepenning.</p>
      <b className="iu-fw-heading">Vem är närstående?</b><br>
      <p>Till närstående räknas anhöriga, men även andra som har nära relationer med den som är sjuk till exempel vänner eller grannar. Flera närstående kan turas om och få ersätttning för olika dagar eller olika delar av dagar.</p>
      <b className="iu-fw-heading">Ansökan och samtycke</b><br>
      <p>När den närstående som stödjer patienten ansöker om närståendepenning ska hen bifoga blankett Samtycke för närståendepenning. Det gäller i de fall patienten har medicinska förutsättningar för att kunna samtycka till en närståendes stöd.</p>
      <b className="iu-fw-heading">Särskilda regler för vissa HIV-smittade</b><br>
      <p>Om patienten har hiv och har blivit smittad på något av följande sätt, ange det vid “Annat” under “Patientens behandling och vårdsituation”.</p>
      <ol>
        <li>Patienten har blivit smittad när hen fick blod- eller blodprodukter, och smittades när hen behandlades av den svenska hälso- och sjukvården.</li>
        <li>Patienten har blivit smittad av nuvarande eller före detta make, maka, sambo eller registrerade partner, och den personen smittades när hen behandlades av den svenska hälso- och sjukvården.</li>
      </ol>
      """;
  private static final String DESCRIPTION = """
         <b className="iu-fw-heading">Vad är närståendepenning?</b><br>
         <p>Närståendepenning är en ersättning för den som avstår från förvärvsarbete, arbetslöshetsersättning (a-kassa) eller föräldrapenning för att vara med en patient som är svårt sjuk i lagens mening. I lagen definierar man svårt sjuk som att patientens hälsotillstånd är så nedsatt att det finns ett påtagligt hot mot hens liv i nuläget eller på viss tids sikt. Sjukdomstillstånd som på flera års sikt utvecklas till livshotande tillstånd ger däremot inte rätt till närståendepenning.</p>
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
  public static final FieldId QUESTION_UTLATANDE_BASERAT_PA_FIELD_ID = new FieldId("1.1");
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
      "fk3226/schematron/lunsp.v1.sch");

  public static final String PDF_FK_3226_PDF = "fk3226/pdf/fk3226_v1.pdf";
  public static final String PDF_NO_ADDRESS_FK_7472_PDF = "fk3226/pdf/fk3226_v1_no_address.pdf";

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
        .availableForCitizen(true)
        .summaryProvider(new FK3226CertificateSummaryProvider())
        .texts(
            List.of(
                CertificateText.builder()
                    .text(
                        "Det här är ditt intyg. Intyget innehåller all information som vården fyllt i. Du kan inte ändra något i ditt intyg. Har du frågor kontaktar du den som skrivit ditt intyg."
                    )
                    .type(CertificateTextType.PREAMBLE_TEXT)
                    .links(Collections.emptyList())
                    .build()
            )
        )
        .rolesWithAccess(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR, Role.NURSE, Role.MIDWIFE,
            Role.CARE_ADMIN))
        .recipient(CertificateRecipientFactory.fkassa())
        .messageTypes(
            List.of(
                CertificateMessageType.builder()
                    .type(MessageType.MISSING)
                    .subject(new Subject(MessageType.MISSING.displayName()))
                    .build(),
                CertificateMessageType.builder()
                    .type(MessageType.CONTACT)
                    .subject(new Subject(MessageType.CONTACT.displayName()))
                    .build(),
                CertificateMessageType.builder()
                    .type(MessageType.OTHER)
                    .subject(new Subject(MessageType.OTHER.displayName()))
                    .build()
            )
        )
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
                    .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR))
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.SEND)
                    .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR))
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.PRINT)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.REVOKE)
                    .allowedRoles(List.of(Role.DOCTOR, Role.PRIVATE_DOCTOR))
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
                    .enabled(true)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.RECEIVE_COMPLEMENT)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.RECEIVE_QUESTION)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.RECEIVE_ANSWER)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.RECEIVE_REMINDER)
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
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.CREATE_MESSAGE)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.ANSWER_MESSAGE)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.SAVE_MESSAGE)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.DELETE_MESSAGE)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.SEND_MESSAGE)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.SAVE_ANSWER)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.DELETE_ANSWER)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.SEND_ANSWER)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.HANDLE_MESSAGE)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.FORWARD_CERTIFICATE)
                    .build(),
                CertificateActionSpecification.builder()
                    .certificateActionType(CertificateActionType.FORWARD_MESSAGE)
                    .build()
            )
        )
        .elementSpecifications(
            List.of(
                categoryGrund(
                    questionUtlatandeBaseratPa(
                        questionUtlatandeBaseratPaAnnat()
                    )
                ),
                categoryHot(
                    questionDiagnos(),
                    questionPatientBehandlingOchVardsituation(
                        questionNarAktivaBehandlingenAvslutades(),
                        questionNarTillstandetBlevAkutLivshotande(),
                        questionPatagligtHotMotPatientensLivAkutLivshotande(),
                        questionUppskattaHurLangeTillstandetKommerVaraLivshotande(),
                        questionTillstandetUppskattasLivshotandeTillOchMed(),
                        questionPatagligtHotMotPatientensLivAnnat()
                    )
                ),
                categorySamtycke(
                    messageForutsattningarForAttLamnaSkriftligtSamtycke(),
                    questionForutsattningarForAttLamnaSkriftligtSamtycke()
                ),
                issuingUnitContactInfo()
            )
        )
        .pdfTemplatePath(PDF_FK_3226_PDF)
        .pdfNoAddressTemplatePath(PDF_NO_ADDRESS_FK_7472_PDF)
        .schematronPath(SCHEMATRON_PATH)
        .build();
  }


  private ElementSpecification questionDiagnos() {
    return ElementSpecification.builder()
        .id(DIAGNOSIS_ID)
        .configuration(
            ElementConfigurationDiagnosis.builder()
                .id(DIAGNOSIS_FIELD_ID)
                .name(
                    "Diagnos eller diagnoser för det tillstånd som orsakar ett hot mot patientens liv")
                .message(
                    ElementMessage.builder()
                        .content(
                            "Ange alla diagnoser som sammantaget medför ett påtagligt hot mot patientens liv.")
                        .level(ElementMessageLevel.OBSERVE)
                        .includedForStatuses(List.of(Status.DRAFT))
                        .build()
                )
                .terminology(
                    List.of(
                        CodeSystemIcd10Se.terminology()
                    )
                )
                .list(
                    List.of(
                        new ElementDiagnosisListItem(DIAGNOS_1),
                        new ElementDiagnosisListItem(DIAGNOS_2),
                        new ElementDiagnosisListItem(DIAGNOS_3),
                        new ElementDiagnosisListItem(DIAGNOS_4),
                        new ElementDiagnosisListItem(DIAGNOS_5)
                    )
                )
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryExist(DIAGNOSIS_ID,
                    DIAGNOS_1),
                CertificateElementRuleFactory.limit(DIAGNOSIS_ID, DIAGNOSIS_CODE_LIMIT)
            )
        )
        .validations(
            List.of(
                ElementValidationDiagnosis.builder()
                    .mandatoryField(DIAGNOS_1)
                    .order(
                        List.of(DIAGNOS_1, DIAGNOS_2, DIAGNOS_3, DIAGNOS_4, DIAGNOS_5)
                    )
                    .diagnosisCodeRepository(diagnosisCodeRepositoy)
                    .build()
            )
        )
        .build();
  }

  private static ElementSpecification messageForutsattningarForAttLamnaSkriftligtSamtycke() {
    return ElementSpecification.builder()
        .id(new ElementId("forutsattningar"))
        .configuration(
            ElementConfigurationMessage.builder()
                .message(
                    ElementMessage.builder()
                        .content(
                            "Om patienten har medicinska förutsättningar att samtycka till en närståendes stöd, så ska patienten göra det. Därför ska du fylla i om hen kan samtycka eller inte.")
                        .level(ElementMessageLevel.INFO)
                        .build()
                )
                .build()
        )
        .build();
  }

  private static ElementSpecification questionForutsattningarForAttLamnaSkriftligtSamtycke() {
    return ElementSpecification.builder()
        .id(FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_FIELD_ID)
                .name(
                    "Har patienten de medicinska förutsättningarna för att kunna lämna sitt samtycke?")
                .selectedText("Ja")
                .unselectedText("Nej")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryExist(
                    FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_ID,
                    FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .build();
  }

  private static ElementSpecification questionPatagligtHotMotPatientensLivAnnat() {
    return ElementSpecification.builder()
        .id(QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_ANNAT_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_ANNAT_FIELD_ID)
                .name(
                    "Beskriv på vilket sätt sjukdomstillståndet utgör ett påtagligt hot mot patientens liv")
                .label(
                    "Ange när tillståndet blev livshotande, och om det är möjligt hur länge hotet mot livet kvarstår när patienten får vård enligt den vårdplan som gäller."
                )
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_ANNAT_ID,
                    QUESTION_PATAGLIGT_HOT_MOT_PATIENTENS_LIV_ANNAT_FIELD_ID
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID,
                    ANNAT_FIELD_ID
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
        .shouldValidate(
            elementData -> elementData.stream()
                .filter(
                    data -> data.id().equals(QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID))
                .map(element -> (ElementValueCode) element.value())
                .anyMatch(value -> value.codeId().equals(ANNAT_FIELD_ID))
        )
        .mapping(
            new ElementMapping(QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID, null)
        )
        .build();
  }

  private static ElementSpecification questionTillstandetUppskattasLivshotandeTillOchMed() {
    return ElementSpecification.builder()
        .id(QUESTION_TILLSTANDET_UPPSKATTAS_LIVSHOTANDE_TILL_OCH_MED_ID)
        .configuration(
            ElementConfigurationDate.builder()
                .id(QUESTION_TILLSTANDET_UPPSKATTAS_LIVSHOTANDE_TILL_OCH_MED_FIELD_ID)
                .name("Till och med vilket datum")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_TILLSTANDET_UPPSKATTAS_LIVSHOTANDE_TILL_OCH_MED_ID,
                    QUESTION_TILLSTANDET_UPPSKATTAS_LIVSHOTANDE_TILL_OCH_MED_FIELD_ID
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_ID,
                    QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationDate.builder()
                    .mandatory(true)
                    .min(Period.ofDays(0))
                    .build()
            )
        )
        .shouldValidate(
            elementData -> elementData.stream()
                .filter(
                    data -> data.id()
                        .equals(QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_ID))
                .map(element -> (ElementValueBoolean) element.value())
                .anyMatch(value -> Boolean.TRUE.equals(value.value()))
        )
        .mapping(
            new ElementMapping(QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID, null)
        )
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
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_ID,
                    QUESTION_UPSKATTA_HUR_LANGE_TILLSTANDET_KOMMER_VARA_LIVSHOTANDE_FIELD_ID
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID,
                    AKUT_LIVSHOTANDE_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .shouldValidate(
            elementData -> elementData.stream()
                .filter(
                    data -> data.id().equals(QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID))
                .map(element -> (ElementValueCode) element.value())
                .anyMatch(value -> value.codeId().equals(AKUT_LIVSHOTANDE_FIELD_ID))
        )
        .mapping(
            new ElementMapping(QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID, null)
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
                .label(
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
        .shouldValidate(
            elementData -> elementData.stream()
                .filter(
                    data -> data.id().equals(QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID))
                .map(element -> (ElementValueCode) element.value())
                .anyMatch(value -> value.codeId().equals(AKUT_LIVSHOTANDE_FIELD_ID))
        )
        .mapping(
            new ElementMapping(QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID, null)
        )
        .build();
  }

  private static ElementSpecification questionNarAktivaBehandlingenAvslutades() {
    return ElementSpecification.builder()
        .id(QUESTION_NAR_AKTIVA_BEHANDLINGEN_AVSLUTADES_ID)
        .configuration(
            ElementConfigurationDate.builder()
                .id(QUESTION_NAR_AKTIVA_BEHANDLINGEN_AVSLUTADES_FIELD_ID)
                .name("Ange när den aktiva behandlingen avslutades")
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
        .shouldValidate(
            elementData -> elementData.stream()
                .filter(
                    data -> data.id().equals(QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID))
                .map(element -> (ElementValueCode) element.value())
                .anyMatch(value -> value.codeId().equals(ENDAST_PALLIATIV_FIELD_ID))
        )
        .mapping(
            new ElementMapping(QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID, null)
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
        .shouldValidate(
            elementData -> elementData.stream()
                .filter(
                    data -> data.id().equals(QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID))
                .map(element -> (ElementValueCode) element.value())
                .anyMatch(value -> value.codeId().equals(AKUT_LIVSHOTANDE_FIELD_ID))
        )
        .mapping(
            new ElementMapping(QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID, null)
        )
        .build();
  }

  private static ElementSpecification questionPatientBehandlingOchVardsituation(
      ElementSpecification... children) {
    final var radioMultipleCodes = List.of(
        new ElementConfigurationCode(
            ENDAST_PALLIATIV_FIELD_ID,
            CodeSystemKvFkmu0010.ENDAST_PALLIATIV.displayName(),
            CodeSystemKvFkmu0010.ENDAST_PALLIATIV
        ),
        new ElementConfigurationCode(
            AKUT_LIVSHOTANDE_FIELD_ID,
            CodeSystemKvFkmu0010.AKUT_LIVSHOTANDE.displayName(),
            CodeSystemKvFkmu0010.AKUT_LIVSHOTANDE
        ),
        new ElementConfigurationCode(
            ANNAT_FIELD_ID,
            CodeSystemKvFkmu0010.ANNAT.displayName(),
            CodeSystemKvFkmu0010.ANNAT
        )
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
        .children(List.of(children))
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
        CheckboxDate.builder()
            .id(new FieldId(UTLATANDE_BASERAT_PA_UNDERSOKNING_AV_PATIENTEN_FIELD_ID))
            .label(CodeSystemKvFkmu0001.UNDERSOKNING.displayName())
            .code(CodeSystemKvFkmu0001.UNDERSOKNING)
            .min(null)
            .max(Period.ofDays(0))
            .build(),
        CheckboxDate.builder()
            .id(new FieldId(UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID))
            .label(CodeSystemKvFkmu0001.JOURNALUPGIFTER.displayName())
            .code(CodeSystemKvFkmu0001.JOURNALUPGIFTER)
            .min(null)
            .max(Period.ofDays(0))
            .build(),
        CheckboxDate.builder()
            .id(UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID)
            .label(CodeSystemKvFkmu0001.ANNAT.displayName())
            .code(CodeSystemKvFkmu0001.ANNAT)
            .min(null)
            .max(Period.ofDays(0))
            .build()
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
                .id(QUESTION_UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID)
                .name(
                    "Ange vad annat är")
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
                CodeSystemKvFkmu0001.ANNAT
            )
        )
        .shouldValidate(
            elementData -> elementData.stream()
                .filter(data -> data.id().equals(QUESTION_UTLATANDE_BASERAT_PA_ID))
                .map(element -> (ElementValueDateList) element.value())
                .anyMatch(value -> value.dateList().stream().anyMatch(
                    valueDate -> valueDate.dateId().equals(UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID))
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
                        Ange på vilket sätt hälsotillståndet utgör ett påtagligt hot mot patientens liv i nuläget eller på viss tids sikt.\s
                        Hälsotillståndet kan utgöra ett påtagligt hot även om det finns hopp om att det förbättras.
                        <ul>
                            <li>Ange alla diagnoser som sammantaget medför ett påtagligt hot mot patientens liv.</li>
                            <li>Ange ett av alternativen som gäller patientens behandling och vårdsituation.</li>
                        </ul>
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
