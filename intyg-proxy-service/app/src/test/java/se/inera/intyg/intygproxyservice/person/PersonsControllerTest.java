package se.inera.intyg.intygproxyservice.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.person.dto.PersonsRequest;
import se.inera.intyg.intygproxyservice.person.dto.PersonsResponse;
import se.inera.intyg.intygproxyservice.person.service.PersonsService;

@ExtendWith(MockitoExtension.class)
class PersonsControllerTest {

  @Mock
  private PersonsService personsService;

  @InjectMocks
  private PersonsController personsController;

  @Test
  void shallReturnPersonResponseWhenCallingFindPerson() {
    final var expectedPersonsResponse = PersonsResponse.builder().build();

    doReturn(expectedPersonsResponse)
        .when(personsService)
        .findPersons(any(PersonsRequest.class));

    final var actualPersonResponse = personsController.findPersons(
        PersonsRequest.builder().build());

    assertEquals(expectedPersonsResponse, actualPersonResponse);
  }
}