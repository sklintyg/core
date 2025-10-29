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
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateMessageType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateTypeName;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SchematronPath;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.Subject;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateRecipientFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvIntygstyp;

@Component
@RequiredArgsConstructor
public class CertificateModelFactoryFK3226 implements CertificateModelFactory {

  private final DiagnosisCodeRepository diagnosisCodeRepository;
  private final CertificateActionFactory certificateActionFactory;

  @Value("${certificate.model.fk3226.v1_0.active.from}")
  private LocalDateTime activeFrom;

  @Value("${sendmessagetofk.logicaladdress}")
  private String fkLogicalAddress;

  private static final String FK_3226 = "fk3226";
  private static final String VERSION = "1.0";
  private static final CertificateTypeName FK3226_TYPE_NAME = new CertificateTypeName("FK3226");
  private static final String NAME = "Läkarutlåtande för närståendepenning";
  private static final String DESCRIPTION = """
         <b className="iu-fw-heading">Vad är närståendepenning?</b><br>
         <p>Närståendepenning är en ersättning för den som avstår från förvärvsarbete, arbetslöshetsersättning (a-kassa) eller föräldrapenning för att vara med en patient som är svårt sjuk i lagens mening. I lagen definierar man svårt sjuk som att patientens hälsotillstånd är så nedsatt att det finns ett påtagligt hot mot hens liv i nuläget eller på viss tids sikt. Sjukdomstillstånd som på flera års sikt utvecklas till livshotande tillstånd ger däremot inte rätt till närståendepenning.</p>
      """;
  private static final String DETAILED_DESCRIPTION = """
      <b className="iu-fw-heading">Vad är närståendepenning?</b><br>
      <p>Närståendepenning är en ersättning för den som avstår från att förvärvsarbeta för att vara med en patient som är svårt sjuk i lagens mening. I lagen definierar man svårt sjuk som att patientens hälsotillstånd är så nedsatt att det finns ett påtagligt hot mot hens liv på viss tids sikt. Sjukdomstillstånd som på flera års sikt utvecklas till livshotande tillstånd ger däremot inte rätt till närståendepenning.</p>
      <b className="iu-fw-heading">Vem är närstående?</b><br>
      <p>Till närstående räknas anhöriga, men även andra som har nära relationer med den som är sjuk till exempel vänner eller grannar. Flera närstående kan turas om och få ersätttning för olika dagar eller olika delar av dagar.</p>
      <b className="iu-fw-heading">Ansökan och samtycke</b><br>
      <p>När den närstående som stödjer patienten ansöker om närståendepenning ska hen bifoga blankett Samtycke för närståendepenning. Det gäller i de fall patienten har medicinska förutsättningar för att kunna samtycka till en närståendes stöd.</p>
      <b className="iu-fw-heading">Särskilda regler för vissa HIV-smittade</b><br>
      <p>Om patienten har hiv och har blivit smittad på något av följande sätt, ange det vid “Annat” under “Patientens behandling och vårdsituation”.</p>
      <ol><li>Patienten har blivit smittad när hen fick blod- eller blodprodukter, och smittades när hen behandlades av den svenska hälso- och sjukvården.</li><li>Patienten har blivit smittad av nuvarande eller före detta make, maka, sambo eller registrerade partner, och den personen smittades när hen behandlades av den svenska hälso- och sjukvården.</li></ol>
      """;

  public static final CertificateModelId FK3226_V1_0 = CertificateModelId.builder()
      .type(new CertificateType(FK_3226))
      .version(new CertificateVersion(VERSION))
      .build();

  public static final SchematronPath SCHEMATRON_PATH = new SchematronPath(
      "fk3226/schematron/lunsp.v1.sch");

  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(FK3226_V1_0)
        .type(CodeSystemKvIntygstyp.FK3226)
        .typeName(FK3226_TYPE_NAME)
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
        .recipient(CertificateRecipientFactory.fkassa(fkLogicalAddress))
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
        .schematronPath(SCHEMATRON_PATH)
        .pdfSpecification(FK3226PdfSpecification.create())
        .certificateActionSpecifications(FK3226CertificateActionSpecification.create())
        .messageActionSpecifications(FK3226MessageActionSpecification.create())
        .elementSpecifications(
            List.of(
                categoryGrund(
                    questionUtlatandeBaseratPa(
                        questionUtlatandeBaseratPaAnnat()
                    )
                ),
                categoryHot(
                    questionDiagnos(diagnosisCodeRepository),
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
        .certificateActionFactory(certificateActionFactory)
        .build();
  }
}
