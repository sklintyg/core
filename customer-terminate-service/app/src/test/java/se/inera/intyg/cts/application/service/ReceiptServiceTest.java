package se.inera.intyg.cts.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import se.inera.intyg.cts.domain.model.Termination;
import se.inera.intyg.cts.domain.model.TerminationBuilder;
import se.inera.intyg.cts.domain.model.TerminationId;
import se.inera.intyg.cts.domain.model.TerminationStatus;
import se.inera.intyg.cts.domain.repository.TerminationRepository;

@ExtendWith(MockitoExtension.class)
class ReceiptServiceTest {

  private static final UUID TERMINATION_UUID = UUID.randomUUID();
  @Spy
  Termination termination = TerminationBuilder.getInstance().terminationId(TERMINATION_UUID)
      .created(LocalDateTime.now()).creatorHSAId("creatorHSAId").creatorName("creatorName")
      .careProviderHSAId("2-orgnr-ALFA").careProviderOrganizationNumber("2-orgnr-ALFA")
      .careProviderOrganizationRepresentativePersonId("191212121212")
      .careProviderOrganizationRepresentativePhoneNumber("phoneNumber")
      .status(TerminationStatus.CREATED).packagePassword("Password").create();
  @Mock
  private TerminationRepository terminationRepository;
  @InjectMocks
  private ReceiptService receiptService;

  @Test
  public void testHandleReceipt() {
    when(terminationRepository.findByTerminationId(any(TerminationId.class))).thenReturn(Optional.of(termination));

    receiptService.handleReceipt(TERMINATION_UUID);

    verify(terminationRepository, times(1)).findByTerminationId(any(TerminationId.class));
    verify(termination, times(1)).receiptReceived(any(LocalDateTime.class));
    verify(terminationRepository, times(1)).store(termination);
  }

  @Test()
  public void testHandleTerminationNotFound() {
    when(terminationRepository.findByTerminationId(any(TerminationId.class))).thenReturn(Optional.empty());

    ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
      receiptService.handleReceipt(TERMINATION_UUID);
    });

    assertEquals(exception.getStatus(), HttpStatus.NOT_FOUND);
 }
}
