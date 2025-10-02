package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueCode;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueCodeList;
import se.inera.intyg.certificateservice.application.certificate.service.converter.ComplementElementVisibilityCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementVisibilityConfigurationsCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ComplementElementVisibilityCheckboxMultipleCodeTest {

  private static final ElementId PARENT_ELEMENT_ID = new ElementId("parentId");
  private static final FieldId PARENT_FIELD_ID = new FieldId("parentFieldId");
  private static final String ID_1 = "id1";
  ComplementElementVisibilityCheckboxMultipleCode visibilityCheckboxMultipleCode;

  @BeforeEach
  void setUp() {
    visibilityCheckboxMultipleCode = new ComplementElementVisibilityCheckboxMultipleCode();
  }

  @Nested
  class SupportsTests {

    @Test
    void shouldSupportCheckboxMultipleCode() {
      assertTrue(visibilityCheckboxMultipleCode.supports(
          ElementVisibilityConfigurationsCheckboxMultipleCode.builder().build())
      );
    }
  }

  @Nested
  class HandleTests {

    @Test
    void shouldUpdateCertificateElementDataMap() {
      final var expectedAddedCode = CertificateDataValueCode.builder()
          .id(PARENT_FIELD_ID.value())
          .code(PARENT_FIELD_ID.value())
          .build();

      final var visibilityConfigurations = ElementVisibilityConfigurationsCheckboxMultipleCode.builder()
          .elementId(PARENT_ELEMENT_ID)
          .fieldId(PARENT_FIELD_ID)
          .build();

      final var certificateDataElement = CertificateDataElement.builder()
          .value(
              CertificateDataValueCodeList.builder()
                  .list(
                      List.of(
                          CertificateDataValueCode.builder()
                              .id(ID_1)
                              .code(ID_1)
                              .build()
                      )
                  )
                  .build()
          )
          .build();

      var dataElementMap = new HashMap<String, CertificateDataElement>();
      dataElementMap.put(PARENT_ELEMENT_ID.id(), certificateDataElement);

      visibilityCheckboxMultipleCode.handle(dataElementMap, visibilityConfigurations);

      final var dataElement = dataElementMap.get(PARENT_ELEMENT_ID.id());
      final var elementValue = (CertificateDataValueCodeList) dataElement.getValue();

      assertEquals(2, elementValue.getList().size());
      assertEquals(expectedAddedCode, elementValue.getList().getLast());
    }

    @Test
    void shouldNotUpdateCertificateElementDataMapIfCodeAlreadyPresent() {
      final var expectedCodes = List.of(
          CertificateDataValueCode.builder()
              .id(ID_1)
              .code(ID_1)
              .build(),
          CertificateDataValueCode.builder()
              .id(PARENT_FIELD_ID.value())
              .code(PARENT_FIELD_ID.value())
              .build()
      );

      final var visibilityConfigurations = ElementVisibilityConfigurationsCheckboxMultipleCode.builder()
          .elementId(PARENT_ELEMENT_ID)
          .fieldId(PARENT_FIELD_ID)
          .build();

      final var certificateDataElement = CertificateDataElement.builder()
          .value(
              CertificateDataValueCodeList.builder()
                  .list(
                      expectedCodes
                  )
                  .build()
          )
          .build();

      var dataElementMap = new HashMap<String, CertificateDataElement>();
      dataElementMap.put(PARENT_ELEMENT_ID.id(), certificateDataElement);

      visibilityCheckboxMultipleCode.handle(dataElementMap, visibilityConfigurations);

      final var dataElement = dataElementMap.get(PARENT_ELEMENT_ID.id());
      final var elementValue = (CertificateDataValueCodeList) dataElement.getValue();

      assertEquals(2, elementValue.getList().size());
      assertEquals(expectedCodes, elementValue.getList());
    }
  }
}