package se.inera.intyg.intygproxyservice.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Request;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Service;
import se.inera.intyg.intygproxyservice.user.dto.UserRequest;
import se.inera.intyg.intygproxyservice.user.dto.UserResponse;

@Service
@RequiredArgsConstructor
public class UserService {

  private final Elva77Service elva77Service;
  private final Elva77ResponseConverter elva77ResponseConverter;

  public UserResponse findUser(UserRequest userRequest) {
    validateRequest(userRequest);

    final var elva77Response = elva77Service.findUser(
        Elva77Request.builder()
            .personId(userRequest.getPersonId())
            .build()
    );

    return UserResponse.builder()
        .user(elva77ResponseConverter.convert(elva77Response.getUser()))
        .build();
  }

  private void validateRequest(UserRequest userRequest) {
    if (userRequest == null) {
      throw new IllegalArgumentException("Invalid request, UserRequest is null");
    }

    if (userRequest.getPersonId() == null || userRequest.getPersonId().isBlank()) {
      throw new IllegalArgumentException("Invalid request, PersonId is missing");
    }
  }
}