package se.inera.intyg.certificateservice.infrastructure.certificate.persistence;

import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.UnitEntityMapper.toCareProviderDomain;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.UnitEntityMapper.toCareUnitDomain;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.UnitEntityMapper.toEntity;
import static se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.UnitEntityMapper.toSubUnitDomain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.unit.model.CareProvider;
import se.inera.intyg.certificateservice.domain.unit.model.CareUnit;
import se.inera.intyg.certificateservice.domain.unit.model.IssuingUnit;
import se.inera.intyg.certificateservice.domain.unit.model.SubUnit;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.Unit;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.UnitEntity;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper.UnitVersionEntityMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitEntityRepository;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.repository.UnitVersionEntityRepository;

@Repository
@RequiredArgsConstructor
public class UnitRepository {

  final UnitEntityRepository unitEntityRepository;
	private final UnitVersionEntityRepository unitVersionEntityRepository;

	public UnitEntity careProvider(CareProvider careProvider) {
    return unitEntityRepository.findByHsaId(careProvider.hsaId().id())
				.map(unitEntity -> updateUnitVersion(unitEntity, careProvider))
        .orElseGet(
            () -> unitEntityRepository.save(
                toEntity(careProvider)
            )
        );
  }

	public UnitEntity careUnit(CareUnit careUnit) {
    return unitEntityRepository.findByHsaId(careUnit.hsaId().id())
				.map(unitEntity -> updateUnitVersion(unitEntity, careUnit))

        .orElseGet(
            () -> unitEntityRepository.save(
                toEntity(careUnit)
            )
        );
  }

  public UnitEntity subUnit(SubUnit subUnit) {
    return unitEntityRepository.findByHsaId(subUnit.hsaId().id())
				.map(unitEntity -> updateUnitVersion(unitEntity, subUnit))
        .orElseGet(
            () -> unitEntityRepository.save(
                toEntity(subUnit)
            )
        );
  }

  public UnitEntity issuingUnit(IssuingUnit issuingUnit) {
    if (issuingUnit instanceof CareUnit careUnit) {
      return careUnit(careUnit);
    }

    if (issuingUnit instanceof SubUnit subUnit) {
      return subUnit(subUnit);
    }

    throw new IllegalStateException(
        "Could not map IssuingUnit due to unknown type: '%s'".formatted(issuingUnit)
    );
  }

	private UnitEntity updateUnitVersion(UnitEntity unitEntity, CareProvider careProvider) {
		if (!careProvider.equals(toCareProviderDomain(unitEntity))){
			var newUnitEntity = toEntity(careProvider);
			return updateUnitVersion(unitEntity, newUnitEntity);
		}
		return unitEntity;
	}

	private UnitEntity updateUnitVersion(UnitEntity unitEntity, CareUnit careUnit) {
		if (!careUnit.equals(toCareUnitDomain(unitEntity))){
			var newUnitEntity = toEntity(careUnit);
			return updateUnitVersion(unitEntity, newUnitEntity);
		}
		return unitEntity;
	}

	private UnitEntity updateUnitVersion(UnitEntity unitEntity, SubUnit subUnit) {
		if (!subUnit.equals(toSubUnitDomain(unitEntity))){
			var newUnitEntity = toEntity(subUnit);
			return updateUnitVersion(unitEntity, newUnitEntity);
		}
		return unitEntity;
	}

	private UnitEntity updateUnitVersion(UnitEntity unitEntity, UnitEntity newUnitEntity) {

		final var unitVersionEntity = UnitVersionEntityMapper.toEntity(unitEntity);
		final var existingVersions = unitVersionEntityRepository
				.findAllByHsaId(unitEntity.getHsaId());

		if (existingVersions.isEmpty() || !existingVersions.contains(unitVersionEntity)) {
			unitVersionEntity.setValidFrom(existingVersions.getFirst().getValidTo());
		}

		unitEntity.setHsaId(newUnitEntity.getHsaId());
		unitEntity.setName(newUnitEntity.getName());
		unitEntity.setAddress(newUnitEntity.getAddress());
		unitEntity.setZipCode(newUnitEntity.getZipCode());
		unitEntity.setCity(newUnitEntity.getCity());
		unitEntity.setPhoneNumber(newUnitEntity.getPhoneNumber());
		unitEntity.setEmail(newUnitEntity.getEmail());
		unitEntity.setWorkplaceCode(newUnitEntity.getWorkplaceCode());
		unitEntity.setType(newUnitEntity.getType());

		unitVersionEntityRepository.save(unitVersionEntity);
		return unitEntityRepository.save(unitEntity);
	}
}
