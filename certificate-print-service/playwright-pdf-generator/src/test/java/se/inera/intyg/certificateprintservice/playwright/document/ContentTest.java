package se.inera.intyg.certificateprintservice.playwright.document;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.jsoup.parser.Tag;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Category;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Question;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.value.ElementValueText;

class ContentTest {

  private static final String CATEGORY_ID = "categoryId";
  private static final String CATEGORY_NAME = "categoryName";
  private static final String QUESTION_ID = "questionId";
  private static final String QUESTION_NAME = "questionName";
  private static final String QUESTION_VALUE = "questionValue";
  private static final String SUB_QUESTION_ID = "subQuestionId";
  private static final String SUB_QUESTION_NAME = "subQuestionName";
  private static final String SUB_QUESTION_VALUE = "subQuestionValue";
  private static final String ISSUER_NAME = "issuerName";
  private static final String ISSUING_UNIT = "issuingUnit";
  private static final String ADDRESS = "address";
  private static final String ZIP_CODE = "zipCode";
  private static final String CITY = "city";
  private static final String CERTIFICATE_NAME = "certificateName";
  private static final String DESCRIPTION = "description";
  private static final String CONTACT_INFO_HEADER = "Kontaktuppgifter:";
  private static final String SIGN_DATE = "2025-01-20";
  private static final String ISSUER_NAME_HEADER = "Intygsutfärdare:";
  private static final String SIGN_DATE_HEADER = "Intyget signerades:";
  private static final String SKICKA_INTYG_TILL_MOTTAGARE = "Skicka intyg till mottagare";
  private static final String INFO_1177 = "Du kan hantera ditt intyg genom att logga in på 1177.se Där kan du till exempel skicka intyget till mottagaren";

  private static final List<Question> SUB_QUESTIONS = List.of(Question.builder()
      .id(SUB_QUESTION_ID)
      .name(SUB_QUESTION_NAME)
      .subQuestions(Collections.emptyList())
      .value(ElementValueText.builder()
          .text(SUB_QUESTION_VALUE)
          .build())
      .build());

  private static final List<Question> QUESTIONS = List.of(
      Question.builder()
          .id(QUESTION_ID)
          .name(QUESTION_NAME)
          .value(ElementValueText.builder()
              .text(QUESTION_VALUE)
              .build())
          .subQuestions(SUB_QUESTIONS)
          .build());

  private final static List<Category> CATEGORIES = List.of(
      Category.builder()
          .id(CATEGORY_ID)
          .name(CATEGORY_NAME)
          .questions(QUESTIONS)
          .build());


  private final Content.ContentBuilder contentBuilder = Content.builder()
      .issuerName(ISSUER_NAME)
      .issuingUnit(ISSUING_UNIT)
      .issuingUnitInfo(List.of(ADDRESS, ZIP_CODE, CITY))
      .certificateName(CERTIFICATE_NAME)
      .description(DESCRIPTION)
      .categories(CATEGORIES);

  @Nested
  class ContentWrapper {

    @Test
    void contentWrapper() {
      final var content = contentBuilder.isDraft(true).signDate(null).build();
      final var element = content.create();
      assertAll(
          () -> assertEquals(Tag.valueOf("div"), element.tag(), "Tag type"),
          () -> assertEquals(3, element.children().size(), "Number of children"),
          () -> assertEquals(0, element.attributes().asList().size(), "Attributes")
      );
    }

    @Nested
    class IssuerInfo {

      @Test
      void issuerInfoWrapperDraft() {
        final var content = contentBuilder.isDraft(true).signDate(null).build();
        final var element = content.create().child(1);
        assertAll(
            () -> assertEquals(Tag.valueOf("div"), element.tag(), "Tag type"),
            () -> assertEquals(1, element.children().size(), "Number of children"),
            () -> assertEquals(1, element.attributes().asList().size(), "Attributes"),
            () -> assertEquals("break-inside-avoid",
                Objects.requireNonNull(element.attribute("class")).getValue(), "Attributes")
        );
      }

