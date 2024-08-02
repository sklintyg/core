package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809;

import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation.UNIT_CONTACT_INFORMATION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CodeSystemFunktionsnedsattning.ANNAN_KROPPSILIG_FUNKTION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CodeSystemFunktionsnedsattning.HORSELFUNKTION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CodeSystemFunktionsnedsattning.INTELLEKTUELL_FUNKTION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CodeSystemFunktionsnedsattning.KOMMUNIKATION_SOCIAL_INTERAKTION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CodeSystemFunktionsnedsattning.KOORDINATION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CodeSystemFunktionsnedsattning.PSYKISK_FUNKTION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CodeSystemFunktionsnedsattning.SINNESFUNKTION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CodeSystemFunktionsnedsattning.SYNFUNKTION;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CodeSystemFunktionsnedsattning.UPPMARKSAMHET;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CodeSystemKvFkmu0005.getAllCodes;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateMessageType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDiagnosis;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationRadioBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextField;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisListItem;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementLayout;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMapping;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MedicalInvestigationConfig;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SchematronPath;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionType;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.Subject;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationBoolean;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationCodeList;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDateList;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationDiagnosis;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationMedicalInvestigationList;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationText;
import se.inera.intyg.certificateservice.domain.validation.model.ElementValidationUnitContactInformation;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateElementRuleFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateRecipientFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0001;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CodeSystemIcd10Se;

@Component
@RequiredArgsConstructor
public class CertificateModelFactoryFK7809 implements CertificateModelFactory {

  private static final String GENERAL_LABEL_FUNKTIONSNEDSATTNING = "Beskriv funktionsnedsättningen, om möjligt med grad. Ange även eventuella undersökningsfynd.";
  @Value("${certificate.model.fk7809.v1_0.active.from}")
  private LocalDateTime activeFrom;

  private final DiagnosisCodeRepository diagnosisCodeRepositoy;

  private static final String FK_7809 = "fk7809";
  private static final String VERSION = "1.0";
  private static final String NAME = "Läkarutlåtande för merkostnadsersättning";
  private static final String DETAILED_DESCRIPTION = """
       <b className="iu-fw-heading">Vem kan få merkostnadsersättnig?</b>
            
       En person kan ha rätt till merkostnadsersättning för kostnader som beror på att hen har en funktionsnedsättning. Funktionsnedsättningen ska antas finnas i minst ett år.
            
       Personen måste ha fått sin funktionsnedsättning innan hen fyllde 66 år. Om personen är född 1957 eller tidigare måste hen ha fått sin funktionsnedsättning innan hen fyllde 65 år.
            
       För att ha rätt till merkostnadsersättning ska merkostnaderna uppgå till minst 25 procent av ett prisbasbelopp per år.
            
       Merkostnader delas in sju olika kategorier:
       <ul>
       <li>hälsa, vård och kost,</li>
       <li>slitage och rengöring,</li>
       <li>resor,</li>
       <li>hjälpmedel</li>
       <li>hjälp i den dagliga livsföringen</li>
       <li>boende, och</li>
       <li>övriga ändamål</li>
       </ul>
       Den som anses vara blind eller gravt hörselskadad kan få en garanterad nivå av merkostnadsersättning utan att ha några merkostnader.
      """;
  private static final String DESCRIPTION = """
      <b className="iu-fw-heading">Vem kan få merkostnadsersättnig?</b>
            
      En person kan ha rätt till merkostnadsersättning för kostnader som beror på att hen fått en varaktig funktionsnedsättning som kan antas finnas i minst ett år. Funktionsnedsättningen ska ha uppstått innan hen fyllde 66 år. Om personen är född 1957 eller tidigare kan hen även få ersättning för kostnader som beror på att hen fått en funktionsnedsättning innan hen fyllde 65 år. För att få merkostnadsersättning ska merkostnaderna uppgå till minst 25 procent av ett prisbasbelopp per år.
            
      Den som anses vara blind eller gravt hörselskadad kan få en garanterad nivå av merkostnadsersättning utan att ha några merkostnader.
      """;
  public static final CertificateModelId FK7809_V1_0 = CertificateModelId.builder()
      .type(new CertificateType(FK_7809))
      .version(new CertificateVersion(VERSION))
      .build();

  private static final ElementId GRUND_FOR_MEDICINSKT_UNDERLAG_CATEGORY_ID = new ElementId("KAT_1");
  public static final ElementId QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID = new ElementId("1");
  public static final FieldId QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_FIELD_ID = new FieldId("1.1");

  public static final ElementId QUESTION_ANNAN_GRUND_FOR_MEDICINSKT_UNDERLAG_ID = new ElementId(
      "1.3");
  private static final FieldId QUESTION_ANNAN_GRUND_FOR_MEDICINSKT_UNDERLAG_FIELD_ID = new FieldId(
      "1.3");
  public static final ElementId QUESTION_RELATION_TILL_PATIENTEN_ID = new ElementId("1.4");
  private static final FieldId QUESTION_RELATION_TILL_PATIENTEN_FIELD_ID = new FieldId("1.4");

  public static final FieldId UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID = new FieldId("annat");
  public static final String UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID = "journaluppgifter";
  public static final FieldId UTLATANDE_BASERAT_PA_ANHORIG_FIELD_ID = new FieldId("anhorig");
  public static final String UTLATANDE_BASERAT_PA_UNDERSOKNING_AV_PATIENTEN_FIELD_ID = "undersokningAvPatienten";

  public static final ElementId QUESTION_BASERAT_PA_ANNAT_UNDERLAG_ID = new ElementId("3");
  public static final FieldId QUESTION_BASERAT_PA_ANNAT_UNDERLAG_FIELD_ID = new FieldId("3.1");

