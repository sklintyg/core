package se.inera.intyg.intygproxyservice.integration.fakehsa.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.HsaSystemRole;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareProvider;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCredentialInformation;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson.ParsedPaTitle;

@ExtendWith(MockitoExtension.class)
class CredentialInformationConverterTest {

  @Mock
  private CommissonListConverter converter;
  @InjectMocks
  private CredentialInformationConverter credentialInformationConverter;
  private static final String CARE_PROVIDER_ID = "careProviderId";
  private static final String CARE_UNIT_ID = "careUnitId";
  private static final String HSA_PERSON_ID = "hsaPersonId";
  private static final String HSA_PERSON_PRESCRIPTION_CODE = "hsaPersonPrescriptionCode";
  private static final String HSA_PERSON_TITLE_CODE = "hsaPersonTitleCode";
  private static final String HSA_PERSON_SYSTEM_ROLE = "systemRole";
  private static final Map<String, ParsedCareProvider> CARE_PROVIDER_MAP = Map.of(
      CARE_PROVIDER_ID,
      ParsedCareProvider.builder()
          .build()
  );
  private static final Map<String, ParsedCareUnit> CARE_UNIT_MAP = Map.of(
      CARE_UNIT_ID,
      ParsedCareUnit.builder()
          .build()
  );

  private static final ParsedHsaPerson PARSED_HSA_PERSON = ParsedHsaPerson.builder()
      .hsaId(HSA_PERSON_ID)
      .personalPrescriptionCode(HSA_PERSON_PRESCRIPTION_CODE)
      .paTitle(
          List.of(
              ParsedPaTitle.builder()
                  .titleCode(HSA_PERSON_TITLE_CODE)
                  .build()
          )
      )
      .systemRoles(
          List.of(
              HSA_PERSON_SYSTEM_ROLE
          )
      )
      .build();
  private static final ParsedCredentialInformation CREDENTIAL_INFORMATION =
      ParsedCredentialInformation.builder()
          .build();


  @Test
  void shouldConvertHsaIdFromPerson() {
    final var result = credentialInformationConverter.convert(CREDENTIAL_INFORMATION,
        PARSED_HSA_PERSON, CARE_PROVIDER_MAP, CARE_UNIT_MAP);

    assertEquals(HSA_PERSON_ID, result.getPersonHsaId());
  }

  @Test
  void shouldConvertPersonalPrescriptionCode() {
    final var result = credentialInformationConverter.convert(CREDENTIAL_INFORMATION,
        PARSED_HSA_PERSON, CARE_PROVIDER_MAP, CARE_UNIT_MAP);

    assertEquals(HSA_PERSON_PRESCRIPTION_CODE, result.getPersonalPrescriptionCode());
  }

  @Test
  void shouldConvertPaTitle() {
    final var result = credentialInformationConverter.convert(CREDENTIAL_INFORMATION,
        PARSED_HSA_PERSON, CARE_PROVIDER_MAP, CARE_UNIT_MAP);

    assertEquals(List.of(HSA_PERSON_TITLE_CODE), result.getPaTitleCode());
  }

  @Test
  void shouldConvertSystemRoles() {
    final var result = credentialInformationConverter.convert(CREDENTIAL_INFORMATION,
        PARSED_HSA_PERSON, CARE_PROVIDER_MAP, CARE_UNIT_MAP);

    assertEquals(
        List.of(
            HsaSystemRole.builder()
                .role(HSA_PERSON_SYSTEM_ROLE)
                .build()
        ),
        result.getHsaSystemRole());
  }
}
