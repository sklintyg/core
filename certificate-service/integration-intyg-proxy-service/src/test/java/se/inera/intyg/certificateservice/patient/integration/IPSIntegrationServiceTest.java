package se.inera.intyg.certificateservice.patient.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.logging.MdcHelper.LOG_SESSION_ID_HEADER;
import static se.inera.intyg.certificateservice.logging.MdcHelper.LOG_TRACE_ID_HEADER;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.SESSION_ID_KEY;
import static se.inera.intyg.certificateservice.logging.MdcLogConstants.TRACE_ID_KEY;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.MDC;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import se.inera.intyg.certificateservice.patient.dto.PersonDTO;
import se.inera.intyg.certificateservice.patient.dto.PersonResponseDTO;
import se.inera.intyg.certificateservice.patient.dto.PersonsRequestDTO;
import se.inera.intyg.certificateservice.patient.dto.PersonsResponseDTO;
import se.inera.intyg.certificateservice.patient.dto.StatusDTOType;

@ExtendWith(MockitoExtension.class)
class IPSIntegrationServiceTest {

  private static final String ENDPOINT = "/api/v1/persons";
  private static final String TRACE_ID = "traceId";
  private static final String SESSION_ID = "sessionId";
  private final RestClient.RequestBodyUriSpec requestBodyUriSpec = mock(
      RestClient.RequestBodyUriSpec.class);
  private final RestClient.RequestBodySpec requestBodySpec = mock(RestClient.RequestBodySpec.class);
  private final RestClient.ResponseSpec responseSpec = mock(RestClient.ResponseSpec.class);

  @Mock
  private RestClient restClient;
  @InjectMocks
  private IPSIntegrationService ipsIntegrationService;

  @BeforeEach
  void setUp() {
    MDC.put(TRACE_ID_KEY, TRACE_ID);
    MDC.put(SESSION_ID_KEY, SESSION_ID);
  }

  @Test
  void shallReturnPersonResponse() {
    final var request = PersonsRequestDTO.builder()
        .personIds(List.of("personId"))
        .build();

    final var expectedResponse =
        PersonsResponseDTO.builder()
            .persons(
                List.of(
                    PersonResponseDTO.builder()
                        .status(StatusDTOType.FOUND)
                        .person(mock(PersonDTO.class))
                        .build()
                )
            )
            .build();

    doReturn(requestBodyUriSpec).when(restClient).post();
    doReturn(requestBodySpec).when(requestBodyUriSpec).uri(ENDPOINT);
    doReturn(requestBodySpec).when(requestBodySpec).body(request);
    doReturn(requestBodySpec).when(requestBodySpec).header(LOG_TRACE_ID_HEADER, TRACE_ID);
    doReturn(requestBodySpec).when(requestBodySpec).header(LOG_SESSION_ID_HEADER, SESSION_ID);
    doReturn(requestBodySpec).when(requestBodySpec).contentType(MediaType.APPLICATION_JSON);
    doReturn(responseSpec).when(requestBodySpec).retrieve();
    doReturn(expectedResponse).when(responseSpec).body(PersonsResponseDTO.class);

    final var response = ipsIntegrationService.findPersons(request);

    assertEquals(expectedResponse, response);
  }

  @Test
  void shallSetHeadersCorrectly() {
    final var request = PersonsRequestDTO.builder()
        .personIds(List.of("personId"))
        .build();

    doReturn(requestBodyUriSpec).when(restClient).post();
    doReturn(requestBodySpec).when(requestBodyUriSpec).uri(ENDPOINT);
    doReturn(requestBodySpec).when(requestBodySpec).body(request);
    doReturn(requestBodySpec).when(requestBodySpec).header(LOG_TRACE_ID_HEADER, TRACE_ID);
    doReturn(requestBodySpec).when(requestBodySpec).header(LOG_SESSION_ID_HEADER, SESSION_ID);
    doReturn(requestBodySpec).when(requestBodySpec).contentType(MediaType.APPLICATION_JSON);
    doReturn(responseSpec).when(requestBodySpec).retrieve();
    doReturn(mock(PersonsResponseDTO.class)).when(responseSpec).body(PersonsResponseDTO.class);

    ipsIntegrationService.findPersons(request);

    verify(requestBodySpec).header(LOG_TRACE_ID_HEADER, TRACE_ID);
    verify(requestBodySpec).header(LOG_SESSION_ID_HEADER, SESSION_ID);
  }

  @Test
  void shallSetContentTypeAsApplicationJson() {
    final var request = PersonsRequestDTO.builder()
        .personIds(List.of("personId"))
        .build();

    doReturn(requestBodyUriSpec).when(restClient).post();
    doReturn(requestBodySpec).when(requestBodyUriSpec).uri(ENDPOINT);
    doReturn(requestBodySpec).when(requestBodySpec).body(request);
    doReturn(requestBodySpec).when(requestBodySpec).header(LOG_TRACE_ID_HEADER, TRACE_ID);
    doReturn(requestBodySpec).when(requestBodySpec).header(LOG_SESSION_ID_HEADER, SESSION_ID);
    doReturn(requestBodySpec).when(requestBodySpec).contentType(MediaType.APPLICATION_JSON);
    doReturn(responseSpec).when(requestBodySpec).retrieve();
    doReturn(mock(PersonsResponseDTO.class)).when(responseSpec).body(PersonsResponseDTO.class);

    ipsIntegrationService.findPersons(request);

    verify(requestBodySpec).contentType(MediaType.APPLICATION_JSON);
  }
}