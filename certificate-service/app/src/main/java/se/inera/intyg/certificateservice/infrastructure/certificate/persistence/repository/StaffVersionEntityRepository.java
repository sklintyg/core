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

  Optional<StaffVersionEntity> findFirstByHsaIdOrderByValidFromDesc(String hsaId);

  @Query("SELECT p FROM StaffVersionEntity p " +
      "WHERE p.hsaId IN :ids " +
      "AND (p.validFrom IS NULL OR p.validFrom <= :ts) " +
      "AND p.validTo >= :ts " +
      "ORDER BY CASE WHEN p.validFrom IS NULL THEN 1 ELSE 0 END, p.validFrom DESC")
  List<StaffVersionEntity> findAllCoveringTimestampByHsaIdIn(@Param("ids") List<String> hsaIds,
      @Param("ts") LocalDateTime ts);

}