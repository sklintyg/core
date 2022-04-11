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
import se.inera.intyg.cts.application.service.TerminationService;

@RestController
@RequestMapping("/api/v1/terminations")
public class TerminationController {

  private final TerminationService terminationService;

  public TerminationController(TerminationService terminationService) {
    this.terminationService = terminationService;
  }

  @PostMapping
  TerminationDTO create(@RequestBody CreateTerminationDTO request) {
    try {
      return terminationService.create(request);
    } catch (IllegalArgumentException ex) {
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
}
