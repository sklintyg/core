package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import jakarta.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificate.repository.StatisticsRepository;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.unit.model.UnitStatistics;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateStatus;

@Repository
@RequiredArgsConstructor
public class JpaStatisticsRepository implements StatisticsRepository {

  private static final String CERTIFICATE_JPQL = "SELECT u.hsaId, COUNT(c.id) " +
      "FROM CertificateEntity c " +
      "LEFT JOIN UnitEntity u ON c.issuedOnUnit.key = u.key " +
      "LEFT JOIN c.patient p " +
      "WHERE u.hsaId IN :hsaIds " +
      "AND c.status.status IN :certificateStatusesForDrafts " +
      "AND (p.protectedPerson = false "
      + "OR (:allowedToViewProtectedPerson = true AND p.protectedPerson = true)) " +
      "GROUP BY u.hsaId";

  private static final String MESSAGE_JPQL = "SELECT u.hsaId, COUNT(m.id) " +
      "FROM MessageEntity m " +
      "LEFT JOIN m.certificate c " +
      "LEFT JOIN c.issuedOnUnit u " +
      "LEFT JOIN c.patient p " +
      "WHERE u.hsaId IN :hsaIds " +
      "AND (m.status.status != :messageStatusHandled AND m.status.status != :messageStatusDraft) " +
      "AND (p.protectedPerson = false "
      + "OR (:allowedToViewProtectedPerson = true AND p.protectedPerson = true)) " +
      "GROUP BY u.hsaId";

  private final EntityManager entityManager;

  @Override
  public Map<String, UnitStatistics> getStatisticsForUnits(List<String> availableUnitIds,
      boolean allowedToViewProtectedPerson) {
    final var draftCertificatesOnAvailableUnits = entityManager.createQuery(CERTIFICATE_JPQL,
            Object[].class)
        .setParameter("hsaIds", availableUnitIds)
        .setParameter("certificateStatusesForDrafts",
            List.of(CertificateStatus.DRAFT.name(), CertificateStatus.LOCKED_DRAFT.name()))
        .setParameter("allowedToViewProtectedPerson", allowedToViewProtectedPerson)
        .getResultList();

    final var messagesWithUnhandledQuestionsOnAvailableUnits = entityManager.createQuery(
            MESSAGE_JPQL,
            Object[].class)
        .setParameter("hsaIds", availableUnitIds)
        .setParameter("messageStatusHandled", MessageStatus.HANDLED.name())
        .setParameter("allowedToViewProtectedPerson", allowedToViewProtectedPerson)
        .setParameter("messageStatusDraft", MessageStatus.DRAFT.name())
        .getResultList();

    final var statisticsMap = new HashMap<String, UnitStatistics>();
    for (var result : draftCertificatesOnAvailableUnits) {
      final var hsaId = (String) result[0];
      final var certificateCount = (Long) result[1];
      statisticsMap.put(hsaId, new UnitStatistics(certificateCount, 0));
    }

    for (var result : messagesWithUnhandledQuestionsOnAvailableUnits) {
      final var hsaId = (String) result[0];
      final var messageCount = (Long) result[1];
      statisticsMap.merge(hsaId,
          new UnitStatistics(0, messageCount),
          (existing, newStat) -> {
            existing.messageCount(newStat.messageCount());
            return existing;
          });
    }

    return statisticsMap;
  }
}
