package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import jakarta.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificate.repository.StatisticsRepository;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.unit.model.UnitStatistics;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateStatus;

@Repository
@RequiredArgsConstructor
public class JpaStatisticsRepository implements StatisticsRepository {

  private final EntityManager entityManager;

  @Override
  public Map<HsaId, UnitStatistics> getStatisticsForUnits(List<HsaId> availableUnitIds,
      boolean allowedToViewProtectedPerson) {
    final var certificateJpql =
        "SELECT u.hsaId, COUNT(c.id) " +
            "FROM CertificateEntity c " +
            "LEFT JOIN UnitEntity u ON c.issuedOnUnit.key = u.key " +
            "LEFT JOIN c.patient p " +
            "WHERE u.hsaId IN :hsaIds " +
            "AND c.status.status IN :certificateStatusesForDrafts " +
            (allowedToViewProtectedPerson
                ? ""
                : "AND p.protectedPerson = false ") +
            "GROUP BY u.hsaId";

    final var messageJpql = "SELECT u.hsaId, COUNT(m.id) " +
        "FROM MessageEntity m " +
        "LEFT JOIN m.certificate c " +
        "LEFT JOIN c.issuedOnUnit u " +
        "LEFT JOIN c.patient p " +
        "WHERE u.hsaId IN :hsaIds " +
        "AND m.status.status NOT IN :messageStatusHandledOrDraft " +
        (allowedToViewProtectedPerson
            ? ""
            : "AND p.protectedPerson = false ") +
        "GROUP BY u.hsaId";

    final var hsaIds = availableUnitIds.stream()
        .map(HsaId::id)
        .toList();

    final var draftCertificatesOnAvailableUnits = entityManager.createQuery(certificateJpql,
            Object[].class)
        .setParameter("hsaIds", hsaIds)
        .setParameter("certificateStatusesForDrafts",
            List.of(CertificateStatus.DRAFT.name(), CertificateStatus.LOCKED_DRAFT.name()))
        .setParameter("allowedToViewProtectedPerson", allowedToViewProtectedPerson)
        .getResultList();

    final var messagesWithUnhandledQuestionsOnAvailableUnits = entityManager.createQuery(
            messageJpql,
            Object[].class)
        .setParameter("hsaIds", hsaIds)
        .setParameter("messageStatusHandledOrDraft",
            List.of(MessageStatus.HANDLED.name(), MessageStatus.DRAFT.name()))
        .setParameter("allowedToViewProtectedPerson", allowedToViewProtectedPerson)
        .getResultList();

    final var statisticsMap = new HashMap<HsaId, UnitStatistics>();
    for (var result : draftCertificatesOnAvailableUnits) {
      final var hsaId = (String) result[0];
      final var certificateCount = (Long) result[1];
      statisticsMap.put(new HsaId(hsaId), new UnitStatistics(certificateCount, 0));
    }

    for (var result : messagesWithUnhandledQuestionsOnAvailableUnits) {
      final var hsaId = (String) result[0];
      final var messageCount = (Long) result[1];
      statisticsMap.merge(new HsaId(hsaId),
          new UnitStatistics(0, messageCount),
          (existing, newStat) -> {
            existing.messageCount(newStat.messageCount());
            return existing;
          });
    }

    return statisticsMap;
  }
}
