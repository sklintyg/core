/*
 * Copyright (C) 2023 Inera AB (http://www.inera.se)
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

import java.time.LocalDateTime;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import org.springframework.stereotype.Service;
import riv.infrastructure.directory.employee._3.HealthCareProfessionalLicenceSpecialityType;
import riv.infrastructure.directory.employee._3.PersonInformationType;
import se.inera.intyg.intygproxyservice.integration.api.employee.HCPSpecialityCode;
import se.inera.intyg.intygproxyservice.integration.api.employee.PersonInformation;

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
        .build();
  }

  private LocalDateTime toLocalDate(XMLGregorianCalendar xmlFormat) {
    return xmlFormat != null
        ? xmlFormat.toGregorianCalendar().toZonedDateTime().toLocalDateTime()
        : null;
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
