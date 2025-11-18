package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.PatientVersionEntity;

@Repository
public interface PatientVersionEntityRepository extends
    JpaRepository<PatientVersionEntity, Integer> {

  @Query("SELECT pv FROM PatientVersionEntity pv " +
      "JOIN pv.patient p " +
      "WHERE p.id = :id " +
      "ORDER BY pv.validFrom DESC " +
      "LIMIT 1")
  Optional<PatientVersionEntity> findFirstByIdOrderByValidFromDesc(@Param("id") String id);

  @Query("SELECT pv FROM PatientVersionEntity pv " +
      "JOIN pv.patient p " +
      "WHERE p.id = :id " +
      "AND (pv.validFrom IS NULL OR pv.validFrom <= :ts) " +
      "AND pv.validTo >= :ts " +
      "ORDER BY CASE WHEN pv.validFrom IS NULL THEN 1 ELSE 0 END, pv.validFrom DESC")
  Optional<PatientVersionEntity> findFirstCoveringTimestampOrderByMostRecent(@Param("id") String id,
      @Param("ts") LocalDateTime ts);

  @Query("SELECT pv FROM PatientVersionEntity pv " +
      "JOIN pv.patient p " +
      "WHERE p.id IN :ids")
  List<PatientVersionEntity> findAllByIdIn(@Param("ids") List<String> ids);
}
