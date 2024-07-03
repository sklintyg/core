package se.inera.intyg.certificateservice.testability.certificate.service.fillservice.fk7809;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CertificateModelFactoryFK7809.DIAGNOSIS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CertificateModelFactoryFK7809.DIAGNOSIS_MOTIVATION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CertificateModelFactoryFK7809.DIAGNOS_1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CertificateModelFactoryFK7809.FK7809_V1_0;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CertificateModelFactoryFK7809.FUNKTIONSNEDSATTNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CertificateModelFactoryFK7809.FUNKTIONSNEDSATTNING_INTELLEKTUELL_FUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CertificateModelFactoryFK7809.FUNKTIONSNEDSATTNING_MOTIVERING_INTELLEKTUELL_FUNKTION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CertificateModelFactoryFK7809.QUESTION_BASERAT_PA_ANNAT_UNDERLAG_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CertificateModelFactoryFK7809.QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CertificateModelFactoryFK7809.QUESTION_PROGNOS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CertificateModelFactoryFK7809.UTLATANDE_BASERAT_PA_UNDERSOKNING_AV_PATIENTEN_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.CodeSystemFunktionsnedsattning.INTELLEKTUELL_FUNKTION;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CodeSystemIcd10Se;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO;
import se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityCertificateFillService;

@Component
public class TestabilityCertificateFillServiceFK7809 implements TestabilityCertificateFillService {

  @Override
  public List<CertificateModelId> certificateModelIds() {
    return List.of(FK7809_V1_0);
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
    final var elementSpecificationGrund =
        certificateModel.elementSpecification(QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID);
    final var elementValueGrund = elementSpecificationGrund.configuration().emptyValue();
    final var elementData = new ArrayList<ElementData>();
    if (elementValueGrund instanceof ElementValueDateList elementValueDateList) {
      final var baseratPa = elementValueDateList.withDateList(
          List.of(
              ElementValueDate.builder()
                  .date(LocalDate.now(ZoneId.systemDefault()))
                  .dateId(new FieldId(UTLATANDE_BASERAT_PA_UNDERSOKNING_AV_PATIENTEN_FIELD_ID))
                  .build()
          ));
      elementData.add(
          ElementData.builder()
              .id(QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID)
              .value(baseratPa)
              .build()
      );
    }

    final var elementSpecificationAnnatUnderlag =
        certificateModel.elementSpecification(QUESTION_BASERAT_PA_ANNAT_UNDERLAG_ID);
    final var elementValueAnnatUnderlag = elementSpecificationAnnatUnderlag
        .configuration().emptyValue();
    if (elementValueAnnatUnderlag instanceof ElementValueBoolean elementValueBoolean) {
      final var anntUnderlag = elementValueBoolean.withValue(false);
      elementData.add(ElementData.builder()
          .id(QUESTION_BASERAT_PA_ANNAT_UNDERLAG_ID)
          .value(anntUnderlag)
          .build()
      );
    }

    final var elementSpecificationDiagnos = certificateModel.elementSpecification(
        DIAGNOSIS_ID);
    final var elementValueDiagnos = elementSpecificationDiagnos.configuration().emptyValue();
    if (elementValueDiagnos instanceof ElementValueDiagnosisList) {
      elementData.add(ElementData.builder()
          .id(DIAGNOSIS_ID)
          .value(ElementValueDiagnosisList.builder()
              .diagnoses(
                  List.of(ElementValueDiagnosis.builder()
                      .id(DIAGNOS_1)
                      .code("A78")
                      .description("Q-feber")
                      .terminology(CodeSystemIcd10Se.DIAGNOS_ICD_10_ID)
                      .build()
                  )
              ).build()
          ).build()
      );
    }

    final var elementSpecificationHistorik = certificateModel.elementSpecification(
        DIAGNOSIS_MOTIVATION_ID);
    final var elementValueHistorik = elementSpecificationHistorik.configuration().emptyValue();
    if (elementValueHistorik instanceof ElementValueText elementValueText) {
      final var historik = elementValueText.withText(
          "Sammanfatta historiken för diagnoserna.");
      elementData.add(ElementData.builder()
          .id(DIAGNOSIS_MOTIVATION_ID)
          .value(historik)
          .build()
      );
    }

    final var elementSpecificationFunktion = certificateModel.elementSpecification(
        FUNKTIONSNEDSATTNING_ID);
    final var elementValueBehandlingOchVard = elementSpecificationFunktion.configuration()
        .emptyValue();
    if (elementValueBehandlingOchVard instanceof ElementValueCodeList elementValueCodeList) {
      final var funktion = elementValueCodeList.withList(
          List.of(
              ElementValueCode.builder()
                  .codeId(FUNKTIONSNEDSATTNING_INTELLEKTUELL_FUNKTION_ID)
                  .code(INTELLEKTUELL_FUNKTION.code())
                  .build()
          ));
      elementData.add(
          ElementData.builder()
              .id(FUNKTIONSNEDSATTNING_ID)
              .value(funktion)
              .build()
      );
    }

    final var elementSpecificationMotiveringIntellektuell = certificateModel.elementSpecification(
        FUNKTIONSNEDSATTNING_MOTIVERING_INTELLEKTUELL_FUNKTION_ID);
    final var elementValueMotiveringInellektuell = elementSpecificationMotiveringIntellektuell.configuration()
        .emptyValue();
    if (elementValueMotiveringInellektuell instanceof ElementValueText elementValueText) {
      final var motiveringIntellektuell = elementValueText.withText(
          "Motivering intellektuell funktionsnedsättning.");
      elementData.add(ElementData.builder()
          .id(FUNKTIONSNEDSATTNING_MOTIVERING_INTELLEKTUELL_FUNKTION_ID)
          .value(motiveringIntellektuell)
          .build()
      );
    }

    final var elementSpecificationPrognos = certificateModel.elementSpecification(
        QUESTION_PROGNOS_ID);
    final var elementValuePrognos = elementSpecificationPrognos.configuration().emptyValue();
    if (elementValuePrognos instanceof ElementValueText elementValueText) {
      final var prognos = elementValueText.withText(
          "Det blir sannolikt sämre innan det blir bättre");
      elementData.add(ElementData.builder()
          .id(QUESTION_PROGNOS_ID)
          .value(prognos)
          .build()
      );
    }

    return elementData;
  }
}
