package se.inera.intyg.certificateanalyticsservice.testability.messages;

import static se.inera.intyg.certificateanalyticsservice.testability.configuration.TestabilityConfiguration.TESTABILITY_PROFILE;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.EventMapper;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.CertificateRelationEntityRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.EventEntityRepository;
import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.repository.JpaAnalyticsEventRepository;

@Primary
@Repository
@Profile(TESTABILITY_PROFILE)
public class TestabilityAnalyticsMessageRepository extends
    JpaAnalyticsEventRepository {

  private final CertificateRelationEntityRepository certificateRelationEntityRepository;

  public TestabilityAnalyticsMessageRepository(
      EventEntityRepository eventEntityRepository,
      EventMapper eventMapper,
      CertificateRelationEntityRepository certificateRelationEntityRepository) {
    super(eventEntityRepository, eventMapper);
    this.certificateRelationEntityRepository = certificateRelationEntityRepository;
  }

  @Override
  @Transactional
  public void clear() {
    getEventEntityRepository().deleteAll();
    certificateRelationEntityRepository.deleteAll();
  }
}
