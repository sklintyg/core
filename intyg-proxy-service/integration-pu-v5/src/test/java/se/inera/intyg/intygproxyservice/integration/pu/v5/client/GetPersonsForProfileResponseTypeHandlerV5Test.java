package se.inera.intyg.intygproxyservice.integration.pu.v5.client;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.TestData.PERSON;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.pu.Status;
import se.inera.intyg.intygproxyservice.integration.pu.v5.client.converter.GetPersonsForProfileResponseTypeConverterV5;
import se.riv.strategicresourcemanagement.persons.person.getpersonsforprofileresponder.v5.GetPersonsForProfileResponseType;
import se.riv.strategicresourcemanagement.persons.person.v5.IIType;
import se.riv.strategicresourcemanagement.persons.person.v5.PersonRecordType;
import se.riv.strategicresourcemanagement.persons.person.v5.RequestedPersonRecordType;


@ExtendWith(MockitoExtension.class)
class GetPersonsForProfileResponseTypeHandlerV5Test {

  @Mock
  private GetPersonsForProfileResponseTypeConverterV5 getPersonsForProfileResponseTypeConverterV5;

  @InjectMocks
  private GetPersonsForProfileResponseTypeHandlerV5 getPersonsForProfileResponseTypeHandlerV5;

  @Nested
  class GetPerson {

    @Nested
    class PersonProvidedInResponse {

      private GetPersonsForProfileResponseType getPersonsForProfileResponseType;

      @BeforeEach
      void setUp() {
        final var requestedPersonRecordType = new RequestedPersonRecordType();
        requestedPersonRecordType.setPersonRecord(new PersonRecordType());
        getPersonsForProfileResponseType = new GetPersonsForProfileResponseType();
        getPersonsForProfileResponseType.getRequestedPersonRecord().add(
            requestedPersonRecordType
        );

        doReturn(PERSON)
            .when(getPersonsForProfileResponseTypeConverterV5)
            .convert(requestedPersonRecordType);
      }

      @Test
      void shallReturnStatusFound() {

        final var actualPuResponse = getPersonsForProfileResponseTypeHandlerV5.handle(
            getPersonsForProfileResponseType
        );

        assertEquals(Status.FOUND, actualPuResponse.status());
      }

      @Test
      void shallReturnPerson() {

        final var actualPuResponse = getPersonsForProfileResponseTypeHandlerV5.handle(
            getPersonsForProfileResponseType
        );

        assertEquals(PERSON, actualPuResponse.person());
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

        final var actualPuResponse = getPersonsForProfileResponseTypeHandlerV5.handle(
            getPersonsForProfileReponseType
        );

        assertEquals(Status.ERROR, actualPuResponse.status());
      }

      @Test
      void shallReturnStatusNotFoundIfNoRecordIsReturned() {
        final var actualPuResponse = getPersonsForProfileResponseTypeHandlerV5.handle(
            getPersonsForProfileReponseType
        );

        assertEquals(Status.NOT_FOUND, actualPuResponse.status());
      }

      @Test
      void shallReturnStatusNotFoundIfNoPersonInRecordIsReturned() {
        final var requestedPersonRecordType = new RequestedPersonRecordType();
        getPersonsForProfileReponseType.getRequestedPersonRecord()
            .add(requestedPersonRecordType);

        final var actualPuResponse = getPersonsForProfileResponseTypeHandlerV5.handle(
            getPersonsForProfileReponseType
        );

        assertEquals(Status.NOT_FOUND, actualPuResponse.status());
      }
    }
  }

  @Nested
  class GetPersons {

    @Nested
    class PersonProvidedInResponse {

      private GetPersonsForProfileResponseType getPersonsForProfileResponseType;

      @BeforeEach
      void setUp() {
        final var requestedPersonRecordType = new RequestedPersonRecordType();
        requestedPersonRecordType.setPersonRecord(new PersonRecordType());
        getPersonsForProfileResponseType = new GetPersonsForProfileResponseType();
        getPersonsForProfileResponseType.getRequestedPersonRecord().add(
            requestedPersonRecordType
        );

        doReturn(PERSON)
            .when(getPersonsForProfileResponseTypeConverterV5)
            .convert(requestedPersonRecordType);
      }

      @Test
      void shallReturnStatusFound() {

        final var actualPuResponse = getPersonsForProfileResponseTypeHandlerV5.handle(
            getPersonsForProfileResponseType
        );

        assertAll(
            () -> assertEquals(Status.FOUND, actualPuResponse.status()),
            () -> assertEquals(PERSON, actualPuResponse.person())
        );
      }

      @Test
      void shallReturnPerson() {

        final var actualPuResponse = getPersonsForProfileResponseTypeHandlerV5.handle(
            getPersonsForProfileResponseType
        );

        assertEquals(PERSON, actualPuResponse.person());
      }
    }

    @Nested
    class ErrorProvidedInResponse {

