package se.inera.intyg.cts.application.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.cts.application.dto.TerminationDTOMapper.toDTO;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_CREATED;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_CREATOR_HSA_ID;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_CREATOR_NAME;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_HSA_ID;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_ORGANIZATIONAL_NUMBER;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_PERSON_ID;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_PHONE_NUMBER;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_STATUS;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.DEFAULT_TERMINATION_ID;
import static se.inera.intyg.cts.testutil.TerminationTestDataBuilder.defaultTermination;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.cts.domain.model.Termination;

class TerminationDTOMapperTest {

  @Nested
  class ToDTO {

    private Termination termination;

    @BeforeEach
    void setUp() {
      termination = defaultTermination();
    }

    @Test
    void shallMapTerminationId() {
      assertEquals(DEFAULT_TERMINATION_ID, toDTO(termination).terminationId());
    }

    @Test
    void shallMapHSAId() {
      assertEquals(DEFAULT_HSA_ID, toDTO(termination).hsaId());
    }

    @Test
    void shallMapOrganizationalNumber() {
      assertEquals(DEFAULT_ORGANIZATIONAL_NUMBER, toDTO(termination).organizationalNumber());
    }

    @Test
    void shallMapPersonId() {
      assertEquals(DEFAULT_PERSON_ID, toDTO(termination).personId());
    }

    @Test
    void shallMapPhoneNumber() {
      assertEquals(DEFAULT_PHONE_NUMBER, toDTO(termination).phoneNumber());
    }

    @Test
    void shallMapCreated() {
      assertEquals(DEFAULT_CREATED, toDTO(termination).created());
    }

    @Test
    void shallMapCreator() {
      assertEquals(DEFAULT_CREATOR_HSA_ID, toDTO(termination).creatorHSAId());
    }

    @Test
    void shallMapCreatorName() {
      assertEquals(DEFAULT_CREATOR_NAME, toDTO(termination).creatorName());
    }

    @Test
    void shallStatus() {
      assertEquals(DEFAULT_STATUS.description(), toDTO(termination).status());
    }
  }
}