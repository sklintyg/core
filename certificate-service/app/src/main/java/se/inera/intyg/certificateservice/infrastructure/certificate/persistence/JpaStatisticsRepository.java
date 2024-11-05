package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import jakarta.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.certificate.repository.StatisticsRepository;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.unit.model.UnitStatistics;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateStatus;

@Repository
@RequiredArgsConstructor
public class JpaStatisticsRepository implements StatisticsRepository {

  private final EntityManager entityManager;

  @Override
  public Map<HsaId, UnitStatistics> getStatisticsForUnits(List<HsaId> issuedByUnitIds,
      List<CertificateModelId> allowedTypesToViewProtectedPerson) {

    final var certificateModelTypes = allowedTypesToViewProtectedPerson.stream()
        .map(certificateModelId -> certificateModelId.type().type())
        .toList();

    final var certificateJpql = "SELECT u.hsaId, COUNT(c.id) " +
        "FROM CertificateEntity c " +
        "INNER JOIN UnitEntity u ON c.issuedOnUnit.key = u.key " +
        "INNER JOIN c.patient p " +
        "WHERE u.hsaId IN :hsaIds " +
        "AND c.status.status IN :certificateStatusesForDrafts " +
        "AND (p.protectedPerson = false " +
        "OR (p.protectedPerson = true AND c.certificateModel.type IN (:certificateModelTypes))) " +
        "GROUP BY u.hsaId";

    final var messageJpql = "SELECT u.hsaId, COUNT(m.id) " +
        "FROM MessageEntity m " +
        "INNER JOIN m.certificate c " +
        "INNER JOIN c.issuedOnUnit u " +
        "INNER JOIN c.patient p " +
        "LEFT JOIN MessageRelationEntity mr on m.key = mr.childMessage.key " +
        "WHERE u.hsaId IN :hsaIds " +
        "AND m.status.status NOT IN :messageStatusHandledOrDraft " +
        "AND (p.protectedPerson = false " +
        "OR (p.protectedPerson = true AND c.certificateModel.type IN (:certificateModelTypes)))" +
        "AND mr.parentMessage IS NULL " +
        "GROUP BY u.hsaId";

    final var hsaIds = issuedByUnitIds.stream()
        .map(HsaId::id)
        .toList();

    final var draftCertificatesIssuedOnUnits = entityManager.createQuery(certificateJpql,
            Object[].class)
        .setParameter("hsaIds", hsaIds)
        .setParameter("certificateStatusesForDrafts",
            List.of(CertificateStatus.DRAFT.name(), CertificateStatus.LOCKED_DRAFT.name()))
        .setParameter("certificateModelTypes", certificateModelTypes)
        .getResultList();

    final var messagesWithUnhandledQuestionsIssuedOnUnits = entityManager.createQuery(
            messageJpql,
            Object[].class)
        .setParameter("hsaIds", hsaIds)
        .setParameter("messageStatusHandledOrDraft",
            List.of(MessageStatus.HANDLED.name(), MessageStatus.DRAFT.name()))
        .setParameter("certificateModelTypes", certificateModelTypes)
        .getResultList();

    final var statisticsMap = new HashMap<HsaId, UnitStatistics>();
    for (var result : draftCertificatesIssuedOnUnits) {
      final var hsaId = (String) result[0];
      final var certificateCount = (Long) result[1];
      statisticsMap.put(new HsaId(hsaId), new UnitStatistics(certificateCount.intValue(), 0));
    }

    for (var result : messagesWithUnhandledQuestionsIssuedOnUnits) {
      final var hsaId = (String) result[0];
      final var messageCount = (Long) result[1];
      statisticsMap.merge(new HsaId(hsaId),
          new UnitStatistics(0, messageCount.intValue()),
          (existing, newStat) -> existing.withMessageCount(newStat.messageCount())
      );
    }

    return statisticsMap;
  }
}
