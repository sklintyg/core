package se.inera.intyg.certificateservice.testability.certificate.service.fillservice.fk7443;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7443.CertificateModelFactoryFK7443.FK7443_V1_0;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7443.CertificateModelFactoryFK7443.QUESTION_SYMPTOM_ID;

import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO;
import se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityCertificateFillService;

@Component
public class TestabilityCertificateFillServiceFK7443 implements TestabilityCertificateFillService {

  @Override
  public List<CertificateModelId> certificateModelIds() {
    return List.of(FK7443_V1_0);
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
    final var elementSpecification = certificateModel.elementSpecification(QUESTION_SYMPTOM_ID);
    final var elementValue = elementSpecification.configuration().emptyValue();
    if (elementValue instanceof ElementValueText elementValueText) {
      final var symptom = elementValueText.withText("Exempel på svar");
      return List.of(
          ElementData.builder()
              .id(QUESTION_SYMPTOM_ID)
              .value(symptom)
              .build()
      );
    }
    return Collections.emptyList();
  }
}