      @Test
      void issuerInfoWrapperSigned() {
        final var content = contentBuilder.isDraft(false).signDate(SIGN_DATE).build();
        final var element = content.create().child(1);
        assertAll(
            () -> assertEquals(Tag.valueOf("div"), element.tag(), "Tag type"),
            () -> assertEquals(3, element.children().size(), "Number of children"),
            () -> assertEquals(1, element.attributes().asList().size(), "Attributes"),
            () -> assertEquals("break-inside-avoid",
                Objects.requireNonNull(element.attribute("class")).getValue(), "Attributes")
        );
      }

      @Nested
      class IssuerInfoWrappers {

        @Test
        void issuerInfoDraft() {
          final var content = contentBuilder.isDraft(true).signDate(null).build();
          final var element = content.create().child(1).child(0);
          assertAll(
              () -> assertEquals(Tag.valueOf("div"), element.tag(), "Tag type"),
              () -> assertEquals(5, element.children().size(), "Number of children"),
              () -> assertEquals(1, element.attributes().asList().size(), "Attributes"),
              () -> assertEquals("grid mt-[5mm]",
                  Objects.requireNonNull(element.attribute("class")).getValue(), "Attributes")
          );
        }

        @Test
        void issuerInfoSignedIssuerName() {
          final var content = contentBuilder.isDraft(false).signDate(SIGN_DATE).build();
          final var element = content.create().child(1).child(0);
          assertAll(
              () -> assertEquals(Tag.valueOf("div"), element.tag(), "Tag type"),
              () -> assertEquals(2, element.children().size(), "Number of children"),
              () -> assertEquals(1, element.attributes().asList().size(), "Attributes"),
              () -> assertEquals("grid mt-[5mm]",
                  Objects.requireNonNull(element.attribute("class")).getValue(), "Attributes")
          );
        }

        @Test
        void issuerInfoSignedContactInfo() {
          final var content = contentBuilder.isDraft(false).signDate(SIGN_DATE).build();
          final var element = content.create().child(1).child(1);
          assertAll(
              () -> assertEquals(Tag.valueOf("div"), element.tag(), "Tag type"),
              () -> assertEquals(5, element.children().size(), "Number of children"),
              () -> assertEquals(1, element.attributes().asList().size(), "Attributes"),
              () -> assertEquals("grid mt-[5mm]",
                  Objects.requireNonNull(element.attribute("class")).getValue(), "Attributes")
          );
        }

        @Test
        void issuerInfoSignedSignDate() {
          final var content = contentBuilder.isDraft(false).signDate(SIGN_DATE).build();
          final var element = content.create().child(1).child(2);
          assertAll(
              () -> assertEquals(Tag.valueOf("div"), element.tag(), "Tag type"),
              () -> assertEquals(2, element.children().size(), "Number of children"),
              () -> assertEquals(1, element.attributes().asList().size(), "Attributes"),
              () -> assertEquals("grid mt-[5mm]",
                  Objects.requireNonNull(element.attribute("class")).getValue(), "Attributes")
          );
        }

        @Nested
        class IssuerName {

          @Test
          void issuerNameHeader() {
            final var content = contentBuilder.isDraft(false).signDate(SIGN_DATE).build();
            final var element = content.create().child(1).child(0).child(0);
            assertAll(
                () -> assertEquals(Tag.valueOf("p"), element.tag(), "Tag type"),
                () -> assertEquals(0, element.children().size(), "Number of children"),
                () -> assertEquals(1, element.attributes().asList().size(), "Attributes"),
                () -> assertEquals(ISSUER_NAME_HEADER, element.text(), "Text"),
                () -> assertEquals("font-bold",
                    Objects.requireNonNull(element.attribute("class")).getValue(), "Attributes")
            );
          }

