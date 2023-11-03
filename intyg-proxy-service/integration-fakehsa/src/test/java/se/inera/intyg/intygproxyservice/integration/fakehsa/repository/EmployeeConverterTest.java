package se.inera.intyg.intygproxyservice.integration.fakehsa.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.intygproxyservice.integration.api.employee.HCPSpecialityCode;
import se.inera.intyg.intygproxyservice.integration.api.employee.PersonInformation.PaTitle;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson.ParsedPaTitle;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson.Speciality;

class EmployeeConverterTest {

  private EmployeeConverter employeeConverter;

  private static final String VALUE = "value";

  @BeforeEach
  void setUp() {
    employeeConverter = new EmployeeConverter();
  }

  @Nested
  class ConvertHsaId {

    @Test
    void shouldConvertHsaId() {
      final var hsaPerson = ParsedHsaPerson.builder().hsaId(VALUE).build();
      final var result = employeeConverter.convert(hsaPerson);
      assertEquals(hsaPerson.getHsaId(), result.getPersonInformation().get(0).getPersonHsaId());
    }

    @Test
    void shouldNotConvertHsaId() {
      final var hsaPerson = ParsedHsaPerson.builder().build();
      final var result = employeeConverter.convert(hsaPerson);
      assertNull(result.getPersonInformation().get(0).getPersonHsaId());
    }
  }

  @Nested
  class ConvertAge {

    @Test
    void shouldConvertAge() {
      final var hsaPerson = ParsedHsaPerson.builder().age(VALUE).build();
      final var result = employeeConverter.convert(hsaPerson);
      assertEquals(hsaPerson.getAge(), result.getPersonInformation().get(0).getAge());
    }

    @Test
    void shouldNotConvertAge() {
      final var hsaPerson = ParsedHsaPerson.builder().build();
      final var result = employeeConverter.convert(hsaPerson);
      assertNull(result.getPersonInformation().get(0).getAge());
    }
  }

  @Nested
  class ConvertGender {

    @Test
    void shouldConvertGender() {
      final var hsaPerson = ParsedHsaPerson.builder().gender(VALUE).build();
      final var result = employeeConverter.convert(hsaPerson);
      assertEquals(hsaPerson.getGender(), result.getPersonInformation().get(0).getGender());
    }

    @Test
    void shouldNotConvertGender() {
      final var hsaPerson = ParsedHsaPerson.builder().build();
      final var result = employeeConverter.convert(hsaPerson);
      assertNull(result.getPersonInformation().get(0).getGender());
    }

  }

  @Nested
  class ConvertGivenName {

    @Test
    void shouldConvertGivenName() {
      final var hsaPerson = ParsedHsaPerson.builder().givenName(VALUE).build();
      final var result = employeeConverter.convert(hsaPerson);
      assertEquals(hsaPerson.getGivenName(), result.getPersonInformation().get(0).getGivenName());
    }

    @Test
    void shouldNotConvertGivenName() {
      final var hsaPerson = ParsedHsaPerson.builder().build();
      final var result = employeeConverter.convert(hsaPerson);
      assertNull(result.getPersonInformation().get(0).getGivenName());
    }
  }

  @Nested
  class ConvertFeignedPerson {

    @Test
    void shouldSetFeignedPersonToFalse() {
      final var hsaPerson = ParsedHsaPerson.builder().build();
      final var result = employeeConverter.convert(hsaPerson);
      assertFalse(result.getPersonInformation().get(0).getFeignedPerson());
    }
  }

  @Nested
  class ConvertTitle {

    @Test
    void shouldConvertTitle() {
      final var hsaPerson = ParsedHsaPerson.builder().title(VALUE).build();
      final var result = employeeConverter.convert(hsaPerson);
      assertEquals(hsaPerson.getTitle(), result.getPersonInformation().get(0).getTitle());
    }

    @Test
    void shouldNotConvertTitle() {
      final var hsaPerson = ParsedHsaPerson.builder().build();
      final var result = employeeConverter.convert(hsaPerson);
      assertEquals(hsaPerson.getTitle(), result.getPersonInformation().get(0).getTitle());
    }
  }

  @Nested
  class ConvertHealthCareProfessionalLicence {

