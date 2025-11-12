package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
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

  @Query("SELECT uv FROM UnitVersionEntity uv " +
      "JOIN uv.unit u " +
      "WHERE u.hsaId = :hsaId " +
      "ORDER BY uv.validFrom DESC " +
      "LIMIT 1")
  Optional<UnitVersionEntity> findFirstByHsaIdOrderByValidFromDesc(@Param("hsaId") String hsaId);

  @Query("SELECT uv FROM UnitVersionEntity uv " +
      "JOIN uv.unit u " +
      "WHERE u.hsaId IN :ids " +
      "AND (uv.validFrom IS NULL OR uv.validFrom <= :ts) " +
      "AND uv.validTo >= :ts " +
      "ORDER BY CASE WHEN uv.validFrom IS NULL THEN 1 ELSE 0 END, uv.validFrom DESC")
  List<UnitVersionEntity> findAllCoveringTimestampByHsaIdIn(@Param("ids") List<String> hsaIds,
      @Param("ts") LocalDateTime ts);

  @Query("SELECT uv FROM UnitVersionEntity uv " +
      "JOIN uv.unit u " +
      "WHERE u.hsaId IN :ids")
  List<UnitVersionEntity> findAllByHsaIdIn(@Param("ids") List<String> hsaIds);

}
