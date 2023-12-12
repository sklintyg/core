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
package se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParsedHsaPerson {

  private String hsaId;

  private String personalIdentityNumber;

  private String givenName;

  private String middleAndSurname;

  private boolean protectedPerson;

  @Builder.Default
  private List<Speciality> specialities = new ArrayList<>();
  @Builder.Default
  private List<String> unitIds = new ArrayList<>();

  private String title;
  @Builder.Default
  private List<String> healthCareProfessionalLicence = new ArrayList<>();
  @Builder.Default
  private List<ParsedPaTitle> paTitle = new ArrayList<>();

  private String personalPrescriptionCode;
  @Builder.Default
  private List<String> systemRoles = new ArrayList<>();
  @Builder.Default
  private List<String> educationCodes = new ArrayList<>();
  @Builder.Default
  private List<Restrictions> restrictions = new ArrayList<>();

  private FakeProperties fakeProperties;

  private String gender;

  private String age;
  @Builder.Default
  private List<HealthCareProfessionalLicenceType> healthCareProfessionalLicenceType = new ArrayList<>();

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ParsedPaTitle {

    private String titleCode;
    private String titleName;
  }

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Restrictions {

    private String restrictionCode;
    private String restrictionName;
  }

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Speciality {

    private String specialityName;
    private String specialityCode;
  }

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class HealthCareProfessionalLicenceType {

    private String healthCareProfessionalLicenceCode;
    private String healthCareProfessionalLicenceName;
  }
}
