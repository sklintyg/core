/*
 * Copyright (C) 2024 Inera AB (http://www.inera.se)
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

package se.inera.intyg.intygproxyservice.integration.employee.client.converter;

import static se.inera.intyg.intygproxyservice.integration.common.TypeConverterHelper.toLocalDate;

import java.util.List;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.employee.HCPSpecialityCode;
import se.inera.intyg.intygproxyservice.integration.api.employee.PersonInformation;
import se.inera.intyg.intygproxyservice.integration.api.employee.PersonInformation.PaTitle;
import se.riv.infrastructure.directory.employee.v3.HealthCareProfessionalLicenceSpecialityType;
import se.riv.infrastructure.directory.employee.v3.PaTitleType;
import se.riv.infrastructure.directory.employee.v3.PersonInformationType;

@Service
public class PersonInformationTypeConverter {

  public PersonInformation convert(PersonInformationType type) {
    return PersonInformation
        .builder()
        .age(type.getAge())
        .feignedPerson(type.isFeignedPerson())
        .gender(type.getGender())
        .personHsaId(type.getPersonHsaId())
        .givenName(type.getGivenName())
        .middleAndSurName(type.getMiddleAndSurName())
        .protectedPerson(type.isProtectedPerson())
        .title(type.getTitle())
        .personEndDate(toLocalDate(type.getPersonEndDate()))
        .personStartDate(toLocalDate(type.getPersonStartDate()))
        .healthCareProfessionalLicence(type.getHealthCareProfessionalLicence())
        .healthCareProfessionalLicenceSpeciality(
            toHCPSpecialityCodes(type.getHealthCareProfessionalLicenceSpeciality())
        )
        .paTitle(paTitleList(type.getParsedPaTitle()))
        .build();
  }

  private List<PaTitle> paTitleList(List<PaTitleType> type) {
    return type.stream()
        .map(this::paTitle)
        .toList();
  }

  private PaTitle paTitle(PaTitleType type) {
    return PaTitle
        .builder()
        .paTitleCode(type.getPaTitleCode())
        .paTitleName(type.getPaTitleName())
        .build();
  }

  private List<HCPSpecialityCode> toHCPSpecialityCodes(
      List<HealthCareProfessionalLicenceSpecialityType> types) {
    return types
        .stream()
        .map(this::toHCPSpecialityCode)
        .toList();
  }

  private HCPSpecialityCode toHCPSpecialityCode(HealthCareProfessionalLicenceSpecialityType type) {
    return HCPSpecialityCode
        .builder()
        .specialityCode(type.getSpecialityCode())
        .specialityName(type.getSpecialityName())
        .healthCareProfessionalLicenceCode(type.getHealthCareProfessionalLicence())
        .build();
  }
}
