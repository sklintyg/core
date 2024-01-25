package se.inera.intyg.certificateservice.infrastructure.certificatemodel;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.model.CertificateModel;
import se.inera.intyg.certificateservice.model.CertificateModelId;
import se.inera.intyg.certificateservice.model.CertificateType;
import se.inera.intyg.certificateservice.model.CertificateVersion;
import se.inera.intyg.certificateservice.model.CertificationActionType;

@Component
public class CertificateModelFactoryFK7211 implements CertificateModelFactory {

  @Value("${certificate.model.fk7211.v1_0.active.from}")
  private LocalDateTime activeFrom;

  private static final String FK_7211 = "fk7211";
  private static final String VERSION = "1.0";
  private static final String NAME = "Intyg om graviditet";
  private static final String DESCRIPTION = "Intyg om graviditet "
      + "Ungefär i vecka 20 får du ett intyg om graviditet av barnmorskan. Intyget anger "
      + "också datum för beräknad förlossning. Intyget skickar du till Försäkringskassan, "
      + "som ger besked om kommande föräldrapenning.";

  @Override
  public CertificateModel create() {
    return CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(new CertificateType(FK_7211))
                .version(new CertificateVersion(VERSION))
                .build()
        )
        .name(NAME)
        .description(DESCRIPTION)
        .activeFrom(activeFrom)
        .certificateActionSpecifications(
            List.of(
                CertificateActionSpecification.builder()
                    .certificationActionType(CertificationActionType.CREATE)
                    .build()
            )
        )
        .build();
  }
}
