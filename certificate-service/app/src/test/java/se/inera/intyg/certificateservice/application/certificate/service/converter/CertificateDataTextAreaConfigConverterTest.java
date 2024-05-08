package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7211_CERTIFICATE;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationTextArea;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@ExtendWith(MockitoExtension.class)
class CertificateDataTextAreaConfigConverterTest {

  @InjectMocks
  CertificateDataTextAreaConfigConverter certificateDataTextAreaConfigConverter;

  @Test
  void shouldThrowExceptionIfWrongClass() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(ElementConfigurationDate.builder().build())
        .build();

    assertThrows(IllegalStateException.class,
        () -> certificateDataTextAreaConfigConverter.convert(elementSpecification,
            FK7211_CERTIFICATE)
    );
  }

  @Test
  void shouldReturnCategoryType() {
    assertEquals(ElementType.TEXT_AREA, certificateDataTextAreaConfigConverter.getType());
  }

  @Test
  void shouldReturnConvertedConfig() {
    final var expected = CertificateDataConfigTextArea.builder()
        .id("ID")
        .text("NAME")
        .build();

    final var response = certificateDataTextAreaConfigConverter.convert(
        ElementSpecification.builder()
            .configuration(
                ElementConfigurationTextArea.builder()
                    .id(new FieldId("ID"))
                    .name("NAME")
                    .build()
            ).build(),
        FK7211_CERTIFICATE
    );

    assertEquals(expected, response);
  }

}