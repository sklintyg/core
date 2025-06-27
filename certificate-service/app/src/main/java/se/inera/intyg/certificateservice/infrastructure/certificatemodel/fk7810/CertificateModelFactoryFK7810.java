package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SchematronPath;
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateRecipientFactory;

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
  private static final String NAME = "Läkarutlåtande för assistansersättning";
  private static final String DESCRIPTION = """
      Vem kan få assistansersättning?
      
      Assistansersättning är till för personer med omfattande funktionsnedsättning som dels tillhör personkrets enligt lagen om stöd och service till vissa funktionshindrade (LSS) dels behöver personligt utformat stöd för sina grundläggande behov i mer än 20 timmar per vecka, i genomsnitt.\s
      
      Ersättningen används till personlig assistans, för att kunna leva som andra och delta i samhällslivet. Både vuxna och barn kan få assistansersättning.
      """;

  private static final String DETAILED_DESCRIPTION = """
      <b className="iu-fw-heading">Vem kan få assistansersättning?</b>
      Assistansersättning är till för personer med omfattande funktionsnedsättning som dels tillhör personkrets enligt lagen om stöd och service till vissa funktionshindrade (LSS) dels behöver personligt utformat stöd för sina grundläggande behov i mer än 20 timmar per vecka, i genomsnitt.
      
      Ersättningen används till personlig assistans, för att kunna leva som andra och delta i samhällslivet. Både vuxna och barn kan få assistansersättning.
      
      Följande tillstånd omfattas av LSS:
      <ul>
        <li>intellektuell funktionsnedsättning (utvecklingsstörning), autism eller autismliknande tillstånd</li>
        <li>betydande och bestående begåvningsmässig funktionsnedsättning efter hjärnskada i vuxen ålder, föranledd av yttre våld eller kroppslig sjukdom</li>
        <li>andra varaktiga och stora fysiska eller psykiska funktionsnedsättningar som orsakar betydande svårigheter i den dagliga livsföringen, och som gör att personen har ett omfattande behov av stöd eller service.</li>
      </ul>
      
      Följande räknas som grundläggande behov:
      <ul>
        <li>andning</li>
        <li>personlig hygien</li>
        <li>av- och påklädning</li>
        <li>kommunikation med andra stöd som en person behöver på grund av en psykisk funktionsnedsättning, för att förebygga att personen fysiskt skadar sig själv, någon annan eller egendom</li>
        <li>stöd som en person behöver löpande under större delen av dygnet på grund av ett medicinskt tillstånd som innebär att det finns fara för den enskildes liv, eller som innebär att det annars finns en överhängande och allvarlig risk för personens fysiska hälsa.</li>
      </ul>
      
      Försäkringskassan kan inte räkna in hälso- och sjukvårdsåtgärder enligt hälso- och sjukvårdslagen i en persons hjälpbehov. Men om hälso- och sjukvårdspersonal bedömer att en åtgärd kan utföras som egenvård, så kan Försäkringskassan i vissa fall bevilja ersättning för det hjälpbehovet.
      
      Mer information finns på <a href="http://forsakringskassan.se/">Försäkringskassans hemsida</a>. Sök på ”assistansersättning”.
      """;

  public static final CertificateModelId FK7810_V1_0 = CertificateModelId.builder()
      .type(new CertificateType(FK_7810))
      .version(new CertificateVersion(VERSION))
      .build();

  //FIXME: This is a placeholder for the actual Schematron path.
  public static final SchematronPath SCHEMATRON_PATH = new SchematronPath("");

  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(FK7810_V1_0)
        .type(
            new Code(
                "PLACEHOLDER", // FIXME: This should be replaced with the actual code for FK7810
                "PLACEHOLDER",
                // FIXME: This should be replaced with the actual code system for FK7810
                NAME
            )
        )
        .name(NAME)
        .description(DESCRIPTION)
        .detailedDescription(DETAILED_DESCRIPTION)
        .activeFrom(activeFrom)
        .availableForCitizen(false) //FIXME: This should be set based on actual requirements
        .recipient(CertificateRecipientFactory.fkassa(fkLogicalAddress))
        .schematronPath(SCHEMATRON_PATH)
        .messageTypes(null) //FIXME: This should be set based on actual requirements
        .build();
  }
}
