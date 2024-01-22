package se.inera.intyg.intygproxyservice.integration.authorization.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
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
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialInformationIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.GetCredentialsForPersonIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.HandleCertificationPersonIntegrationRequest;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialInformation;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialsForPerson;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.Result;
import se.inera.intyg.intygproxyservice.integration.authorization.client.converter.GetCredentialInformationResponseTypeConverter;
import se.inera.intyg.intygproxyservice.integration.authorization.client.converter.GetCredentialsForPersonResponseTypeConverter;
import se.inera.intyg.intygproxyservice.integration.authorization.client.converter.GetLastUpdateResponseTypeConverter;
import se.inera.intyg.intygproxyservice.integration.authorization.client.converter.HandleCertificationPersonResponseTypeConverter;
import se.riv.infrastructure.directory.authorizationmanagement.getcredentialsforpersonincludingprotectedperson.v2.rivtabp21.GetCredentialsForPersonIncludingProtectedPersonResponderInterface;
import se.riv.infrastructure.directory.authorizationmanagement.getcredentialsforpersonincludingprotectedpersonresponder.v2.GetCredentialsForPersonIncludingProtectedPersonResponseType;
import se.riv.infrastructure.directory.authorizationmanagement.getcredentialsforpersonincludingprotectedpersonresponder.v2.GetCredentialsForPersonIncludingProtectedPersonType;
import se.riv.infrastructure.directory.authorizationmanagement.gethospcredentialsforperson.v1.rivtabp21.GetHospCredentialsForPersonResponderInterface;
import se.riv.infrastructure.directory.authorizationmanagement.gethospcredentialsforpersonresponder.v1.GetHospCredentialsForPersonResponseType;
import se.riv.infrastructure.directory.authorizationmanagement.gethospcredentialsforpersonresponder.v1.GetHospCredentialsForPersonType;
import se.riv.infrastructure.directory.authorizationmanagement.gethosplastupdate.v1.rivtabp21.GetHospLastUpdateResponderInterface;
import se.riv.infrastructure.directory.authorizationmanagement.gethosplastupdateresponder.v1.GetHospLastUpdateResponseType;
import se.riv.infrastructure.directory.authorizationmanagement.handlehospcertificationperson.v1.rivtabp21.HandleHospCertificationPersonResponderInterface;
import se.riv.infrastructure.directory.authorizationmanagement.handlehospcertificationpersonresponder.v1.HandleHospCertificationPersonResponseType;
import se.riv.infrastructure.directory.authorizationmanagement.handlehospcertificationpersonresponder.v1.HandleHospCertificationPersonType;
import se.riv.infrastructure.directory.authorizationmanagement.handlehospcertificationpersonresponder.v1.OperationEnum;

@ExtendWith(MockitoExtension.class)
class HsaAuthorizationClientTest {

  private static final String HSA_ID = "HSA_ID";

  private static final LocalDateTime LAST_UPDATE = LocalDateTime.now();

  private static final GetCredentialInformationIntegrationRequest REQUEST = GetCredentialInformationIntegrationRequest
      .builder()
      .personHsaId(HSA_ID)
      .build();

  private static final GetCredentialsForPersonIntegrationRequest FOR_PERSON_REQUEST = GetCredentialsForPersonIntegrationRequest
      .builder()
      .personId("PERSON_ID")
      .build();

  private static final HandleCertificationPersonIntegrationRequest HANDLE_PERSON_REQUEST = HandleCertificationPersonIntegrationRequest
      .builder()
      .personId("PERSON_ID")
      .reason("REASON")
      .operation("add")
      .certificationId("C_ID")
      .build();

  private static final String LOGICAL_ADDRESS = "LOGICAL_ADDRESS";

  private static final List<CredentialInformation> CREDENTIALS = List.of(
      CredentialInformation.builder().build());

  @Mock
  GetCredentialInformationResponseTypeConverter getCredentialInformationResponseTypeConverter;

  @Mock
  GetLastUpdateResponseTypeConverter getLastUpdateResponseTypeConverter;


  @Mock
  GetCredentialsForPersonResponseTypeConverter getCredentialsForPersonResponseTypeConverter;

  @Mock
  HandleCertificationPersonResponseTypeConverter handleCertificationPersonResponseTypeConverter;

  @Mock
  GetHospCredentialsForPersonResponderInterface getHospCredentialsForPersonResponderInterface;