  public static final FieldId MEDICAL_INVESTIGATION_FIELD_ID_1 = new FieldId(
      "medicalInvestigation1");
  public static final FieldId MEDICAL_INVESTIGATION_FIELD_ID_2 = new FieldId(
      "medicalInvestigation2");
  public static final FieldId MEDICAL_INVESTIGATION_FIELD_ID_3 = new FieldId(
      "medicalInvestigation3");

  public static final ElementId QUESTION_ANDRA_MEDICINSKA_UTREDNINGAR_ID = new ElementId("4");
  private static final FieldId QUESTION_ANDRA_MEDICINSKA_UTREDNINGAR_FIELD_ID = new FieldId("4.1");

  private static final ElementId AKTIVITETSBEGRANSNINGAR_CATEGORY_ID = new ElementId("KAT_6");
  public static final ElementId QUESTION_AKTIVITETSBEGRANSNINGAR_ID = new ElementId("17");
  private static final FieldId QUESTION_AKTIVITETSBEGRANSNINGAR_FIELD_ID = new FieldId("17.1");

  private static final ElementId MEDICINSK_BEHANDLING_CATEGORY_ID = new ElementId("KAT_7");
  public static final ElementId QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_ID = new ElementId(
      "50");
  private static final FieldId QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_FIELD_ID = new FieldId(
      "50.1");
  public static final ElementId QUESTION_VARDENHET_OCH_TIDPLAN_ID = new ElementId(
      "50.2");
  private static final FieldId QUESTION_VARDENHET_OCH_TIDPLAN_FIELD_ID = new FieldId("50.2");

  private static final ElementId PROGNOS_CATEGORY_ID = new ElementId("KAT_8");
  public static final ElementId QUESTION_PROGNOS_ID = new ElementId(
      "51");
  private static final FieldId QUESTION_PROGNOS_FIELD_ID = new FieldId("51.1");

  private static final ElementId OVRIGT_CATEGORY_ID = new ElementId("KAT_9");
  public static final ElementId QUESTION_OVRIGT_ID = new ElementId(
      "25");
  private static final FieldId QUESTION_OVRIGT_FIELD_ID = new FieldId("25.1");

  private static final ElementId DIAGNOS_CATEGORY_ID = new ElementId("KAT_4");
  private static final FieldId DIAGNOSIS_FIELD_ID = new FieldId(
      "58.1");
  public static final ElementId DIAGNOSIS_ID = new ElementId("58");
  public static final ElementId DIAGNOSIS_MOTIVATION_ID = new ElementId("5");
  public static final FieldId DIAGNOSIS_MOTIVATION_FIELD_ID = new FieldId("5.1");
  public static final FieldId DIAGNOS_1 = new FieldId("huvuddiagnos");
  public static final FieldId DIAGNOS_2 = new FieldId("diagnos2");
  public static final FieldId DIAGNOS_3 = new FieldId("diagnos3");
  public static final FieldId DIAGNOS_4 = new FieldId("diagnos4");
  public static final FieldId DIAGNOS_5 = new FieldId("diagnos5");
  private static final short DIAGNOSIS_CODE_LIMIT = (short) 81;

  private static final ElementId FUNKTIONSNEDSATTNING_CATEGORY_ID = new ElementId("KAT_5");
  public static final ElementId FUNKTIONSNEDSATTNING_ID = new ElementId("funktionsnedsattning");
  public static final FieldId FUNKTIONSNEDSATNING_FIELD_ID = new FieldId(
      "funktionsnedsattning");
  public static final FieldId FUNKTIONSNEDSATTNING_INTELLEKTUELL_FUNKTION_ID = new FieldId("8.2");
  public static final FieldId FUNKTIONSNEDSATTNING_KOMMUNIKATION_SOCIAL_INTERAKTION_ID = new FieldId(
      "9.2");
  public static final FieldId FUNKTIONSNEDSATTNING_UPPMAKRMSAHET_ID = new FieldId("10.2");
  public static final FieldId FUNKTIONSNEDSATTNING_PSYKISK_FUNKTION_ID = new FieldId("11.2");
  public static final FieldId FUNKTIONSNEDSATTNING_HORSELFUNKTION_ID = new FieldId("48.2");
  public static final FieldId FUNKTIONSNEDSATTNING_SYNFUNKTION_ID = new FieldId("49.2");
  public static final FieldId FUNKTIONSNEDSATTNING_SINNESFUNKTION_ID = new FieldId("12.2");
  public static final FieldId FUNKTIONSNEDSATTNING_KOORDINATION_ID = new FieldId("13.2");
  public static final FieldId FUNKTIONSNEDSATTNING_ANNAN_KROPPSILIG_FUNKTION_ID = new FieldId(
      "14.2");
  public static final ElementId FUNKTIONSNEDSATTNING_MOTIVERING_INTELLEKTUELL_FUNKTION_ID = new ElementId(
      "8");
  public static final ElementId FUNKTIONSNEDSATTNING_MOTIVERING_KOMMUNIKATION_SOCIAL_INTERAKTION_ID = new ElementId(
      "9");
  public static final ElementId FUNKTIONSNEDSATTNING_MOTIVERING_UPPMAKRMSAHET_ID = new ElementId(
      "10");
  public static final ElementId FUNKTIONSNEDSATTNING_MOTIVERING_PSYKISK_FUNKTION_ID = new ElementId(
      "11");
  public static final ElementId FUNKTIONSNEDSATTNING_MOTIVERING_HORSELFUNKTION_ID = new ElementId(
      "48");
  public static final ElementId FUNKTIONSNEDSATTNING_MOTIVERING_SYNFUNKTION_ID = new ElementId(
      "49");
  public static final ElementId FUNKTIONSNEDSATTNING_MOTIVERING_SINNESFUNKTION_ID = new ElementId(
      "12");
  public static final ElementId FUNKTIONSNEDSATTNING_MOTIVERING_KOORDINATION_ID = new ElementId(
      "13");
  public static final ElementId FUNKTIONSNEDSATTNING_MOTIVERING_ANNAN_KROPPSILIG_FUNKTION_ID = new ElementId(
      "14");
  private static final FieldId FUNKTIONSNEDSATTNING_MOTIVERING_INTELLEKTUELL_FUNKTION_FIELD_ID = new FieldId(
      "8.1");
  private static final FieldId FUNKTIONSNEDSATTNING_MOTIVERING_KOMMUNIKATION_SOCIAL_INTERAKTION_FIELD_ID = new FieldId(
      "9.1");
  private static final FieldId FUNKTIONSNEDSATTNING_MOTIVERING_UPPMAKRMSAHET_FIELD_ID = new FieldId(
      "10.1");
  private static final FieldId FUNKTIONSNEDSATTNING_MOTIVERING_PSYKISK_FUNKTION_FIELD_ID = new FieldId(
      "11.1");
  private static final FieldId FUNKTIONSNEDSATTNING_MOTIVERING_HORSELFUNKTION_FIELD_ID = new FieldId(
      "48.1");
  private static final FieldId FUNKTIONSNEDSATTNING_MOTIVERING_SYNFUNKTION_FIELD_ID = new FieldId(
      "49.1");
  private static final FieldId FUNKTIONSNEDSATTNING_MOTIVERING_SINNESFUNKTION_FIELD_ID = new FieldId(
      "12.1");
  private static final FieldId FUNKTIONSNEDSATTNING_MOTIVERING_KOORDINATION_FIELD_ID = new FieldId(
      "13.1");
  private static final FieldId FUNKTIONSNEDSATTNING_MOTIVERING_ANNAN_KROPPSILIG_FUNKTION_FIELD_ID = new FieldId(
      "14.1");

