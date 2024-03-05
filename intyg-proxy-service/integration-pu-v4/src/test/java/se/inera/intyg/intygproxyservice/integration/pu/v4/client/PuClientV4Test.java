package se.inera.intyg.intygproxyservice.integration.pu.v4.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.intygproxyservice.integration.pu.v4.TestData.PERSON;
import static se.inera.intyg.intygproxyservice.integration.pu.v4.TestData.PERSON_ID_AS_PERSONNUMMER;
import static se.inera.intyg.intygproxyservice.integration.pu.v4.TestData.PERSON_ID_AS_SAMORDNINGSNUMMER;
import static se.inera.intyg.intygproxyservice.integration.pu.v4.configuration.configuration.PuConstants.KODVERK_PERSONNUMMER;
import static se.inera.intyg.intygproxyservice.integration.pu.v4.configuration.configuration.PuConstants.KODVERK_SAMORDNINGSNUMMER;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse.Status;
import se.riv.strategicresourcemanagement.persons.person.getpersonsforprofile.v4.rivtabp21.GetPersonsForProfileResponderInterface;
import se.riv.strategicresourcemanagement.persons.person.getpersonsforprofileresponder.v4.GetPersonsForProfileResponseType;
import se.riv.strategicresourcemanagement.persons.person.getpersonsforprofileresponder.v4.GetPersonsForProfileType;
import se.riv.strategicresourcemanagement.persons.person.v4.LookupProfileType;

@ExtendWith(MockitoExtension.class)
class PuClientV4Test {

  private static final String LOGICAL_ADDRESS = "PU-LogicalAddress";
  @Mock
  private GetPersonsForProfileResponderInterface getPersonsForProfileResponderInterface;

  @Mock
  private GetPersonsForProfileResponseTypeHandlerV4 getPersonsForProfileResponseTypeHandlerV4;

  @InjectMocks
  private PuClientV4 puClientV4;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(puClientV4, "logicalAddress", LOGICAL_ADDRESS);
  }

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
          .when(getPersonsForProfileResponseTypeHandlerV4)
          .handle(getPersonsForProfileResponseType);
    }

    @Test
    void shallReturnPuResponseWithStatusFound() {
      final var actualPuResponse = puClientV4.findPerson(puRequest);

      assertEquals(Status.FOUND, actualPuResponse.getStatus());
    }

    @Test
    void shallReturnPuResponseWithPerson() {
      final var actualPuResponse = puClientV4.findPerson(puRequest);

      assertEquals(PERSON, actualPuResponse.getPerson());
    }
  }

  @Nested
  class PuArguments {

    @Test
    void shallCallPuWithLogicalAddressProvidedByConfig() {
      final var logicalAddressArgumentCaptor = ArgumentCaptor.forClass(String.class);

      puClientV4.findPerson(
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

      puClientV4.findPerson(
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

      puClientV4.findPerson(
          PuRequest.builder()
              .personId(PERSON_ID_AS_PERSONNUMMER)
              .build()
      );

      verify(getPersonsForProfileResponderInterface)
          .getPersonsForProfile(anyString(), getPersonsForProfileTypeArgumentCaptor.capture());

      assertEquals(KODVERK_PERSONNUMMER,
          getPersonsForProfileTypeArgumentCaptor.getValue().getPersonId().get(0).getRoot()
      );
    }

    @Test
    void shallCallPuWithPersonnummerWhenPersonnummerIsProvided() {

      final var getPersonsForProfileTypeArgumentCaptor = ArgumentCaptor.forClass(
          GetPersonsForProfileType.class);

      puClientV4.findPerson(
          PuRequest.builder()
              .personId(PERSON_ID_AS_PERSONNUMMER)
              .build()
      );

      verify(getPersonsForProfileResponderInterface)
          .getPersonsForProfile(anyString(), getPersonsForProfileTypeArgumentCaptor.capture());

      assertEquals(PERSON_ID_AS_PERSONNUMMER,
          getPersonsForProfileTypeArgumentCaptor.getValue().getPersonId().get(0).getExtension()
      );
    }

    @Test
    void shallCallPuWithKodverkSamordningsnummerWhenSamordningsnummerIsProvided() {

      final var getPersonsForProfileTypeArgumentCaptor = ArgumentCaptor.forClass(
          GetPersonsForProfileType.class);

      puClientV4.findPerson(
          PuRequest.builder()
              .personId(PERSON_ID_AS_SAMORDNINGSNUMMER)
              .build()
      );

      verify(getPersonsForProfileResponderInterface)
          .getPersonsForProfile(anyString(), getPersonsForProfileTypeArgumentCaptor.capture());

      assertEquals(KODVERK_SAMORDNINGSNUMMER,
          getPersonsForProfileTypeArgumentCaptor.getValue().getPersonId().get(0).getRoot()
      );
    }

    @Test
    void shallCallPuWithSamordningsnummerWhenSamordningsnummerIsProvided() {

      final var getPersonsForProfileTypeArgumentCaptor = ArgumentCaptor.forClass(
          GetPersonsForProfileType.class);

      puClientV4.findPerson(
          PuRequest.builder()
              .personId(PERSON_ID_AS_SAMORDNINGSNUMMER)
              .build()
      );

      verify(getPersonsForProfileResponderInterface)
          .getPersonsForProfile(anyString(), getPersonsForProfileTypeArgumentCaptor.capture());

      assertEquals(PERSON_ID_AS_SAMORDNINGSNUMMER,
          getPersonsForProfileTypeArgumentCaptor.getValue().getPersonId().get(0).getExtension()
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

      final var actualPuResponse = puClientV4.findPerson(puRequest);

      assertEquals(Status.ERROR, actualPuResponse.getStatus());
    }
  }
}