package se.inera.intyg.intygproxyservice.integration.fakepu.repository;

import static se.inera.intyg.intygproxyservice.integration.api.pu.PuConstants.FAKEPU_PROFILE;
import static se.inera.intyg.intygproxyservice.integration.fakepu.repository.PersonConverter.convert;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedPerson;

@Service
@Slf4j
@AllArgsConstructor
@Profile(FAKEPU_PROFILE)
public class BootstrapPersonsService {

  public static final String LOCATION_PATTERN = "bootstrap-persons/*.json";
  private final FakePuRepository fakePuRepository;
  private final ObjectMapper objectMapper;

  @PostConstruct
  public void bootstrapPersoner() {
    final var files = getResourceListing();
    log.info("Bootstrapping {} personer for PU stub ...", files.size());
    for (Resource res : files) {
      try {
        addPerson(res);
      } catch (Exception e) {
        log.error("Could not add person!", e);
      }
    }
  }

  private List<Resource> getResourceListing() {
    try {
      final var r = new PathMatchingResourcePatternResolver();
      return Arrays.asList(r.getResources(LOCATION_PATTERN));
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  private void addPerson(Resource res) throws IOException {
    log.debug("Loading person from " + res.getFilename());

    final var parsedPerson = objectMapper.readValue(res.getInputStream(), ParsedPerson.class);
    fakePuRepository.addPerson(
        convert(parsedPerson)
    );

    log.debug("Loaded person " + parsedPerson.getPersonalIdentity().getExtension());
  }
}
