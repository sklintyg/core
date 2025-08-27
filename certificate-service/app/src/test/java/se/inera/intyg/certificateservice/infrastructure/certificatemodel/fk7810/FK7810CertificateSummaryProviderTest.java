package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionDiagnos.DIAGNOSIS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7810.elements.QuestionDiagnos.DIAGNOS_1;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosis;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDiagnosisList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@ExtendWith(MockitoExtension.class)
class FK7810CertificateSummaryProviderTest {

  private static final String DESCRIPTION = "description";

  @InjectMocks
  private FK7810CertificateSummaryProvider provider;

  @Test
  void shallIncludeLabel() {
    assertEquals("Avser diagnos",
        provider.summaryOf(MedicalCertificate.builder().build()).label()
    );
  }

  @Nested
  class ValueTests {


    @Test
    void shallReturnEmptyValueIfCertificateSignedIsNull() {
      final var certificate = MedicalCertificate.builder()
          .signed(null)
          .build();

      assertTrue(
          provider.summaryOf(certificate).value().isEmpty()
      );
    }

    @Test
    void shallReturnEmptyValueIfCertificateCertificateDontContainDiagnosisValue() {
      final var certificate = MedicalCertificate.builder()
          .signed(LocalDateTime.now())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(new ElementId("notDiagnosisId"))
                      .build()
              )
          )
          .build();

      assertTrue(
          provider.summaryOf(certificate).value().isEmpty()
      );
    }

    @Test
    void shallThrowIfCertificateCertificateContainDiagnosisValueButWrongType() {
      final var certificate = MedicalCertificate.builder()
          .signed(LocalDateTime.now())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(DIAGNOSIS_ID)
                      .value(
                          ElementValueText.builder().build()
                      )
                      .build()
              )
          )
          .build();

      assertThrows(IllegalStateException.class, () -> provider.summaryOf(certificate));
    }

    @Test
    void shallReturnValueFromDiagnosisIfCertificateContainDiagnosisValue() {
      final var expectedCode = "expectedCode";
      final var certificate = MedicalCertificate.builder()
          .signed(LocalDateTime.now())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(DIAGNOSIS_ID)
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .id(DIAGNOS_1)
                                          .description(expectedCode)
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build()
              )
          )
          .build();

      assertEquals(expectedCode, provider.summaryOf(certificate).value());
    }

    @Test
    void shallReturnEmptyValueFromDiagnosisIfCertificateContainDiagnosisValueButNotCorrectId() {
      final var certificate = MedicalCertificate.builder()
          .signed(LocalDateTime.now())
          .elementData(
              List.of(
                  ElementData.builder()
                      .id(DIAGNOSIS_ID)
                      .value(
                          ElementValueDiagnosisList.builder()
                              .diagnoses(
                                  List.of(
                                      ElementValueDiagnosis.builder()
                                          .id(new FieldId("wrongId"))
                                          .description(DESCRIPTION)
                                          .build()
                                  )
                              )
                              .build()
                      )
                      .build()
              )
          )
          .build();

      assertTrue(provider.summaryOf(certificate).value().isEmpty());
    }
  }
}