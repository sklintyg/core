package se.inera.intyg.certificateservice.application.certificate.service.converter;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.message.model.Message;

@Component
@RequiredArgsConstructor
public class CertificateConverter {

  private final CertificateDataConverter certificateDataConverter;
  private final CertificateMetadataConverter certificateMetadataConverter;
  private final HandleComplementElementVisibilityService handleComplementElementVisibilityService;

  public CertificateDTO convert(Certificate certificate, List<ResourceLinkDTO> resourceLinks,
      ActionEvaluation actionEvaluation) {
    var certificateDataElementMap = certificateDataConverter.convert(certificate, false);

    if (draftWithComplementRequestOnParent(certificate)) {
      certificate.parent().certificate().messages().stream()
          .map(Message::complements)
          .flatMap(List::stream)
          .forEach(complement ->
              certificate.certificateModel()
                  .elementSpecification(complement.elementId())
                  .getVisibilityConfiguration()
                  .ifPresent(
                      config -> handleComplementElementVisibilityService.handle(
                          complement.elementId(),
                          certificateDataElementMap,
                          certificate,
                          config
                      )));
    }

    return CertificateDTO.builder()
        .metadata(certificateMetadataConverter.convert(certificate, actionEvaluation))
        .data(certificateDataElementMap)
        .links(resourceLinks)
        .build();
  }

  public CertificateDTO convertForCitizen(Certificate certificate,
      List<ResourceLinkDTO> resourceLinks) {
    return CertificateDTO.builder()
        .metadata(certificateMetadataConverter.convert(certificate, null))
        .data(certificateDataConverter.convert(certificate, true))
        .links(resourceLinks)
        .build();
  }

  private static boolean draftWithComplementRequestOnParent(Certificate certificate) {
    return certificate.isDraft() && certificate.parent() != null && certificate.parent().type()
        .equals(RelationType.COMPLEMENT);
  }
}