          @Test
          void issuerName() {
            final var content = contentBuilder.isDraft(false).signDate(SIGN_DATE).build();
            final var element = content.create().child(1).child(0).child(1);
            assertAll(
                () -> assertEquals(Tag.valueOf("p"), element.tag(), "Tag type"),
                () -> assertEquals(0, element.children().size(), "Number of children"),
                () -> assertEquals(0, element.attributes().asList().size(), "Attributes"),
                () -> assertEquals(ISSUER_NAME, element.text(), "Text")
            );
          }
        }


        @Nested
        class ContactInfo {

          @Test
          void contactInfoHeader() {
            final var content = contentBuilder.isDraft(false).signDate(SIGN_DATE).build();
            final var element = content.create().child(1).child(1).child(0);
            assertAll(
                () -> assertEquals(Tag.valueOf("p"), element.tag(), "Tag type"),
                () -> assertEquals(0, element.children().size(), "Number of children"),
                () -> assertEquals(1, element.attributes().asList().size(), "Attributes"),
                () -> assertEquals(CONTACT_INFO_HEADER, element.text(), "Text"),
                () -> assertEquals("font-bold",
                    Objects.requireNonNull(element.attribute("class")).getValue(),
                    "Attributes")
            );
          }

          @Test
          void issuingUnit() {
            final var content = contentBuilder.isDraft(false).signDate(SIGN_DATE).build();
            final var element = content.create().child(1).child(1).child(1);
            assertAll(
                () -> assertEquals(Tag.valueOf("p"), element.tag(), "Tag type"),
                () -> assertEquals(0, element.children().size(), "Number of children"),
                () -> assertEquals(0, element.attributes().asList().size(), "Attributes"),
                () -> assertEquals(ISSUING_UNIT, element.text(), "Text")
            );
          }

          @Test
          void address() {
            final var content = contentBuilder.isDraft(false).signDate(SIGN_DATE).build();
            final var element = content.create().child(1).child(1).child(2);
            assertAll(
                () -> assertEquals(Tag.valueOf("p"), element.tag(), "Tag type"),
                () -> assertEquals(0, element.children().size(), "Number of children"),
                () -> assertEquals(0, element.attributes().asList().size(), "Attributes"),
                () -> assertEquals(ADDRESS, element.text(), "Text")
            );
          }

          @Test
          void zipCode() {
            final var content = contentBuilder.isDraft(false).signDate(SIGN_DATE).build();
            final var element = content.create().child(1).child(1).child(3);
            assertAll(
                () -> assertEquals(Tag.valueOf("p"), element.tag(), "Tag type"),
                () -> assertEquals(0, element.children().size(), "Number of children"),
                () -> assertEquals(0, element.attributes().asList().size(), "Attributes"),
                () -> assertEquals(ZIP_CODE, element.text(), "Text")
            );
          }

          @Test
          void city() {
            final var content = contentBuilder.isDraft(false).signDate(SIGN_DATE).build();
            final var element = content.create().child(1).child(1).child(4);
            assertAll(
                () -> assertEquals(Tag.valueOf("p"), element.tag(), "Tag type"),
                () -> assertEquals(0, element.children().size(), "Number of children"),
                () -> assertEquals(0, element.attributes().asList().size(), "Attributes"),
                () -> assertEquals(CITY, element.text(), "Text")
            );
          }
        }

        @Nested
        class SignDate {

          @Test
          void signDateHeader() {
            final var content = contentBuilder.isDraft(false).signDate(SIGN_DATE).build();
            final var element = content.create().child(1).child(2).child(0);
            assertAll(
                () -> assertEquals(Tag.valueOf("p"), element.tag(), "Tag type"),
                () -> assertEquals(0, element.children().size(), "Number of children"),
                () -> assertEquals(1, element.attributes().asList().size(), "Attributes"),
                () -> assertEquals(SIGN_DATE_HEADER, element.text(), "Text"),
                () -> assertEquals("font-bold",
                    Objects.requireNonNull(element.attribute("class")).getValue(), "Attributes")
            );
          }

