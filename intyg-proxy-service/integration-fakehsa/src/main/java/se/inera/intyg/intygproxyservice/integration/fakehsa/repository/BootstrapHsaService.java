package se.inera.intyg.intygproxyservice.integration.fakehsa.repository;

import static se.inera.intyg.intygproxyservice.integration.api.constants.HsaConstants.FAKE_HSA_PROFILE;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCareProvider;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedCredentialInformation;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson;

@Slf4j
@Service
@RequiredArgsConstructor
@Profile(FAKE_HSA_PROFILE)
public class BootstrapHsaService {

  private final FakeHsaRepository fakeHsaRepository;
  private final ObjectMapper objectMapper;

  @PostConstruct
  public void bootstrapVardgivare() throws IOException {

    List<Resource> files = getResourceListing("bootstrap-careprovider/*.json");
    log.info("Bootstrapping {} careprovider for fake hsa ...", files.size());
    for (Resource resource : files) {
      addCareProvider(resource);
    }

    files = getResourceListing("bootstrap-person/*.json");
    log.info("Bootstrapping {} persons for fake hsa ...", files.size());
    for (Resource resource : files) {
      addHsaPerson(resource);
      addCredentialInformation(resource);
    }

    files = getResourceListing("bootstrap-hospperson/*.json");
    log.info("Bootstrapping {} hospperson for fake hsa ...", files.size());
    for (Resource resource : files) {
      addHsaPerson(resource);
      addCredentialInformation(resource);
    }
  }

  private void addCredentialInformation(Resource resource) throws IOException {
    final var parsedCredentialInformation = objectMapper.readValue(
        resource.getInputStream(),
        ParsedCredentialInformation.class);
    fakeHsaRepository.addParsedCredentialInformation(parsedCredentialInformation);
  }

  private void addHsaPerson(Resource resource) throws IOException {
    final var parsedHsaPerson = objectMapper.readValue(resource.getInputStream(),
        ParsedHsaPerson.class);
    fakeHsaRepository.addParsedHsaPerson(parsedHsaPerson);
  }

  private void addCareProvider(Resource resource) throws IOException {
    final var careProvider = objectMapper.readValue(resource.getInputStream(),
        ParsedCareProvider.class);
    fakeHsaRepository.addParsedCareProvider(careProvider);
  }

  private List<Resource> getResourceListing(String classpathResourcePath) {
    try {
      final var pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
      return Arrays.asList(pathMatchingResourcePatternResolver.getResources(classpathResourcePath));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
