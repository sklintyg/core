package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffVersionEntity;

@Repository
public interface StaffVersionEntityRepository extends JpaRepository<StaffVersionEntity, Integer> {

  @Query("SELECT sv FROM StaffVersionEntity sv " +
      "JOIN sv.staff s " +
      "WHERE s.hsaId = :hsaId " +
      "ORDER BY sv.validFrom DESC " +
      "LIMIT 1")
  Optional<StaffVersionEntity> findFirstByHsaIdOrderByValidFromDesc(@Param("hsaId") String hsaId);

  @Query("SELECT sv FROM StaffVersionEntity sv " +
      "JOIN sv.staff s " +
      "WHERE s.hsaId IN :ids " +
      "AND (sv.validFrom IS NULL OR sv.validFrom <= :ts) " +
      "AND sv.validTo >= :ts " +
      "ORDER BY CASE WHEN sv.validFrom IS NULL THEN 1 ELSE 0 END, sv.validFrom DESC")
  List<StaffVersionEntity> findAllCoveringTimestampByHsaIdIn(@Param("ids") List<String> hsaIds,
      @Param("ts") LocalDateTime ts);

  @Query("SELECT sv FROM StaffVersionEntity sv " +
      "JOIN sv.staff s " +
      "WHERE s.hsaId IN :ids")
  List<StaffVersionEntity> findAllByHsaIdIn(@Param("ids") List<String> hsaIds);
}
