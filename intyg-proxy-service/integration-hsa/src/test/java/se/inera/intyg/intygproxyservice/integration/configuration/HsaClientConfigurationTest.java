package se.inera.intyg.intygproxyservice.integration.configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.intygproxyservice.integration.common.WebServiceClientFactory;
import se.riv.infrastructure.directory.authorizationmanagement.gethospcredentialsforperson.v1.rivtabp21.GetHospCredentialsForPersonResponderInterface;
import se.riv.infrastructure.directory.authorizationmanagement.handlehospcertificationperson.v1.rivtabp21.HandleHospCertificationPersonResponderInterface;
import se.riv.infrastructure.directory.authorizationmanagement.gethosplastupdate.v1.rivtabp21.GetHospLastUpdateResponderInterface;
import se.riv.infrastructure.directory.employee.getemployeeincludingprotectedperson.v3.rivtabp21.GetEmployeeIncludingProtectedPersonResponderInterface;
import se.riv.infrastructure.directory.organization.gethealthcareunit.v2.rivtabp21.GetHealthCareUnitResponderInterface;
import se.riv.infrastructure.directory.organization.gethealthcareunitmembers.v2.rivtabp21.GetHealthCareUnitMembersResponderInterface;

@ExtendWith(MockitoExtension.class)
class HsaClientConfigurationTest {

  public static final String GET_EMPLOYEE_INCLUDING_PROTECTED_PERSON_ENDPOINT = "GET_EMPLOYEE_INCLUDING_PROTECTED_PERSON_ENDPOINT";
  public static final String GET_HEALTH_CARE_UNIT_ENDPOINT = "GET_HEALTH_CARE_UNIT_ENDPOINT";
  public static final String GET_HEALTH_CARE_UNIT_MEMBERS_ENDPOINT = "GET_HEALTH_CARE_UNIT_MEMBERS_ENDPOINT";
  public static final String GET_LAST_UPDATE_ENDPOINT = "GET_LAST_UPDATE_ENDPOINT";
  public static final String GET_CREDENTIALS_FOR_PERSON_ENDPOINT = "GET_CREDENTIALS_FOR_PERSON";
  public static final String HANDLE_CERTIFICATION_PERSON_ENDPOINT = "HANDLE_CERTIFICATION_PERSON_ENDPOINT";

  @Mock
  private WebServiceClientFactory webServiceClientFactory;

  @InjectMocks
  private HsaClientConfiguration hsaClientConfiguration;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(
        hsaClientConfiguration,
        "getEmployeeIncludingProtectedPersonEndpoint",
        GET_EMPLOYEE_INCLUDING_PROTECTED_PERSON_ENDPOINT
    );
    ReflectionTestUtils.setField(
        hsaClientConfiguration,
        "getHealthCareUnitEndpoint",
        GET_HEALTH_CARE_UNIT_ENDPOINT
    );
    ReflectionTestUtils.setField(
        hsaClientConfiguration,
        "getHealthCareUnitMembersEndpoint",
        GET_HEALTH_CARE_UNIT_MEMBERS_ENDPOINT
    );
    ReflectionTestUtils.setField(
        hsaClientConfiguration,
        "getCredentialsForPersonEndpoint",
        GET_CREDENTIALS_FOR_PERSON_ENDPOINT
    );
    ReflectionTestUtils.setField(
        hsaClientConfiguration,
        "handleCertificationPersonEndpoint",
        HANDLE_CERTIFICATION_PERSON_ENDPOINT
    );
    ReflectionTestUtils.setField(
        hsaClientConfiguration,
        "getLastUpdateEndpoint",
        GET_LAST_UPDATE_ENDPOINT
    );
  }

  @Test
  void shallReturnGetEmployeeIncludingProtectedPerson() {
    final var expected = mock(GetEmployeeIncludingProtectedPersonResponderInterface.class);

    doReturn(expected)
        .when(webServiceClientFactory)
        .create(
            GetEmployeeIncludingProtectedPersonResponderInterface.class,
            GET_EMPLOYEE_INCLUDING_PROTECTED_PERSON_ENDPOINT
        );

    final var actual = hsaClientConfiguration.getEmployeeIncludingProtectedPerson();
    assertEquals(expected, actual);
  }

  @Test
  void shallReturnGetHealthCareUnit() {
    final var expected = mock(GetHealthCareUnitResponderInterface.class);

    doReturn(expected)
        .when(webServiceClientFactory)
        .create(
            GetHealthCareUnitResponderInterface.class,
            GET_HEALTH_CARE_UNIT_ENDPOINT
        );

    final var actual = hsaClientConfiguration.getHealthCareUnitResponderInterface();
    assertEquals(expected, actual);
  }

  @Test
  void shallReturnGetHealthCareUnitMembers() {
    final var expected = mock(GetHealthCareUnitMembersResponderInterface.class);

    doReturn(expected)
        .when(webServiceClientFactory)
        .create(
            GetHealthCareUnitMembersResponderInterface.class,
            GET_HEALTH_CARE_UNIT_MEMBERS_ENDPOINT
        );

    final var actual = hsaClientConfiguration.getHealthCareUnitMembersResponderInterface();
    assertEquals(expected, actual);
  }

  @Test
  void shallReturnGetLastUpdate() {
    final var expected = mock(GetHospLastUpdateResponderInterface.class);

    doReturn(expected)
        .when(webServiceClientFactory)
        .create(
            GetHospLastUpdateResponderInterface.class,
            GET_LAST_UPDATE_ENDPOINT
        );

    final var actual = hsaClientConfiguration.getLastUpdate();
    assertEquals(expected, actual);
  }

  @Test
  void shallReturnGetCredentialsForPerson() {
    final var expected = mock(GetHospCredentialsForPersonResponderInterface.class);

    doReturn(expected)
        .when(webServiceClientFactory)
        .create(
            GetHospCredentialsForPersonResponderInterface.class,
            GET_CREDENTIALS_FOR_PERSON_ENDPOINT
        );

    final var actual = hsaClientConfiguration.getCredentialsForPerson();
    assertEquals(expected, actual);
  }

  @Test
  void shallReturnHandleCertificationPersonInterface() {
    final var expected = mock(HandleHospCertificationPersonResponderInterface.class);

    doReturn(expected)
        .when(webServiceClientFactory)
        .create(
            HandleHospCertificationPersonResponderInterface.class,
            HANDLE_CERTIFICATION_PERSON_ENDPOINT
        );

    final var actual = hsaClientConfiguration.getHandleCertificationPersonInterface();
    assertEquals(expected, actual);
  }
}