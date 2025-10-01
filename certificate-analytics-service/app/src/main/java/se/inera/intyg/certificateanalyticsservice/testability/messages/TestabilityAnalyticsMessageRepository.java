package se.inera.intyg.certificateanalyticsservice.testability.messages;

import static se.inera.intyg.certificateanalyticsservice.testability.configuration.TestabilityConfiguration.TESTABILITY_PROFILE;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.EventMapper;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.EventEntityRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.JpaAnalyticsEventRepository;

@Primary
@Repository
@Profile(TESTABILITY_PROFILE)
public class TestabilityAnalyticsMessageRepository extends
    JpaAnalyticsEventRepository {

  public TestabilityAnalyticsMessageRepository(
      EventEntityRepository eventEntityRepository,
      EventMapper eventMapper) {
    super(eventEntityRepository, eventMapper);
  }

  @Override
  public void clear() {
    getEventEntityRepository().deleteAll();
  }
}
