package se.inera.intyg.cts.testability.api;

import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.cts.testability.dto.TestabilityExportEmbeddableDTO;
import se.inera.intyg.cts.testability.dto.TestabilityTerminationDTO;
import se.inera.intyg.cts.testability.service.TestabilityTerminationService;

@RestController
@Profile("testability")
@RequestMapping("/testability/v1/terminations")
public class TestabilityTerminationController {

  private final TestabilityTerminationService testabilityTerminationService;

  public TestabilityTerminationController(
      TestabilityTerminationService testabilityTerminationService) {
    this.testabilityTerminationService = testabilityTerminationService;
  }

  @PostMapping
  void create(@RequestBody TestabilityTerminationDTO testabilityTerminationDTO) {
    testabilityTerminationService.createTermination(testabilityTerminationDTO);
  }

  @DeleteMapping("/{terminationId}")
  void delete(@PathVariable UUID terminationId) {
    testabilityTerminationService.deleteTermination(terminationId);
  }

  @GetMapping("/export/{terminationId}")
  TestabilityExportEmbeddableDTO getExportEmbeddable(@PathVariable UUID terminationId) {
    return testabilityTerminationService.getExportEmbeddable(terminationId);
  }

  @GetMapping("/{terminationId}")
  int getCertificatesCount(@PathVariable UUID terminationId) {
    return testabilityTerminationService.getCertificatesCount(terminationId);
  }
}
