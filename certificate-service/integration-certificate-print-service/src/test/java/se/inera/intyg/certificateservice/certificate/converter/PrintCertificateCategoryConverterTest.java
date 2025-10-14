package se.inera.intyg.certificateservice.certificate.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7210CertificateBuilder;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7210.elements.QuestionBeraknatFodelsedatum.QUESTION_BERAKNAT_FODELSEDATUM_ID;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.certificate.dto.PrintCertificateQuestionDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.service.PdfGeneratorOptions;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@ExtendWith(MockitoExtension.class)
class PrintCertificateCategoryConverterTest {

  @InjectMocks
  private PrintCertificateCategoryConverter printCertificateCategoryConverter;

  @Mock
  private PrintCertificateQuestionConverter printCertificateQuestionConverter;

  private static final PrintCertificateQuestionDTO PRINT_CERTIFICATE_QUESTION_DTO =
      PrintCertificateQuestionDTO.builder().build();
  private static final boolean IS_CITIZEN_FORMAT = true;


  public static final String CATEGORY_NAME = "Beräknat födelsedatum kategori";
  public static final ElementSpecification KAT_1 = ElementSpecification.builder()
      .id(new ElementId("KAT_1"))
      .configuration(
          ElementConfigurationCategory.builder()
              .name(CATEGORY_NAME)
              .build()
      )
      .children(List.of(
          ElementSpecification.builder()
              .id(QUESTION_BERAKNAT_FODELSEDATUM_ID)
              .build())
      )
      .build();
  private static final Certificate CERTIFICATE = fk7210CertificateBuilder()
      .elementData(List.of(ElementData.builder()
          .id(QUESTION_BERAKNAT_FODELSEDATUM_ID)
          .value(ElementValueDate.builder()
              .dateId(new FieldId("54.1"))
              .date(LocalDate.now())
              .build()
          )
          .build())
      )
      .certificateModel(CertificateModel.builder()
          .elementSpecifications(List.of(KAT_1)
          )
          .build())
      .build();
  private static final List<ElementId> HIDDEN = List.of(new ElementId("1"));
  private static final PdfGeneratorOptions OPTIONS = PdfGeneratorOptions.builder()
      .additionalInfoText("Text")
      .citizenFormat(true)
      .hiddenElements(List.of())
      .build();
  private static final PdfGeneratorOptions OPTIONS_WITH_ELEMENT = PdfGeneratorOptions.builder()
      .additionalInfoText("Text")
      .citizenFormat(true)
      .hiddenElements(List.of(QUESTION_BERAKNAT_FODELSEDATUM_ID))
      .build();
  private static final PdfGeneratorOptions OPTIONS_WITH_HIDDEN = PdfGeneratorOptions.builder()
      .additionalInfoText("Text")
      .citizenFormat(true)
      .hiddenElements(HIDDEN)
      .build();


  @Test
  void shouldSetName() {
    final var response = printCertificateCategoryConverter.convert(CERTIFICATE, KAT_1, OPTIONS);

    assertEquals(
        CERTIFICATE.certificateModel().elementSpecifications().getFirst().configuration().name(),
        response.getName());
  }

  @Test
  void shouldSetId() {
    final var response = printCertificateCategoryConverter.convert(CERTIFICATE, KAT_1, OPTIONS);
    assertEquals(CERTIFICATE.certificateModel().elementSpecifications().getFirst().id().id(),
        response.getId());
  }

  @Test
  void shouldNotConvertChildrenIfPartOfHiddenElementIds() {
    final var response = printCertificateCategoryConverter.convert(CERTIFICATE, KAT_1,
        OPTIONS_WITH_ELEMENT);
    assertEquals(List.of(), response.getQuestions());
  }

  @Nested
  class ChildrenTest {

    @BeforeEach
    void setUp() {
      when(printCertificateQuestionConverter.convert(
          CERTIFICATE.certificateModel().elementSpecifications().getFirst().children().getFirst(),
          CERTIFICATE, OPTIONS_WITH_HIDDEN
      )).thenReturn(Optional.of(PRINT_CERTIFICATE_QUESTION_DTO));
    }

    @Test
    void shouldSetChildren() {
      final var response = printCertificateCategoryConverter.convert(CERTIFICATE, KAT_1,
          OPTIONS_WITH_HIDDEN);
      assertEquals(List.of(PRINT_CERTIFICATE_QUESTION_DTO), response.getQuestions());
    }
  }
}
