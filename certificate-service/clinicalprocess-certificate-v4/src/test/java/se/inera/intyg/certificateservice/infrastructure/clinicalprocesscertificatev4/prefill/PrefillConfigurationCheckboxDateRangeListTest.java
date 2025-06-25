package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import java.time.Period;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementMessage;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.MessageLevel;

class PrefillConfigurationCheckboxDateRangeListTest {

  private static final ElementId ELEMENT_ID = new ElementId("1");
  private static final FieldId FIELD_ID = new FieldId("F2");

  private static final ElementSpecification SPECIFICATION = ElementSpecification.builder()
      .id(ELEMENT_ID)
      .configuration(
          ElementConfigurationCheckboxDateRangeList.builder()
              .id(FIELD_ID)
              .name("TEXT")
              .label("LABEL")
              .message(ElementMessage.builder()
                  .content("CONTENT")
                  .includedForStatuses(List.of())
                  .level(MessageLevel.INFO)
                  .build()
              )
              .hideWorkingHours(false)
              .dateRanges(List.of())
              .min(Period.of(2020, 1, 1))
              .max(Period.of(2025, 12, 31))
              .build()
      )
      .build();

  private static final ElementData EXPECTED_ELEMENT_DATA = ElementData.builder()
      .id(ELEMENT_ID)
      .value(
          ElementValueDateRangeList.builder()
              .dateRangeListId(FIELD_ID)
              .dateRangeList(List.of())
              .build()
      )
      .build();
}