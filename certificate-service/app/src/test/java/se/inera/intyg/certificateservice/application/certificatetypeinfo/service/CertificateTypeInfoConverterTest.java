package se.inera.intyg.certificateservice.application.certificatetypeinfo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.model.CertificateModel;
import se.inera.intyg.certificateservice.model.CertificateModelId;
import se.inera.intyg.certificateservice.model.CertificateType;

class CertificateTypeInfoConverterTest {

  private static final String TYPE = "TYPE";
  private static final String NAME = "NAME";
  private static final String DESCRIPTION = "DESCRIPTION";
  private CertificateTypeInfoConverter certificateTypeInfoConverter;
  private CertificateModel certificateModel;

  @BeforeEach
  void setUp() {
    certificateTypeInfoConverter = new CertificateTypeInfoConverter();
    certificateModel = CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(new CertificateType(TYPE))
                .build()
        )
        .name(NAME)
        .description(DESCRIPTION)
        .build();
  }

  @Test
  void shallIncludeType() {
    final var result = certificateTypeInfoConverter.convert(certificateModel);
    assertEquals(TYPE, result.getType());
  }

  @Test
  void shallIncludeName() {
    final var result = certificateTypeInfoConverter.convert(certificateModel);
    assertEquals(NAME, result.getName());
  }

  @Test
  void shallIncludeDescription() {
    final var result = certificateTypeInfoConverter.convert(certificateModel);
    assertEquals(DESCRIPTION, result.getDescription());
  }
}