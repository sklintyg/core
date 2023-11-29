package se.inera.intyg.intygproxyservice.integration.authorization.client.converter;

import static se.inera.intyg.intygproxyservice.integration.common.TypeConverterHelper.toLocalDate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.Commission;
import se.riv.infrastructure.directory.authorizationmanagement.v2.CommissionType;

@Component
@RequiredArgsConstructor
public class CommissionTypeConverter {

  private final CommissionRightTypeConverter commissionRightTypeConverter;

  public Commission convert(CommissionType type) {
    if (type == null) {
      return Commission.builder().build();
    }

    return Commission.builder()
        .commissionHsaId(type.getCommissionHsaId())
        .commissionName(type.getCommissionName())
        .commissionPurpose(type.getCommissionPurpose())
        .healthCareProviderHsaId(type.getHealthCareProviderHsaId())
        .healthCareProviderName(type.getHealthCareProviderName())
        .healthCareProviderOrgNo(type.getHealthCareProviderOrgNo())
        .healthCareProviderEndDate(toLocalDate(type.getHealthCareProviderEndDate()))
        .healthCareProviderStartDate(toLocalDate(type.getHealthCareProviderStartDate()))
        .healthCareUnitName(type.getHealthCareUnitName())
        .healthCareProviderHsaId(type.getHealthCareProviderHsaId())
        .healthCareUnitEndDate(toLocalDate(type.getHealthCareUnitEndDate()))
        .healthCareUnitStartDate(toLocalDate(type.getHealthCareUnitStartDate()))
        .feignedCommission(type.isFeignedCommission())
        .archivedHealthCareProvider(type.isArchivedHealthCareProvider())
        .archivedHealthCareUnit(type.isArchivedHealthCareUnit())
        .feignedHealthCareUnit(type.isFeignedHealthCareUnit())
        .feignedHealthCareProvider(type.isFeignedHealthCareProvider())
        .pharmacyIdentifier(type.getPharmacyIdentifier())
        .commissionRight(
            type.getCommissionRight().stream()
                .map(commissionRightTypeConverter::convert)
                .toList()
        )
        .build();
  }
}
