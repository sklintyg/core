package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.application.certificate.service.converter.ComplementElementVisibilityCheckboxMultipleCode;
import se.inera.intyg.certificateservice.application.certificate.service.converter.HandleComplementElementVisibilityService;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementVisibilityConfigurationsCheckboxMultipleCode;

@ExtendWith(MockitoExtension.class)
class HandleComplementElementVisibilityServiceTest {

  private static final ElementId COMPLEMENT_ID = new ElementId("complementId");
  private static final Map<String, CertificateDataElement> DATA_ELEMENT_MAP = Collections.emptyMap();
  private static final ElementVisibilityConfigurationsCheckboxMultipleCode VISIBILITY_CONFIGURATION = ElementVisibilityConfigurationsCheckboxMultipleCode.builder()
      .build();
  private static final ElementId MISSING_COMPLEMENT_ID = new ElementId("missingComplementId");
  @Mock
  ComplementElementVisibilityCheckboxMultipleCode complementElementVisibilityCheckboxMultipleCode;

  HandleComplementElementVisibilityService handleComplementElementVisibilityService;

  @BeforeEach
  void setUp() {
    handleComplementElementVisibilityService = new HandleComplementElementVisibilityService(
        List.of(complementElementVisibilityCheckboxMultipleCode)
    );
  }

  @Test
  void shouldNotProceedIfShouldValidateIsNull() {
    final var certificate = MedicalCertificate.builder()
        .certificateModel(
            CertificateModel.builder()
                .elementSpecifications(
                    List.of(
                        ElementSpecification.builder()
                            .id(COMPLEMENT_ID)
                            .build()
                    )
                )
                .build()
        )
        .build();

    handleComplementElementVisibilityService.handle(COMPLEMENT_ID, DATA_ELEMENT_MAP, certificate,
        VISIBILITY_CONFIGURATION);

    verifyNoInteractions(complementElementVisibilityCheckboxMultipleCode);
  }

  @Test
  void shouldNotProceedIfShouldValidateReturnsTrue() {
    final var certificate = MedicalCertificate.builder()
        .elementData(
            List.of(
                ElementData.builder()
                    .id(COMPLEMENT_ID)
                    .build()
            )
        )
        .certificateModel(
            CertificateModel.builder()
                .elementSpecifications(
                    List.of(
                        ElementSpecification.builder()
                            .id(COMPLEMENT_ID)
                            .shouldValidate(
                                elementData -> elementData.getFirst().id().equals(COMPLEMENT_ID)
                            )
                            .build()
                    )
                )
                .build()
        )
        .build();

    handleComplementElementVisibilityService.handle(COMPLEMENT_ID, DATA_ELEMENT_MAP, certificate,
        VISIBILITY_CONFIGURATION);

    verifyNoInteractions(complementElementVisibilityCheckboxMultipleCode);
  }

  @Test
  void shouldThrowIfNoVisibilityServiceIsFoundForType() {
    final var certificate = MedicalCertificate.builder()
        .elementData(
            List.of(
                ElementData.builder()
                    .id(MISSING_COMPLEMENT_ID)
                    .build()
            )
        )
        .certificateModel(
            CertificateModel.builder()
                .elementSpecifications(
                    List.of(
                        ElementSpecification.builder()
                            .id(COMPLEMENT_ID)
                            .shouldValidate(
                                elementData -> elementData.getFirst().id().equals(COMPLEMENT_ID)
                            )
                            .build()
                    )
                )
                .build()
        )
        .build();

    when(complementElementVisibilityCheckboxMultipleCode.supports(
        VISIBILITY_CONFIGURATION)).thenReturn(false);

    assertThrows(
        IllegalStateException.class, () -> handleComplementElementVisibilityService.handle(
            COMPLEMENT_ID,
            DATA_ELEMENT_MAP,
            certificate,
            VISIBILITY_CONFIGURATION)
    );
  }

  @Test
  void shouldHandleVisibilityIfVisibilityServiceIsFoundForType() {
    final var certificate = MedicalCertificate.builder()
        .elementData(
            List.of(
                ElementData.builder()
                    .id(MISSING_COMPLEMENT_ID)
                    .build()
            )
        )
        .certificateModel(
            CertificateModel.builder()
                .elementSpecifications(
                    List.of(
                        ElementSpecification.builder()
                            .id(COMPLEMENT_ID)
                            .shouldValidate(
                                elementData -> elementData.getFirst().id().equals(COMPLEMENT_ID)
                            )
                            .build()
                    )
                )
                .build()
        )
        .build();

    when(complementElementVisibilityCheckboxMultipleCode.supports(
        VISIBILITY_CONFIGURATION)).thenReturn(true);

    handleComplementElementVisibilityService.handle(COMPLEMENT_ID, DATA_ELEMENT_MAP, certificate,
        VISIBILITY_CONFIGURATION);

    verify(complementElementVisibilityCheckboxMultipleCode).handle(DATA_ELEMENT_MAP,
        VISIBILITY_CONFIGURATION);
  }
}