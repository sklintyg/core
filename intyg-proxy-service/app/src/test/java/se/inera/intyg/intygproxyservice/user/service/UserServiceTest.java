package se.inera.intyg.intygproxyservice.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.user.dto.UserRequest;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

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
}