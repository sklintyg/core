package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateActionFactory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;

class CertificateModelFactoryFK7804Test {

  @Mock
  private CertificateActionFactory certificateActionFactory;

  @Mock
  private DiagnosisCodeRepository diagnosisCodeRepository;

  private CertificateModelFactoryFK7804 certificateModelFactoryFK7804;

  @BeforeEach
  void setUp() {
    certificateModelFactoryFK7804 = new CertificateModelFactoryFK7804(
        certificateActionFactory,
        diagnosisCodeRepository
    );
    ReflectionTestUtils.setField(certificateModelFactoryFK7804, "activeFrom",
        java.time.LocalDateTime.now(java.time.ZoneId.systemDefault()));
    ReflectionTestUtils.setField(certificateModelFactoryFK7804, "fkLogicalAddress", "L-A");
  }

  @Nested
  class CertificateSpecifications {

    @ParameterizedTest
    @ValueSource(strings = {
        "KAT_1", "KAT_2", "KAT_3", "KAT_4", "KAT_5", "KAT_6", "KAT_7", "KAT_8", "KAT_9", "KAT_10",
        "KAT_11", "KAT_12", "UNIT_CONTACT_INFORMATION", "1"
    })
    void shallIncludeCategories(String id) {
      final var elementId = new ElementId(id);
      final var certificateModel = certificateModelFactoryFK7804.create();

      assertTrue(certificateModel.elementSpecificationExists(elementId),
          "Expected elementId: '%s' to exist in elementSpecifications".formatted(elementId));
    }
  }
}