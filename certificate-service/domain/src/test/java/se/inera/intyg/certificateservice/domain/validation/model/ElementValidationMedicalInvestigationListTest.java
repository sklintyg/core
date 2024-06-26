package se.inera.intyg.certificateservice.domain.validation.model;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;

@ExtendWith(MockitoExtension.class)
class ElementValidationMedicalInvestigationListTest {

  private static final ElementData ELEMENT_DATA = ElementData.builder().build();

  @InjectMocks
  private ElementValidationMedicalInvestigationList elementValidationMedicalInvestigationList;

//  @Test
//  void shouldThrowIllegalArgumentExceptionIfElementDataIsNull() {
//    assertThrows(IllegalArgumentException.class,
//        () -> elementValidationMedicalInvestigationList.validate()
//    );
//  }
}
