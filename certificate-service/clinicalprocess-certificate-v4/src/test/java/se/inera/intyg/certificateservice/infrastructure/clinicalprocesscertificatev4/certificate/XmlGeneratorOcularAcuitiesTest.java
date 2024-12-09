package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Correction;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueOcularAcuities;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.OcularAcuity;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

class XmlGeneratorOcularAcuitiesTest {

  private static final String ID = "id";
  private static final ElementSpecification ELEMENT_SPECIFICATION = ElementSpecification.builder()
      .build();
  XmlGeneratorOcularAcuities xmlGeneratorOcularAcuities;

  @BeforeEach
  void setUp() {
    xmlGeneratorOcularAcuities = new XmlGeneratorOcularAcuities();
  }

  @Test
  void shallSupportElementValueOcularAcuities() {
    assertEquals(ElementValueOcularAcuities.class, xmlGeneratorOcularAcuities.supports());
  }

  @Test
  void shallReturnEmptyListIfWrongElementValueType() {
    final var elementDataWithValueText = ElementData.builder()
        .value(ElementValueText.builder().build())
        .build();

    final var actualResult = xmlGeneratorOcularAcuities.generate(elementDataWithValueText,
        ELEMENT_SPECIFICATION);
    assertEquals(Collections.emptyList(), actualResult);
  }

  @Test
  void shallReturnEmptyListIfOcularAcuitiesIsEmpty() {
    final var emptyOcularAcuities = ElementData.builder()
        .value(
            ElementValueOcularAcuities.builder()
                .rightEye(
                    OcularAcuity.builder()
                        .withCorrection(
                            Correction.builder()
                                .build()
                        )
                        .withoutCorrection(
                            Correction.builder()
                                .build()
                        )
                        .build()
                )
                .leftEye(
                    OcularAcuity.builder()
                        .withCorrection(
                            Correction.builder()
                                .build()
                        )
                        .withoutCorrection(
                            Correction.builder()
                                .build()
                        )
                        .build()
                )
                .binocular(
                    OcularAcuity.builder()
                        .withCorrection(
                            Correction.builder()
                                .build()
                        )
                        .withoutCorrection(
                            Correction.builder()
                                .build()
                        )
                        .build()
                )
                .build()
        )
        .build();

    final var actualResult = xmlGeneratorOcularAcuities.generate(emptyOcularAcuities,
        ELEMENT_SPECIFICATION);
    assertEquals(Collections.emptyList(), actualResult);
  }
}