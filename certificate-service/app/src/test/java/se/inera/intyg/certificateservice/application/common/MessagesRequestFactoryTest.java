package se.inera.intyg.certificateservice.application.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.unit.dto.MessagesQueryCriteriaDTO;
import se.inera.intyg.certificateservice.application.unit.dto.QuestionSenderTypeDTO;
import se.inera.intyg.certificateservice.domain.common.model.HsaId;
import se.inera.intyg.certificateservice.domain.common.model.MessagesRequest;
import se.inera.intyg.certificateservice.domain.message.model.Author;
import se.inera.intyg.certificateservice.domain.message.model.Forwarded;

class MessagesRequestFactoryTest {

  private MessagesRequestFactory requestFactory;

  @BeforeEach
  void setUp() {
    requestFactory = new MessagesRequestFactory();
  }

  @Test
  void shallCreateDefaultRequest() {
    final var expectedRequest = MessagesRequest.builder()
        .build();

    assertEquals(expectedRequest, requestFactory.create());
  }

  @Nested
  class CreateFromMessagesQueryCriteriaDTO {

    @Nested
    class TestFrom {

      @Test
      void shallIncludeFrom() {
        final var expectedFrom = LocalDateTime.now(ZoneId.systemDefault());
        final var queryCriteriaDTO = MessagesQueryCriteriaDTO.builder()
            .sentDateFrom(expectedFrom)
            .build();

        assertEquals(expectedFrom, requestFactory.create(queryCriteriaDTO).sentDateFrom());
      }

      @Test
      void shallNotIncludeFrom() {
        final var queryCriteriaDTO = MessagesQueryCriteriaDTO.builder()
            .build();

        assertNull(requestFactory.create(queryCriteriaDTO).sentDateFrom());
      }

    }

    @Nested
    class TestTo {

      @Test
      void shallIncludeTo() {
        final var expectedTo = LocalDateTime.now(ZoneId.systemDefault());
        final var queryCriteriaDTO = MessagesQueryCriteriaDTO.builder()
            .sentDateTo(expectedTo)
            .build();

        assertEquals(expectedTo,
            requestFactory.create(queryCriteriaDTO).sentDateTo()
        );
      }

      @Test
      void shallNotIncludeTo() {
        final var queryCriteriaDTO = MessagesQueryCriteriaDTO.builder()
            .build();

        assertNull(requestFactory.create(queryCriteriaDTO).sentDateTo());
      }
    }

    @Nested
    class IssuedByStaff {

      @Test
      void shallIncludeIssuedByStaffId() {
        final var expectedIssuedByStaffId = AJLA_DOKTOR.hsaId();
        final var queryCriteriaDTO = MessagesQueryCriteriaDTO.builder()
            .issuedByStaffId(expectedIssuedByStaffId.id())
            .build();

        assertEquals(expectedIssuedByStaffId,
            requestFactory.create(queryCriteriaDTO).issuedByStaffId()
        );
      }

      @Test
      void shallNotIncludeIssuedByStaffId() {
        final var queryCriteriaDTO = MessagesQueryCriteriaDTO.builder()
            .build();

        assertNull(requestFactory.create(queryCriteriaDTO).issuedByStaffId());
      }
    }

    @Nested
    class IssuedOnUnit {

      @Test
      void shallNotIncludeIssuedUnitId() {
        final var queryCriteriaDTO = MessagesQueryCriteriaDTO.builder()
            .build();

        assertEquals(Collections.emptyList(),
            requestFactory.create(queryCriteriaDTO).issuedOnUnitIds());
      }

      @Test
      void shallIncludeIssuedUnitIds() {
        final var queryCriteriaDTO = MessagesQueryCriteriaDTO.builder()
            .issuedOnUnitIds(List.of("HSA1", "HSA2"))
            .build();

        assertEquals(List.of(new HsaId("HSA1"), new HsaId("HSA2")),
            requestFactory.create(queryCriteriaDTO).issuedOnUnitIds());
      }
    }

    @Nested
    class TestForwarded {

      @Test
      void shallNotIncludeForwarded() {
        final var queryCriteriaDTO = MessagesQueryCriteriaDTO.builder()
            .build();

        assertNull(requestFactory.create(queryCriteriaDTO).forwarded());
      }

      @Test
      void shallIncludeForwardedTrue() {
        final var queryCriteriaDTO = MessagesQueryCriteriaDTO.builder()
            .forwarded(true)
            .build();

        assertEquals(new Forwarded(true), requestFactory.create(queryCriteriaDTO).forwarded());
      }

      @Test
      void shallIncludeForwardedFalse() {
        final var queryCriteriaDTO = MessagesQueryCriteriaDTO.builder()
            .forwarded(false)
            .build();

        assertEquals(new Forwarded(false), requestFactory.create(queryCriteriaDTO).forwarded());
      }
    }
  }

  @Nested
  class TestAuthor {

    @Test
    void shallNotIncludeAuthor() {
      final var queryCriteriaDTO = MessagesQueryCriteriaDTO.builder()
          .build();

      assertNull(requestFactory.create(queryCriteriaDTO).author());
    }

    @Test
    void shallNotIncludeAuthorIfShowAllIsChosen() {
      final var queryCriteriaDTO = MessagesQueryCriteriaDTO.builder()
          .senderType(QuestionSenderTypeDTO.SHOW_ALL)
          .build();

      assertNull(requestFactory.create(queryCriteriaDTO).author());
    }

    @Test
    void shallIncludeAuthorFK() {
      final var queryCriteriaDTO = MessagesQueryCriteriaDTO.builder()
          .senderType(QuestionSenderTypeDTO.FK)
          .build();

      assertEquals(new Author("FK"), requestFactory.create(queryCriteriaDTO).author());
    }

    @Test
    void shallIncludeAuthorWC() {
      final var queryCriteriaDTO = MessagesQueryCriteriaDTO.builder()
          .senderType(QuestionSenderTypeDTO.WC)
          .build();

      assertEquals(new Author("WC"), requestFactory.create(queryCriteriaDTO).author());
    }
  }

  @Nested
  class PersonId {

    @Test
    void shallIncludePersonId() {
      final var expectedPersonId = ATHENA_REACT_ANDERSSON.id();
      final var queryCriteriaDTO = MessagesQueryCriteriaDTO.builder()
          .patientId(
              PersonIdDTO.builder()
                  .id(expectedPersonId.id())
                  .type(expectedPersonId.type().name())
                  .build()
          )
          .build();

      assertEquals(expectedPersonId,
          requestFactory.create(queryCriteriaDTO).personId()
      );
    }

    @Test
    void shallNotIncludePersonId() {
      final var queryCriteriaDTO = MessagesQueryCriteriaDTO.builder()
          .build();

      assertNull(requestFactory.create(queryCriteriaDTO).personId());
    }
  }
}