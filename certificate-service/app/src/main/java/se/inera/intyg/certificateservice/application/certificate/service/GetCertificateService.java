package se.inera.intyg.certificateservice.application.certificate.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.GetCertificateResponse;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.validation.GetCertificateRequestValidator;
import se.inera.intyg.certificateservice.application.common.ActionEvaluationFactory;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkTypeDTO;
import se.inera.intyg.certificateservice.domain.action.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.service.GetCertificateDomainService;
import se.inera.intyg.certificateservice.domain.common.model.Role;

@Service
@RequiredArgsConstructor
public class GetCertificateService {

  private final ActionEvaluationFactory actionEvaluationFactory;
  private final GetCertificateRequestValidator getCertificateRequestValidator;
  private final GetCertificateDomainService getCertificateDomainService;
  private final CertificateConverter certificateConverter;
  private final ResourceLinkConverter resourceLinkConverter;

  public GetCertificateResponse get(GetCertificateRequest getCertificateRequest,
      String certificateId) {
    getCertificateRequestValidator.validate(getCertificateRequest, certificateId);
    final var actionEvaluation = actionEvaluationFactory.create(
        getCertificateRequest.getUser(),
        getCertificateRequest.getUnit(),
        getCertificateRequest.getCareUnit(),
        getCertificateRequest.getCareProvider()
    );
    final var certificate = getCertificateDomainService.get(
        new CertificateId(certificateId),
        actionEvaluation
    );

    final var resourceLinkDTOS = new java.util.ArrayList<>(
        certificate.actions(actionEvaluation).stream()
            .map(resourceLinkConverter::convert)
            .toList()
    );

    if (userIsDoctor(actionEvaluation)) {
      addSignActionIfUserHasEditPrivilege(resourceLinkDTOS);
    }

    return GetCertificateResponse.builder()
        .certificate(certificateConverter.convert(
                certificate,
                resourceLinkDTOS
            )
        )
        .build();
  }

  /**
   * Temporaly fix to get sign certificate resource link. Related methods:
   * <p>
   * addSignActionIfUserHasEditPrivilege() createResourceLinkDTO() userIsDoctor()
   */
  private void addSignActionIfUserHasEditPrivilege(List<ResourceLinkDTO> resourceLinkDTOS) {
    resourceLinkDTOS.stream()
        .filter(link -> link.getType().equals(ResourceLinkTypeDTO.EDIT_CERTIFICATE))
        .findAny()
        .ifPresent(action -> resourceLinkDTOS.add(createResourceLinkDTO()));
  }

  private ResourceLinkDTO createResourceLinkDTO() {
    return ResourceLinkDTO.builder()
        .type(ResourceLinkTypeDTO.SIGN_CERTIFICATE)
        .name("Signera intyget")
        .description("Intyget signeras.")
        .enabled(true)
        .build();
  }

  private static boolean userIsDoctor(ActionEvaluation actionEvaluation) {
    return actionEvaluation.user().role().equals(Role.DOCTOR);
  }
}
