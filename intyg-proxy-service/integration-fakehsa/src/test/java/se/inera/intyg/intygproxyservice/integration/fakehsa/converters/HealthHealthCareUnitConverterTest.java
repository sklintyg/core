package se.inera.intyg.intygproxyservice.integration.fakehsa.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedSubUnit;

class HealthHealthCareUnitConverterTest {

  private static final String HSA_ID = "hsaId";
  private static final String UNIT_NAME = "unitName";
  private static final String POSTAL_ADDRESS = "post1";
  private static final String POSTAL_TOWN = "postalTown";
  private static final String POSTAL_CODE = "postalCode";
  private static final String MUNICIPALITY_CODE = "municipalityCode";
  private static final String MAIL = "mail";
  private static final String COUNTRY_CODE = "countryCode";
  private UnitConverter unitConverter = new UnitConverter();

  @Nested
  class ParsedUnitTest {

    @Test
    void shouldConvertId() {
      final var result = unitConverter.convert(getParsedUnit()).getUnitHsaId();
      assertEquals(HSA_ID, result);
    }

    @Test
    void shouldConvertName() {
      final var result = unitConverter.convert(getParsedUnit()).getUnitName();
      assertEquals(UNIT_NAME, result);
    }

    @Test
    void shouldConvertMail() {
      final var result = unitConverter.convert(getParsedUnit()).getMail();
      assertEquals(MAIL, result);
    }

    @Test
    void shouldConvertPostalCode() {
      final var result = unitConverter.convert(getParsedUnit()).getPostalCode();
      assertEquals(POSTAL_CODE, result);
    }

    @Test
    void shouldConvertPostalAddress() {
      final var result = unitConverter.convert(getParsedUnit()).getPostalAddress();
      assertEquals(List.of(POSTAL_ADDRESS, POSTAL_TOWN), result);
    }

    @Test
    void shouldConvertCountryCode() {
      final var result = unitConverter.convert(getParsedUnit()).getCountyCode();
      assertEquals(COUNTRY_CODE, result);
    }

    @Test
    void shouldConvertMunicipalityCode() {
      final var result = unitConverter.convert(getParsedUnit()).getMunicipalityCode();
      assertEquals(MUNICIPALITY_CODE, result);
    }
  }

  @Nested
  class ParsedSubUnitTest {

    @Test
    void shouldConvertId() {
      final var result = unitConverter.convert(getParsedSubUnit()).getUnitHsaId();
      assertEquals(HSA_ID, result);
    }

    @Test
    void shouldConvertName() {
      final var result = unitConverter.convert(getParsedSubUnit()).getUnitName();
      assertEquals(UNIT_NAME, result);
    }

    @Test
    void shouldConvertMail() {
      final var result = unitConverter.convert(getParsedSubUnit()).getMail();
      assertEquals(MAIL, result);
    }

    @Test
    void shouldConvertPostalCode() {
      final var result = unitConverter.convert(getParsedSubUnit()).getPostalCode();
      assertEquals(POSTAL_CODE, result);
    }

    @Test
    void shouldConvertPostalAddress() {
      final var result = unitConverter.convert(getParsedSubUnit()).getPostalAddress();
      assertEquals(List.of(POSTAL_ADDRESS, POSTAL_TOWN), result);
    }

    @Test
    void shouldConvertCountryCode() {
      final var result = unitConverter.convert(getParsedSubUnit()).getCountyCode();
      assertEquals(COUNTRY_CODE, result);
    }

    @Test
    void shouldConvertMunicipalityCode() {
      final var result = unitConverter.convert(getParsedSubUnit()).getMunicipalityCode();
      assertEquals(MUNICIPALITY_CODE, result);
    }
  }

  private ParsedSubUnit getParsedSubUnit() {
    return ParsedSubUnit.builder()
        .id(HSA_ID)
        .name(UNIT_NAME)
        .postalAddress(POSTAL_ADDRESS)
        .postalTown(POSTAL_TOWN)
        .postalCode(POSTAL_CODE)
        .countyCode(COUNTRY_CODE)
        .mail(MAIL)
        .municipalityCode(MUNICIPALITY_CODE)
        .build();
  }

  private ParsedCareUnit getParsedUnit() {
    return ParsedCareUnit.builder()
        .id(HSA_ID)
        .name(UNIT_NAME)
        .postalAddress(POSTAL_ADDRESS)
        .postalTown(POSTAL_TOWN)
        .postalCode(POSTAL_CODE)
        .countyCode(COUNTRY_CODE)
        .mail(MAIL)
        .municipalityCode(MUNICIPALITY_CODE)
        .build();
  }
}