  private static final String PREAMBLE_TEXT =
      "Det här är ditt intyg. Intyget innehåller all information som vården fyllt i. Du kan inte ändra något i ditt intyg. "
          + "Har du frågor kontaktar du den som skrivit ditt intyg.";
  private static final SchematronPath SCHEMATRON_PATH = new SchematronPath(
      "fk7809/schematron/lumek.v1.sch");

  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(FK7809_V1_0)
        .type(
            new Code(
                "LUMEK",
                "b64ea353-e8f6-4832-b563-fc7d46f29548",
                NAME
            )
        )
        .name(NAME)
        .description(DESCRIPTION)
        .detailedDescription(DETAILED_DESCRIPTION)
        .schematronPath(SCHEMATRON_PATH)
        .activeFrom(activeFrom)
        .availableForCitizen(true)
        .summaryProvider(new FK7809CertificateSummaryProvider())
        .texts(
            List.of(
                CertificateText.builder()
                    .text(PREAMBLE_TEXT)
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
                    .certificateActionType(CertificateActionType.RESPONSIBLE_ISSUER)
                    .allowedRoles(List.of(Role.NURSE, Role.MIDWIFE, Role.CARE_ADMIN))
                    .build()
            )
        )
        .messageActionSpecifications(
            List.of(
                MessageActionSpecification.builder()
                    .messageActionType(MessageActionType.ANSWER)
                    .build(),
                MessageActionSpecification.builder()
                    .messageActionType(MessageActionType.HANDLE_COMPLEMENT)
                    .build(),
                MessageActionSpecification.builder()
                    .messageActionType(MessageActionType.COMPLEMENT)
                    .build(),
                MessageActionSpecification.builder()
                    .messageActionType(MessageActionType.CANNOT_COMPLEMENT)
                    .build(),
                MessageActionSpecification.builder()
                    .messageActionType(MessageActionType.FORWARD)
                    .build(),
                MessageActionSpecification.builder()
                    .messageActionType(MessageActionType.HANDLE_MESSAGE)
                    .build()
            )
        )
        .elementSpecifications(
            List.of(
                categoryGrundForMedicinsktUnderlag(
                    questionGrundForMedicinsktUnderlag(
                        questionRelationTillPatienten(),
                        questionAnnanGrundForMedicinsktUnderlag()
                    ),
                    questionBaseratPaAnnatMedicinsktUnderlag(),
                    questionUtredningEllerUnderlag()
                ),
                categoryDiagnos(
                    questionDiagnos(),
                    questionDiagnosHistorik()
                ),
                categoryFunktionsnedsattning(
                    questionFunkionsnedsattning(),
                    questionIntellektuellFunktionMotivering(),
                    questionKommunikationSocialInteraktionMotivering(),
                    questionMotiveringUppmarksamhetMotivering(),
                    questionPsykiskFunktionMotivering(),
                    questionHorselFunktionMotivering(),
                    questionSynfunktionMotivering(),
                    questionSinnesfunktionMotivering(),
                    questionKoordinationMotivering(),
                    questionAnnanKroppsligFunktionMotivering()
                ),
                categoryAktivitetsbegransningar(
                    questionAktivitetsbegransningar()
                ),
                categoryMedicinskBehandling(
                    questionPagaendeOchPlaneradeBehandlingar(
                        questionVardenhetOchTidplan()
                    )
                ),
                categoryPrognos(
                    questionPrognos()
                ),
                categoryOvrigt(
                    questionOvrigt()
                ),
                issuingUnitContactInfo()
            )
        )
        .build();
  }

  private static ElementSpecification categoryGrundForMedicinsktUnderlag(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(GRUND_FOR_MEDICINSKT_UNDERLAG_CATEGORY_ID)
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

  private static ElementSpecification categoryDiagnos(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(DIAGNOS_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Diagnos")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }

  private static ElementSpecification categoryFunktionsnedsattning(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(FUNKTIONSNEDSATTNING_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Funktionsnedsättning")
                .description(
                    """
                        Beskriv de funktionsnedsättningar som patienten har. Ange om din bedömning är baserad på observationer, undersökningsfynd eller testresultat. Det kan till exempel vara:
                        <ul>
                        <li>avvikelser i somatiskt och psykiskt status</li><li>röntgen- och laboratoriefynd</li><li>resultat av kliniskt fysiologiska undersökningar</li><li>andra testresultat, exempelvis neuropsykologiska.</li></ul>
                        Ange även vilka uppgifter som är baserade på anamnes. Ange om möjligt grad av funktionsnedsättning (till exempel lätt, måttlig, stor eller total).
                                            
                        Funktionsområdenas hjälptexter följer väsentligen ICF men då kategorierna i läkarutlåtandena är färre har vissa förenklingar gjorts."""
                )
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }

  private static ElementSpecification categoryAktivitetsbegransningar(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(AKTIVITETSBEGRANSNINGAR_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Aktivitetsbegränsningar")
                .description(
                    """
                        Beskriv de aktivitetsbegränsningar som du bedömer att patienten har. Beskriv även om din bedömning är baserad på observationer, anamnes eller utredning gjord av någon annan. Någon annan kan till exempel vara psykolog, arbetsterapeut, audionom, syn- eller hörselpedagog.
                                                
                        I beskrivningen kan du utgå från aktiviteter inom områden som till exempel kommunikation, förflyttning, personlig vård och hemliv. Ange om möjligt svårighetsgraden på aktivitetsbegränsningarna.
                        """)
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }

  private static ElementSpecification categoryMedicinskBehandling(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(MEDICINSK_BEHANDLING_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Medicinsk behandling")
                .description(
                    "Ange pågående och planerade medicinska behandlingar eller åtgärder som är relevanta utifrån funktionsnedsättningen. Det kan vara ordinerade läkemedel, hjälpmedel, träningsinsatser eller särskild kost. Ange den medicinska indikationen och syftet med behandlingen eller åtgärden. Du kan även beskriva andra behandlingar och åtgärder som prövats utifrån funktionsnedsättningen. Om patienten använder läkemedel som inte är subventionerade: beskriv anledningen till det.")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }

  private static ElementSpecification categoryPrognos(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(PROGNOS_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Prognos")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }

  private static ElementSpecification categoryOvrigt(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(OVRIGT_CATEGORY_ID)
        .configuration(
            ElementConfigurationCategory.builder()
                .name("Övrigt")
                .build()
        )
        .children(
            List.of(children)
        )
        .build();
  }

  private static ElementSpecification questionGrundForMedicinsktUnderlag(
      ElementSpecification... children) {
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
            .label("journaluppgifter från och med")
            .code(CodeSystemKvFkmu0001.JOURNALUPGIFTER)
            .min(null)
            .max(Period.ofDays(0))
            .build(),
        CheckboxDate.builder()
            .id(UTLATANDE_BASERAT_PA_ANHORIG_FIELD_ID)
            .label(CodeSystemKvFkmu0001.ANHORIG.displayName())
            .code(CodeSystemKvFkmu0001.ANHORIG)
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
        .id(QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID)
        .configuration(
            ElementConfigurationCheckboxMultipleDate.builder()
                .id(QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_FIELD_ID)
                .name("Utlåtandet är baserat på")
                .dates(checkboxDates)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
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

  private static ElementSpecification questionRelationTillPatienten() {
    return ElementSpecification.builder()
        .id(QUESTION_RELATION_TILL_PATIENTEN_ID)
        .configuration(
            ElementConfigurationTextField.builder()
                .id(QUESTION_RELATION_TILL_PATIENTEN_FIELD_ID)
                .name(
                    "Ange anhörig eller annans relation till patienten")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_RELATION_TILL_PATIENTEN_ID,
                    QUESTION_RELATION_TILL_PATIENTEN_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_RELATION_TILL_PATIENTEN_ID,
                    (short) 4000),
                CertificateElementRuleFactory.show(
                    QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
                    UTLATANDE_BASERAT_PA_ANHORIG_FIELD_ID
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
                QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
                CodeSystemKvFkmu0001.ANHORIG
            )
        )
        .shouldValidate(
            elementData -> elementData.stream()
                .filter(data -> data.id().equals(QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID))
                .map(element -> (ElementValueDateList) element.value())
                .anyMatch(value -> value.dateList().stream().anyMatch(
                    valueDate -> valueDate.dateId().equals(UTLATANDE_BASERAT_PA_ANHORIG_FIELD_ID))
                )
        )
        .build();
  }

  private static ElementSpecification questionAnnanGrundForMedicinsktUnderlag() {
    return ElementSpecification.builder()
        .id(QUESTION_ANNAN_GRUND_FOR_MEDICINSKT_UNDERLAG_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_ANNAN_GRUND_FOR_MEDICINSKT_UNDERLAG_FIELD_ID)
                .name(
                    "Ange vad annat är")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_ANNAN_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
                    QUESTION_ANNAN_GRUND_FOR_MEDICINSKT_UNDERLAG_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_ANNAN_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
                    (short) 4000),
                CertificateElementRuleFactory.show(
                    QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
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
                QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID,
                CodeSystemKvFkmu0001.ANNAT
            )
        )
        .shouldValidate(
            elementData -> elementData.stream()
                .filter(data -> data.id().equals(QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID))
                .map(element -> (ElementValueDateList) element.value())
                .anyMatch(value -> value.dateList().stream().anyMatch(
                    valueDate -> valueDate.dateId().equals(UTLATANDE_BASERAT_PA_ANNAT_FIELD_ID))
                )
        )
        .build();
  }

  private static ElementSpecification questionUtredningEllerUnderlag(
      ElementSpecification... children) {
    final var medicalInvestigations = List.of(
        getMedicalInvestigationConfig(MEDICAL_INVESTIGATION_FIELD_ID_1),
        getMedicalInvestigationConfig(MEDICAL_INVESTIGATION_FIELD_ID_2),
        getMedicalInvestigationConfig(MEDICAL_INVESTIGATION_FIELD_ID_3)
    );

    return ElementSpecification.builder()
        .id(QUESTION_ANDRA_MEDICINSKA_UTREDNINGAR_ID)
        .configuration(
            ElementConfigurationMedicalInvestigationList.builder()
                .id(QUESTION_ANDRA_MEDICINSKA_UTREDNINGAR_FIELD_ID)
                .name("Ange utredning eller underlag")
                .informationSourceDescription(
                    "Skriv exempelvis Neuropsykiatriska kliniken på X-stads sjukhus eller om patienten själv kommer att bifoga utredningen till sin ansökan.")
                .dateText("Datum")
                .typeText("Utredning eller underlag")
                .informationSourceText("Från vilken vårdgivare")
                .list(medicalInvestigations)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryNotEmpty(
                    QUESTION_ANDRA_MEDICINSKA_UTREDNINGAR_ID,
                    List.of(
                        getDateId(MEDICAL_INVESTIGATION_FIELD_ID_1),
                        getInvestigationTypeId(MEDICAL_INVESTIGATION_FIELD_ID_1),
                        getInformationSourceId(MEDICAL_INVESTIGATION_FIELD_ID_1)
                    )
                ),
                CertificateElementRuleFactory.show(
                    QUESTION_BASERAT_PA_ANNAT_UNDERLAG_ID,
                    QUESTION_BASERAT_PA_ANNAT_UNDERLAG_FIELD_ID
                )
            )
        )
        .validations(
            List.of(
                ElementValidationMedicalInvestigationList.builder()
                    .mandatory(true)
                    .max(Period.ofDays(0))
                    .limit(4000)
                    .build()
            )
        )
        .shouldValidate(
            elementData -> elementData.stream()
                .filter(data -> data.id().equals(QUESTION_BASERAT_PA_ANNAT_UNDERLAG_ID))
                .map(element -> (ElementValueBoolean) element.value())
                .anyMatch(value -> value != null && value.value() != null && value.value())
        )
        .children(List.of(children))
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

  private static ElementSpecification questionBaseratPaAnnatMedicinsktUnderlag() {
    return ElementSpecification.builder()
        .id(QUESTION_BASERAT_PA_ANNAT_UNDERLAG_ID)
        .configuration(
            ElementConfigurationRadioBoolean.builder()
                .id(QUESTION_BASERAT_PA_ANNAT_UNDERLAG_FIELD_ID)
                .selectedText("Ja")
                .unselectedText("Nej")
                .name("Är utlåtandet även baserat på andra medicinska utredningar eller underlag?")
                .build()
        )
        .validations(
            List.of(
                ElementValidationBoolean.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatoryExist(
                    QUESTION_BASERAT_PA_ANNAT_UNDERLAG_ID,
                    QUESTION_BASERAT_PA_ANNAT_UNDERLAG_FIELD_ID
                )
            )
        )
        .build();
  }

  private static ElementSpecification questionAktivitetsbegransningar() {
    return ElementSpecification.builder()
        .id(QUESTION_AKTIVITETSBEGRANSNINGAR_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_AKTIVITETSBEGRANSNINGAR_FIELD_ID)
                .name(
                    "Beskriv vad patienten har svårt att göra på grund av sin funktionsnedsättning")
                .label("Ge konkreta exempel på aktiviteter i vardagen där svårigheter uppstår")
                .build())
        .rules(
            List.of(
                CertificateElementRuleFactory.limit(
                    QUESTION_AKTIVITETSBEGRANSNINGAR_ID,
                    (short) 4000)
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(false)
                    .limit(4000)
                    .build()
            )
        )
        .build();
  }

  private static ElementSpecification questionPagaendeOchPlaneradeBehandlingar(
      ElementSpecification... children) {
    return ElementSpecification.builder()
        .id(QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_FIELD_ID)
                .name(
                    "Ange pågående och planerade medicinska behandlingar")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.limit(
                    QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_ID,
                    (short) 4000)
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(false)
                    .limit(4000)
                    .build()
            )
        )
        .children(List.of(children))
        .build();
  }

  private static ElementSpecification questionVardenhetOchTidplan() {
    return ElementSpecification.builder()
        .id(QUESTION_VARDENHET_OCH_TIDPLAN_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_VARDENHET_OCH_TIDPLAN_FIELD_ID)
                .name("Ange ansvarig vårdenhet och tidplan")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_VARDENHET_OCH_TIDPLAN_ID,
                    QUESTION_VARDENHET_OCH_TIDPLAN_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_VARDENHET_OCH_TIDPLAN_ID,
                    (short) 4000),
                CertificateElementRuleFactory.show(
                    QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_ID,
                    QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_FIELD_ID
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
            new ElementMapping(QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_ID, null)
        )
        .shouldValidate(
            elementData -> elementData.stream()
                .filter(data -> data.id().equals(QUESTION_PAGAENDE_ELLER_PLANERAD_BEHANDLING_ID))
                .map(element -> (ElementValueText) element.value())
                .anyMatch(value -> value.text() != null && !value.text().isEmpty())
        ).build();
  }

  private static ElementSpecification questionPrognos() {
    return ElementSpecification.builder()
        .id(QUESTION_PROGNOS_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_PROGNOS_FIELD_ID)
                .name(
                    "Hur förväntas patientens funktionsnedsättning och aktivitetsbegränsningar utvecklas över tid?")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    QUESTION_PROGNOS_ID,
                    QUESTION_PROGNOS_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    QUESTION_PROGNOS_ID,
                    (short) 4000)
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

  private static ElementSpecification questionOvrigt() {
    return ElementSpecification.builder()
        .id(QUESTION_OVRIGT_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(QUESTION_OVRIGT_FIELD_ID)
                .name(
                    "Övriga upplysningar")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.limit(
                    QUESTION_OVRIGT_ID,
                    (short) 4000)
            )
        )
        .validations(
            List.of(
                ElementValidationText.builder()
                    .mandatory(false)
                    .limit(4000)
                    .build()
            )
        )
        .build();
  }

  private ElementSpecification questionDiagnos() {
    return ElementSpecification.builder()
        .id(DIAGNOSIS_ID)
        .configuration(
            ElementConfigurationDiagnosis.builder()
                .id(DIAGNOSIS_FIELD_ID)
                .name(
                    "Diagnos eller diagnoser")
                .description(
                    "Ange diagnoskod med så många positioner som möjligt. Använd inga andra tecken än bokstäver och siffror.")
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

  private static ElementSpecification questionDiagnosHistorik() {
    return ElementSpecification.builder()
        .id(DIAGNOSIS_MOTIVATION_ID)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(DIAGNOSIS_MOTIVATION_FIELD_ID)
                .name(
                    "Sammanfatta historiken för diagnoserna")
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    DIAGNOSIS_MOTIVATION_ID,
                    DIAGNOSIS_MOTIVATION_FIELD_ID
                ),
                CertificateElementRuleFactory.limit(
                    DIAGNOSIS_MOTIVATION_ID,
                    (short) 4000)
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

  private ElementSpecification questionFunkionsnedsattning() {
    final var checkboxes = List.of(
        getCodeConfig(FUNKTIONSNEDSATTNING_INTELLEKTUELL_FUNKTION_ID, INTELLEKTUELL_FUNKTION),
        getCodeConfig(FUNKTIONSNEDSATTNING_KOMMUNIKATION_SOCIAL_INTERAKTION_ID,
            KOMMUNIKATION_SOCIAL_INTERAKTION),
        getCodeConfig(FUNKTIONSNEDSATTNING_UPPMAKRMSAHET_ID, UPPMARKSAMHET),
        getCodeConfig(FUNKTIONSNEDSATTNING_PSYKISK_FUNKTION_ID, PSYKISK_FUNKTION),
        getCodeConfig(FUNKTIONSNEDSATTNING_HORSELFUNKTION_ID, HORSELFUNKTION),
        getCodeConfig(FUNKTIONSNEDSATTNING_SYNFUNKTION_ID, SYNFUNKTION),
        getCodeConfig(FUNKTIONSNEDSATTNING_SINNESFUNKTION_ID, SINNESFUNKTION),
        getCodeConfig(FUNKTIONSNEDSATTNING_KOORDINATION_ID, KOORDINATION),
        getCodeConfig(FUNKTIONSNEDSATTNING_ANNAN_KROPPSILIG_FUNKTION_ID, ANNAN_KROPPSILIG_FUNKTION)
    );

    return ElementSpecification.builder()
        .id(FUNKTIONSNEDSATTNING_ID)
        .includeInXml(false)
        .configuration(
            ElementConfigurationCheckboxMultipleCode.builder()
                .id(FUNKTIONSNEDSATNING_FIELD_ID)
                .name("Välj alternativ att fylla i för att visa fritextfält. Välj minst ett:")
                .elementLayout(ElementLayout.COLUMNS)
                .list(checkboxes)
                .build()
        )
        .rules(
            List.of(
                CertificateElementRuleFactory.mandatory(
                    FUNKTIONSNEDSATTNING_ID, List.of(
                        FUNKTIONSNEDSATTNING_INTELLEKTUELL_FUNKTION_ID,
                        FUNKTIONSNEDSATTNING_KOMMUNIKATION_SOCIAL_INTERAKTION_ID,
                        FUNKTIONSNEDSATTNING_UPPMAKRMSAHET_ID,
                        FUNKTIONSNEDSATTNING_PSYKISK_FUNKTION_ID,
                        FUNKTIONSNEDSATTNING_HORSELFUNKTION_ID,
                        FUNKTIONSNEDSATTNING_SYNFUNKTION_ID,
                        FUNKTIONSNEDSATTNING_SINNESFUNKTION_ID,
                        FUNKTIONSNEDSATTNING_KOORDINATION_ID,
                        FUNKTIONSNEDSATTNING_ANNAN_KROPPSILIG_FUNKTION_ID
                    )
                )
            )
        )
        .validations(
            List.of(
                ElementValidationCodeList.builder()
                    .mandatory(true)
                    .build()
            )
        )
        .build();
  }

  private static ElementSpecification questionIntellektuellFunktionMotivering() {
    return getFunktionsnedsattningMotivering(
        FUNKTIONSNEDSATTNING_MOTIVERING_INTELLEKTUELL_FUNKTION_ID,
        FUNKTIONSNEDSATTNING_MOTIVERING_INTELLEKTUELL_FUNKTION_FIELD_ID,
        FUNKTIONSNEDSATTNING_INTELLEKTUELL_FUNKTION_ID,
        "Intellektuell funktion",
        "Beskriv eventuella iakttagelser alternativt testresultat från psykologutredning",
        "Med intellektuell funktion, teoretisk begåvning eller intelligens menas förmågan att tänka logiskt. För att bedöma nivån krävs det att test utförts av en psykolog.");
  }

  private static ElementSpecification questionKommunikationSocialInteraktionMotivering() {
    return getFunktionsnedsattningMotivering(
        FUNKTIONSNEDSATTNING_MOTIVERING_KOMMUNIKATION_SOCIAL_INTERAKTION_ID,
        FUNKTIONSNEDSATTNING_MOTIVERING_KOMMUNIKATION_SOCIAL_INTERAKTION_FIELD_ID,
        FUNKTIONSNEDSATTNING_KOMMUNIKATION_SOCIAL_INTERAKTION_ID,
        "Övergripande psykosociala funktioner",
        GENERAL_LABEL_FUNKTIONSNEDSATTNING,
        """
            Med psykosociala funktioner menas
            <ul>
            <li>förmågan till emotionell kontakt</li><li>social ömsesidighet</li><li>samspel</li><li>förmågan att på ett teoretiskt plan kunna sätta sig in i hur andra människor tänker och känner, även om man inte har varit med om samma sak själv.</li></ul>
            """
    );
  }

  private static ElementSpecification questionMotiveringUppmarksamhetMotivering() {
    return getFunktionsnedsattningMotivering(
        FUNKTIONSNEDSATTNING_MOTIVERING_UPPMAKRMSAHET_ID,
        FUNKTIONSNEDSATTNING_MOTIVERING_UPPMAKRMSAHET_FIELD_ID,
        FUNKTIONSNEDSATTNING_UPPMAKRMSAHET_ID,
        "Uppmärksamhet, koncentration och exekutiv funktion",
        GENERAL_LABEL_FUNKTIONSNEDSATTNING,
        "Uppmärksamhet handlar om förmågan att rikta fokus på rätt sak vid rätt tillfälle samt att skifta, fördela och vidmakthålla uppmärksamheten. En person behöver även viljemässigt kunna rikta sin uppmärksamhet under en längre tid. Med exekutiv funktion menas förmågan att planera, initiera, genomföra, korrigera och avsluta en handling."
    );
  }

  private static ElementSpecification questionPsykiskFunktionMotivering() {
    return getFunktionsnedsattningMotivering(
        FUNKTIONSNEDSATTNING_MOTIVERING_PSYKISK_FUNKTION_ID,
        FUNKTIONSNEDSATTNING_MOTIVERING_PSYKISK_FUNKTION_FIELD_ID,
        FUNKTIONSNEDSATTNING_PSYKISK_FUNKTION_ID,
        "Annan psykisk funktion",
        GENERAL_LABEL_FUNKTIONSNEDSATTNING,
        """
            Med annan psykisk funktion menas exempelvis
            <ul>
            <li>stämningsläge, depressivitet, ångest och reglering av affekter</li><li>motivation, energinivå, impulskontroll och initiativförmåga</li><li>kognitiv flexibilitet, omdöme och insikt</li><li>minnesfunktioner</li><li>sömnfunktioner</li><li>vanföreställningar och tvångstankar</li><li>psykiska språkfunktioner</li><li>orientering i tid samt till plats, situation och person.</li></ul>
            """
    );
  }

  private static ElementSpecification questionHorselFunktionMotivering() {
    return getFunktionsnedsattningMotivering(
        FUNKTIONSNEDSATTNING_MOTIVERING_HORSELFUNKTION_ID,
        FUNKTIONSNEDSATTNING_MOTIVERING_HORSELFUNKTION_FIELD_ID,
        FUNKTIONSNEDSATTNING_HORSELFUNKTION_ID,
        "Hörselfunktion",
        "Beskriv funktionsnedsättningen och undersökningsfynd.",
        """
            Hörselfunktion handlar om förmågan att förnimma närvaro av ljud och att urskilja lokalisering, tonhöjd, ljudstyrka och ljudkvalitet.
                                        
            För att Försäkringskassan ska kunna bedöma om det finns rätt till garanterad nivå av merkostnadsersättning är följande information viktig. 
                  
            Beskriv eventuell nedsättning av hörseln utifrån hörselmätningar och öronstatus. Motivera konstaterade diagnoser utifrån hörseltesterna. Värdera sambandet mellan hörseltesterna och eventuella avvikelser. Beskriv förmågan till kommunikation och taluppfattning utifrån observation och resultat av mätningar eller tester. Skriv om objektiva hörselmätningar har gjorts. Ange vilken typ av hörhjälpmedel patienten använder, och om hen erbjudits utredning för cochleaimplantat (CI).
                    
            Du kan skicka in
            <ul>
            <li>resultat av hörseltester – tonaudiogram med ben och luftledning</li><li>resultat av maximal taluppfattning med angiven stimuleringsnivå i decibel (dB)</li><li>taluppfattning i ljudfält 65dB med optimalt anpassade hörhjälpmedel</li><li>resultat av eventuella objektiva hörselmätningar</li><li>underlag från andra bedömningar som gäller kommunikation.</li></ul>
            """
    );
  }

  private static ElementSpecification questionSynfunktionMotivering() {
    return getFunktionsnedsattningMotivering(
        FUNKTIONSNEDSATTNING_MOTIVERING_SYNFUNKTION_ID,
        FUNKTIONSNEDSATTNING_MOTIVERING_SYNFUNKTION_FIELD_ID,
        FUNKTIONSNEDSATTNING_SYNFUNKTION_ID,
        "Synfunktion",
        "Beskriv funktionsnedsättningen och undersökningsfynd.",
        """
            Synfunktion handlar om förmågan att förnimma närvaro av ljus och synintryckets form, storlek, utformning och färg. För att Försäkringskassan ska kunna bedöma om det finns rätt till garanterad nivå av merkostnadsersättning är följande information viktig.
                                        
            Beskriv nedsättningen av synen:
            <ul>
            <li>synskärpa på långt håll med bästa korrektion</li><li>synfält – du kan skicka in en kopia av perimetri eller beskriva synfältet på annat sätt till exempel enligt Donders metod.</li><li>andra synfunktioner som till exempel förmåga till samsyn, dubbelseende, nystagmus, mörkerseende, färgseende, kontrastseende, bländningskänslighet och perception.</li></ul>                                          
            Beskriv:
            <ul>
            <li>personens förmåga att orientera sig och förflytta sig med hjälp av synen</li><li>hur stora svårigheter personen har att orientera sig och förflytta sig i en främmande miljö.</li></ul>
            Bedöm sambandet mellan svårigheterna och den nedsatta synfunktionen. Ange den ledsagning eller hjälpmedel som personen behöver när hen ska förflytta sig. Det kan till exempel vara markeringskäpp, teknikkäpp eller ledarhund.                       
            """
    );
  }

  private static ElementSpecification questionSinnesfunktionMotivering() {
    return getFunktionsnedsattningMotivering(
        FUNKTIONSNEDSATTNING_MOTIVERING_SINNESFUNKTION_ID,
        FUNKTIONSNEDSATTNING_MOTIVERING_SINNESFUNKTION_FIELD_ID,
        FUNKTIONSNEDSATTNING_SINNESFUNKTION_ID,
        "Övriga sinnesfunktioner och smärta",
        GENERAL_LABEL_FUNKTIONSNEDSATTNING,
        "Med övriga sinnesfunktioner menas exempelvis känslighet eller upplevelse av obehag vid ljud, ljus, temperatur, beröring, smak eller lukt. Med smärta menas förnimmelse av en obehaglig känsla som tyder på tänkbar eller faktisk skada i någon del av kroppen. Det innefattar förnimmelser av generell eller lokal smärta i en eller flera kroppsdelar, eller i ett dermatom (hudavsnitt). Det kan till exempel vara huggande, brännande, molande smärta och värk."
    );
  }

  private static ElementSpecification questionKoordinationMotivering() {
    return getFunktionsnedsattningMotivering(
        FUNKTIONSNEDSATTNING_MOTIVERING_KOORDINATION_ID,
        FUNKTIONSNEDSATTNING_MOTIVERING_KOORDINATION_FIELD_ID,
        FUNKTIONSNEDSATTNING_KOORDINATION_ID,
        "Balans, koordination och motorik",
        GENERAL_LABEL_FUNKTIONSNEDSATTNING,
        "Med balans menas kroppens balansfunktion och förnimmelse av kroppsställning (positionsuppfattning). Med koordination menas till exempel ögahandkoordination, gångkoordination och att samordna rörelser av armar och ben. Med motorik menas fin- och grovmotorik eller till exempel munmotorik.");
  }

  private static ElementSpecification questionAnnanKroppsligFunktionMotivering() {
    return getFunktionsnedsattningMotivering(
        FUNKTIONSNEDSATTNING_MOTIVERING_ANNAN_KROPPSILIG_FUNKTION_ID,
        FUNKTIONSNEDSATTNING_MOTIVERING_ANNAN_KROPPSILIG_FUNKTION_FIELD_ID,
        FUNKTIONSNEDSATTNING_ANNAN_KROPPSILIG_FUNKTION_ID,
        "Annan kroppslig funktion",
        GENERAL_LABEL_FUNKTIONSNEDSATTNING,
        "Med annan kroppslig funktion menas till exempel andningsfunktion, matsmältnings- och ämnesomsättningsfunktion samt blås- och tarmfunktion.");
  }


  private static ElementSpecification getFunktionsnedsattningMotivering(ElementId questionId,
      FieldId questionFieldId, FieldId parentFieldId, String name,
      String label, String description) {
    return ElementSpecification.builder()
        .id(questionId)
        .configuration(
            ElementConfigurationTextArea.builder()
                .id(questionFieldId)
                .name(name)
                .label(label)
                .description(description)
                .build())
        .rules(
            List.of(
                CertificateElementRuleFactory.limit(
                    questionId,
                    (short) 4000),
                CertificateElementRuleFactory.mandatory(
                    questionId,
                    questionFieldId
                ),
                CertificateElementRuleFactory.show(
                    FUNKTIONSNEDSATTNING_ID,
                    parentFieldId
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
                .filter(data -> data.id().equals(FUNKTIONSNEDSATTNING_ID))
                .map(element -> (ElementValueCodeList) element.value())
                .anyMatch(value -> value.list()
                    .stream()
                    .anyMatch(codeValue -> codeValue.codeId().equals(parentFieldId))
                )
        )
        .build();
  }

  private static ElementConfigurationCode getCodeConfig(FieldId fieldId, Code code) {
    return new ElementConfigurationCode(
        fieldId,
        code.displayName(),
        code
    );
  }

  private static MedicalInvestigationConfig getMedicalInvestigationConfig(FieldId fieldId) {
    return MedicalInvestigationConfig.builder()
        .id(fieldId)
        .dateId(getDateId(fieldId))
        .investigationTypeId(getInvestigationTypeId(fieldId))
        .informationSourceId(getInformationSourceId(fieldId))
        .typeOptions(getAllCodes())
        .min(null)
        .max(Period.ofDays(0))
        .build();
  }

  private static FieldId getInformationSourceId(FieldId fieldId) {
    return new FieldId(fieldId.value() + "_INFORMATION_SOURCE");
  }

  private static FieldId getInvestigationTypeId(FieldId fieldId) {
    return new FieldId(fieldId.value() + "_INVESTIGATION_TYPE");
  }

  private static FieldId getDateId(FieldId fieldId) {
    return new FieldId(fieldId.value() + "_DATE");
  }
}