  @Mock
  GetCredentialsForPersonIncludingProtectedPersonResponderInterface getCredentialsForPersonIncludingProtectedPersonResponderInterface;

  @Mock
  GetHospLastUpdateResponderInterface getHospLastUpdateResponderInterface;

  @Mock
  HandleHospCertificationPersonResponderInterface handleHospCertificationPersonResponderInterface;

  @InjectMocks
  HsaAuthorizationClient hsaAuthorizationClient;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(hsaAuthorizationClient, "logicalAddress", LOGICAL_ADDRESS);
  }

  @Nested
  class CredentialInformationTest {

    @Nested
    class UnexpectedError {

      @Test
      void shouldThrowErrorIfInterfaceThrowsError() {
        when(getCredentialsForPersonIncludingProtectedPersonResponderInterface
            .getCredentialsForPersonIncludingProtectedPerson(
                anyString(),
                any(GetCredentialsForPersonIncludingProtectedPersonType.class)
            )
        ).thenThrow(new IllegalStateException());

        assertThrows(IllegalStateException.class,
            () -> hsaAuthorizationClient.getCredentialInformation(REQUEST));
      }
    }

    @Nested
    class CorrectResponseFromInterface {

      @BeforeEach
      void setup() {
        when(getCredentialsForPersonIncludingProtectedPersonResponderInterface
            .getCredentialsForPersonIncludingProtectedPerson(
                anyString(),
                any(GetCredentialsForPersonIncludingProtectedPersonType.class)
            )
        ).thenReturn(new GetCredentialsForPersonIncludingProtectedPersonResponseType());
      }

      @Test
      void shouldReturnResponseWithHealthCareUnitReturnedFromConverter() {
        when(getCredentialInformationResponseTypeConverter.convert(
                any(GetCredentialsForPersonIncludingProtectedPersonResponseType.class)
            )
        ).thenReturn(CREDENTIALS);

        final var response = hsaAuthorizationClient.getCredentialInformation(
            GetCredentialInformationIntegrationRequest.builder().build()
        );

        assertEquals(CREDENTIALS, response);
      }

      @Test
      void shouldSendHsaIdInRequest() {
        hsaAuthorizationClient.getCredentialInformation(
            GetCredentialInformationIntegrationRequest
                .builder()
                .personHsaId(HSA_ID)
                .build()
        );

        final var captor = ArgumentCaptor.forClass(
            GetCredentialsForPersonIncludingProtectedPersonType.class);

        verify(
            getCredentialsForPersonIncludingProtectedPersonResponderInterface)
            .getCredentialsForPersonIncludingProtectedPerson(anyString(), captor.capture());

        assertEquals(HSA_ID, captor.getValue().getPersonHsaId());
      }

      @Test
      void shouldSendIncludeFeignedObjectsAsFalseInRquest() {
        hsaAuthorizationClient.getCredentialInformation(
            GetCredentialInformationIntegrationRequest
                .builder()
                .personHsaId(HSA_ID)
                .build()
        );

        final var captor = ArgumentCaptor.forClass(
            GetCredentialsForPersonIncludingProtectedPersonType.class);

        verify(
            getCredentialsForPersonIncludingProtectedPersonResponderInterface)
            .getCredentialsForPersonIncludingProtectedPerson(anyString(), captor.capture());

        assertFalse(captor.getValue().isIncludeFeignedObject());
      }

      @Test
      void shouldSendIncludeProfileInRquest() {
        hsaAuthorizationClient.getCredentialInformation(
            GetCredentialInformationIntegrationRequest
                .builder()
                .personHsaId(HSA_ID)
                .build()
        );

        final var captor = ArgumentCaptor.forClass(
            GetCredentialsForPersonIncludingProtectedPersonType.class);

        verify(
            getCredentialsForPersonIncludingProtectedPersonResponderInterface)
            .getCredentialsForPersonIncludingProtectedPerson(anyString(), captor.capture());

        assertEquals("extended1", captor.getValue().getProfile());
      }

      @Test
      void shouldSendLogicalAddressInRequest() {
        hsaAuthorizationClient.getCredentialInformation(
            GetCredentialInformationIntegrationRequest
                .builder()
                .personHsaId(HSA_ID)
                .build()
        );

        final var captor = ArgumentCaptor.forClass(String.class);

        verify(
            getCredentialsForPersonIncludingProtectedPersonResponderInterface)
            .getCredentialsForPersonIncludingProtectedPerson(captor.capture(),
                any(GetCredentialsForPersonIncludingProtectedPersonType.class));

        assertEquals(LOGICAL_ADDRESS, captor.getValue());
      }
    }
  }

  @Nested
  class LastUpdateTest {

    @Nested
    class UnexpectedError {

      @Test
      void shouldThrowErrorIfInterfaceThrowsError() {
        when(getHospLastUpdateResponderInterface
            .getHospLastUpdate(
                anyString(),
                any()
            )
        ).thenThrow(new IllegalStateException());

        assertThrows(IllegalStateException.class,
            () -> hsaAuthorizationClient.getLastUpdate());
      }
    }

    @Nested
    class CorrectResponseFromInterface {

      @BeforeEach
      void setup() {
        when(getHospLastUpdateResponderInterface
            .getHospLastUpdate(
                anyString(),
                any()
            )
        ).thenReturn(new GetHospLastUpdateResponseType());
      }

      @Test
      void shouldReturnResponseWithValueFromConverter() {
        when(getLastUpdateResponseTypeConverter.convert(any()))
            .thenReturn(LAST_UPDATE);

        final var response = hsaAuthorizationClient.getLastUpdate();

        assertEquals(LAST_UPDATE, response);
      }

      @Test
      void shouldSendLogicalAddressInRequest() {
        hsaAuthorizationClient.getLastUpdate();

        final var captor = ArgumentCaptor.forClass(String.class);

        verify(getHospLastUpdateResponderInterface)
            .getHospLastUpdate(captor.capture(), any());

        assertEquals(LOGICAL_ADDRESS, captor.getValue());
      }
    }
  }

  @Nested
  class CredentialsForPersonTest {

    @Nested
    class UnexpectedError {

      @Test
      void shouldThrowErrorIfInterfaceThrowsError() {
        when(getHospCredentialsForPersonResponderInterface
            .getHospCredentialsForPerson(
                anyString(),
                any(GetHospCredentialsForPersonType.class)
            )
        ).thenThrow(new IllegalStateException());

        assertThrows(IllegalStateException.class,
            () -> hsaAuthorizationClient.getCredentialsForPerson(FOR_PERSON_REQUEST));
      }

      @Test
      void shouldReturnNullIfResponseContainsKnownExceptionMessage() {
        when(getHospCredentialsForPersonResponderInterface
            .getHospCredentialsForPerson(
                anyString(),
                any(GetHospCredentialsForPersonType.class)
            )
        ).thenThrow(
            new IllegalStateException("Response message did not contain proper response data.")
        );

        assertNull(hsaAuthorizationClient.getCredentialsForPerson(FOR_PERSON_REQUEST));
      }
    }

    @Nested
    class CorrectResponseFromInterface {

      @BeforeEach
      void setup() {
        when(getHospCredentialsForPersonResponderInterface
            .getHospCredentialsForPerson(
                anyString(),
                any(GetHospCredentialsForPersonType.class)
            )
        ).thenReturn(new GetHospCredentialsForPersonResponseType());
      }

      @Test
      void shouldReturnResponseFromConverter() {
        final var expected = CredentialsForPerson.builder().build();
        when(getCredentialsForPersonResponseTypeConverter.convert(
                any(GetHospCredentialsForPersonResponseType.class)
            )
        ).thenReturn(expected);

        final var response = hsaAuthorizationClient.getCredentialsForPerson(FOR_PERSON_REQUEST);

        assertEquals(expected, response);
      }

      @Test
      void shouldSendPersonIdInRequest() {
        hsaAuthorizationClient.getCredentialsForPerson(FOR_PERSON_REQUEST);

        final var captor = ArgumentCaptor.forClass(GetHospCredentialsForPersonType.class);

        verify(getHospCredentialsForPersonResponderInterface)
            .getHospCredentialsForPerson(anyString(), captor.capture());

        assertEquals(FOR_PERSON_REQUEST.getPersonId(),
            captor.getValue().getPersonalIdentityNumber());
      }

      @Test
      void shouldSendLogicalAddressInRequest() {
        hsaAuthorizationClient.getCredentialsForPerson(
            GetCredentialsForPersonIntegrationRequest
                .builder()
                .personId(HSA_ID)
                .build()
        );

        final var captor = ArgumentCaptor.forClass(String.class);

        verify(getHospCredentialsForPersonResponderInterface)
            .getHospCredentialsForPerson(captor.capture(),
                any(GetHospCredentialsForPersonType.class));

        assertEquals(LOGICAL_ADDRESS, captor.getValue());
      }
    }
  }

  @Nested
  class HandleCertificationPersonTest {

    @Test
    void shouldThrowErrorIfOperationIsNotEnum() {
      final var request = HandleCertificationPersonIntegrationRequest.builder()
          .operation("NO_MATCH")
          .build();

      assertThrows(
          IllegalArgumentException.class,
          () -> hsaAuthorizationClient.handleCertificationPerson(request)
      );
    }

    @Nested
    class UnexpectedError {

      @Test
      void shouldThrowErrorIfInterfaceThrowsError() {
        when(handleHospCertificationPersonResponderInterface
            .handleHospCertificationPerson(
                anyString(),
                any(HandleHospCertificationPersonType.class)
            )
        ).thenThrow(new IllegalStateException());

        assertThrows(IllegalStateException.class,
            () -> hsaAuthorizationClient.handleCertificationPerson(HANDLE_PERSON_REQUEST));
      }
    }

    @Nested
    class CorrectResponseFromInterface {

      @BeforeEach
      void setup() {
        when(handleHospCertificationPersonResponderInterface
            .handleHospCertificationPerson(
                anyString(),
                any(HandleHospCertificationPersonType.class)
            )
        ).thenReturn(new HandleHospCertificationPersonResponseType());
      }

      @Test
      void shouldReturnResponseFromConverter() {
        final var expected = Result.builder().build();
        when(handleCertificationPersonResponseTypeConverter.convert(
                any(HandleHospCertificationPersonResponseType.class)
            )
        ).thenReturn(expected);

        final var response = hsaAuthorizationClient.handleCertificationPerson(
            HANDLE_PERSON_REQUEST);

        assertEquals(expected, response);
      }

      @Test
      void shouldSendPersonIdInRequest() {
        hsaAuthorizationClient.handleCertificationPerson(HANDLE_PERSON_REQUEST);

        final var captor = ArgumentCaptor.forClass(HandleHospCertificationPersonType.class);

        verify(handleHospCertificationPersonResponderInterface)
            .handleHospCertificationPerson(anyString(), captor.capture());

        assertEquals(HANDLE_PERSON_REQUEST.getPersonId(),
            captor.getValue().getPersonalIdentityNumber());
      }

      @Test
      void shouldSendCertificationIdInRequest() {
        hsaAuthorizationClient.handleCertificationPerson(HANDLE_PERSON_REQUEST);

        final var captor = ArgumentCaptor.forClass(HandleHospCertificationPersonType.class);

        verify(handleHospCertificationPersonResponderInterface)
            .handleHospCertificationPerson(anyString(), captor.capture());

        assertEquals(HANDLE_PERSON_REQUEST.getCertificationId(),
            captor.getValue().getCertificationId());
      }

      @Test
      void shouldSendReasonInRequest() {
        hsaAuthorizationClient.handleCertificationPerson(HANDLE_PERSON_REQUEST);

        final var captor = ArgumentCaptor.forClass(HandleHospCertificationPersonType.class);

        verify(handleHospCertificationPersonResponderInterface)
            .handleHospCertificationPerson(anyString(), captor.capture());

        assertEquals(HANDLE_PERSON_REQUEST.getReason(),
            captor.getValue().getReason());
      }

      @Test
      void shouldSendOperationInRequest() {
        hsaAuthorizationClient.handleCertificationPerson(HANDLE_PERSON_REQUEST);

        final var captor = ArgumentCaptor.forClass(HandleHospCertificationPersonType.class);

        verify(handleHospCertificationPersonResponderInterface)
            .handleHospCertificationPerson(anyString(), captor.capture());

        assertEquals(OperationEnum.ADD.toString(),
            captor.getValue().getOperation().toString());
      }

      @Test
      void shouldSendLogicalAddressInRequest() {
        hsaAuthorizationClient.handleCertificationPerson(HANDLE_PERSON_REQUEST);

        final var captor = ArgumentCaptor.forClass(String.class);

        verify(handleHospCertificationPersonResponderInterface)
            .handleHospCertificationPerson(captor.capture(), any());

        assertEquals(LOGICAL_ADDRESS, captor.getValue());
      }
    }
  }
}
