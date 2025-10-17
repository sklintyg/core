package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionDiagnos.QUESTION_DIAGNOS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionNedsattningArbetsformaga.QUESTION_NEDSATTNING_ARBETSFORMAGA_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionSysselsattning.QUESTION_SYSSELSATTNING_ID;

import java.util.List;
import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificate.model.SickLeaveCertificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SickLeaveProvider;

public class AG7804SickLeaveProvider implements SickLeaveProvider {

  private static final ElementValueDiagnosis DEFAULT_DIAGNOSIS = ElementValueDiagnosis.builder()
      .code("X")
      .description("Diagnoskod X är okänd och har ingen beskrivning")
      .build();

  @Override
  public Optional<SickLeaveCertificate> build(Certificate certificate) {
    final var isNotSickLeaveCertificate = certificate.elementData().stream()
        .filter(elementData -> elementData.id().equals(QUESTION_SMITTBARARPENNING_ID))
        .findFirst()
        .map(ElementData::value)
        .map(ElementValueBoolean.class::cast)
        .map(ElementValueBoolean::value)
        .orElse(false);

    if (isNotSickLeaveCertificate) {
      return Optional.empty();
    }

    final var metadata = certificate.certificateMetaData();
    final var diagnoses = getElementValueDiagnoses(certificate);

    return Optional.of(
        SickLeaveCertificate.builder()
            .id(certificate.id())
            .careGiverId(metadata.careProvider().hsaId())
            .careUnitId(metadata.careUnit().hsaId())
            .careUnitName(metadata.careUnit().name())
            .type(certificate.certificateModel().type())
            .civicRegistrationNumber(metadata.patient().id())
            .signingDoctorName(metadata.issuer().name())
            .patientName(metadata.patient().name())
            .diagnoseCode(!diagnoses.isEmpty() ? diagnoses.getFirst() : DEFAULT_DIAGNOSIS)
            .biDiagnoseCode1(
                diagnoses.size() > 1 ? diagnoses.get(1) : null
            )
            .biDiagnoseCode2(
                diagnoses.size() > 2 ? diagnoses.get(2) : null
            )
            .signingDoctorId(metadata.issuer().hsaId())
            .signingDateTime(certificate.signed())
            .deleted(certificate.revoked())
            .workCapacities(
                certificate.elementData().stream()
                    .filter(
                        elementData -> elementData.id()
                            .equals(QUESTION_NEDSATTNING_ARBETSFORMAGA_ID))
                    .findFirst()
                    .map(ElementData::value)
                    .map(ElementValueDateRangeList.class::cast)
                    .map(ElementValueDateRangeList::dateRangeList)
                    .orElseThrow()
            )
            .employment(
                certificate.elementData().stream()
                    .filter(
                        elementData -> elementData.id().equals(QUESTION_SYSSELSATTNING_ID))
                    .findFirst()
                    .map(ElementData::value)
                    .map(ElementValueCodeList.class::cast)
                    .map(ElementValueCodeList::list)
                    .orElseThrow()
            )
            .build()
    );
  }

  private static List<ElementValueDiagnosis> getElementValueDiagnoses(Certificate certificate) {
    return certificate.elementData().stream()
        .filter(elementData -> elementData.id().equals(QUESTION_DIAGNOS_ID))
        .findFirst()
        .map(ElementData::value)
        .map(ElementValueDiagnosisList.class::cast)
        .map(ElementValueDiagnosisList::diagnoses)
        .orElse(List.of());
  }
}
