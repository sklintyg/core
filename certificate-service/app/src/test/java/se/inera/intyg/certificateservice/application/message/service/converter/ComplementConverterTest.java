package se.inera.intyg.certificateservice.application.message.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.message.model.Complement;
import se.inera.intyg.certificateservice.domain.message.model.Content;

class ComplementConverterTest {

  private static final ElementId ELEMENT_SPECIFICATION_ID = new ElementId("elementSpecificationId");
  private static final ElementId MISSING_ELEMENT_SPECIFICATION_ID = new ElementId(
      "missingElementSpecificationId");
  private static final String QUESTION_TEXT = "questionText";
  private static final Content CONTENT = new Content("content");
  private ComplementConverter complementConverter;
  private Complement complement;
  private Certificate certificate;

  @BeforeEach
  void setUp() {
    complementConverter = new ComplementConverter();
    certificate = MedicalCertificate.builder()
        .certificateModel(
            CertificateModel.builder()
                .elementSpecifications(
                    List.of(
                        ElementSpecification.builder()
                            .id(ELEMENT_SPECIFICATION_ID)
                            .configuration(
                                ElementConfigurationDate.builder()
                                    .name(QUESTION_TEXT)
                                    .build()
                            )
                            .build()
                    )
                )
                .build()
        )
        .build();

    complement = Complement.builder()
        .elementId(ELEMENT_SPECIFICATION_ID)
        .content(CONTENT)
        .build();
  }

  @Test
  void shallIncludeQuestionId() {
    assertEquals(ELEMENT_SPECIFICATION_ID.id(),
        complementConverter.convert(complement, certificate).getQuestionId());
  }

  @Test
  void shallIncludeQuestionText() {
    assertEquals(QUESTION_TEXT,
        complementConverter.convert(complement, certificate).getQuestionText());
  }

  @Test
  void shallIncludeMessage() {
    assertEquals(CONTENT.content(),
        complementConverter.convert(complement, certificate).getMessage());
  }

  @Nested
  class ComplementingQuestionIdThatIsMissing {

    @BeforeEach
    void setUp() {
      complement = Complement.builder()
          .elementId(MISSING_ELEMENT_SPECIFICATION_ID)
          .content(CONTENT)
          .build();
    }

    @Test
    void shallIncludeQuestionId() {
      assertEquals(MISSING_ELEMENT_SPECIFICATION_ID.id(),
          complementConverter.convert(complement, certificate).getQuestionId());
    }

    @Test
    void shallIncludeQuestionText() {
      assertEquals(MISSING_ELEMENT_SPECIFICATION_ID.id(),
          complementConverter.convert(complement, certificate).getQuestionText());
    }

    @Test
    void shallIncludeMessage() {
      assertEquals(CONTENT.content(),
          complementConverter.convert(complement, certificate).getMessage());
    }
  }
}