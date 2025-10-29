package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.elements.ElementUnitContactInformation.issuingUnitContactInfo;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.CategoryAktivitetsbegransningar.categoryAktivitetsbegransningar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.CategoryDiagnos.categoryDiagnos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.CategoryFunktionsnedsattning.categoryFunktionsnedsattning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.CategoryGrundForMedicinsktUnderlag.categoryGrundForMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.CategoryMedicinskBehandling.categoryMedicinskBehandling;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.CategoryOvrigt.categoryOvrigt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.CategoryPrognos.categoryPrognos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.CategorySjukvardandeInsats.categorySjukvardandeInsats;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAktivitetsbegransning.questionAktivitetsbegransning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAndningsFunktionMotivering.questionAndningsFunktionMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAnnanGrundForMedicinsktUnderlag.questionAnnanGrundForMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionAnnanKroppsligFunktionMotivering.questionAnnanKroppsligFunktionMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionBaseratPaAnnatMedicinsktUnderlag.questionBaseratPaAnnatMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionDiagnos.questionDiagnos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionDiagnosHistorik.questionDiagnosHistorik;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionForflyttningBegransningMotivering.questionForflyttningBegransningMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionFunktionsnedsattning.questionFunktionsnedsattning;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionGrundForMedicinsktUnderlag.questionGrundForMedicinsktUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionIntellektuellFunktionMotivering.questionIntellektuellFunktionMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionKannedomOmPatienten.questionKannedomOmPatienten;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionKommunikationBegransningMotivering.questionKommunikationBegransningMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionKommunikationSocialInteraktionMotivering.questionKommunikationSocialInteraktionMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionKoordinationMotivering.questionKoordinationMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionLarandeBegransningMotivering.questionLarandeBegransningMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionOvrigaBegransningMotivering.questionOvrigaBegransningMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionOvrigt.questionOvrigt;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionPagaendeOchPlaneradeBehandlingar.questionPagaendeOchPlaneradeBehandlingar;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionPersonligVardBegransningMotivering.questionPersonligVardBegransningMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionPrognos.questionPrognos;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionPsykiskFunktionMotivering.questionPsykiskFunktionMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionRelationTillPatienten.questionRelationTillPatienten;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionSinnesfunktionMotivering.questionSinnesfunktionMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionSjukvardandeInsatsEgenvard.questionSjukvardandeInsatsEgenvard;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionSjukvardandeInsatsEgenvardInsatser.questionSjukvardandeInsatsEgenvardInsatser;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionSjukvardandeInsatsHSL.questionSjukvardandeInsatsHSL;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionSjukvardandeInsatsHSLInsatser.questionSjukvardandeInsatsHSLInsatser;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionUppmarksamhetMotivering.questionUppmarksamhetMotivering;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionUtredningEllerUnderlag.questionUtredningEllerUnderlag;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionVardenhetOchTidplan.questionVardenhetOchTidplan;

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
public class CertificateModelFactoryFK7810 implements CertificateModelFactory {

  private final CertificateActionFactory certificateActionFactory;
  @Value("${certificate.model.fk7810.v1_0.active.from}")
  private LocalDateTime activeFrom;

  @Value("${sendmessagetofk.logicaladdress}")
  private String fkLogicalAddress;

  private final DiagnosisCodeRepository diagnosisCodeRepository;

  private static final String FK_7810 = "fk7810";
  private static final String VERSION = "1.0";
  private static final CertificateTypeName FK7810_TYPE_NAME = new CertificateTypeName("FK7810");
  private static final String NAME = "Läkarutlåtande för assistansersättning";
  private static final String DESCRIPTION = """
      Vem kan få assistansersättning?
      
      Assistansersättning är till för personer med omfattande funktionsnedsättning som dels tillhör personkrets enligt lagen om stöd och service till vissa funktionshindrade (LSS) dels behöver personligt utformat stöd för sina grundläggande behov i mer än 20 timmar per vecka, i genomsnitt.\s
      
      Ersättningen används till personlig assistans, för att kunna leva som andra och delta i samhällslivet. Både vuxna och barn kan få assistansersättning.
      """;

