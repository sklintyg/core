package se.inera.intyg.intygproxyservice.integration.pu.v5;

import static se.inera.intyg.intygproxyservice.integration.api.constants.PuConstants.PU_PROFILE_V5;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuRequest;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuService;
import se.inera.intyg.intygproxyservice.integration.pu.v5.batch.BatchUtil;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile(PU_PROFILE_V5)
public class PuBatchIntegrationServiceV5 implements PuService {

  private final PuIntegrationServiceV5 puIntegrationServiceV5;

  @Value("${integration.pu.batch.size}")
  private int batchSize;

  @Override
  public PuResponse findPerson(PuRequest puRequest) {
    return puIntegrationServiceV5.findPerson(puRequest);
  }

  @Override
  public PuPersonsResponse findPersons(PuPersonsRequest puRequest) {
    final var batches = BatchUtil.split(puRequest.getPersonIds(), batchSize);
    return PuPersonsResponse.builder()
        .persons(
            batches.stream()
                .map(batch -> PuPersonsRequest.builder().personIds(batch).build())
                .map(puIntegrationServiceV5::findPersons)
                .map(PuPersonsResponse::getPersons)
                .flatMap(List::stream)
                .toList()
        )
        .build();
  }
}
