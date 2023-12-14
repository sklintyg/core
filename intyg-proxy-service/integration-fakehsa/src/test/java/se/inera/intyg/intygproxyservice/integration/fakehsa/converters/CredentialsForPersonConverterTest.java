package se.inera.intyg.intygproxyservice.integration.fakehsa.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialsForPerson;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.HCPSpecialityCodes;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.HealthCareProfessionalLicence;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.Restriction;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson.HealthCareProfessionalLicenceType;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson.Restrictions;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson.Speciality;

@ExtendWith(MockitoExtension.class)
class CredentialsForPersonConverterTest {

  private static final String RESTRICTION_NAME = "restrictionName";
  private static final String RESTRICTION_CODE = "restrictionCode";

  private static final String PERONSAL_IDENTITY_NUMBER = "peronsalIdentityNumber";
  private static final String PERSONAL_PRESCRIPTION_CODE = "personalPrescriptionCode";
  private static final List<String> EDUCATION_CODES = List.of("educationCode1", "educationCode2");
  private static final List<Restrictions> RESTRICTIONS = List.of(
      Restrictions.builder()
          .restrictionName(RESTRICTION_NAME)
          .restrictionCode(RESTRICTION_CODE)
          .build()
  );
  private static final String SPECIALITY_NAME = "specialityName";
  private static final String SPECIALITY_CODE = "specialityCode";
  private static final List<Speciality> SPECIALITIES = List.of(
      Speciality.builder()
          .specialityName(SPECIALITY_NAME)
          .specialityCode(SPECIALITY_CODE)
          .build()
  );
  private static final String HEALTH_CARE_PROFESSIONAL_LICENCE_NAME = "healthCareProfessionalLicenceName";
  private static final String HEALTH_CARE_PROFESSIONAL_LICENCE_CODE = "healthCareProfessionalLicenceCode";
  private static final List<HealthCareProfessionalLicenceType> HEALTH_CARE_PROFESSIONAL_LICENCE_TYPES = List.of(
      HealthCareProfessionalLicenceType.builder()
          .healthCareProfessionalLicenceName(HEALTH_CARE_PROFESSIONAL_LICENCE_NAME)
          .healthCareProfessionalLicenceCode(HEALTH_CARE_PROFESSIONAL_LICENCE_CODE)
          .build()
  );
  private static final ParsedHsaPerson PARSED_HSA_PERSON = ParsedHsaPerson.builder()
      .personalIdentityNumber(PERONSAL_IDENTITY_NUMBER)
      .personalPrescriptionCode(PERSONAL_PRESCRIPTION_CODE)
      .educationCodes(
          EDUCATION_CODES
      )
      .restrictions(
          RESTRICTIONS
      )
      .specialities(
          SPECIALITIES
      )
      .healthCareProfessionalLicenceType(
          HEALTH_CARE_PROFESSIONAL_LICENCE_TYPES
      )
      .build();

  @Mock
  private RestrictionConverter restrictionConverter;
  @Mock
  private SpecialitiesConverter specialitiesConverter;
  @Mock
  private HealthCareProfessionalLicenceTypeConverter licenceTypeConverter;
  @InjectMocks
  private CredentialsForPersonConverter credentialsForPersonConverter;

  @Test
  void shouldReturnEmptyResponseIfHsaPersonIsNull() {
    final var expectedResult = CredentialsForPerson.builder().build();

    final var result = credentialsForPersonConverter.convert(null);

    assertEquals(expectedResult, result);
  }

  @Test
  void shouldConvertPersonalIdentityNumber() {
    final var result = credentialsForPersonConverter.convert(PARSED_HSA_PERSON);

    assertEquals(PERONSAL_IDENTITY_NUMBER, result.getPersonalIdentityNumber());
  }

  @Test
  void shouldConvertPersonalPrescriptionCode() {
    final var result = credentialsForPersonConverter.convert(PARSED_HSA_PERSON);

    assertEquals(PERSONAL_PRESCRIPTION_CODE, result.getPersonalPrescriptionCode());
  }

  @Test
  void shouldConvertEducationCodes() {
    final var result = credentialsForPersonConverter.convert(PARSED_HSA_PERSON);

    assertEquals(EDUCATION_CODES, result.getEducationCode());
  }

  @Test
  void shouldConvertRestrictions() {
    final var restriction = Restriction.builder()
        .restrictionName(RESTRICTION_NAME)
        .restrictionCode(RESTRICTION_CODE)
        .build();

    final var expectedResult = List.of(
        restriction
    );

    when(restrictionConverter.convert(RESTRICTIONS.get(0))).thenReturn(restriction);

    final var result = credentialsForPersonConverter.convert(PARSED_HSA_PERSON);

    assertEquals(expectedResult, result.getRestrictions());
  }

  @Test
  void shouldConvertSpecialities() {
    final var hcpSpecialityCodes = HCPSpecialityCodes.builder()
        .specialityName(SPECIALITY_NAME)
        .specialityCode(SPECIALITY_CODE)
        .build();

    final var expectedResult = List.of(
        hcpSpecialityCodes
    );

    when(specialitiesConverter.convert(SPECIALITIES.get(0))).thenReturn(hcpSpecialityCodes);
    final var result = credentialsForPersonConverter.convert(PARSED_HSA_PERSON);

    assertEquals(expectedResult, result.getHealthCareProfessionalLicenceSpeciality());
  }

  @Test
  void shouldConvertHealthCareProfessionalLicenceType() {
    final var healthCareProfessionalLicence = HealthCareProfessionalLicence.builder()
        .healthCareProfessionalLicenceCode(HEALTH_CARE_PROFESSIONAL_LICENCE_CODE)
        .healthCareProfessionalLicenceName(HEALTH_CARE_PROFESSIONAL_LICENCE_NAME)
        .build();

    final var expectedResult = List.of(
        healthCareProfessionalLicence
    );

    when(licenceTypeConverter.convert(HEALTH_CARE_PROFESSIONAL_LICENCE_TYPES.get(0))).thenReturn(
        healthCareProfessionalLicence);
    final var result = credentialsForPersonConverter.convert(PARSED_HSA_PERSON);

    assertEquals(expectedResult, result.getHealthCareProfessionalLicence());
  }
}
