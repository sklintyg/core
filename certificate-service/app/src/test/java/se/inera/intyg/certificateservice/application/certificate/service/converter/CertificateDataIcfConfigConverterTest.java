package se.inera.intyg.certificateservice.application.certificate.service.converter;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7804_CERTIFICATE;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigIcf;
import se.inera.intyg.certificateservice.application.certificate.dto.config.IcfCodesPropertyTypeDTO;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationIcf;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.IcfCodesPropertyType;

class CertificateDataIcfConfigConverterTest {

  CertificateDataIcfConfigConverter certificateDataIcfConfigConverter;

  @BeforeEach
  void setUp() {
    certificateDataIcfConfigConverter = new CertificateDataIcfConfigConverter();
  }

  @Test
  void shouldThrowExceptionIfWrongClass() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(ElementConfigurationDate.builder().build())
        .build();

    assertThrows(IllegalStateException.class,
        () -> certificateDataIcfConfigConverter.convert(elementSpecification,
            FK7804_CERTIFICATE)
    );
  }

  @Test
  void shouldReturnCategoryType() {
    assertEquals(ElementType.ICF, certificateDataIcfConfigConverter.getType());
  }

  @Test
  void shouldReturnConvertedConfig() {
    final var expected = CertificateDataConfigIcf.builder()
        .id("ID")
        .description("DESCRIPTION")
        .modalLabel("MODAL_LABEL")
        .collectionsLabel("COLLECTIONS_LABEL")
        .placeholder("PLACEHOLDER")
        .icfCodesPropertyName(IcfCodesPropertyTypeDTO.FUNKTIONSNEDSATTNINGAR)
        .build();

    final var response = certificateDataIcfConfigConverter.convert(
        ElementSpecification.builder()
            .configuration(
                ElementConfigurationIcf.builder()
                    .id(new FieldId("ID"))
                    .description("DESCRIPTION")
                    .modalLabel("MODAL_LABEL")
                    .collectionsLabel("COLLECTIONS_LABEL")
                    .placeholder("PLACEHOLDER")
                    .icfCodesPropertyName(IcfCodesPropertyType.FUNKTIONSNEDSATTNINGAR)
                    .build()
            ).build(),
        FK7804_CERTIFICATE
    );

    assertEquals(expected, response);
  }

}