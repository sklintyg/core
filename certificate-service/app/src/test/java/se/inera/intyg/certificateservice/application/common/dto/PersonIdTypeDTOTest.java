package se.inera.intyg.certificateservice.application.common.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.common.model.PersonIdType;

class PersonIdTypeDTOTest {

  @Test
  void shallReturnPersonIdTypePersonalIdentityNumber() {
    assertEquals(PersonIdType.PERSONAL_IDENTITY_NUMBER,
        PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER.toPersonIdType()
    );
  }

  @Test
  void shallReturnPersonIdTypeCoordinationNumber() {
    assertEquals(PersonIdType.COORDINATION_NUMBER,
        PersonIdTypeDTO.COORDINATION_NUMBER.toPersonIdType()
    );
  }

  @Test
  void shallReturnPersonIdTypeDTOPersonalIdentityNumber() {
    assertEquals(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER,
        PersonIdTypeDTO.toPersonIdTypeDTO(PersonIdType.PERSONAL_IDENTITY_NUMBER)
    );
  }

  @Test
  void shallReturnPersonIdTypeDTOCoordinationNumber() {
    assertEquals(PersonIdTypeDTO.COORDINATION_NUMBER,
        PersonIdTypeDTO.toPersonIdTypeDTO(PersonIdType.COORDINATION_NUMBER)
    );
  }
}