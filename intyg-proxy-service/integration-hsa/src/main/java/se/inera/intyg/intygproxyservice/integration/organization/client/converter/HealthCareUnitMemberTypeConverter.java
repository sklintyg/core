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

package se.inera.intyg.intygproxyservice.integration.organization.client.converter;

import static se.inera.intyg.intygproxyservice.integration.common.TypeConverterHelper.toLocalDate;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnitMember;
import se.riv.infrastructure.directory.organization.gethealthcareunitmembersresponder.v2.HealthCareUnitMemberType;

@Service
@RequiredArgsConstructor
public class HealthCareUnitMemberTypeConverter {

  private final AddressTypeConverter addressTypeConverter;

  public HealthCareUnitMember convert(HealthCareUnitMemberType type) {
    return HealthCareUnitMember.builder()
        .archivedHealthCareUnitMember(type.isArchivedHealthCareUnitMember())
        .feignedHealthCareUnitMember(type.isFeignedHealthCareUnitMember())
        .healthCareUnitMemberHsaId(type.getHealthCareUnitMemberHsaId())
        .healthCareUnitMemberName(type.getHealthCareUnitMemberName())
        .healthCareUnitMemberPublicName(type.getHealthCareUnitMemberPublicName())
        .healthCareUnitMemberEndDate(toLocalDate(type.getHealthCareUnitMemberEndDate()))
        .healthCareUnitMemberStartDate(toLocalDate(type.getHealthCareUnitMemberStartDate()))
        .healthCareUnitMemberPrescriptionCode(type.getHealthCareUnitMemberPrescriptionCode())
        .healthCareUnitMemberTelephoneNumber(type.getHealthCareUnitMemberTelephoneNumber())
        .healthCareUnitMemberpostalAddress(
            addressTypeConverter.convertV2(type.getHealthCareUnitMemberpostalAddress()))
        .healthCareUnitMemberpostalCode(type.getHealthCareUnitMemberpostalCode())
        .build();
  }
}
