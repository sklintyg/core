package se.inera.intyg.certificateservice.application.certificatetypeinfo.service.converter;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateTypeInfoDTO;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;

@Component
@RequiredArgsConstructor
public class CertificateTypeInfoConverter {

  private final ResourceLinkConverter resourceLinkConverter;

  public CertificateTypeInfoDTO convert(CertificateModel certificateModel,
      List<CertificateAction> certificateActions, ActionEvaluation actionEvaluation) {
    return CertificateTypeInfoDTO.builder()
        .type(certificateModel.id().type().type())
        .name(certificateModel.name())
        .description(certificateModel.description())
        .links(
            certificateActions.stream()
                .map(certificateAction ->
                    resourceLinkConverter.convert(
                        certificateAction,
                        Optional.empty(),
                        actionEvaluation
                    )
                )
                .toList()
        )
        .build();
  }
}
