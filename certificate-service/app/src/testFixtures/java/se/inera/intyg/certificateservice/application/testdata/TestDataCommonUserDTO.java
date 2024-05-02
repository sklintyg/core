package se.inera.intyg.certificateservice.application.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_BLOCKED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_FULLNAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_PA_TITLES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_ROLE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_SPECIALITIES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_BLOCKED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_FULL_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.ALVA_VARDADMINISTRATOR_ROLE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BERTIL_BARNMORSKA_BLOCKED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BERTIL_BARNMORSKA_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BERTIL_BARNMORSKA_FULL_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BERTIL_BARNMORSKA_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BERTIL_BARNMORSKA_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BERTIL_BARNMORSKA_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.BERTIL_BARNMORSKA_ROLE;

import java.util.Collections;
import se.inera.intyg.certificateservice.application.common.dto.AccessScopeTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.PaTitleDTO;
import se.inera.intyg.certificateservice.application.common.dto.RoleTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO.UserDTOBuilder;
import se.inera.intyg.certificateservice.domain.common.model.Speciality;

public class TestDataCommonUserDTO {

  public static final UserDTO AJLA_DOCTOR_DTO = ajlaDoktorDtoBuilder().build();
  public static final UserDTO ALVA_VARDADMINISTRATOR_DTO = alvaVardadministratorDtoBuilder().build();
  public static final UserDTO BERTIL_BARNMORSKA_DTO = bertilBarnmorskaDtoBuilder().build();

  public static UserDTOBuilder ajlaDoktorDtoBuilder() {
    return UserDTO.builder()
        .id(AJLA_DOCTOR_HSA_ID)
        .firstName(AJLA_DOCTOR_FIRST_NAME)
        .middleName(AJLA_DOCTOR_MIDDLE_NAME)
        .lastName(AJLA_DOCTOR_LAST_NAME)
        .fullName(AJLA_DOCTOR_FULLNAME)
        .role(RoleTypeDTO.toRoleType(AJLA_DOCTOR_ROLE))
        .paTitles(
            AJLA_DOCTOR_PA_TITLES.stream()
                .map(paTitle ->
                    PaTitleDTO.builder()
                        .code(paTitle.code())
                        .description(paTitle.description())
                        .build()
                )
                .toList()
        )
        .specialities(
            AJLA_DOCTOR_SPECIALITIES.stream()
                .map(Speciality::value)
                .toList()
        )
        .blocked(AJLA_DOCTOR_BLOCKED.value())
        .accessScope(AccessScopeTypeDTO.WITHIN_CARE_PROVIDER);
  }

  public static UserDTOBuilder alvaVardadministratorDtoBuilder() {
    return UserDTO.builder()
        .id(ALVA_VARDADMINISTRATOR_HSA_ID)
        .firstName(ALVA_VARDADMINISTRATOR_FIRST_NAME)
        .middleName(ALVA_VARDADMINISTRATOR_MIDDLE_NAME)
        .lastName(ALVA_VARDADMINISTRATOR_LAST_NAME)
        .fullName(ALVA_VARDADMINISTRATOR_FULL_NAME)
        .role(RoleTypeDTO.toRoleType(ALVA_VARDADMINISTRATOR_ROLE))
        .paTitles(Collections.emptyList())
        .specialities(Collections.emptyList())
        .blocked(ALVA_VARDADMINISTRATOR_BLOCKED.value());
  }

  public static UserDTOBuilder bertilBarnmorskaDtoBuilder() {
    return UserDTO.builder()
        .id(BERTIL_BARNMORSKA_HSA_ID)
        .firstName(BERTIL_BARNMORSKA_FIRST_NAME)
        .middleName(BERTIL_BARNMORSKA_MIDDLE_NAME)
        .lastName(BERTIL_BARNMORSKA_LAST_NAME)
        .fullName(BERTIL_BARNMORSKA_FULL_NAME)
        .role(RoleTypeDTO.toRoleType(BERTIL_BARNMORSKA_ROLE))
        .paTitles(Collections.emptyList())
        .specialities(Collections.emptyList())
        .blocked(BERTIL_BARNMORSKA_BLOCKED.value());
  }
}
