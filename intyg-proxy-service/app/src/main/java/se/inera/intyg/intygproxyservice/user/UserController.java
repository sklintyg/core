package se.inera.intyg.intygproxyservice.user;

import static se.inera.intyg.intygproxyservice.logging.MdcLogConstants.EVENT_TYPE_ACCESSED;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.intygproxyservice.logging.PerformanceLogging;
import se.inera.intyg.intygproxyservice.user.dto.UserRequest;
import se.inera.intyg.intygproxyservice.user.dto.UserResponse;
import se.inera.intyg.intygproxyservice.user.service.UserService;

@RestController()
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("")
  @PerformanceLogging(eventAction = "find-user", eventType = EVENT_TYPE_ACCESSED)
  UserResponse findUser(@RequestBody UserRequest userRequest) {
    return userService.findUser(userRequest);
  }
}