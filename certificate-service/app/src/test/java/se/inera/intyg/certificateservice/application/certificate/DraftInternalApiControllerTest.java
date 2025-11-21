package se.inera.intyg.certificateservice.application.certificate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.DisposeObsoleteDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.DisposeObsoleteDraftsResponse;
import se.inera.intyg.certificateservice.application.certificate.dto.ListObsoleteDraftsRequest;
import se.inera.intyg.certificateservice.application.certificate.dto.ListObsoleteDraftsResponse;
import se.inera.intyg.certificateservice.application.certificate.service.DisposeObsoleteDraftsInternalService;

@ExtendWith(MockitoExtension.class)
class DraftInternalApiControllerTest {

  @Mock
  private DisposeObsoleteDraftsInternalService disposeObsoleteDraftsInternalService;

  @InjectMocks
  private DraftInternalApiController draftInternalApiController;

  @Test
  void shouldReturnListDraftsResponse() {
    final var expectedResult = ListObsoleteDraftsResponse.builder().build();
    final var request = ListObsoleteDraftsRequest.builder().build();
    doReturn(expectedResult).when(disposeObsoleteDraftsInternalService).list(request);

    final var actualResult = draftInternalApiController.listObsoleteDrafts(request);
    assertEquals(expectedResult, actualResult);
  }

  @Test
  void shouldReturnDeleteDraftsResponse() {
    final var expectedResult = DisposeObsoleteDraftsResponse.builder().build();
    final var request = DisposeObsoleteDraftsRequest.builder().build();
    doReturn(expectedResult).when(disposeObsoleteDraftsInternalService).delete(request);

    final var actualResult = draftInternalApiController.deleteDrafts(request);
    assertEquals(expectedResult, actualResult);
  }
}

