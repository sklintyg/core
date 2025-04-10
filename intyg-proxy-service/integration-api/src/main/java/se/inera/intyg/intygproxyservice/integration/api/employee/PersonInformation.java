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

package se.inera.intyg.intygproxyservice.integration.api.employee;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Value
@Builder
public class PersonInformation {

  String personHsaId;
  String givenName;
  String middleAndSurName;
  @Builder.Default
  List<String> healthCareProfessionalLicence = new ArrayList<>();
  @Builder.Default
  List<PaTitle> paTitle = new ArrayList<>();
  @Builder.Default
  List<String> specialityName = new ArrayList<>();
  @Builder.Default
  List<String> specialityCode = new ArrayList<>();
  Boolean protectedPerson;
  LocalDateTime personStartDate;
  LocalDateTime personEndDate;
  Boolean feignedPerson;
  @Builder.Default
  List<HCPSpecialityCode> healthCareProfessionalLicenceSpeciality = new ArrayList<>();
  String age;
  String gender;
  String title;

  @Data
  @Builder
  public static class PaTitle {

    private String paTitleName;
    private String paTitleCode;
  }
}
