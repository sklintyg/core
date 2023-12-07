package se.inera.intyg.intygproxyservice.integration.authorization.client;

import static org.junit.jupiter.api.Assertions.*;
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
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialInformation;
import se.inera.intyg.intygproxyservice.integration.authorization.client.converter.GetCredentialInformationResponseTypeConverter;
import se.inera.intyg.intygproxyservice.integration.authorization.client.converter.GetLastUpdateResponseTypeConverter;
import se.riv.infrastructure.directory.authorizationmanagement.getcredentialsforpersonincludingprotectedperson.v2.rivtabp21.GetCredentialsForPersonIncludingProtectedPersonResponderInterface;
import se.riv.infrastructure.directory.authorizationmanagement.getcredentialsforpersonincludingprotectedpersonresponder.v2.GetCredentialsForPersonIncludingProtectedPersonResponseType;
import se.riv.infrastructure.directory.authorizationmanagement.getcredentialsforpersonincludingprotectedpersonresponder.v2.GetCredentialsForPersonIncludingProtectedPersonType;
import se.riv.infrastructure.directory.authorizationmanagement.gethosplastupdate.v1.rivtabp21.GetHospLastUpdateResponderInterface;
import se.riv.infrastructure.directory.authorizationmanagement.gethosplastupdateresponder.v1.GetHospLastUpdateResponseType;

@ExtendWith(MockitoExtension.class)
class HsaAuthorizationClientTest {

  private static final String HSA_ID = "HSA_ID";

  private static final LocalDateTime LAST_UPDATE = LocalDateTime.now();

  private static final GetCredentialInformationIntegrationRequest REQUEST = GetCredentialInformationIntegrationRequest
      .builder()
      .personHsaId(HSA_ID)
      .build();


  private static final String LOGICAL_ADDRESS = "LOGICAL_ADDRESS";

  private static final List<CredentialInformation> CREDENTIALS = List.of(
      CredentialInformation.builder().build());

  @Mock
  GetCredentialInformationResponseTypeConverter getCredentialInformationResponseTypeConverter;

  @Mock
  GetLastUpdateResponseTypeConverter getLastUpdateResponseTypeConverter;

  @Mock
  GetCredentialsForPersonIncludingProtectedPersonResponderInterface getCredentialsForPersonIncludingProtectedPersonResponderInterface;

  @Mock
  GetHospLastUpdateResponderInterface getHospLastUpdateResponderInterface;

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

        assertEquals("basic", captor.getValue().getProfile());
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
}