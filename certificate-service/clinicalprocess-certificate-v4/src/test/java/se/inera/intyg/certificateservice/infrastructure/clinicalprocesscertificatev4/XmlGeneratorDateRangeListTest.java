package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;

@ExtendWith(MockitoExtension.class)
class XmlGeneratorDateRangeListTest {

  private static final String QUESTION_ID = "QUESTION_ID";
  private static final String VALUE_ID = "ANSWER_ID";
  private static final String RANGE_ID = "RANGE_ID";
  private static final LocalDate FROM = LocalDate.now().minusDays(1);
  private static final LocalDate TO = LocalDate.now();
  private static final LocalDate DATE = LocalDate.now();

  @InjectMocks
  private XmlGeneratorDateRangeList xmlGenerator;

  @Test
  void shouldMapOneRange() {
    final var data = ElementData.builder()
        .id(new ElementId(QUESTION_ID))
        .value(ElementValueDateRangeList.builder()
            .dateRangeListId(new FieldId(VALUE_ID))
            .dateRangeList(
                List.of(
                    DateRange.builder()
                        .dateRangeId(new FieldId(RANGE_ID))
                        .from(FROM)
                        .to(TO)
                        .build()
                )
            )
            .build())
        .build();

    final var expectedData = new Svar();

    final var response = xmlGenerator.generate(data);

    //TODO: FIX TEST
    //assertEquals(List.of(expectedData), response);
  }

  @Test
  void shouldMapEmptyIfNoValue() {
    final var data = ElementData.builder()
        .value(ElementValueDateRangeList.builder()
            .dateRangeListId(new FieldId(VALUE_ID))
            .build()
        )
        .id(new ElementId(QUESTION_ID))
        .build();

    final var response = xmlGenerator.generate(data);

    assertTrue(response.isEmpty());
  }

  @Test
  void shouldMapEmptyIfValueIsNotDateRangeList() {
    final var data = ElementData.builder()
        .value(ElementValueUnitContactInformation.builder()
            .build()
        )
        .id(new ElementId(QUESTION_ID))
        .build();

    final var response = xmlGenerator.generate(data);

    assertTrue(response.isEmpty());
  }
}