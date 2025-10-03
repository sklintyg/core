package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.StaffVersionEntity;

@Repository
public interface StaffVersionEntityRepository extends JpaRepository<StaffVersionEntity, Integer> {

	List<StaffVersionEntity> findAllByHsaIdOrderByKeyDesc(String hsaId);

	List<StaffVersionEntity> findAllByHsaIdOrderByValidFromDesc(String hsaId);

	List<StaffVersionEntity> findAllByHsaIdInOrderByValidFromDesc(List<String> hsaIds);



}