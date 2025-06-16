package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3221;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.elements.ElementUnitContactInformation.issuingUnitContactInfo;

import java.time.LocalDateTime;
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
import se.inera.intyg.certificateservice.domain.common.model.Code;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.Subject;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.CertificateModelFactory;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.CertificateRecipientFactory;

@Component
@RequiredArgsConstructor
public class CertificateModelFactoryFK3221 implements CertificateModelFactory {

  private final CertificateActionFactory certificateActionFactory;
  @Value("${certificate.model.fk3221.v1_0.active.from}")
  private LocalDateTime activeFrom;

  @Value("${sendmessagetofk.logicaladdress}")
  private String fkLogicalAddress;

  private final DiagnosisCodeRepository diagnosisCodeRepository;

  private static final String FK_3221 = "fk3221";
  private static final String VERSION = "1.0";
  private static final String NAME = "Läkarutlåtande för omvårdnadsbidrag och merkostnadsersättning";
  private static final String DESCRIPTION = """
      <b className="iu-fw-heading">Vem kan få omvårdnadsbidrag eller merkostnadsersättning för barn?</b>
      
      Omvårdnadsbidrag och merkostnadsersättning för barn är till för föräldrar som har barn med funktionsnedsättning.
      
      Omvårdnadsbidrag kan beviljas om barnet behöver mer omvårdnad och tillsyn än barn i samma ålder som inte har en funktionsnedsättning. Behoven ska antas finnas i minst 6 månader.
      
      Merkostnadsersättning kan beviljas om föräldern har merkostnader som beror på barnets funktionsnedsättning. Merkostnaderna ska uppgå till minst 25 procent av ett prisbasbelopp per år, och funktionsnedsättningen ska antas finnas i minst 6 månader.
      """;
  private static final String DETAILED_DESCRIPTION = """
       <b className="iu-fw-heading">Vem kan få omvårdnadsbidrag eller merkostnadsersättning för barn?</b>
      
       Föräldrar som har barn med funktionsnedsättning kan ha rätt till omvårdnadsbidrag eller mekostnadsersättning.
      
       Omvårdnadsbidrag kan beviljas om barnet har behov av mer omvårdnad och tillsyn jämfört med barn i samma ålder som inte har en funktionsnedsättning. Behoven ska antas finnas i minst 6 månader. Omvårdnad och tillsyn kan till exempel vara behov av praktisk hjälp i vardagen, hjälp med struktur och kommunikation eller att föräldern behöver ha extra uppsikt över barnet.
      
      Merkostnadsersättning kan beviljas om föräldern har merkostnader som beror på barnets funktionsnedsättning. Funktionsnedsättningen ska antas finnas i minst 6 månader. För att få merkostnadsersättning ska merkostnaderna uppgå till minst 25 procent av ett prisbasbelopp per år.
      
       Merkostnader delas in sju olika kategorier:
       <ul><li>hälsa, vård och kost,</li><li>slitage och rengöring,</li><li>resor,</li><li>hjälpmedel</li><li>hjälp i den dagliga livsföringen</li><li>boende, och</li><li>övriga ändamål</li></ul>
      """;

  public static final CertificateModelId FK3221_V1_0 = CertificateModelId.builder()
      .type(new CertificateType(FK_3221))
      .version(new CertificateVersion(VERSION))
      .build();

  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(FK3221_V1_0)
        .type(
            new Code(
                "LU_OMV_MEK",
                "b64ea353-e8f6-4832-b563-fc7d46f29548",
                NAME
            )
        )
        .name(NAME)
        .description(DESCRIPTION)
        .detailedDescription(DETAILED_DESCRIPTION)
        .activeFrom(activeFrom)
        .availableForCitizen(false)
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
        .certificateActionSpecifications(FK3221CertificateActionSpecification.create())
        .messageActionSpecifications(FK3221MessageActionSpecification.create())
        .elementSpecifications(
            List.of(
                issuingUnitContactInfo()
            )
        )
        .certificateActionFactory(certificateActionFactory)
        .build();
  }
}