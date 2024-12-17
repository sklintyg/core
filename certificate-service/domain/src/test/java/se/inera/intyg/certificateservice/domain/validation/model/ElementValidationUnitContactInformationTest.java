package se.inera.intyg.certificateservice.domain.validation.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation.UNIT_CONTACT_INFORMATION;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation.ElementValueUnitContactInformationBuilder;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValidationUnitContactInformationTest {

  private ElementValidationUnitContactInformation elementValidation;
  private ElementValueUnitContactInformationBuilder elementValueUnitContactInformationBuilder;
  private static final Optional<ElementId> EMPTY_CATEGORY = Optional.empty();
  private static final String ADDRESS = "address";
  private static final String ZIP_CODE = "zipCode";
  private static final String CITY = "city";
  private static final String PHONE_NUMBER = "phoneNumber";

  @BeforeEach
  void setUp() {
    elementValidation = ElementValidationUnitContactInformation.builder().build();
    elementValueUnitContactInformationBuilder = ElementValueUnitContactInformation.builder()
        .address(ADDRESS)
        .zipCode(ZIP_CODE)
        .city(CITY)
        .phoneNumber(PHONE_NUMBER);
  }

  @Test
  void shallThrowIfDataIsNull() {
    assertThrows(IllegalArgumentException.class,
        () -> elementValidation.validate(null, EMPTY_CATEGORY, Collections.emptyList())
    );
  }

  @Test
  void shallThrowIllegalArgumentExceptionIfValueIsNull() {
    final Optional<ElementId> categoryId = Optional.empty();
    final var elementData = ElementData.builder()
        .id(UNIT_CONTACT_INFORMATION)
        .build();

    assertThrows(IllegalArgumentException.class,
        () -> elementValidation.validate(elementData, categoryId, Collections.emptyList())
    );
  }

  @Test
  void shallThrowIllegalArgumentExceptionIfValueIsWrongType() {
    final Optional<ElementId> categoryId = Optional.empty();
    final var elementData = ElementData.builder()
        .id(UNIT_CONTACT_INFORMATION)
        .value(ElementValueDate.builder().build())
        .build();

    assertThrows(IllegalArgumentException.class,
        () -> elementValidation.validate(elementData, categoryId, Collections.emptyList())
    );
  }

  @Test
  void shallReturnValidationErrorIfAddressIsNull() {
    final var expectedValidationError = List.of(
        ValidationError.builder()
            .elementId(UNIT_CONTACT_INFORMATION)
            .fieldId(new FieldId("grunddata.skapadAv.vardenhet.postadress"))
            .categoryId(new ElementId("vardenhet"))
            .message(new ErrorMessage("Ange postadress."))
            .build()
    );

    final var elementData = ElementData.builder()
        .id(UNIT_CONTACT_INFORMATION)
        .value(
            elementValueUnitContactInformationBuilder
                .address(null)
                .build()
        )
        .build();

    final var actualResult = elementValidation.validate(elementData, Optional.empty(),
        Collections.emptyList());
    assertEquals(expectedValidationError, actualResult);
  }

  @Test
  void shallReturnValidationErrorIfAddressIsEmpty() {
    final var expectedValidationError = List.of(
        ValidationError.builder()
            .elementId(UNIT_CONTACT_INFORMATION)
            .fieldId(new FieldId("grunddata.skapadAv.vardenhet.postadress"))
            .categoryId(new ElementId("vardenhet"))
            .message(new ErrorMessage("Ange postadress."))
            .build()
    );

    final var elementData = ElementData.builder()
        .id(UNIT_CONTACT_INFORMATION)
        .value(
            elementValueUnitContactInformationBuilder
                .address("")
                .build()
        )
        .build();

    final var actualResult = elementValidation.validate(elementData, Optional.empty(),
        Collections.emptyList());
    assertEquals(expectedValidationError, actualResult);
  }

  @Test
  void shallReturnValidationErrorIfZipCodeIsNull() {
    final var expectedValidationError = List.of(
        ValidationError.builder()
            .elementId(UNIT_CONTACT_INFORMATION)
            .fieldId(new FieldId("grunddata.skapadAv.vardenhet.postnummer"))
            .categoryId(new ElementId("vardenhet"))
            .message(new ErrorMessage("Ange postnummer."))
            .build()
    );

    final var elementData = ElementData.builder()
        .id(UNIT_CONTACT_INFORMATION)
        .value(
            elementValueUnitContactInformationBuilder
                .zipCode(null)
                .build()
        )
        .build();

    final var actualResult = elementValidation.validate(elementData, Optional.empty(),
        Collections.emptyList());
    assertEquals(expectedValidationError, actualResult);
  }

  @Test
  void shallReturnValidationErrorIfZipCodeIsEmpty() {
    final var expectedValidationError = List.of(
        ValidationError.builder()
            .elementId(UNIT_CONTACT_INFORMATION)
            .fieldId(new FieldId("grunddata.skapadAv.vardenhet.postnummer"))
            .categoryId(new ElementId("vardenhet"))
            .message(new ErrorMessage("Ange postnummer."))
            .build()
    );

    final var elementData = ElementData.builder()
        .id(UNIT_CONTACT_INFORMATION)
        .value(
            elementValueUnitContactInformationBuilder
                .zipCode("")
                .build()
        )
        .build();

    final var actualResult = elementValidation.validate(elementData, Optional.empty(),
        Collections.emptyList());
    assertEquals(expectedValidationError, actualResult);
  }

  @Test
  void shallReturnValidationErrorIfCityIsNull() {
    final var expectedValidationError = List.of(
        ValidationError.builder()
            .elementId(UNIT_CONTACT_INFORMATION)
            .fieldId(new FieldId("grunddata.skapadAv.vardenhet.postort"))
            .categoryId(new ElementId("vardenhet"))
            .message(new ErrorMessage("Ange postort."))
            .build()
    );

    final var elementData = ElementData.builder()
        .id(UNIT_CONTACT_INFORMATION)
        .value(
            elementValueUnitContactInformationBuilder
                .city(null)
                .build()
        )
        .build();

    final var actualResult = elementValidation.validate(elementData, Optional.empty(),
        Collections.emptyList());
    assertEquals(expectedValidationError, actualResult);
  }

  @Test
  void shallReturnValidationErrorIfCityIsEmpty() {
    final var expectedValidationError = List.of(
        ValidationError.builder()
            .elementId(UNIT_CONTACT_INFORMATION)
            .fieldId(new FieldId("grunddata.skapadAv.vardenhet.postort"))
            .categoryId(new ElementId("vardenhet"))
            .message(new ErrorMessage("Ange postort."))
            .build()
    );

    final var elementData = ElementData.builder()
        .id(UNIT_CONTACT_INFORMATION)
        .value(
            elementValueUnitContactInformationBuilder
                .city("")
                .build()
        )
        .build();

    final var actualResult = elementValidation.validate(elementData, Optional.empty(),
        Collections.emptyList());
    assertEquals(expectedValidationError, actualResult);
  }

  @Test
  void shallReturnValidationErrorIfPhoneNumberIsNull() {
    final var expectedValidationError = List.of(
        ValidationError.builder()
            .elementId(UNIT_CONTACT_INFORMATION)
            .fieldId(new FieldId("grunddata.skapadAv.vardenhet.telefonnummer"))
            .categoryId(new ElementId("vardenhet"))
            .message(new ErrorMessage("Ange telefonnummer."))
            .build()
    );

    final var elementData = ElementData.builder()
        .id(UNIT_CONTACT_INFORMATION)
        .value(
            elementValueUnitContactInformationBuilder
                .phoneNumber(null)
                .build()
        )
        .build();

    final var actualResult = elementValidation.validate(elementData, Optional.empty(),
        Collections.emptyList());
    assertEquals(expectedValidationError, actualResult);
  }

  @Test
  void shallReturnValidationErrorIfPhoneNumberIsEmpty() {
    final var expectedValidationError = List.of(
        ValidationError.builder()
            .elementId(UNIT_CONTACT_INFORMATION)
            .fieldId(new FieldId("grunddata.skapadAv.vardenhet.telefonnummer"))
            .categoryId(new ElementId("vardenhet"))
            .message(new ErrorMessage("Ange telefonnummer."))
            .build()
    );

    final var elementData = ElementData.builder()
        .id(UNIT_CONTACT_INFORMATION)
        .value(
            elementValueUnitContactInformationBuilder
                .phoneNumber("")
                .build()
        )
        .build();

    final var actualResult = elementValidation.validate(elementData, Optional.empty(),
        Collections.emptyList());
    assertEquals(expectedValidationError, actualResult);
  }

  @Test
  void shallReturnAllValidationErrorWhenMissing() {
    final var expectedValidationError = List.of(
        ValidationError.builder()
            .elementId(UNIT_CONTACT_INFORMATION)
            .fieldId(new FieldId("grunddata.skapadAv.vardenhet.postadress"))
            .categoryId(new ElementId("vardenhet"))
            .message(new ErrorMessage("Ange postadress."))
            .build(),
        ValidationError.builder()
            .elementId(UNIT_CONTACT_INFORMATION)
            .fieldId(new FieldId("grunddata.skapadAv.vardenhet.postnummer"))
            .categoryId(new ElementId("vardenhet"))
            .message(new ErrorMessage("Ange postnummer."))
            .build(),
        ValidationError.builder()
            .elementId(UNIT_CONTACT_INFORMATION)
            .fieldId(new FieldId("grunddata.skapadAv.vardenhet.postort"))
            .categoryId(new ElementId("vardenhet"))
            .message(new ErrorMessage("Ange postort."))
            .build(),
        ValidationError.builder()
            .elementId(UNIT_CONTACT_INFORMATION)
            .fieldId(new FieldId("grunddata.skapadAv.vardenhet.telefonnummer"))
            .categoryId(new ElementId("vardenhet"))
            .message(new ErrorMessage("Ange telefonnummer."))
            .build()
    );

    final var elementData = ElementData.builder()
        .id(UNIT_CONTACT_INFORMATION)
        .value(
            ElementValueUnitContactInformation.builder().build()
        )
        .build();

    final var actualResult = elementValidation.validate(elementData, Optional.empty(),
        Collections.emptyList());
    assertEquals(expectedValidationError, actualResult);
  }

  @Test
  void shallReturnEmptyValidationErrors() {
    final var expectedValidationError = Collections.emptyList();

    final var elementData = ElementData.builder()
        .id(UNIT_CONTACT_INFORMATION)
        .value(
            elementValueUnitContactInformationBuilder.build()
        )
        .build();

    final var actualResult = elementValidation.validate(elementData, Optional.empty(),
        Collections.emptyList());
    assertEquals(expectedValidationError, actualResult);
  }
}