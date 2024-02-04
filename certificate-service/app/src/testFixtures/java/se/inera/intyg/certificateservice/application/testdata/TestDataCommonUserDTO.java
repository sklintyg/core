package se.inera.intyg.certificateservice.application.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_BLOCKED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_ROLE;

import se.inera.intyg.certificateservice.application.common.dto.RoleTypeDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO;
import se.inera.intyg.certificateservice.application.common.dto.UserDTO.UserDTOBuilder;

public class TestDataCommonUserDTO {

  public static final UserDTO AJLA_DOCTOR_DTO = ajlaDoktorDtoBuilder().build();

  public static UserDTOBuilder ajlaDoktorDtoBuilder() {
    return UserDTO.builder()
        .id(AJLA_DOCTOR_HSA_ID)
        .name(AJLA_DOCTOR_NAME)
        .role(RoleTypeDTO.toRoleType(AJLA_DOCTOR_ROLE))
        .blocked(AJLA_DOCTOR_BLOCKED.value());
  }
}
