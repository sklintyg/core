package se.inera.intyg.certificateservice.infrastructure.certificatemodel.testintyg;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.elements.ElementUnitContactInformation.issuingUnitContactInfo;

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
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SchematronPath;
import se.inera.intyg.certificateservice.domain.common.model.CertificateText;
import se.inera.intyg.certificateservice.domain.common.model.CertificateTextType;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.Subject;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateRecipientFactory;

@Component
@RequiredArgsConstructor
public class CertificateModelFactoryTestIntyg implements CertificateModelFactory {

  private final CertificateActionFactory certificateActionFactory;
  @Value("${certificate.model.testintyg.v1_0.active.from}")
  private LocalDateTime activeFrom;

  @Value("${sendmessagetofk.logicaladdress}")
  private String fkLogicalAddress;

  private final DiagnosisCodeRepository diagnosisCodeRepository;

  private static final String FK_7810 = "testintyg";
  private static final String VERSION = "1.0";
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
      <p>Mer information finns på <LINK:forsakringskassanLink>. Sök på ”assistansersättning”.</p>
      """;

  private enum ElementType {
    CATEGORY, DATE, ISSUING_UNIT, TEXT_AREA, CHECKBOX_DATE_RANGE_LIST, CHECKBOX_MULTIPLE_DATE,
    RADIO_MULTIPLE_CODE, RADIO_BOOLEAN, MESSAGE, DIAGNOSIS, TEXT_FIELD, MEDICAL_INVESTIGATION_LIST,
    CHECKBOX_MULTIPLE_CODE, VISUAL_ACUITIES, DROPDOWN, DATE_RANGE
  }


  private static final String PREAMBLE_TEXT =
      "Det här är ditt intyg. Intyget innehåller all information som vården fyllt i. Du kan inte ändra något i ditt intyg. "
          + "Har du frågor kontaktar du den som skrivit ditt intyg.";

  public static final SchematronPath SCHEMATRON_PATH = new SchematronPath(
      "testintyg/schematron/luas.v1.sch");

  public static final CertificateModelId TestIntyg_V1_0 = CertificateModelId.builder()
      .type(new CertificateType(FK_7810))
      .version(new CertificateVersion(VERSION))
      .build();

  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(TestIntyg_V1_0)
        .type(
            new Code(
                "LUAS",
                "b64ea353-e8f6-4832-b563-fc7d46f29548",
                NAME
            )
        )
        .name(NAME)
        .description(DESCRIPTION)
        .detailedDescription(DETAILED_DESCRIPTION.replace("\n", ""))
        .activeFrom(activeFrom)
        .availableForCitizen(true)
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
        .certificateActionSpecifications(TestIntygCertificateActionSpecification.create())
        .messageActionSpecifications(TestIntygMessageActionSpecification.create())
        .elementSpecifications(List.of(
            issuingUnitContactInfo()
        ))
        .certificateActionFactory(certificateActionFactory)
        .build();
  }
}
