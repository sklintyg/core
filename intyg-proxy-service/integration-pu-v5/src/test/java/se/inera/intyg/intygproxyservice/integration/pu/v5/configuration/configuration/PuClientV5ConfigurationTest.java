package se.inera.intyg.intygproxyservice.integration.pu.v5.configuration.configuration;

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
import se.riv.strategicresourcemanagement.persons.person.getpersonsforprofile.v5.rivtabp21.GetPersonsForProfileResponderInterface;

@ExtendWith(MockitoExtension.class)
class PuClientV5ConfigurationTest {

  public static final String GET_PERSONS_FOR_PROFILE_ENDPOINT = "endpoint";
  @Mock
  private WebServiceClientFactory webServiceClientFactory;

  @InjectMocks
  private PuClientConfigurationV5 puClientConfigurationV5;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(
        puClientConfigurationV5,
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

    final var actual = puClientConfigurationV5.getPersonsForProfileResponder();
    assertEquals(expected, actual);
  }
}