package se.inera.intyg.certificateprintservice.playwright.document;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Objects;
import org.jsoup.parser.Tag;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class HeaderTest {

  private static final String CERTIFICATE_NAME = "certificateName";
  private static final String CERTIFICATE_TYPE = "certificateType";
  private static final String CERTIFICATE_VERSION = "certificateVersion";
  private static final byte[] RECIPIENT_LOGO = "recipientLogo".getBytes();
  private static final String PERSON_ID = "personId";
  private static final String RECIPIENT_NAME = "recipientName";
  private static final String TYPE_VERSION_TEXT = "(%s v%s)";
  private static final String DRAFT_ALERT_MESSAGE = "Detta är en utskrift av ett elektroniskt intygsutkast och ska INTE skickas till %s.";
  private static final String SENT_ALERT_MESSAGE = "Detta är en utskrift av ett elektroniskt intyg. Intyget har signerats elektroniskt av intygsutfärdaren. Notera att intyget redan har skickats till %s.";
  private static final String SIGNED_ALERT_MESSAGE = "Detta är en utskrift av ett elektroniskt intyg. Intyget har signerats elektroniskt av intygsutfärdaren.";

  private final Header.HeaderBuilder headerBuilder = Header.builder()
      .recipientLogo(RECIPIENT_LOGO)
      .personId(PERSON_ID)
      .certificateName(CERTIFICATE_NAME)
      .certificateType(CERTIFICATE_TYPE)
      .certificateVersion(CERTIFICATE_VERSION)
      .recipientName(RECIPIENT_NAME);

  @Nested
  class HeaderWrapper {

    @Test
    void headerWrapper() {
      final var header = headerBuilder.isDraft(true).isSent(false).build();
      final var element = header.create();
      assertAll(
          () -> assertEquals(Tag.valueOf("div"), element.tag(), "Tag type"),
          () -> assertEquals(0, element.attributes().asList().size(), "Attributes"),
          () -> assertEquals(2, element.children().size(), "Number of children")
      );
    }

    @Nested
    class PageHeader {

      @Test
      void pageHeaderWrapper() {
        final var header = headerBuilder.isDraft(true).isSent(false).build();
        final var element = header.create().child(0);
        assertAll(
            () -> assertEquals(Tag.valueOf("div"), element.tag(), "Tag type"),
            () -> assertEquals(2, element.children().size(), "Number of children"),
            () -> assertEquals(1, element.attributes().asList().size(), "Attributes"),
            () -> assertEquals("flex top-0 left-0 mb-[10mm]",
                Objects.requireNonNull(element.attribute("class")).getValue(), "Attributes")
        );
      }

      @Nested
      class PageHeaderElements {

        @Test
        void recipientLogoWrapper() {
          final var header = headerBuilder.isDraft(true).isSent(false).build();
          final var element = header.create().child(0).child(0);
          assertAll(
              () -> assertEquals(Tag.valueOf("div"), element.tag(), "Tag type"),
              () -> assertEquals(1, element.children().size(), "Number of children"),
              () -> assertEquals(0, element.attributes().asList().size(), "Attributes")
          );
        }

        @Test
        void personIdWrapper() {
          final var header = headerBuilder.isDraft(true).isSent(false).build();
          final var element = header.create().child(0).child(1);
          assertAll(
              () -> assertEquals(Tag.valueOf("div"), element.tag(), "Tag type"),
              () -> assertEquals(2, element.children().size(), "Number of children"),
              () -> assertEquals(1, element.attributes().asList().size(), "Attributes"),
              () -> assertEquals("float-right text-right w-full",
                  Objects.requireNonNull(element.attribute("class")).getValue(), "Attributes")
          );
        }

        @Nested
        class RecipientLogo {

          @Test
          void recipientLogo() throws NullPointerException {
            final var header = headerBuilder.isDraft(true).isSent(false).build();
            final var element = header.create().child(0).child(0).child(0);
            assertAll(
                () -> assertEquals(Tag.valueOf("img"), element.tag(), "Tag type"),
                () -> assertEquals(0, element.children().size(), "Number of children"),
                () -> assertEquals(3, element.attributes().asList().size(), "Number of attributes"),
                () -> assertEquals("data:image/png;base64, cmVjaXBpZW50TG9nbw==",
                    Objects.requireNonNull(element.attribute("src")).getValue(), "Attributes"),
                () -> assertEquals("logotyp intygsmottagare",
                    Objects.requireNonNull(element.attribute("alt")).getValue(), "Attributes"),
                () -> assertEquals("max-h-[15mm] max-w-[35mm]",
                    Objects.requireNonNull(element.attribute("class")).getValue(), "Attributes")
            );
          }
        }

        @Nested
        class PersonId {

          @Test
          void personIdHeader() throws NullPointerException {
            final var header = headerBuilder.isDraft(true).isSent(false).build();
            final var element = header.create().child(0).child(1).child(0);
            assertAll(
                () -> assertEquals(Tag.valueOf("p"), element.tag(), "Tag type"),
                () -> assertEquals(0, element.children().size(), "Number of children"),
                () -> assertEquals(1, element.attributes().asList().size(),
                    "Number of attributes"),
                () -> assertEquals("Person- /samordningsnr", element.text(), "Text"),
                () -> assertEquals("font-bold",
                    Objects.requireNonNull(element.attribute("class")).getValue(), "Attributes")
            );
          }

          @Test
          void personId() throws NullPointerException {
            final var header = headerBuilder.isDraft(true).isSent(false).build();
            final var element = header.create().child(0).child(1).child(1);
            assertAll(
                () -> assertEquals(Tag.valueOf("p"), element.tag(), "Tag type"),
                () -> assertEquals(0, element.children().size(), "Number of children"),
                () -> assertEquals(0, element.attributes().asList().size(),
                    "Number of attributes"),
                () -> assertEquals(PERSON_ID, element.text(), "Text")
            );
          }
        }
      }
    }

    @Nested
    class CertificateHeader {

      @Test
      void certificateHeaderWrapper() {
        final var header = headerBuilder.isDraft(true).isSent(false).build();
        final var element = header.create().child(1);
        assertAll(
            () -> assertEquals(Tag.valueOf("div"), element.tag(), "Tag type"),
            () -> assertEquals(2, element.children().size(), "Number of children"),
            () -> assertEquals(1, element.attributes().asList().size(), "Attributes"),
            () -> assertEquals("mb-[5mm]",
                Objects.requireNonNull(element.attribute("class")).getValue(), "Attributes")
        );
      }

      @Nested
      class CertificateHeaderElements {

        @Test
        void titleWrapper() {
          final var header = headerBuilder.isDraft(true).isSent(false).build();
          final var element = header.create().child(1).child(0);
          assertAll(
              () -> assertEquals(Tag.valueOf("div"), element.tag(), "Tag type"),
              () -> assertEquals(2, element.children().size(), "Number of children"),
              () -> assertEquals(1, element.attributes().asList().size(), "Attributes"),
              () -> assertEquals("text-[14pt] pb-[1mm] border-b border-solid border-black",
                  Objects.requireNonNull(element.attribute("class")).getValue(), "Attributes")
          );
        }

        @Test
        void alertWrapper() {
          final var header = headerBuilder.isDraft(true).isSent(false).build();
          final var element = header.create().child(1).child(1);
          assertAll(
              () -> assertEquals(Tag.valueOf("div"), element.tag(), "Tag type"),
              () -> assertEquals(1, element.children().size(), "Number of children"),
              () -> assertEquals(1, element.attributes().asList().size(), "Attributes"),
              () -> assertEquals("mt-[5mm] py-[3mm] px-[5mm] border border-solid border-red-600",
                  Objects.requireNonNull(element.attribute("class")).getValue(), "Attributes")
          );
        }

        @Nested
        class Title {

          @Test
          void certificateName() throws NullPointerException {
            final var header = headerBuilder.isDraft(true).isSent(false).build();
            final var element = header.create().child(1).child(0).child(0);
            assertAll(
                () -> assertEquals(Tag.valueOf("p"), element.tag(), "Tag type"),
                () -> assertEquals(0, element.children().size(), "Number of children"),
                () -> assertEquals(1, element.attributes().asList().size(), "Number of attributes"),
                () -> assertEquals(CERTIFICATE_NAME, element.text(), "Text"),
                () -> assertEquals("font-bold inline",
                    Objects.requireNonNull(element.attribute("class")).getValue(), "Attributes")
            );
          }

          @Test
          void certificateType() throws NullPointerException {
            final var expectedText = TYPE_VERSION_TEXT.formatted(CERTIFICATE_TYPE,
                CERTIFICATE_VERSION);
            final var header = headerBuilder.isDraft(true).isSent(false).build();
            final var element = header.create().child(1).child(0).child(1);
            assertAll(
                () -> assertEquals(Tag.valueOf("p"), element.tag(), "Tag type"),
                () -> assertEquals(0, element.children().size(), "Number of children"),
                () -> assertEquals(1, element.attributes().asList().size(),
                    "Number of attributes"),
                () -> assertEquals(expectedText, element.text(), "Text"),
                () -> assertEquals("inline",
                    Objects.requireNonNull(element.attribute("class")).getValue(), "Attributes")
            );
          }
        }

        @Nested
        class Alert {

          @Test
          void alertMessageDraft() throws NullPointerException {
            final var header = headerBuilder.isDraft(true).isSent(false).build();
            final var element = header.create().child(1).child(1).child(0);
            assertAll(
                () -> assertEquals(Tag.valueOf("p"), element.tag(), "Tag type"),
                () -> assertEquals(0, element.children().size(), "Number of children"),
                () -> assertEquals(0, element.attributes().asList().size(),
                    "Number of attributes"),
                () -> assertEquals(DRAFT_ALERT_MESSAGE.formatted(RECIPIENT_NAME), element.text(),
                    "Text")
            );
          }

          @Test
          void alertMessageSigned() throws NullPointerException {
            final var header = headerBuilder.isDraft(false).isSent(false).build();
            final var element = header.create().child(1).child(1).child(0);
            assertAll(
                () -> assertEquals(Tag.valueOf("p"), element.tag(), "Tag type"),
                () -> assertEquals(0, element.children().size(), "Number of children"),
                () -> assertEquals(0, element.attributes().asList().size(),
                    "Number of attributes"),
                () -> assertEquals(SIGNED_ALERT_MESSAGE, element.text(), "Text")
            );
          }

          @Test
          void alertMessageSent() throws NullPointerException {
            final var header = headerBuilder.isDraft(false).isSent(true).build();
            final var element = header.create().child(1).child(1).child(0);
            assertAll(
                () -> assertEquals(Tag.valueOf("p"), element.tag(), "Tag type"),
                () -> assertEquals(0, element.children().size(), "Number of children"),
                () -> assertEquals(0, element.attributes().asList().size(),
                    "Number of attributes"),
                () -> assertEquals(SENT_ALERT_MESSAGE.formatted(RECIPIENT_NAME), element.text(),
                    "Text")
            );
          }
        }
      }
    }
  }

}
