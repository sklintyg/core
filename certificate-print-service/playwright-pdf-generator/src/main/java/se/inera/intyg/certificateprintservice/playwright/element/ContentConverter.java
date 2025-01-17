package se.inera.intyg.certificateprintservice.playwright.element;

import se.inera.intyg.certificateprintservice.pdfgenerator.api.Certificate;

public class ContentConverter {

  private ContentConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static Content convert(Certificate certificate) {
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
