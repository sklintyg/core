package se.inera.intyg.certificateservice.testability.certificate.service.fillservice.fk7211;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7211.CertificateModelFactoryFK7211.FK7211_V1_0;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7211.CertificateModelFactoryFK7211.QUESTION_BERAKNAT_NEDKOMSTDATUM_ID;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO;
import se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityCertificateFillService;

@Component
public class TestabilityCertificateFillServiceFK7211 implements TestabilityCertificateFillService {

  @Override
  public List<CertificateModelId> certificateModelIds() {
    return List.of(FK7211_V1_0);
  }

  @Override
  public List<ElementData> fill(CertificateModel certificateModel,
      TestabilityFillTypeDTO fillType) {
    final var elementSpecification = certificateModel.elementSpecification(
        QUESTION_BERAKNAT_NEDKOMSTDATUM_ID);
    final var elementValue = elementSpecification.configuration().emptyValue();
    if (elementValue instanceof ElementValueDate elementValueDate) {
      final var beraknatNedkomstdatum = elementValueDate
          .withDate(LocalDate.now(ZoneId.systemDefault()).plusMonths(6));
      return List.of(
          ElementData.builder()
              .id(QUESTION_BERAKNAT_NEDKOMSTDATUM_ID)
              .value(beraknatNedkomstdatum)
              .build()
      );
    }
    return Collections.emptyList();
  }
}
