package se.inera.intyg.intygproxyservice.integration.pu.v5.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.TestData.PERSON;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.TestData.PERSON_ID_AS_PERSONNUMMER;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.TestData.PERSON_ID_AS_SAMORDNINGSNUMMER;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.configuration.configuration.PuConstants.CODE_COORDINATION_NUMBER;
import static se.inera.intyg.intygproxyservice.integration.pu.v5.configuration.configuration.PuConstants.CODE_PERSONAL_ID;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.Status;
import se.riv.strategicresourcemanagement.persons.person.getpersonsforprofile.v5.rivtabp21.GetPersonsForProfileResponderInterface;
import se.riv.strategicresourcemanagement.persons.person.getpersonsforprofileresponder.v5.GetPersonsForProfileResponseType;
import se.riv.strategicresourcemanagement.persons.person.getpersonsforprofileresponder.v5.GetPersonsForProfileType;
import se.riv.strategicresourcemanagement.persons.person.v5.LookupProfileType;

@ExtendWith(MockitoExtension.class)
class PuClientV5Test {

  private static final String LOGICAL_ADDRESS = "PU-LogicalAddress";
  @Mock
  private GetPersonsForProfileResponderInterface getPersonsForProfileResponderInterface;

  @Mock
  private GetPersonsForProfileResponseTypeHandlerV5 getPersonsForProfileResponseTypeHandlerV5;

