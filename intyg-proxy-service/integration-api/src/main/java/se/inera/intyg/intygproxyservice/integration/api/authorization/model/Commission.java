package se.inera.intyg.intygproxyservice.integration.api.authorization.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Commission {

  String commissionPurpose;
  String healthCareUnitHsaId;
  String healthCareUnitName;
  String healthCareProviderHsaId;
  String healthCareProviderName;
  LocalDateTime healthCareUnitStartDate;
  LocalDateTime healthCareUnitEndDate;

  String commissionName;
  String commissionHsaId;
  @Builder.Default
  List<CommissionRight> commissionRight = new ArrayList<>();
  String healthCareProviderOrgNo;
  LocalDateTime healthCareProviderStartDate;
  LocalDateTime healthCareProviderEndDate;
  Boolean feignedHealthCareProvider;
  Boolean feignedHealthCareUnit;
  Boolean feignedCommission;
  Boolean archivedHealthCareProvider;
  Boolean archivedHealthCareUnit;
  String pharmacyIdentifier;
}
