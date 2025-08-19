package se.inera.intyg.intygproxyservice.user;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.user.dto.UserDTO;
import se.inera.intyg.intygproxyservice.user.dto.UserRequest;
import se.inera.intyg.intygproxyservice.user.dto.UserResponse;
import se.inera.intyg.intygproxyservice.user.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  private static final UserRequest REQUEST = UserRequest.builder().build();

  @Mock
  UserService userService;
  @InjectMocks
  UserController userController;

  @Test
  void shouldFindUser() {
    final var expectedResponse = UserResponse.builder()
        .user(UserDTO.builder().build())
        .build();

    when(userService.findUser(REQUEST)).thenReturn(expectedResponse);

    final var response = userController.findUser(REQUEST);
    assertEquals(expectedResponse, response);
  }
}