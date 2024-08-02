package se.inera.intyg.certificateservice.testability.certificate.service.fillservice.fk7472;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0008.EN_ATTONDEL;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0008.EN_FJARDEDEL;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0008.HALVA;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0008.HELA;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0008.TRE_FJARDEDELAR;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.CertificateModelFactoryFK7472.FK7472_V1_0;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.elements.QuestionPeriod.QUESTION_PERIOD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.elements.QuestionSymptom.QUESTION_SYMPTOM_ID;
import static se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO.EMPTY;
import static se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO.MAXIMAL;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.dateRange;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.elementData;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.emptyValue;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.nowPlusDays;
import static se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityFIllCertificateUtil.spec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO;
import se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityCertificateFillService;

@Component
public class TestabilityCertificateFillServiceFK7472 implements
    TestabilityCertificateFillService {

  @Override
  public List<CertificateModelId> certificateModelIds() {
    return List.of(FK7472_V1_0);
  }

  @Override
  public List<ElementData> fill(CertificateModel certificateModel,
      TestabilityFillTypeDTO fillType) {

    return fillType == EMPTY
        ? Collections.emptyList()
        : fillWithValues(certificateModel, fillType);
  }

  private static List<ElementData> fillWithValues(CertificateModel certificateModel,
      TestabilityFillTypeDTO fillType) {
    final var elementData = new ArrayList<ElementData>();
    final var specSymptom = spec(QUESTION_SYMPTOM_ID, certificateModel);
    final var specPeriod = spec(QUESTION_PERIOD_ID, certificateModel);

    symptomDiagnos(specSymptom, elementData);
    vardperiod(specPeriod, elementData, fillType);

    return elementData;
  }

  private static void symptomDiagnos(ElementSpecification spec, List<ElementData> list) {
    if (emptyValue(spec) instanceof ElementValueText elementValueText) {
      final var text = elementValueText.withText("Symptom och diagnos");
      list.add(elementData(spec.id(), text));
    }
  }

  private static void vardperiod(ElementSpecification spec, List<ElementData> list,
      TestabilityFillTypeDTO fillType) {
    if (emptyValue(spec) instanceof ElementValueDateRangeList elementValueDateRangeList) {
      final var ranges = List.of(
          dateRange(new FieldId(HELA.code()), nowPlusDays(0L), nowPlusDays(6L)),
          dateRange(new FieldId(TRE_FJARDEDELAR.code()), nowPlusDays(7L), nowPlusDays(13L)),
          dateRange(new FieldId(HALVA.code()), nowPlusDays(14L), nowPlusDays(20L)),
          dateRange(new FieldId(EN_FJARDEDEL.code()), nowPlusDays(21L), nowPlusDays(27L)),
          dateRange(new FieldId(EN_ATTONDEL.code()), nowPlusDays(28L), nowPlusDays(34L)));

      final var rangeList = elementValueDateRangeList.withDateRangeList(
          fillType == MAXIMAL ? ranges : ranges.subList(0, 1));
      list.add(elementData(spec.id(), rangeList));
    }
  }
}