    @Test
    void shouldConvertHealthCareProfessionalLicense() {
      final var hsaPerson = ParsedHsaPerson.builder().healthCareProfessionalLicence(List.of(VALUE))
          .build();
      final var result = employeeConverter.convert(hsaPerson);
      assertEquals(hsaPerson.getHealthCareProfessionalLicence(),
          result.getPersonInformation().get(0).getHealthCareProfessionalLicence());
    }

    @Test
    void shouldNotConvertHealthCareProfessionalLicense() {
      final var hsaPerson = ParsedHsaPerson.builder().build();
      final var result = employeeConverter.convert(hsaPerson);
      assertTrue(result.getPersonInformation().get(0).getHealthCareProfessionalLicence().isEmpty());
    }
  }

  @Nested
  class ConvertMiddleAndSurName {

    @Test
    void shouldConvertMiddleAndSurName() {
      final var hsaPerson = ParsedHsaPerson.builder().middleAndSurname(VALUE).build();
      final var result = employeeConverter.convert(hsaPerson);
      assertEquals(hsaPerson.getMiddleAndSurname(),
          result.getPersonInformation().get(0).getMiddleAndSurName());
    }

    @Test
    void shouldNotConvertHealthCareProfessionalLicense() {
      final var hsaPerson = ParsedHsaPerson.builder().build();
      final var result = employeeConverter.convert(hsaPerson);
      assertNull(result.getPersonInformation().get(0).getMiddleAndSurName());
    }
  }

  @Nested
  class ConvertProtectedPerson {

    @Test
    void shouldConvertProtectedPerson() {
      final var hsaPerson = ParsedHsaPerson.builder().protectedPerson(false).build();
      final var result = employeeConverter.convert(hsaPerson);
      assertEquals(hsaPerson.isProtectedPerson(),
          result.getPersonInformation().get(0).getProtectedPerson());
    }

    @Test
    void shouldSetProtectedPersonToFalseByDefault() {
      final var hsaPerson = ParsedHsaPerson.builder().build();
      final var result = employeeConverter.convert(hsaPerson);
      assertFalse(result.getPersonInformation().get(0).getProtectedPerson());
    }
  }

  @Nested
  class ConvertHealthCareProfessionalLicenceSpeciality {

    @Test
    void shouldConvertHealthCareProfessionalLicenceSpeciality() {
      final var hsaPerson = ParsedHsaPerson.builder()
          .specialities(
              List.of(
                  Speciality.builder()
                      .specialityCode(VALUE)
                      .specialityName(VALUE)
                      .build()
              )
          )
          .build();
      final var expectedResult = List.of(
          HCPSpecialityCode.builder().specialityCode(VALUE).specialityName(VALUE).build()
      );
      final var result = employeeConverter.convert(hsaPerson);
      assertEquals(expectedResult,
          result.getPersonInformation().get(0).getHealthCareProfessionalLicenceSpeciality());
    }

    @Test
    void shouldNotConvertHealthCareProfessionalLicenceSpeciality() {
      final var hsaPerson = ParsedHsaPerson.builder().build();
      final var result = employeeConverter.convert(hsaPerson);
      assertTrue(result.getPersonInformation().get(0).getHealthCareProfessionalLicenceSpeciality()
          .isEmpty());
    }
  }

  @Nested
  class ConvertParsedPaTitle {

    @Test
    void shouldConvertPaTitle() {
      final var hsaPerson = ParsedHsaPerson.builder()
          .parsedPaTitle(
              List.of(
                  ParsedPaTitle.builder()
                      .titleName(VALUE)
                      .titleCode(VALUE)
                      .build()
              )
          )
          .build();
      final var expectedResponse = List.of(
          PaTitle.builder()
              .paTitleCode(VALUE)
              .paTitleName(VALUE)
              .build()
      );
      final var result = employeeConverter.convert(hsaPerson);
      assertEquals(expectedResponse, result.getPersonInformation().get(0).getPaTitle());
    }

    @Test
    void shouldNotConvertPaTitle() {
      final var hsaPerson = ParsedHsaPerson.builder().build();
      final var result = employeeConverter.convert(hsaPerson);
      assertTrue(result.getPersonInformation().get(0).getPaTitle().isEmpty());
    }
  }
}
