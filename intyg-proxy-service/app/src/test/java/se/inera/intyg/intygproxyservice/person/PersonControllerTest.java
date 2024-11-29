package se.inera.intyg.intygproxyservice.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.person.dto.PersonRequest;
import se.inera.intyg.intygproxyservice.person.dto.PersonResponse;
import se.inera.intyg.intygproxyservice.person.service.PersonService;

@ExtendWith(MockitoExtension.class)
class PersonControllerTest {

  @Mock
  private PersonService personService;

  @InjectMocks
  private PersonController personController;

  @Test
  void shallReturnPersonResponseWhenCallingFindPerson() {
    final var expectedPersonResponse = PersonResponse.builder().build();

    doReturn(expectedPersonResponse)
        .when(personService)
        .findPerson(any(PersonRequest.class));

    final var actualPersonResponse = personController.findPerson(PersonRequest.builder().build());

    assertEquals(expectedPersonResponse, actualPersonResponse);
  }
}