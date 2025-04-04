package se.inera.intyg.intygproxyservice.integration.pu.configuration;

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
import se.riv.strategicresourcemanagement.persons.person.getpersonsforprofile.v3.rivtabp21.GetPersonsForProfileResponderInterface;

@ExtendWith(MockitoExtension.class)
class PuClientV3ConfigurationTest {

  public static final String GET_PERSONS_FOR_PROFILE_ENDPOINT = "endpoint";
  @Mock
  private WebServiceClientFactory webServiceClientFactory;

  @InjectMocks
  private PuClientConfiguration puClientConfiguration;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(
        puClientConfiguration,
        "getPersonsForProfileEndpoint",
        GET_PERSONS_FOR_PROFILE_ENDPOINT
    );
  }

  @Test
  void shallReturnGetPersonsForProfileResponder() {
    final var expected = mock(GetPersonsForProfileResponderInterface.class);

    doReturn(expected)
        .when(webServiceClientFactory)
        .create(GetPersonsForProfileResponderInterface.class, GET_PERSONS_FOR_PROFILE_ENDPOINT);

    final var actual = puClientConfiguration.getPersonsForProfileResponder();
    assertEquals(expected, actual);
  }
}