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
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnitMembers;
import se.riv.infrastructure.directory.organization.gethealthcareunitmembersresponder.v2.HealthCareUnitMembersType;

@Service
@RequiredArgsConstructor
public class HealthCareUnitMembersTypeConverter {

  private final HealthCareUnitMemberTypeConverter healthCareUnitMemberTypeConverter;
  private final HealthCareProviderTypeConverter healthCareProviderTypeConverter;
  private final AddressTypeConverter addressTypeConverter;

  public HealthCareUnitMembers convert(HealthCareUnitMembersType type) {
    return HealthCareUnitMembers.builder()
        .archivedHealthCareUnit(type.isArchivedHealthCareUnit())
        .feignedHealthCareUnit(type.isFeignedHealthCareUnit())
        .healthCareUnitHsaId(type.getHealthCareUnitHsaId())
        .healthCareUnitName(type.getHealthCareUnitName())
        .healthCareUnitPublicName(type.getHealthCareUnitPublicName())
        .healthCareUnitEndDate(toLocalDate(type.getHealthCareUnitEndDate()))
        .healthCareUnitStartDate(toLocalDate(type.getHealthCareUnitStartDate()))
        .healthCareUnitPrescriptionCode(type.getHealthCareUnitPrescriptionCode())
        .postalCode(type.getPostalCode())
        .telephoneNumber(type.getTelephoneNumber())
        .postalAddress(addressTypeConverter.convertV2(type.getPostalAddress()))
        .healthCareUnitMember(
            type.getHealthCareUnitMember()
                .stream()
                .map(healthCareUnitMemberTypeConverter::convert)
                .toList()
        )
        .healthCareProvider(healthCareProviderTypeConverter.convertV2(type.getHealthCareProvider()))
        .build();
  }

}
