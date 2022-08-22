package se.inera.intyg.cts.application.api;

import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import se.inera.intyg.cts.application.dto.CreateTerminationDTO;
import se.inera.intyg.cts.application.dto.TerminationDTO;
import se.inera.intyg.cts.application.dto.UpdateTerminationDTO;
import se.inera.intyg.cts.application.service.EraseService;
import se.inera.intyg.cts.application.service.TerminationService;
import se.inera.intyg.cts.domain.model.TerminationId;

@RestController
@RequestMapping("/api/v1/terminations")
public class TerminationController {

  private final TerminationService terminationService;
  private final EraseService eraseService;

  public TerminationController(TerminationService terminationService, EraseService eraseService) {
    this.terminationService = terminationService;
    this.eraseService = eraseService;
  }

  @PostMapping
  TerminationDTO create(@RequestBody CreateTerminationDTO request) {
    try {
      return terminationService.create(request);
    } catch (IllegalArgumentException ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
    }
  }

  @PostMapping("/{terminationId}")
  TerminationDTO update(@PathVariable UUID terminationId,
      @RequestBody UpdateTerminationDTO updateTerminationDTO) {
    try {
      return terminationService.update(terminationId, updateTerminationDTO);
    } catch (IllegalArgumentException | IllegalStateException ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
    }
  }

  @GetMapping("/{terminationId}")
  TerminationDTO findById(@PathVariable UUID terminationId) {
    return terminationService.findById(terminationId)
        .orElseThrow(
            () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                String.format("Couldn't find termination with id: %s", terminationId))
        );
  }

  @GetMapping
  List<TerminationDTO> findAll() {
    return terminationService.findAll();
  }

  @PostMapping("/{terminationId}/erase")
  TerminationDTO startErase(@PathVariable UUID terminationId) {
    try {
      return eraseService.initiateErase(new TerminationId(terminationId));
    } catch (IllegalArgumentException ex) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage(), ex);
    }
  }

  /**
   * Trigger a resend of the password
   * @param terminationId termination id tied to the termination password that should be resent
   * @return updated termination
   */
  @PostMapping("/{terminationId}/resendpassword")
  TerminationDTO resendPassword(@PathVariable UUID terminationId) {
    try {
      return terminationService.resendPassword(terminationId);
    } catch (IllegalArgumentException ex) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, ex.getMessage());
    } catch (RuntimeException ex) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, String.format("Couldn't find termination with id: %s", terminationId), ex);
    }
  }
}
