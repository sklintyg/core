package se.inera.intyg.certificateservice.testability.certificate.service.fillservice.fk7210;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.CertificateModelFactoryFK7210.FK7210_V1_0;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.CertificateModelFactoryFK7210.QUESTION_BERAKNAT_FODELSEDATUM_ID;

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
public class TestabilityCertificateFillServiceFK7210 implements TestabilityCertificateFillService {

  @Override
  public List<CertificateModelId> certificateModelIds() {
    return List.of(FK7210_V1_0);
  }

  @Override
  public List<ElementData> fill(CertificateModel certificateModel,
      TestabilityFillTypeDTO fillType) {
    if (TestabilityFillTypeDTO.EMPTY.equals(fillType)) {
      return Collections.emptyList();
    }
    return fillWithValues(certificateModel);
  }

  private static List<ElementData> fillWithValues(CertificateModel certificateModel) {
    final var elementSpecification = certificateModel.elementSpecification(
        QUESTION_BERAKNAT_FODELSEDATUM_ID);
    final var elementValue = elementSpecification.configuration().emptyValue();
    if (elementValue instanceof ElementValueDate elementValueDate) {
      final var beraknatFodelsedatum = elementValueDate
          .withDate(LocalDate.now(ZoneId.systemDefault()).plusMonths(6));
      return List.of(
          ElementData.builder()
              .id(QUESTION_BERAKNAT_FODELSEDATUM_ID)
              .value(beraknatFodelsedatum)
              .build()
      );
    }
    return Collections.emptyList();
  }
}