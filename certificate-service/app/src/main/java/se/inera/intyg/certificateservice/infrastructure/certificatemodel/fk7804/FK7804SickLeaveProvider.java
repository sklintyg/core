package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionDiagnos.QUESTION_DIAGNOS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionNedsattningArbetsformaga.QUESTION_NEDSATTNING_ARBETSFORMAGA_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSmittbararpenning.QUESTION_SMITTBARARPENNING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionSysselsattning.QUESTION_SYSSELSATTNING_ID;

import java.util.List;
import java.util.Optional;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.SickLeaveCertificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SickLeaveProvider;

public class FK7804SickLeaveProvider implements SickLeaveProvider {

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
    return Optional.of(
        SickLeaveCertificate.builder()
            .partOfSickLeaveChain(true)
            .id(certificate.id())
            .careGiverId(metadata.careProvider().hsaId())
            .careUnitId(metadata.careUnit().hsaId())
            .careUnitName(metadata.careUnit().name())
            .type(certificate.certificateModel().type())
            .civicRegistrationNumber(metadata.patient().id())
            .signingDoctorName(metadata.issuer().name())
            .patientName(metadata.patient().name())
            .diagnoseCode(getElementValueDiagnoses(certificate).getFirst())
            .biDiagnoseCode1(
                getElementValueDiagnoses(certificate).size() > 1
                    ? getElementValueDiagnoses(certificate).get(1)
                    : null
            )
            .biDiagnoseCode2(
                getElementValueDiagnoses(certificate).size() > 2
                    ? getElementValueDiagnoses(certificate).get(2)
                    : null
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
            .extendsCertificateId(certificate.hasParent(RelationType.RENEW) ?
                certificate.parent().certificate().id().id() : null)
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
        .orElseThrow();
  }
}