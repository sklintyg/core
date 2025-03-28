/*
 * Copyright (C) 2025 Inera AB (http://www.inera.se)
 *
 * This file is part of sklintyg (https://github.com/sklintyg).
 *
 * sklintyg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sklintyg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package se.inera.intyg.intygproxyservice.integration.api.organization.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class HealthCareUnitMembers {

  String healthCareUnitName;
  String healthCareUnitPublicName;
  String healthCareUnitHsaId;
  LocalDateTime healthCareUnitStartDate;
  LocalDateTime healthCareUnitEndDate;
  @Builder.Default
  List<String> healthCareUnitPrescriptionCode = new ArrayList<>();
  @Builder.Default
  List<String> telephoneNumber = new ArrayList<>();
  @Builder.Default
  List<String> postalAddress = new ArrayList<>();
  String postalCode;
  Boolean feignedHealthCareUnit;
  Boolean archivedHealthCareUnit;
  HealthCareProvider healthCareProvider;
  @Builder.Default
  List<HealthCareUnitMember> healthCareUnitMember = new ArrayList<>();
}
