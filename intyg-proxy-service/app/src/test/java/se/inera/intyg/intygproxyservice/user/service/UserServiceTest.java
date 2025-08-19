package se.inera.intyg.intygproxyservice.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Request;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Response;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Service;
import se.inera.intyg.intygproxyservice.integration.api.elva77.User;
import se.inera.intyg.intygproxyservice.user.dto.UserDTO;
import se.inera.intyg.intygproxyservice.user.dto.UserRequest;
import se.inera.intyg.intygproxyservice.user.dto.UserResponse;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

  private static final String PERSON_ID = "personId";

  @Mock
  Elva77Service elva77Service;
  @Mock
  Elva77ResponseConverter elva77ResponseConverter;
  @InjectMocks
  UserService userService;

  @Nested
  class ValidateRequest {

    @Test
    void shouldThrowIfRequestIsNull() {
      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> userService.findUser(null));
      assertEquals("Invalid request, UserRequest is null", illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfPersonIdInRequestIsNull() {
      final var request = UserRequest.builder()
          .personId(null)
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> userService.findUser(request));
      assertEquals("Invalid request, PersonId is missing", illegalArgumentException.getMessage());
    }

    @Test
    void shouldThrowIfPersonIdInRequestIsEmpty() {
      final var request = UserRequest.builder()
          .personId("")
          .build();

      final var illegalArgumentException = assertThrows(IllegalArgumentException.class,
          () -> userService.findUser(request));
      assertEquals("Invalid request, PersonId is missing", illegalArgumentException.getMessage());
    }
  }

  @Test
  void shouldReturnUserResponse() {
    final var userDTO = UserDTO.builder().build();
    final var expectedResponse = UserResponse.builder()
        .user(userDTO)
        .build();

    final var request = UserRequest.builder()
        .personId(PERSON_ID)
        .build();

    final var elva77Request = Elva77Request.builder()
        .personId(PERSON_ID)
        .build();

    final var user = User.builder().build();
    final var elva77Response = Elva77Response.builder()
        .user(user)
        .build();

    when(elva77Service.findUser(elva77Request)).thenReturn(elva77Response);
    when(elva77ResponseConverter.convert(user)).thenReturn(userDTO);

    final var response = userService.findUser(request);
    assertEquals(expectedResponse, response);
  }
}