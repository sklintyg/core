package se.inera.intyg.certificateservice.domain.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.DATE_ELEMENT_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataElementDataConstants.DATE_ELEMENT_VALUE_DATE;

import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

public class TestDataElementData {

  public static final ElementData DATE = dateElementDataBuilder().build();

  public static ElementData.ElementDataBuilder dateElementDataBuilder() {
    return ElementData.builder()
        .id(new ElementId(DATE_ELEMENT_ID))
        .value(
            ElementValueDate.builder()
                .date(DATE_ELEMENT_VALUE_DATE)
                .build()
        );
  }
}
