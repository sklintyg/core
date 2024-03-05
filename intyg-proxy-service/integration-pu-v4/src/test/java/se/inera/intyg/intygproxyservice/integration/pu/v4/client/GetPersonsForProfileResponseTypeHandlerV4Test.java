package se.inera.intyg.intygproxyservice.integration.pu.v4.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.intygproxyservice.integration.pu.v4.TestData.PERSON;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse.Status;
import se.inera.intyg.intygproxyservice.integration.pu.v4.client.converter.GetPersonsForProfileResponseTypeConverterV4;
import se.riv.strategicresourcemanagement.persons.person.getpersonsforprofileresponder.v4.GetPersonsForProfileResponseType;
import se.riv.strategicresourcemanagement.persons.person.v4.PersonRecordType;
import se.riv.strategicresourcemanagement.persons.person.v4.RequestedPersonRecordType;


@ExtendWith(MockitoExtension.class)
class GetPersonsForProfileResponseTypeHandlerV4Test {

  @Mock
  private GetPersonsForProfileResponseTypeConverterV4 getPersonsForProfileResponseTypeConverterV4;

  @InjectMocks
  private GetPersonsForProfileResponseTypeHandlerV4 getPersonsForProfileResponseTypeHandlerV4;

  @Nested
  class PersonProvidedInResponse {

    private PuResponse puResponse;
    private GetPersonsForProfileResponseType getPersonsForProfileReponseType;

    @BeforeEach
    void setUp() {
      puResponse = PuResponse.found(PERSON);

      final var requestedPersonRecordType = new RequestedPersonRecordType();
      requestedPersonRecordType.setPersonRecord(new PersonRecordType());
      getPersonsForProfileReponseType = new GetPersonsForProfileResponseType();
      getPersonsForProfileReponseType.getRequestedPersonRecord().add(
          requestedPersonRecordType
      );

      doReturn(PERSON)
          .when(getPersonsForProfileResponseTypeConverterV4)
          .convert(requestedPersonRecordType);
    }

    @Test
    void shallReturnStatusFound() {

      final var actualPuResponse = getPersonsForProfileResponseTypeHandlerV4.handle(
          getPersonsForProfileReponseType
      );

      assertEquals(Status.FOUND, actualPuResponse.getStatus());
    }

    @Test
    void shallReturnPerson() {

      final var actualPuResponse = getPersonsForProfileResponseTypeHandlerV4.handle(
          getPersonsForProfileReponseType
      );

      assertEquals(PERSON, actualPuResponse.getPerson());
    }
  }

  @Nested
  class ErrorProvidedInResponse {

    private GetPersonsForProfileResponseType getPersonsForProfileReponseType;

    @BeforeEach
    void setUp() {
      getPersonsForProfileReponseType = new GetPersonsForProfileResponseType();
    }

    @Test
    void shallReturnStatusErrorIfMoreThanOnePersonIsReturned() {
      getPersonsForProfileReponseType.getRequestedPersonRecord()
          .addAll(
              List.of(
                  new RequestedPersonRecordType(),
                  new RequestedPersonRecordType())
          );

      final var actualPuResponse = getPersonsForProfileResponseTypeHandlerV4.handle(
          getPersonsForProfileReponseType
      );

      assertEquals(Status.ERROR, actualPuResponse.getStatus());
    }

    @Test
    void shallReturnStatusNotFoundIfNoRecordIsReturned() {
      final var actualPuResponse = getPersonsForProfileResponseTypeHandlerV4.handle(
          getPersonsForProfileReponseType
      );

      assertEquals(Status.NOT_FOUND, actualPuResponse.getStatus());
    }

    @Test
    void shallReturnStatusNotFoundIfNoPersonInRecordIsReturned() {
      final var requestedPersonRecordType = new RequestedPersonRecordType();
      getPersonsForProfileReponseType.getRequestedPersonRecord()
          .add(requestedPersonRecordType);

      final var actualPuResponse = getPersonsForProfileResponseTypeHandlerV4.handle(
          getPersonsForProfileReponseType
      );

      assertEquals(Status.NOT_FOUND, actualPuResponse.getStatus());
    }
  }
}