  @InjectMocks
  private PuClientV5 puClientV5;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(puClientV5, "logicalAddress", LOGICAL_ADDRESS);
  }

  @Nested
  class GetPerson {

    @Nested
    class PersonFoundInPu {

      private PuRequest puRequest;

      @BeforeEach
      void setUp() {
        puRequest = PuRequest.builder()
            .personId(PERSON_ID_AS_PERSONNUMMER)
            .build();

        final var getPersonsForProfileResponseType = new GetPersonsForProfileResponseType();

        doReturn(getPersonsForProfileResponseType)
            .when(getPersonsForProfileResponderInterface)
            .getPersonsForProfile(anyString(), any(GetPersonsForProfileType.class));

        doReturn(PuResponse.found(PERSON))
            .when(getPersonsForProfileResponseTypeHandlerV5)
            .handle(getPersonsForProfileResponseType);
      }

      @Test
      void shallReturnPuResponseWithStatusFound() {
        final var actualPuResponse = puClientV5.findPerson(puRequest);

        assertEquals(Status.FOUND, actualPuResponse.status());
      }

      @Test
      void shallReturnPuResponseWithPerson() {
        final var actualPuResponse = puClientV5.findPerson(puRequest);

        assertEquals(PERSON, actualPuResponse.person());
      }
    }

    @Nested
    class PuArguments {

      @Test
      void shallCallPuWithLogicalAddressProvidedByConfig() {
        final var logicalAddressArgumentCaptor = ArgumentCaptor.forClass(String.class);

        puClientV5.findPerson(
            PuRequest.builder()
                .personId(PERSON_ID_AS_PERSONNUMMER)
                .build()
        );

        verify(getPersonsForProfileResponderInterface)
            .getPersonsForProfile(logicalAddressArgumentCaptor.capture(),
                any(GetPersonsForProfileType.class));

        assertEquals(LOGICAL_ADDRESS, logicalAddressArgumentCaptor.getValue());
      }

      @Test
      void shallCallPuWithLookupProfileP2() {

        final var getPersonsForProfileTypeArgumentCaptor = ArgumentCaptor.forClass(
            GetPersonsForProfileType.class);

        puClientV5.findPerson(
            PuRequest.builder()
                .personId(PERSON_ID_AS_PERSONNUMMER)
                .build()
        );

        verify(getPersonsForProfileResponderInterface)
            .getPersonsForProfile(anyString(), getPersonsForProfileTypeArgumentCaptor.capture());

        assertEquals(LookupProfileType.P_2,
            getPersonsForProfileTypeArgumentCaptor.getValue().getProfile()
        );
      }
      @Test
      void shallCallPuWithIgnoredReferredIdentity() {

        final var getPersonsForProfileTypeArgumentCaptor = ArgumentCaptor.forClass(
            GetPersonsForProfileType.class);

        puClientV5.findPerson(
            PuRequest.builder()
                .personId(PERSON_ID_AS_PERSONNUMMER)
                .build()
        );

        verify(getPersonsForProfileResponderInterface)
            .getPersonsForProfile(anyString(), getPersonsForProfileTypeArgumentCaptor.capture());

       assertTrue(getPersonsForProfileTypeArgumentCaptor.getValue().isIgnoreReferredIdentity());
      }
      @Test
      void shallCallPuWithKodverkPersonnummerWhenPersonnummerIsProvided() {

        final var getPersonsForProfileTypeArgumentCaptor = ArgumentCaptor.forClass(
            GetPersonsForProfileType.class);

        puClientV5.findPerson(
            PuRequest.builder()
                .personId(PERSON_ID_AS_PERSONNUMMER)
                .build()
        );

        verify(getPersonsForProfileResponderInterface)
            .getPersonsForProfile(anyString(), getPersonsForProfileTypeArgumentCaptor.capture());

        assertEquals(CODE_PERSONAL_ID,
            getPersonsForProfileTypeArgumentCaptor.getValue().getPersonId().getFirst().getRoot()
        );
      }

      @Test
      void shallCallPuWithPersonnummerWhenPersonnummerIsProvided() {

        final var getPersonsForProfileTypeArgumentCaptor = ArgumentCaptor.forClass(
            GetPersonsForProfileType.class);

        puClientV5.findPerson(
            PuRequest.builder()
                .personId(PERSON_ID_AS_PERSONNUMMER)
                .build()
        );

        verify(getPersonsForProfileResponderInterface)
            .getPersonsForProfile(anyString(), getPersonsForProfileTypeArgumentCaptor.capture());

        assertEquals(PERSON_ID_AS_PERSONNUMMER,
            getPersonsForProfileTypeArgumentCaptor.getValue().getPersonId().getFirst()
                .getExtension()
        );
      }

      @Test
      void shallCallPuWithKodverkSamordningsnummerWhenSamordningsnummerIsProvided() {

        final var getPersonsForProfileTypeArgumentCaptor = ArgumentCaptor.forClass(
            GetPersonsForProfileType.class);

        puClientV5.findPerson(
            PuRequest.builder()
                .personId(PERSON_ID_AS_SAMORDNINGSNUMMER)
                .build()
        );

        verify(getPersonsForProfileResponderInterface)
            .getPersonsForProfile(anyString(), getPersonsForProfileTypeArgumentCaptor.capture());

        assertEquals(CODE_COORDINATION_NUMBER,
            getPersonsForProfileTypeArgumentCaptor.getValue().getPersonId().getFirst().getRoot()
        );
      }

      @Test
      void shallCallPuWithSamordningsnummerWhenSamordningsnummerIsProvided() {

        final var getPersonsForProfileTypeArgumentCaptor = ArgumentCaptor.forClass(
            GetPersonsForProfileType.class);

        puClientV5.findPerson(
            PuRequest.builder()
                .personId(PERSON_ID_AS_SAMORDNINGSNUMMER)
                .build()
        );

        verify(getPersonsForProfileResponderInterface)
            .getPersonsForProfile(anyString(), getPersonsForProfileTypeArgumentCaptor.capture());

        assertEquals(PERSON_ID_AS_SAMORDNINGSNUMMER,
            getPersonsForProfileTypeArgumentCaptor.getValue().getPersonId().getFirst()
                .getExtension()
        );
      }
    }

    @Nested
    class UnexpectedErrors {

      private PuRequest puRequest;

      @BeforeEach
      void setUp() {
        puRequest = PuRequest.builder()
            .personId(PERSON_ID_AS_PERSONNUMMER)
            .build();
      }

      @Test
      void shallReturnPuResponseWithStatusFound() {
        doThrow(new RuntimeException("UnexpectedError"))
            .when(getPersonsForProfileResponderInterface)
            .getPersonsForProfile(anyString(), any(GetPersonsForProfileType.class));

        final var actualPuResponse = puClientV5.findPerson(puRequest);

        assertEquals(Status.ERROR, actualPuResponse.status());
      }
    }
  }

  @Nested
  class GetPersons {

    @Nested
    class PersonFoundInPu {

      private PuPersonsRequest puRequest;

      @BeforeEach
      void setUp() {
        puRequest = PuPersonsRequest.builder()
            .personIds(List.of(PERSON_ID_AS_PERSONNUMMER))
            .build();

        final var getPersonsForProfileResponseType = new GetPersonsForProfileResponseType();

        doReturn(getPersonsForProfileResponseType)
            .when(getPersonsForProfileResponderInterface)
            .getPersonsForProfile(anyString(), any(GetPersonsForProfileType.class));

        doReturn(
            PuPersonsResponse.builder()
                .persons(List.of(PuResponse.found(PERSON)))
                .build()
        )
            .when(getPersonsForProfileResponseTypeHandlerV5)
            .handle(List.of(PERSON_ID_AS_PERSONNUMMER), getPersonsForProfileResponseType);
      }

      @Test
      void shallReturnPuResponseWithStatusFound() {
        final var actualPuResponse = puClientV5.findPersons(puRequest);

        assertEquals(Status.FOUND, actualPuResponse.getPersons().getFirst().status());
      }

      @Test
      void shallReturnPuResponseWithPerson() {
        final var actualPuResponse = puClientV5.findPersons(puRequest);

        assertEquals(PERSON, actualPuResponse.getPersons().getFirst().person());
      }
    }

    @Nested
    class PuArguments {

      @Test
      void shallCallPuWithLogicalAddressProvidedByConfig() {
        final var logicalAddressArgumentCaptor = ArgumentCaptor.forClass(String.class);

        puClientV5.findPersons(
            PuPersonsRequest.builder()
                .personIds(List.of(PERSON_ID_AS_PERSONNUMMER))
                .build()
        );

        verify(getPersonsForProfileResponderInterface)
            .getPersonsForProfile(logicalAddressArgumentCaptor.capture(),
                any(GetPersonsForProfileType.class));

        assertEquals(LOGICAL_ADDRESS, logicalAddressArgumentCaptor.getValue());
      }

      @Test
      void shallCallPuWithLookupProfileP2() {

        final var getPersonsForProfileTypeArgumentCaptor = ArgumentCaptor.forClass(
            GetPersonsForProfileType.class);

        puClientV5.findPerson(
            PuRequest.builder()
                .personId(PERSON_ID_AS_PERSONNUMMER)
                .build()
        );

        verify(getPersonsForProfileResponderInterface)
            .getPersonsForProfile(anyString(), getPersonsForProfileTypeArgumentCaptor.capture());

        assertEquals(LookupProfileType.P_2,
            getPersonsForProfileTypeArgumentCaptor.getValue().getProfile()
        );
      }

      @Test
      void shallCallPuWithKodverkPersonnummerWhenPersonnummerIsProvided() {

        final var getPersonsForProfileTypeArgumentCaptor = ArgumentCaptor.forClass(
            GetPersonsForProfileType.class);

        puClientV5.findPersons(
            PuPersonsRequest.builder()
                .personIds(List.of(PERSON_ID_AS_PERSONNUMMER))
                .build()
        );

        verify(getPersonsForProfileResponderInterface)
            .getPersonsForProfile(anyString(), getPersonsForProfileTypeArgumentCaptor.capture());

        assertEquals(CODE_PERSONAL_ID,
            getPersonsForProfileTypeArgumentCaptor.getValue().getPersonId().getFirst().getRoot()
        );
      }

      @Test
      void shallCallPuWithPersonnummerWhenPersonnummerIsProvided() {

        final var getPersonsForProfileTypeArgumentCaptor = ArgumentCaptor.forClass(
            GetPersonsForProfileType.class);

        puClientV5.findPersons(
            PuPersonsRequest.builder()
                .personIds(List.of(PERSON_ID_AS_PERSONNUMMER))
                .build()
        );

        verify(getPersonsForProfileResponderInterface)
            .getPersonsForProfile(anyString(), getPersonsForProfileTypeArgumentCaptor.capture());

        assertEquals(PERSON_ID_AS_PERSONNUMMER,
            getPersonsForProfileTypeArgumentCaptor.getValue().getPersonId().getFirst()
                .getExtension()
        );
      }

      @Test
      void shallCallPuWithKodverkSamordningsnummerWhenSamordningsnummerIsProvided() {

        final var getPersonsForProfileTypeArgumentCaptor = ArgumentCaptor.forClass(
            GetPersonsForProfileType.class);

        puClientV5.findPersons(
            PuPersonsRequest.builder()
                .personIds(List.of(PERSON_ID_AS_SAMORDNINGSNUMMER))
                .build()
        );

        verify(getPersonsForProfileResponderInterface)
            .getPersonsForProfile(anyString(), getPersonsForProfileTypeArgumentCaptor.capture());

        assertEquals(CODE_COORDINATION_NUMBER,
            getPersonsForProfileTypeArgumentCaptor.getValue().getPersonId().getFirst().getRoot()
        );
      }

      @Test
      void shallCallPuWithSamordningsnummerWhenSamordningsnummerIsProvided() {

        final var getPersonsForProfileTypeArgumentCaptor = ArgumentCaptor.forClass(
            GetPersonsForProfileType.class);

        puClientV5.findPersons(
            PuPersonsRequest.builder()
                .personIds(List.of(PERSON_ID_AS_SAMORDNINGSNUMMER))
                .build()
        );

        verify(getPersonsForProfileResponderInterface)
            .getPersonsForProfile(anyString(), getPersonsForProfileTypeArgumentCaptor.capture());

        assertEquals(PERSON_ID_AS_SAMORDNINGSNUMMER,
            getPersonsForProfileTypeArgumentCaptor.getValue().getPersonId().getFirst()
                .getExtension()
        );
      }
    }

    @Nested
    class UnexpectedErrors {

      private PuRequest puRequest;

      @BeforeEach
      void setUp() {
        puRequest = PuRequest.builder()
            .personId(PERSON_ID_AS_PERSONNUMMER)
            .build();
      }

      @Test
      void shallReturnPuResponseWithStatusFound() {
        doThrow(new RuntimeException("UnexpectedError"))
            .when(getPersonsForProfileResponderInterface)
            .getPersonsForProfile(anyString(), any(GetPersonsForProfileType.class));

        final var actualPuResponse = puClientV5.findPerson(puRequest);

        assertEquals(Status.ERROR, actualPuResponse.status());
      }
    }
  }
}