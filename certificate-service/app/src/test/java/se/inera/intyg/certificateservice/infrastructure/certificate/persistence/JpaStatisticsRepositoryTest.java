package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_ID;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.unit.model.UnitStatistics;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateStatus;

@ExtendWith(MockitoExtension.class)
class JpaStatisticsRepositoryTest {

  private static final String CERTIFICATE_JPQL = "SELECT u.hsaId, COUNT(c.id) "
      + "FROM CertificateEntity c "
      + "INNER JOIN UnitEntity u ON c.issuedOnUnit.key = u.key "
      + "INNER JOIN c.patient p "
      + "WHERE u.hsaId IN :hsaIds "
      + "AND c.status.status IN :certificateStatusesForDrafts "
      + "AND (p.protectedPerson = false "
      + "OR (p.protectedPerson = true AND c.certificateModel.type IN (:certificateModelTypes))) "
      + "GROUP BY u.hsaId";

  private static final String MESSAGE_JPQL = "SELECT u.hsaId, COUNT(m.id) "
      + "FROM MessageEntity m "
      + "INNER JOIN m.certificate c "
      + "INNER JOIN c.issuedOnUnit u "
      + "INNER JOIN c.patient p "
      + "LEFT JOIN MessageRelationEntity mr on m.key = mr.childMessage.key "
      + "WHERE u.hsaId IN :hsaIds "
      + "AND m.status.status NOT IN :messageStatusHandledOrDraft "
      + "AND (p.protectedPerson = false "
      + "OR (p.protectedPerson = true AND c.certificateModel.type IN (:certificateModelTypes)))"
      + "AND mr.parentMessage IS NULL "
      + "GROUP BY u.hsaId";

  private static final String UNIT_1 = "unit1";
  private static final String UNIT_2 = "unit2";
  @Mock
  private EntityManager entityManager;
  @Mock
  private TypedQuery<Object[]> certificateQuery;
  @Mock
  private TypedQuery<Object[]> query;

  @InjectMocks
  private JpaStatisticsRepository jpaStatisticsRepository;

  @Test
  void shallHandleEmptyResultsCorrectly() {
    doReturn(query).when(entityManager).createQuery(any(String.class), any());
    doReturn(query).when(query).setParameter(any(String.class), any());
    doReturn(List.of()).when(query).getResultList();

    final var actualStatistics = jpaStatisticsRepository.getStatisticsForUnits(
        List.of(new HsaId(UNIT_1)), List.of(FK7210_ID));

    assertTrue(actualStatistics.isEmpty());
  }

  @Test
  void shallMergeStatisticsCorrectlyForMultipleUnits() {
    final var draftCertificatesResult = List.of(
        new Object[]{UNIT_1, 3L},
        new Object[]{UNIT_2, 3L}
    );
    final var messagesResult = List.of(
        new Object[]{UNIT_1, 3L},
        new Object[]{UNIT_2, 3L}
    );

    doReturn(certificateQuery).when(entityManager).createQuery(eq(CERTIFICATE_JPQL), any());
    doReturn(certificateQuery).when(certificateQuery).setParameter(any(String.class), any());
    doReturn(draftCertificatesResult).when(certificateQuery).getResultList();

    doReturn(query).when(entityManager).createQuery(eq(MESSAGE_JPQL), any());
    doReturn(query).when(query).setParameter(any(String.class), any());
    doReturn(messagesResult).when(query).getResultList();

    final var expectedStatistics = Map.of(
        new HsaId(UNIT_2), new UnitStatistics(3, 3),
        new HsaId(UNIT_1), new UnitStatistics(3, 3)
    );
    final var actualStatistics = jpaStatisticsRepository.getStatisticsForUnits(
        List.of(new HsaId(UNIT_1), new HsaId(UNIT_2)), List.of(FK7210_ID));

    assertEquals(expectedStatistics, actualStatistics);
  }

  @Test
  void shallSetCorrectParametersForCertificateQuery() {
    final var draftCertificatesResult = List.of(
        new Object[]{UNIT_1, 3L},
        new Object[]{UNIT_2, 3L}
    );

    doReturn(query).when(entityManager).createQuery(eq(MESSAGE_JPQL), any());
    doReturn(query).when(query).setParameter(any(String.class), any());
    doReturn(Collections.emptyList()).when(query).getResultList();

    doReturn(certificateQuery).when(entityManager).createQuery(eq(CERTIFICATE_JPQL), any());
    doReturn(certificateQuery).when(certificateQuery).setParameter(any(String.class), any());
    doReturn(draftCertificatesResult).when(certificateQuery).getResultList();

    jpaStatisticsRepository.getStatisticsForUnits(List.of(new HsaId(UNIT_1)), List.of(FK7210_ID));

    final var hsaIdsCaptor = ArgumentCaptor.forClass(List.class);
    final var statusCaptor = ArgumentCaptor.forClass(List.class);

    verify(certificateQuery).setParameter(eq("hsaIds"), hsaIdsCaptor.capture());
    verify(certificateQuery).setParameter(eq("certificateStatusesForDrafts"),
        statusCaptor.capture());

    assertAll(
        () -> assertEquals(List.of(UNIT_1), hsaIdsCaptor.getValue()),
        () -> assertEquals(
            List.of(CertificateStatus.DRAFT.name(), CertificateStatus.LOCKED_DRAFT.name()),
            statusCaptor.getValue())
    );
  }

  @Test
  void shallSetCorrectParametersForMessageQuery() {
    final var messagesResult = List.of(
        new Object[]{UNIT_1, 3L},
        new Object[]{UNIT_2, 3L}
    );

    doReturn(certificateQuery).when(entityManager).createQuery(eq(CERTIFICATE_JPQL), any());
    doReturn(certificateQuery).when(certificateQuery).setParameter(any(String.class), any());
    doReturn(Collections.emptyList()).when(certificateQuery).getResultList();

    doReturn(query).when(entityManager).createQuery(eq(MESSAGE_JPQL), any());
    doReturn(query).when(query).setParameter(any(String.class), any());
    doReturn(messagesResult).when(query).getResultList();

    jpaStatisticsRepository.getStatisticsForUnits(List.of(new HsaId(UNIT_1)), List.of(FK7210_ID));

    final var hsaIdsCaptor = ArgumentCaptor.forClass(List.class);
    final var statusCaptorHandledOrDraft = ArgumentCaptor.forClass(List.class);

    verify(query).setParameter(eq("hsaIds"), hsaIdsCaptor.capture());
    verify(query).setParameter(eq("messageStatusHandledOrDraft"),
        statusCaptorHandledOrDraft.capture());

    assertAll(
        () -> assertEquals(List.of(UNIT_1), hsaIdsCaptor.getValue()),
        () -> assertEquals(List.of(MessageStatus.HANDLED.name(), MessageStatus.DRAFT.name()),
            statusCaptorHandledOrDraft.getValue())
    );
  }
}