      private static final String PATIENT_ID_1 = "191212121212";
      private static final String PATIENT_ID_2 = "201212121212";
      private static final String PATIENT_ID_3 = "201212121213";

      private GetPersonsForProfileResponseType getPersonsForProfileReponseType;
      private RequestedPersonRecordType recordPatient1;
      private RequestedPersonRecordType recordPatient11;
      private RequestedPersonRecordType recordPatient2;
      private RequestedPersonRecordType recordWithNull;

      @BeforeEach
      void setUp() {
        getPersonsForProfileReponseType = new GetPersonsForProfileResponseType();

        final var id1 = new IIType();
        id1.setExtension(PATIENT_ID_1);
        final var id11 = new IIType();
        id11.setExtension(PATIENT_ID_1);
        final var id2 = new IIType();
        id2.setExtension(PATIENT_ID_2);
        final var id3 = new IIType();
        id3.setExtension(PATIENT_ID_3);

        recordPatient11 = new RequestedPersonRecordType();
        recordPatient1 = new RequestedPersonRecordType();
        recordPatient2 = new RequestedPersonRecordType();
        recordWithNull = new RequestedPersonRecordType();

        final var personRecord1 = new PersonRecordType();
        final var personRecord11 = new PersonRecordType();
        final var personRecord2 = new PersonRecordType();

        recordPatient1.setPersonRecord(personRecord1);
        recordPatient11.setPersonRecord(personRecord11);
        recordPatient2.setPersonRecord(personRecord2);
        recordWithNull.setPersonRecord(null);

        personRecord1.setPersonalIdentity(id1);
        personRecord11.setPersonalIdentity(id11);
        personRecord2.setPersonalIdentity(id2);

        recordPatient11.setRequestedPersonalIdentity(id11);
        recordPatient1.setRequestedPersonalIdentity(id1);
        recordPatient2.setRequestedPersonalIdentity(id2);
        recordWithNull.setRequestedPersonalIdentity(id3);
      }

      @Test
      void shallReturnStatusErrorIfMoreThanOnePersonIsReturnedForTheSamePersonId() {
        getPersonsForProfileReponseType.getRequestedPersonRecord()
            .addAll(
                List.of(
                    recordPatient1,
                    recordPatient11,
                    recordPatient2
                )
            );

        final var actualPuResponse = getPersonsForProfileResponseTypeHandlerV5.handle(
            List.of(PATIENT_ID_1, PATIENT_ID_2),
            getPersonsForProfileReponseType
        );

        assertAll(
            () -> assertEquals(Status.ERROR, actualPuResponse.getPersons().getFirst().status()),
            () -> assertEquals(PATIENT_ID_1,
                actualPuResponse.getPersons().getFirst().person().getPersonnummer().id())
        );
      }

      @Test
      void shallReturnStatusNotFoundIfNoRecordIsReturned() {
        final var actualPuResponse = getPersonsForProfileResponseTypeHandlerV5.handle(
            List.of(PATIENT_ID_1),
            getPersonsForProfileReponseType
        );

        assertAll(
            () -> assertEquals(Status.NOT_FOUND,
                actualPuResponse.getPersons().getFirst().status()),
            () -> assertEquals(PATIENT_ID_1,
                actualPuResponse.getPersons().getFirst().person().getPersonnummer().id())
        );
      }

      @Test
      void shallReturnStatusNotFoundIfNoPersonInRecordIsReturned() {
        getPersonsForProfileReponseType.getRequestedPersonRecord()
            .add(recordPatient1);

        final var actualPuResponse = getPersonsForProfileResponseTypeHandlerV5.handle(
            List.of(PATIENT_ID_1, PATIENT_ID_2),
            getPersonsForProfileReponseType
        );

        assertAll(
            () -> assertEquals(Status.NOT_FOUND, actualPuResponse.getPersons().get(1).status()),
            () -> assertEquals(Status.FOUND, actualPuResponse.getPersons().getFirst().status()),
            () -> assertEquals(
                PATIENT_ID_2,
                actualPuResponse.getPersons().get(1).person().getPersonnummer().id()
            )
        );
      }

      @Test
      void shallReturnStatusNotFoundIfNullPersonRecordIsReturned() {
        getPersonsForProfileReponseType.getRequestedPersonRecord()
            .addAll(List.of(recordWithNull, recordPatient1));

        final var actualPuResponse = getPersonsForProfileResponseTypeHandlerV5.handle(
            List.of(PATIENT_ID_1, PATIENT_ID_3),
            getPersonsForProfileReponseType
        );

        assertAll(
            () -> assertEquals(Status.NOT_FOUND, actualPuResponse.getPersons().get(1).status()),
            () -> assertEquals(Status.FOUND, actualPuResponse.getPersons().getFirst().status()),
            () -> assertEquals(
                PATIENT_ID_3,
                actualPuResponse.getPersons().get(1).person().getPersonnummer().id()
            )
        );
      }
    }
  }
}