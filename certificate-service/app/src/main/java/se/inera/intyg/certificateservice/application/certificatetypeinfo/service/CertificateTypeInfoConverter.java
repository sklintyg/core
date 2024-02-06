package se.inera.intyg.certificateservice.application.certificatetypeinfo.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.dto.CertificateTypeInfoDTO;
import se.inera.intyg.certificateservice.application.common.ResourceLinkConverter;
import se.inera.intyg.certificateservice.domain.action.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;

@Component
@RequiredArgsConstructor
public class CertificateTypeInfoConverter {

  private final ResourceLinkConverter resourceLinkConverter;

  public CertificateTypeInfoDTO convert(CertificateModel certificateModel,
      List<CertificateAction> certificateActions) {
    return CertificateTypeInfoDTO.builder()
        .type(certificateModel.id().type().type())
        .name(certificateModel.name())
        .description(certificateModel.description())
        .links(
            certificateActions.stream()
                .map(resourceLinkConverter::convert)
                .toList()
        )
        .build();
  }
}