  private static final String DETAILED_DESCRIPTION = """
      <b className="iu-fw-heading">Vem kan få assistansersättning?</b>
      <p>Assistansersättning är till för personer med omfattande funktionsnedsättning som dels tillhör personkrets enligt lagen om stöd och service till vissa funktionshindrade (LSS) dels behöver personligt utformat stöd för sina grundläggande behov i mer än 20 timmar per vecka, i genomsnitt.</p>
      <p>Ersättningen används till personlig assistans, för att kunna leva som andra och delta i samhällslivet. Både vuxna och barn kan få assistansersättning.</p></br>
      <b className="iu-fw-heading">Följande tillstånd omfattas av LSS:</b>
      <ul>
        <li>intellektuell funktionsnedsättning (utvecklingsstörning), autism eller autismliknande tillstånd</li>
        <li>betydande och bestående begåvningsmässig funktionsnedsättning efter hjärnskada i vuxen ålder, föranledd av yttre våld eller kroppslig sjukdom</li>
        <li>andra varaktiga och stora fysiska eller psykiska funktionsnedsättningar som orsakar betydande svårigheter i den dagliga livsföringen, och som gör att personen har ett omfattande behov av stöd eller service.</li>
      </ul>
      </p><b className="iu-fw-heading">Följande räknas som grundläggande behov:</b>
      <ul>
        <li>andning</li><li>personlig hygien</li>
        <li>måltider</li>
        <li>av- och påklädning</li>
        <li>kommunikation med andra</li>
        <li>stöd som en person behöver på grund av en psykisk funktionsnedsättning, för att förebygga att personen fysiskt skadar sig själv, någon annan eller egendom</li>
        <li>stöd som en person behöver löpande under större delen av dygnet på grund av ett medicinskt tillstånd som innebär att det finns fara för den enskildes liv, eller som innebär att det annars finns en överhängande och allvarlig risk för personens fysiska hälsa.</li>
      </ul>
      <p>Försäkringskassan kan inte räkna in hälso- och sjukvårdsåtgärder enligt hälso- och sjukvårdslagen i en persons hjälpbehov. Men om hälso- och sjukvårdspersonal bedömer att en åtgärd kan utföras som egenvård, så kan Försäkringskassan i vissa fall bevilja ersättning för det hjälpbehovet.</p>
      <br>
      Mer information finns på <LINK:forsakringskassanLink>. Sök på ”assistansersättning”.
      """;

  private static final String PREAMBLE_TEXT =
      "Det här är ditt intyg. Intyget innehåller all information som vården fyllt i. Du kan inte ändra något i ditt intyg. "
          + "Har du frågor kontaktar du den som skrivit ditt intyg.";

  public static final SchematronPath SCHEMATRON_PATH = new SchematronPath(
      "fk7810/schematron/luas.v1.sch");

  public static final CertificateModelId FK7810_V1_0 = CertificateModelId.builder()
      .type(new CertificateType(FK_7810))
      .version(new CertificateVersion(VERSION))
      .build();

  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(FK7810_V1_0)
        .type(CodeSystemKvIntygstyp.FK7810)
        .typeName(FK7810_TYPE_NAME)
        .name(NAME)
        .description(DESCRIPTION)
        .detailedDescription(DETAILED_DESCRIPTION.replaceAll("\\R", ""))
        .activeFrom(activeFrom)
        .availableForCitizen(true)
        .summaryProvider(new FK7810CertificateSummaryProvider())
        .texts(
            List.of(
                CertificateText.builder()
                    .text(PREAMBLE_TEXT)
                    .type(CertificateTextType.PREAMBLE_TEXT)
                    .links(Collections.emptyList())
                    .build()
            )
        )
        .recipient(CertificateRecipientFactory.fkassa(fkLogicalAddress))
        .schematronPath(SCHEMATRON_PATH)
        .messageTypes(List.of(
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
        ))
        .certificateActionSpecifications(FK7810CertificateActionSpecification.create())
        .messageActionSpecifications(FK7810MessageActionSpecification.create())
        .elementSpecifications(List.of(
            categoryGrundForMedicinsktUnderlag(
                questionGrundForMedicinsktUnderlag(
                    questionRelationTillPatienten(),
                    questionAnnanGrundForMedicinsktUnderlag()
                ),
                questionKannedomOmPatienten(),
                questionBaseratPaAnnatMedicinsktUnderlag(),
                questionUtredningEllerUnderlag()
            ),
            categoryDiagnos(
                questionDiagnos(diagnosisCodeRepository),
                questionDiagnosHistorik()
            ),
            categoryFunktionsnedsattning(
                questionFunktionsnedsattning(),
                questionIntellektuellFunktionMotivering(),
                questionKommunikationSocialInteraktionMotivering(),
                questionUppmarksamhetMotivering(),
                questionPsykiskFunktionMotivering(),
                questionSinnesfunktionMotivering(),
                questionKoordinationMotivering(),
                questionAndningsFunktionMotivering(),
                questionAnnanKroppsligFunktionMotivering()
            ),
            categoryAktivitetsbegransningar(
                questionAktivitetsbegransning(),
                questionLarandeBegransningMotivering(),
                questionKommunikationBegransningMotivering(),
                questionForflyttningBegransningMotivering(),
                questionPersonligVardBegransningMotivering(),
                questionOvrigaBegransningMotivering()
            ),
            categoryMedicinskBehandling(
                questionPagaendeOchPlaneradeBehandlingar(
                    questionVardenhetOchTidplan()
                )
            ),
            categoryPrognos(
                questionPrognos()
            ),
            categorySjukvardandeInsats(
                questionSjukvardandeInsatsHSL(questionSjukvardandeInsatsHSLInsatser()),
                questionSjukvardandeInsatsEgenvard(questionSjukvardandeInsatsEgenvardInsatser())

            ),
            categoryOvrigt(
                questionOvrigt()
            ),
            issuingUnitContactInfo()
        ))
        .certificateActionFactory(certificateActionFactory)
        .pdfSpecification(FK7810PdfSpecification.create())
        .build();
  }
}