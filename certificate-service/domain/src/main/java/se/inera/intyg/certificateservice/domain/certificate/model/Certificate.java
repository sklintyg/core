package se.inera.intyg.certificateservice.domain.certificate.model;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.action.model.CertificateActionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;

@Getter
@Builder
@EqualsAndHashCode
public class Certificate {

  private final CertificateId id;
  private final CertificateModel certificateModel;
  private final LocalDateTime created;
  private CertificateMetaData certificateMetaData;
  @Builder.Default
  private List<ElementData> elementData = Collections.emptyList();

  public List<CertificateAction> actions(ActionEvaluation actionEvaluation) {
    return certificateModel.actions().stream()
        .filter(
            certificateAction -> certificateAction.evaluate(Optional.of(this), actionEvaluation)
        )
        .toList();
  }

  public boolean allowTo(CertificateActionType certificateActionType,
      ActionEvaluation actionEvaluation) {
    return certificateModel.actions().stream()
        .filter(certificateAction -> certificateActionType.equals(certificateAction.getType()))
        .findFirst()
        .map(certificateAction -> certificateAction.evaluate(Optional.of(this), actionEvaluation))
        .orElse(false);
  }

  public void updateMetadata(ActionEvaluation actionEvaluation) {
    certificateMetaData = CertificateMetaData.builder()
        .patient(
            actionEvaluation.patient() == null
                ? certificateMetaData.patient()
                : actionEvaluation.patient()
        )
        .issuer(Staff.create(actionEvaluation.user()))
        .careUnit(actionEvaluation.careUnit())
        .careProvider(actionEvaluation.careProvider())
        .issuingUnit(actionEvaluation.subUnit())
        .build();
  }

  public void updateData(List<ElementData> newData) {
    final var missingIds = newData.stream()
        .filter(newDataElement -> !certificateModel.elementSpecificationExists(newDataElement.id()))
        .map(newDataElement -> newDataElement.id().id())
        .reduce("", (s, s2) -> s.isBlank() ? s2 : s.concat(", " + s2));

    if (!missingIds.isEmpty()) {
      throw new IllegalArgumentException(
          "ElementIds '%s' are missing from certificateModelId '%s'"
              .formatted(missingIds, certificateModel.id())
      );
    }

    this.elementData = newData.stream().toList();
  }
}
