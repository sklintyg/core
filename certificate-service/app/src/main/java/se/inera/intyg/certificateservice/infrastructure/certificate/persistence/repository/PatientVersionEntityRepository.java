package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientVersionEntity;

@Repository
public interface PatientVersionEntityRepository extends
    JpaRepository<PatientVersionEntity, Integer> {

  Optional<PatientVersionEntity> findFirstByIdOrderByValidFromDesc(String id);

  @Query("SELECT p FROM PatientVersionEntity p " +
      "WHERE p.id = :id " +
      "AND (p.validFrom IS NULL OR p.validFrom <= :ts) " +
      "AND p.validTo >= :ts " +
      "ORDER BY CASE WHEN p.validFrom IS NULL THEN 1 ELSE 0 END, p.validFrom DESC")
  Optional<PatientVersionEntity> findFirstCoveringTimestampOrderByMostRecent(@Param("id") String id,
      @Param("ts") LocalDateTime ts);
}
