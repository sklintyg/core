package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitVersionEntity;

@Repository
public interface UnitVersionEntityRepository extends CrudRepository<UnitVersionEntity, Long>,
    JpaSpecificationExecutor<UnitVersionEntity> {

  Optional<UnitVersionEntity> findFirstByHsaIdOrderByValidFromDesc(String hsaId);

  // Custom query handling null validFrom (treated as unbounded start). Orders so non-null validFrom (newer) first, then nulls last.
  @Query("SELECT p FROM UnitVersionEntity p " +
      "WHERE p.hsaId = :id " +
      "AND (p.validFrom IS NULL OR p.validFrom <= :ts) " +
      "AND p.validTo >= :ts " +
      "ORDER BY CASE WHEN p.validFrom IS NULL THEN 1 ELSE 0 END, p.validFrom DESC")
  Optional<UnitVersionEntity> findFirstCoveringTimestampOrderByMostRecent(@Param("id") String hsaId,
      @Param("ts") LocalDateTime ts);

}
