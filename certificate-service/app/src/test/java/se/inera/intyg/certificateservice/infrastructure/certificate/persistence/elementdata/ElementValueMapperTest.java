package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValue;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueIssuingUnit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationIssuingUnit;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class ElementValueMapperTest {

  private static final String ID = "id";

  @Test
  void shallThrowExceptionIfUnknownDomainData() {
    final var unknownValue = ElementData.builder()
        .id(new ElementId(ID))
        .value(
            new ElementValue() {
            }
        )
        .build();

    assertThrows(IllegalStateException.class,
        () -> ElementDataMapper.toMapped(unknownValue)
    );
  }

  @Test
  void shallThrowExceptionIfUnknownMappedData() {
    final var unknownValue = MappedElementData.builder()
        .id(ID)
        .value(
            new MappedElementValue() {
            }
        )
        .build();

    assertThrows(IllegalStateException.class,
        () -> ElementDataMapper.toDomain(unknownValue)
    );
  }

  @Nested
  class Date {

    private static final LocalDate DATE = LocalDate.now(ZoneId.systemDefault());
    private static final MappedElementData DATE_MAPPED_DATA = MappedElementData.builder()
        .id(ID)
        .value(
            MappedElementValueDate.builder()
                .date(DATE)
                .build()
        )
        .build();

    private static final ElementData DATE_DATA = ElementData.builder()
        .id(new ElementId(ID))
        .value(
            ElementValueDate.builder()
                .date(DATE)
                .build()
        )
        .build();

    @Test
    void shallMapToDomain() {
      final var actualData = ElementDataMapper.toDomain(DATE_MAPPED_DATA);
      assertEquals(DATE_DATA, actualData);
    }

    @Test
    void shallMapToMapped() {
      final var actualData = ElementDataMapper.toMapped(DATE_DATA);
      assertEquals(DATE_MAPPED_DATA, actualData);
    }
  }

  @Nested
  class IssuingUnit {

    ;
    private static final String ADDRESS = "address";
    private static final String ZIP_CODE = "zipCode";
    private static final String CITY = "city";
    private static final String PHONE_NUMBER = "phoneNumber";
    private static final MappedElementData ISSUING_UNIT_MAPPED_DATA = MappedElementData.builder()
        .id(ElementConfigurationIssuingUnit.ISSUING_UNIT.id())
        .value(
            MappedElementValueIssuingUnit.builder()
                .address(ADDRESS)
                .zipCode(ZIP_CODE)
                .city(CITY)
                .phoneNumber(PHONE_NUMBER)
                .build()
        )
        .build();
    private static final ElementData ISSUING_UNIT_DATA = ElementData.builder()
        .id(ElementConfigurationIssuingUnit.ISSUING_UNIT)
        .value(
            ElementValueIssuingUnit.builder()
                .address(ADDRESS)
                .zipCode(ZIP_CODE)
                .city(CITY)
                .phoneNumber(PHONE_NUMBER)
                .build()
        )
        .build();

    @Test
    void shallMapToDomain() {
      final var actualData = ElementDataMapper.toDomain(ISSUING_UNIT_MAPPED_DATA);
      assertEquals(ISSUING_UNIT_DATA, actualData);
    }

    @Test
    void shallMapToMapped() {
      final var actualData = ElementDataMapper.toMapped(ISSUING_UNIT_DATA);
      assertEquals(ISSUING_UNIT_MAPPED_DATA, actualData);
    }
  }
}