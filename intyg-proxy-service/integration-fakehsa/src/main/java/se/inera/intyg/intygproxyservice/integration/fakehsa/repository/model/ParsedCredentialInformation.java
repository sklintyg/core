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
public class ParsedCredentialInformation {

  public static final String VARD_OCH_BEHANDLING = "Vård och behandling";
  public static final String STATISTIK = "Statistik";

  private String hsaId;
  private String givenName;
  @Builder.Default
  private List<ParsedCommission> commissionList = new ArrayList<>();

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ParsedCommission {

    private String healthCareProviderHsaId;
    private String healthCareUnitHsaId;
    @Builder.Default
    private List<String> commissionPurpose = new ArrayList<>();
  }
}
