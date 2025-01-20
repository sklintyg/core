package se.inera.intyg.certificateprintservice.playwright.converters;

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Certificate;
import se.inera.intyg.certificateprintservice.playwright.document.Content;

@Component
public class ContentConverter {

  public Content convert(Certificate certificate) {
    return Content.builder()
        .categories(certificate.getCategories())
        .issuerName(certificate.getMetadata().getIssuerName())
        .issuingUnit(certificate.getMetadata().getIssuingUnit())
        .issuingUnitInfo(certificate.getMetadata().getIssuingUnitInfo())
        .signDate(certificate.getMetadata().isSigned() ? certificate.getMetadata()
            .getSigningDateAsString() : null)
        .description(certificate.getMetadata().getDescription())
        .certificateName(certificate.getMetadata().getName())
        .isDraft(certificate.getMetadata().isDraft())
        .build();
  }
}