          @Test
          void signDate() {
            final var content = contentBuilder.isDraft(false).signDate(SIGN_DATE).build();
            final var element = content.create().child(1).child(2).child(1);
            assertAll(
                () -> assertEquals(Tag.valueOf("p"), element.tag(), "Tag type"),
                () -> assertEquals(0, element.children().size(), "Number of children"),
                () -> assertEquals(0, element.attributes().asList().size(), "Attributes"),
                () -> assertEquals(SIGN_DATE, element.text(), "Text")
            );
          }
        }
      }
    }

    @Nested
    class CertificateInfo {

      @Test
      void certificateInfoInfoWrapper() {
        final var content = contentBuilder.isDraft(true).signDate(null).build();
        final var element = content.create().child(2);
        assertAll(
            () -> assertEquals(Tag.valueOf("div"), element.tag(), "Tag type"),
            () -> assertEquals(5, element.children().size(), "Number of children"),
            () -> assertEquals(1, element.attributes().asList().size(), "Attributes"),
            () -> assertEquals("break-before-page",
                Objects.requireNonNull(element.attribute("class")).getValue(), "Attributes")
        );
      }

      @Nested
      class CertificateInfoElements {

        @Test
        void certificateName() {
          final var content = contentBuilder.isDraft(true).signDate(null).build();
          final var element = content.create().child(2).child(0);
          assertAll(
              () -> assertEquals(Tag.valueOf("strong"), element.tag(), "Tag type"),
              () -> assertEquals(0, element.children().size(), "Number of children"),
              () -> assertEquals(0, element.attributes().asList().size(), "Attributes"),
              () -> assertEquals(CERTIFICATE_NAME, element.text(), "Text")
          );
        }

        @Test
        void description() {
          final var content = contentBuilder.isDraft(true).signDate(null).build();
          final var element = content.create().child(2).child(1);
          assertAll(
              () -> assertEquals(Tag.valueOf("p"), element.tag(), "Tag type"),
              () -> assertEquals(0, element.children().size(), "Number of children"),
              () -> assertEquals(1, element.attributes().asList().size(), "Attributes"),
              () -> assertEquals(DESCRIPTION, element.text(), "Text"),
              () -> assertEquals("whitespace-pre-line",
                  Objects.requireNonNull(element.attribute("class")).getValue(), "Attributes")
          );
        }

        @Test
        void emptyLine() {
          final var content = contentBuilder.isDraft(true).signDate(null).build();
          final var element = content.create().child(2).child(2);
          assertAll(
              () -> assertEquals(Tag.valueOf("br"), element.tag(), "Tag type"),
              () -> assertEquals(0, element.children().size(), "Number of children"),
              () -> assertEquals(0, element.attributes().asList().size(), "Attributes")
          );
        }

        @Test
        void sendCertificateToRecipientText() {
          final var content = contentBuilder.isDraft(true).signDate(null).build();
          final var element = content.create().child(2).child(3);
          assertAll(
              () -> assertEquals(Tag.valueOf("strong"), element.tag(), "Tag type"),
              () -> assertEquals(0, element.children().size(), "Number of children"),
              () -> assertEquals(0, element.attributes().asList().size(), "Attributes"),
              () -> assertEquals(SKICKA_INTYG_TILL_MOTTAGARE, element.text(), "Text")
          );
        }

        @Test
        void info1177() {
          final var content = contentBuilder.isDraft(true).signDate(null).build();
          final var element = content.create().child(2).child(4);
          assertAll(
              () -> assertEquals(Tag.valueOf("p"), element.tag(), "Tag type"),
              () -> assertEquals(0, element.children().size(), "Number of children"),
              () -> assertEquals(1, element.attributes().asList().size(), "Attributes"),
              () -> assertEquals(INFO_1177, element.text(), "Text"),
              () -> assertEquals("whitespace-pre-line",
                  Objects.requireNonNull(element.attribute("class")).getValue(), "Attributes")
          );
        }
      }
    }
  }

}
