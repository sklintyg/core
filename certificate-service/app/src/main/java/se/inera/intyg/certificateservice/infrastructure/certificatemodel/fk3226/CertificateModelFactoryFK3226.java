package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.elements.ElementUnitContactInformation.issuingUnitContactInfo;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.CategoryGrund.categoryGrund;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.CategoryHot.categoryHot;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.CategorySamtycke.categorySamtycke;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.MessageForutsattningarForAttLamnaSkriftligtSamtycke.messageForutsattningarForAttLamnaSkriftligtSamtycke;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionDiagnos.questionDiagnos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionForutsattningarForAttLamnaSkriftligtSamtycke.questionForutsattningarForAttLamnaSkriftligtSamtycke;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionNarAktivaBehandlingenAvslutades.questionNarAktivaBehandlingenAvslutades;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionNarTillstandetBlevAkutLivshotande.questionNarTillstandetBlevAkutLivshotande;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionPatagligtHotMotPatientensLivAkutLivshotande.questionPatagligtHotMotPatientensLivAkutLivshotande;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionPatagligtHotMotPatientensLivAnnat.questionPatagligtHotMotPatientensLivAnnat;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionPatientensBehandlingOchVardsituation.questionPatientBehandlingOchVardsituation;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionTillstandetUppskattasLivshotandeTillOchMed.questionTillstandetUppskattasLivshotandeTillOchMed;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionUppskattaHurLangeTillstandetKommerVaraLivshotande.questionUppskattaHurLangeTillstandetKommerVaraLivshotande;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionUtlatandeBaseratPa.questionUtlatandeBaseratPa;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.elements.QuestionUtlatandeBaseratPaAnnat.questionUtlatandeBaseratPaAnnat;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateMessageType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfFieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfMcid;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfSignature;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfTagIndex;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SchematronPath;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.common.model.Role;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.message.model.MessageActionType;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.Subject;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateRecipientFactory;

@Component
@RequiredArgsConstructor
public class CertificateModelFactoryFK3226 implements CertificateModelFactory {

  private final DiagnosisCodeRepository diagnosisCodeRepositoy;
  
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


  public static final SchematronPath SCHEMATRON_PATH = new SchematronPath(
      "fk3226/schematron/lunsp.v1.sch");

  public static final String PDF_FK_3226_PDF = "fk3226/pdf/fk3226_v1_no_address.pdf"; //TODO: update when new template has been provided
  public static final String PDF_NO_ADDRESS_FK_3226_PDF = "fk3226/pdf/fk3226_v1_no_address.pdf";
  public static final PdfMcid PDF_MCID = new PdfMcid(100);
  private static final int PDF_SIGNATURE_PAGE_INDEX = 1;
  private static final PdfTagIndex PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX = new PdfTagIndex(50);
  private static final PdfTagIndex PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX = new PdfTagIndex(42);
  private static final PdfFieldId PDF_PATIENT_ID_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[0].flt_txtPnr[0]");
  private static final PdfFieldId PDF_SIGNED_DATE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].flt_datUnderskrift[0]");
  private static final PdfFieldId PDF_SIGNED_BY_NAME_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].flt_txtNamnfortydligande[0]");
  private static final PdfFieldId PDF_SIGNED_BY_PA_TITLE = new PdfFieldId(
      "form1[0].#subform[1].flt_txtBefattning[0]");
  private static final PdfFieldId PDF_SIGNED_BY_SPECIALTY = new PdfFieldId(
      "form1[0].#subform[1].flt_txtEventuellSpecialistkompetens[0]");
  private static final PdfFieldId PDF_HSA_ID_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].flt_txtLakarensHSA-ID[0]");
  private static final PdfFieldId PDF_WORKPLACE_CODE_FIELD_ID = new PdfFieldId(
      "form1[0].#subform[1].flt_txtArbetsplatskod[0]");
  private static final PdfFieldId PDF_CONTACT_INFORMATION = new PdfFieldId(
      "form1[0].#subform[1].flt_txtVardgivarensNamnAdressTelefon[0]");

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
                categoryGrund(
                    questionUtlatandeBaseratPa(
                        questionUtlatandeBaseratPaAnnat()
                    )
                ),
                categoryHot(
                    questionDiagnos(diagnosisCodeRepositoy),
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
        .schematronPath(SCHEMATRON_PATH)
        .pdfSpecification(PdfSpecification.builder()
            .pdfTemplatePath(PDF_FK_3226_PDF)
            .pdfNoAddressTemplatePath(PDF_NO_ADDRESS_FK_3226_PDF)
            .patientIdFieldId(PDF_PATIENT_ID_FIELD_ID)
            .pdfMcid(PDF_MCID)
            .signature(PdfSignature.builder()
                .signatureWithAddressTagIndex(PDF_SIGNATURE_WITH_ADDRESS_TAG_INDEX)
                .signatureWithoutAddressTagIndex(PDF_SIGNATURE_WITHOUT_ADDRESS_TAG_INDEX)
                .signaturePageIndex(PDF_SIGNATURE_PAGE_INDEX)
                .signedDateFieldId(PDF_SIGNED_DATE_FIELD_ID)
                .signedByNameFieldId(PDF_SIGNED_BY_NAME_FIELD_ID)
                .paTitleFieldId(PDF_SIGNED_BY_PA_TITLE)
                .specialtyFieldId(PDF_SIGNED_BY_SPECIALTY)
                .hsaIdFieldId(PDF_HSA_ID_FIELD_ID)
                .workplaceCodeFieldId(PDF_WORKPLACE_CODE_FIELD_ID)
                .contactInformation(PDF_CONTACT_INFORMATION)
                .build()
            )
            .build())
        .build();
  }
}
