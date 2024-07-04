package se.inera.intyg.certificateservice.testability.certificate.service.fillservice.fk3226;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CertificateModelFactoryFK3226.DIAGNOSIS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CertificateModelFactoryFK3226.DIAGNOS_1;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CertificateModelFactoryFK3226.ENDAST_PALLIATIV_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CertificateModelFactoryFK3226.FK3226_V1_0;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CertificateModelFactoryFK3226.FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CertificateModelFactoryFK3226.QUESTION_NAR_AKTIVA_BEHANDLINGEN_AVSLUTADES_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CertificateModelFactoryFK3226.QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CertificateModelFactoryFK3226.QUESTION_UTLATANDE_BASERAT_PA_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CertificateModelFactoryFK3226.UTLATANDE_BASERAT_PA_UNDERSOKNING_AV_PATIENTEN_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CodeSystemKvFkmu0010.ENDAST_PALLIATIV;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226.CodeSystemIcd10Se;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO;
import se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityCertificateFillService;

@Component
public class TestabilityCertificateFillServiceFK3226 implements TestabilityCertificateFillService {

  @Override
  public List<CertificateModelId> certificateModelIds() {
    return List.of(FK3226_V1_0);
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
    final var elementSpecificationBaseratPa = certificateModel.elementSpecification(
        QUESTION_UTLATANDE_BASERAT_PA_ID);
    final var elementValueBaseratPa = elementSpecificationBaseratPa.configuration().emptyValue();
    final var elementData = new ArrayList<ElementData>();
    if (elementValueBaseratPa instanceof ElementValueDateList elementValueDateList) {
      final var baseratPa = elementValueDateList.withDateList(
          List.of(
              ElementValueDate.builder()
                  .date(LocalDate.now(ZoneId.systemDefault()))
                  .dateId(new FieldId(UTLATANDE_BASERAT_PA_UNDERSOKNING_AV_PATIENTEN_FIELD_ID))
                  .build()
          ));
      elementData.add(
          ElementData.builder()
              .id(QUESTION_UTLATANDE_BASERAT_PA_ID)
              .value(baseratPa)
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

    final var elementSpecificationBehandlingOchVard = certificateModel.elementSpecification(
        QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID
    );
    final var elementValueBehandlingOchVard = elementSpecificationBehandlingOchVard.configuration()
        .emptyValue();
    if (elementValueBehandlingOchVard instanceof ElementValueCode) {
      elementData.add(
          ElementData.builder()
              .id(QUESTION_PATIENTENS_BEHANDLING_OCH_VARDSITUATION_ID)
              .value(
                  ElementValueCode.builder()
                      .codeId(ENDAST_PALLIATIV_FIELD_ID)
                      .code(ENDAST_PALLIATIV.code())
                      .build()
              )
              .build()
      );
    }

    final var elementSpecificationBehandlingAvslutad = certificateModel.elementSpecification(
        QUESTION_NAR_AKTIVA_BEHANDLINGEN_AVSLUTADES_ID
    );
    final var elementValueBehandlingAvslutad = elementSpecificationBehandlingAvslutad.configuration()
        .emptyValue();
    if (elementValueBehandlingAvslutad instanceof ElementValueDate elementValueDate) {
      final var avslutDatum = elementValueDate.withDate(LocalDate.now(ZoneId.systemDefault()));
      elementData.add(
          ElementData.builder()
              .id(QUESTION_NAR_AKTIVA_BEHANDLINGEN_AVSLUTADES_ID)
              .value(avslutDatum)
              .build()
      );
    }

    final var elementSpecificationSamtycke = certificateModel.elementSpecification(
        FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_ID
    );
    final var elementValueSamtycke = elementSpecificationSamtycke.configuration()
        .emptyValue();
    if (elementValueSamtycke instanceof ElementValueBoolean elementValueBoolean) {
      final var samtycke = elementValueBoolean.withValue(true);
      elementData.add(
          ElementData.builder()
              .id(FORUTSATTNINGAR_FOR_ATT_LAMNA_SKRIFTLIGT_SAMTYCKE_ID)
              .value(samtycke)
              .build()
      );
    }

    return elementData;
  }
}
