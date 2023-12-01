package se.inera.intyg.intygproxyservice.integration.fakehsa.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareProvider;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareUnit;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCredentialInformation;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCredentialInformation.ParsedCommission;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson.ParsedPaTitle;

class CommissonListConverterTest {

  private CommissonListConverter commissonListConverter = new CommissonListConverter();
  private static final String CARE_PROVIDER_ID = "careProviderId";
  private static final String CARE_PROVIDER_NAME = "careProviderName";
  private static final String CREDENTIAL_INFORMATION_ID = "credentialInformationId";
  private static final String CREDENTIAL_INFORMATION_GIVEN_NAME = "credentialInformationGivenName";
  private static final String CREDENTIAL_INFORMATION_COMMISSION_PURPOSE = "credentialInformationCommissionPurpose";
  private static final String CARE_UNIT_ID = "careUnitId";
  private static final String CARE_UNIT_NAME = "careUnitName";
  private static final String CARE_UNIT_ORG_NUMBER = "careUnitOrgNumber";
  private static final String HSA_PERSON_ID = "hsaPersonId";
  private static final String HSA_PERSON_PRESCRIPTION_CODE = "hsaPersonPrescriptionCode";
  private static final String HSA_PERSON_TITLE_CODE = "hsaPersonTitleCode";
  private static final String HSA_PERSON_SYSTEM_ROLE = "systemRole";
  private static final Map<String, ParsedCareProvider> CARE_PROVIDER_MAP = Map.of(
      CARE_PROVIDER_ID,
      ParsedCareProvider.builder()
          .id(CARE_PROVIDER_ID)
          .name(CARE_PROVIDER_NAME)
          .build()
  );
  private static final Map<String, ParsedCareUnit> CARE_UNIT_MAP = Map.of(
      CARE_UNIT_ID,
      ParsedCareUnit.builder()
          .id(CARE_UNIT_ID)
          .name(CARE_UNIT_NAME)
          .careProviderHsaId(CARE_PROVIDER_ID)
          .start(LocalDateTime.now())
          .end(LocalDateTime.now())
          .healthCareProviderOrgno(CARE_UNIT_ORG_NUMBER)
          .build()
  );

  private static final ParsedHsaPerson PARSED_HSA_PERSON = ParsedHsaPerson.builder()
      .hsaId(HSA_PERSON_ID)
      .personalPrescriptionCode(HSA_PERSON_PRESCRIPTION_CODE)
      .parsedPaTitle(
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
          .hsaId(CREDENTIAL_INFORMATION_ID)
          .givenName(CREDENTIAL_INFORMATION_GIVEN_NAME)
          .parsedCommissionList(
              List.of(
                  ParsedCommission.builder()
                      .healthCareUnitHsaId(CARE_UNIT_ID)
                      .commissionPurpose(
                          List.of(
                              CREDENTIAL_INFORMATION_COMMISSION_PURPOSE
                          )
                      )
                      .build()
              )
          )
          .build();

  @Test
  void shouldConvertCommissonHsaId() {
    final var result = commissonListConverter.convert(CARE_UNIT_MAP, CARE_PROVIDER_MAP,
        CREDENTIAL_INFORMATION);

    assertEquals(CREDENTIAL_INFORMATION_ID, result.get(0).getCommissionHsaId());
  }

  @Test
  void shouldConvertCommissonPurpose() {
    final var result = commissonListConverter.convert(CARE_UNIT_MAP, CARE_PROVIDER_MAP,
        CREDENTIAL_INFORMATION);

    assertEquals(CREDENTIAL_INFORMATION_COMMISSION_PURPOSE,
        result.get(0).getCommissionPurpose()
    );
  }

  @Test
  void shouldConvertCommissonName() {
    final var result = commissonListConverter.convert(CARE_UNIT_MAP, CARE_PROVIDER_MAP,
        CREDENTIAL_INFORMATION);

    assertEquals(CREDENTIAL_INFORMATION_GIVEN_NAME,
        result.get(0).getCommissionName()
    );
  }

  @Test
  void shouldConvertHealthCareUnitHsaId() {
    final var result = commissonListConverter.convert(CARE_UNIT_MAP, CARE_PROVIDER_MAP,
        CREDENTIAL_INFORMATION);

    assertEquals(CARE_UNIT_ID,
        result.get(0).getHealthCareUnitHsaId()
    );
  }

  @Test
  void shouldConvertHealthCareUnitName() {
    final var result = commissonListConverter.convert(CARE_UNIT_MAP, CARE_PROVIDER_MAP,
        CREDENTIAL_INFORMATION);

    assertEquals(CARE_UNIT_NAME,
        result.get(0).getHealthCareUnitName()
    );
  }

  @Test
  void shouldConvertHealthCareUnitStartDate() {
    final var result = commissonListConverter.convert(CARE_UNIT_MAP, CARE_PROVIDER_MAP,
        CREDENTIAL_INFORMATION);

    final var expectedStartDate = CARE_UNIT_MAP.get(CARE_UNIT_ID).getStart();
    assertEquals(expectedStartDate,
        result.get(0).getHealthCareUnitStartDate()
    );
  }

  @Test
  void shouldConvertHealthCareUnitEndDate() {
    final var result = commissonListConverter.convert(CARE_UNIT_MAP, CARE_PROVIDER_MAP,
        CREDENTIAL_INFORMATION);

    final var expectedEndDate = CARE_UNIT_MAP.get(CARE_UNIT_ID).getEnd();
    assertEquals(expectedEndDate,
        result.get(0).getHealthCareUnitEndDate()
    );
  }

  @Test
  void shouldConvertHealthCareProviderHsaId() {
    final var result = commissonListConverter.convert(CARE_UNIT_MAP, CARE_PROVIDER_MAP,
        CREDENTIAL_INFORMATION);

    assertEquals(CARE_PROVIDER_ID,
        result.get(0).getHealthCareProviderHsaId()
    );
  }

  @Test
  void shouldConvertHealthCareProviderName() {
    final var result = commissonListConverter.convert(CARE_UNIT_MAP, CARE_PROVIDER_MAP,
        CREDENTIAL_INFORMATION);

    assertEquals(CARE_PROVIDER_NAME,
        result.get(0).getHealthCareProviderName()
    );
  }

  @Test
  void shouldConvertHealthCareProviderOrgNo() {
    final var result = commissonListConverter.convert(CARE_UNIT_MAP, CARE_PROVIDER_MAP,
        CREDENTIAL_INFORMATION);

    assertEquals(CARE_UNIT_ORG_NUMBER,
        result.get(0).getHealthCareProviderOrgNo()
    );
  }

  @Test
  void shouldSetCommissionToEmptyListIfCommissionListIsNull() {
    final var result = commissonListConverter.convert(
        CARE_UNIT_MAP, CARE_PROVIDER_MAP, ParsedCredentialInformation.builder()
            .hsaId(CREDENTIAL_INFORMATION_ID)
            .givenName(CREDENTIAL_INFORMATION_GIVEN_NAME)
            .parsedCommissionList(null)
            .build());
    assertTrue(result.isEmpty());
  }

  @Test
  void shouldSetCommissionToEmptyListCommissonHsaIdNotFoundInUnitMap() {
    final var result = commissonListConverter.convert(CARE_UNIT_MAP, CARE_PROVIDER_MAP,
        CREDENTIAL_INFORMATION);
    assertTrue(result.isEmpty());
  }
}
