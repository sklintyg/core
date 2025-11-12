package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionDiagnos.QUESTION_DIAGNOS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionPeriodBedomning.QUESTION_PERIOD_BEDOMNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag114.elements.QuestionPeriodProcentBedomning.QUESTION_PERIOD_PROCENT_BEDOMNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems.CodeSystemKvFkmu0002.NUVARANDE_ARBETE;

import java.util.List;
import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueInteger;
import se.inera.intyg.certificateservice.domain.certificate.model.SickLeaveCertificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SickLeaveProvider;

public class AG114SickLeaveProvider implements SickLeaveProvider {

  private static final ElementValueDiagnosis DEFAULT_DIAGNOSIS = ElementValueDiagnosis.builder()
      .code("X")
      .description("Diagnoskod X är okänd och har ingen beskrivning")
      .build();

  @Override
  public Optional<SickLeaveCertificate> build(Certificate certificate) {
    final var metadata = certificate.certificateMetaData();
    final var diagnoses = getElementValueDiagnoses(certificate);

    final var workCapacityPercentage = getWorkCapacityPercentage(certificate);
    final var workCapacityDateRange = getWorkCapacityDateRange(certificate, workCapacityPercentage);

    return Optional.of(
        SickLeaveCertificate.builder()
            .partOfSickLeaveChain(false)
            .id(certificate.id())
            .careGiverId(metadata.careProvider().hsaId())
            .issuingUnitId(metadata.issuingUnit().hsaId())
            .issuingUnitName(metadata.issuingUnit().name())
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
            .workCapacities(List.of(workCapacityDateRange))
            .employment(List.of(
                ElementValueCode.builder()
                    .code(NUVARANDE_ARBETE.displayName())
                    .build()
            ))
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

  private static Integer getWorkCapacityPercentage(Certificate certificate) {
    return certificate.elementData().stream()
        .filter(elementData -> elementData.id().equals(QUESTION_PERIOD_PROCENT_BEDOMNING_ID))
        .findFirst()
        .map(ElementData::value)
        .map(ElementValueInteger.class::cast)
        .map(ElementValueInteger::value)
        .orElseThrow();
  }

  private static DateRange getWorkCapacityDateRange(Certificate certificate,
      Integer workCapacityPercentage) {
    final var elementValueDateRange = certificate.elementData().stream()
        .filter(elementData -> elementData.id().equals(QUESTION_PERIOD_BEDOMNING_ID))
        .findFirst()
        .map(ElementData::value)
        .map(ElementValueDateRange.class::cast)
        .orElseThrow();

    return DateRange.builder()
        .dateRangeId(new FieldId(workCapacityPercentage.toString()))
        .from(elementValueDateRange.fromDate())
        .to(elementValueDateRange.toDate())
        .build();
  }
}
