package se.inera.intyg.certificateservice.application.certificate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteStaleDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.DeleteStaleDraftsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ListStaleDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ListStaleDraftsResponse;
import se.inera.intyg.certificateservice.application.certificate.service.DeleteStaleDraftsInternalService;

@ExtendWith(MockitoExtension.class)
class DraftInternalApiControllerTest {

  @Mock
  private DeleteStaleDraftsInternalService deleteStaleDraftsInternalService;

  @InjectMocks
  private DraftInternalApiController draftInternalApiController;

  @Test
  void shouldReturnListDraftsResponse() {
    final var expectedResult = ListStaleDraftsResponse.builder().build();
    final var request = ListStaleDraftsRequest.builder().build();
    doReturn(expectedResult).when(deleteStaleDraftsInternalService).list(request);

    final var actualResult = draftInternalApiController.listStaleDrafts(request);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shouldReturnDeleteDraftsResponse() {
    final var expectedResult = DeleteStaleDraftsResponse.builder().build();
    final var request = DeleteStaleDraftsRequest.builder().build();
    doReturn(expectedResult).when(deleteStaleDraftsInternalService).delete(request);

    final var actualResult = draftInternalApiController.deleteDrafts(request);
    assertEquals(expectedResult, actualResult);
  }
}

