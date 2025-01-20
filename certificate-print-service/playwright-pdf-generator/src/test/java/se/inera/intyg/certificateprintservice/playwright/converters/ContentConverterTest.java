package se.inera.intyg.certificateprintservice.playwright.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Category;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Certificate;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;

class ContentConverterTest {

  private final ContentConverter contentConverter = new ContentConverter();


  private static final String CATEGORY_ID = "categoryId";
  private static final String CATEGORY_NAME = "categoryName";
  private static final String ISSUER_NAME = "issuerName";
  private static final String ISSUING_UNIT = "issuingUnit";
  private static final List<String> ISSUING_UNIT_INFO = List.of("address", "zipCeode", "city");
  private static final String SIGN_DATE = "2025-01-18";
  private static final String DESCRIPTION = "description";
  private static final String CERTIFICATE_NAME = "certificateName";

  private final static List<Category> CATEGORIES = List.of(
      Category.builder()
          .id(CATEGORY_ID)
          .name(CATEGORY_NAME)
          .build());

  private static final Metadata METADATA = Metadata.builder()
      .issuerName(ISSUER_NAME)
      .issuingUnit(ISSUING_UNIT)
      .issuingUnitInfo(ISSUING_UNIT_INFO)
      .signingDate(SIGN_DATE)
      .description(DESCRIPTION)
      .name(CERTIFICATE_NAME)
      .build();

  private final static Certificate CERTIFICATE = Certificate.builder()
      .categories(CATEGORIES)
      .metadata(METADATA)
      .build();

  @Test
  void shouldSetCategories() {
    final var response = contentConverter.convert(CERTIFICATE);
    assertEquals(CATEGORIES, response.getCategories());
  }

  @Test
  void shouldSetIssuerName() {
    final var response = contentConverter.convert(CERTIFICATE);
    assertEquals(ISSUER_NAME, response.getIssuerName());
  }

  @Test
  void shouldSetIssuingUnit() {
    final var response = contentConverter.convert(CERTIFICATE);
    assertEquals(ISSUING_UNIT, response.getIssuingUnit());
  }

  @Test
  void shouldSetIssuingUnitInfo() {
    final var response = contentConverter.convert(CERTIFICATE);
    assertEquals(ISSUING_UNIT_INFO, response.getIssuingUnitInfo());
  }

  @Test
  void shouldSetSignDate() {
    final var response = contentConverter.convert(CERTIFICATE);
    assertEquals(SIGN_DATE, response.getSignDate());
  }

  @Test
  void shouldSetDescription() {
    final var response = contentConverter.convert(CERTIFICATE);
    assertEquals(DESCRIPTION, response.getDescription());
  }

  @Test
  void shouldSetName() {
    final var response = contentConverter.convert(CERTIFICATE);
    assertEquals(CERTIFICATE_NAME, response.getCertificateName());
  }

  @Test
  void shouldSetIsDraft() {
    final var response = contentConverter.convert(CERTIFICATE);
    assertFalse(response.isDraft());
  }

}
