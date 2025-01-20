package se.inera.intyg.certificateprintservice.playwright.document;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.ALT;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.ATTRIBUTES;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.CLASS;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.DIV;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.IMG;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.NUM_ATTRIBUTES;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.NUM_CHILDREN;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.P;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.SRC;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.TAG_TYPE;
import static se.inera.intyg.certificateprintservice.playwright.document.Constants.TEXT;

import java.util.Objects;
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
          () -> assertEquals(DIV, element.tag(), TAG_TYPE),
          () -> assertEquals(0, element.attributes().asList().size(), NUM_ATTRIBUTES),
          () -> assertEquals(2, element.children().size(), NUM_CHILDREN)
      );
    }

    @Nested
    class PageHeader {

      @Test
      void pageHeaderWrapper() {
        final var header = headerBuilder.isDraft(true).isSent(false).build();
        final var element = header.create().child(0);
        assertAll(
            () -> assertEquals(DIV, element.tag(), TAG_TYPE),
            () -> assertEquals(2, element.children().size(), NUM_CHILDREN),
            () -> assertEquals(1, element.attributes().asList().size(), NUM_ATTRIBUTES),
            () -> assertEquals("flex top-0 left-0 mb-[10mm]",
                Objects.requireNonNull(element.attribute(CLASS)).getValue(), ATTRIBUTES)
        );
      }

      @Nested
      class PageHeaderElements {

        @Test
        void recipientLogoWrapper() {
          final var header = headerBuilder.isDraft(true).isSent(false).build();
          final var element = header.create().child(0).child(0);
          assertAll(
              () -> assertEquals(DIV, element.tag(), TAG_TYPE),
              () -> assertEquals(1, element.children().size(), NUM_CHILDREN),
              () -> assertEquals(0, element.attributes().asList().size(), NUM_ATTRIBUTES)
          );
        }

        @Test
        void personIdWrapper() {
          final var header = headerBuilder.isDraft(true).isSent(false).build();
          final var element = header.create().child(0).child(1);
          assertAll(
              () -> assertEquals(DIV, element.tag(), TAG_TYPE),
              () -> assertEquals(2, element.children().size(), NUM_CHILDREN),
              () -> assertEquals(1, element.attributes().asList().size(), NUM_ATTRIBUTES),
              () -> assertEquals("float-right text-right w-full",
                  Objects.requireNonNull(element.attribute(CLASS)).getValue(), ATTRIBUTES)
          );
        }

        @Nested
        class RecipientLogo {

          @Test
          void recipientLogo() throws NullPointerException {
            final var header = headerBuilder.isDraft(true).isSent(false).build();
            final var element = header.create().child(0).child(0).child(0);
            assertAll(
                () -> assertEquals(IMG, element.tag(), TAG_TYPE),
                () -> assertEquals(0, element.children().size(), NUM_CHILDREN),
                () -> assertEquals(3, element.attributes().asList().size(), NUM_ATTRIBUTES),
                () -> assertEquals("data:image/png;base64, cmVjaXBpZW50TG9nbw==",
                    Objects.requireNonNull(element.attribute(SRC)).getValue(), ATTRIBUTES),
                () -> assertEquals("logotyp intygsmottagare",
                    Objects.requireNonNull(element.attribute(ALT)).getValue(), ATTRIBUTES),
                () -> assertEquals("max-h-[15mm] max-w-[35mm]",
                    Objects.requireNonNull(element.attribute(CLASS)).getValue(), ATTRIBUTES)
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
                () -> assertEquals(P, element.tag(), TAG_TYPE),
                () -> assertEquals(0, element.children().size(), NUM_CHILDREN),
                () -> assertEquals(1, element.attributes().asList().size(), NUM_ATTRIBUTES),
                () -> assertEquals("Person- /samordningsnr", element.text(), TEXT),
                () -> assertEquals("font-bold",
                    Objects.requireNonNull(element.attribute(CLASS)).getValue(), ATTRIBUTES)
            );
          }

          @Test
          void personId() throws NullPointerException {
            final var header = headerBuilder.isDraft(true).isSent(false).build();
            final var element = header.create().child(0).child(1).child(1);
            assertAll(
                () -> assertEquals(P, element.tag(), TAG_TYPE),
                () -> assertEquals(0, element.children().size(), NUM_CHILDREN),
                () -> assertEquals(0, element.attributes().asList().size(), NUM_ATTRIBUTES),
                () -> assertEquals(PERSON_ID, element.text(), TEXT)
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
            () -> assertEquals(DIV, element.tag(), TAG_TYPE),
            () -> assertEquals(2, element.children().size(), NUM_CHILDREN),
            () -> assertEquals(1, element.attributes().asList().size(), NUM_ATTRIBUTES),
            () -> assertEquals("mb-[5mm]",
                Objects.requireNonNull(element.attribute(CLASS)).getValue(), ATTRIBUTES)
        );
      }

      @Nested
      class CertificateHeaderElements {

        @Test
        void titleWrapper() {
          final var header = headerBuilder.isDraft(true).isSent(false).build();
          final var element = header.create().child(1).child(0);
          assertAll(
              () -> assertEquals(DIV, element.tag(), TAG_TYPE),
              () -> assertEquals(2, element.children().size(), NUM_CHILDREN),
              () -> assertEquals(1, element.attributes().asList().size(), NUM_ATTRIBUTES),
              () -> assertEquals("text-[14pt] pb-[1mm] border-b border-solid border-black",
                  Objects.requireNonNull(element.attribute(CLASS)).getValue(), ATTRIBUTES)
          );
        }

        @Test
        void alertWrapper() {
          final var header = headerBuilder.isDraft(true).isSent(false).build();
          final var element = header.create().child(1).child(1);
          assertAll(
              () -> assertEquals(DIV, element.tag(), TAG_TYPE),
              () -> assertEquals(1, element.children().size(), NUM_CHILDREN),
              () -> assertEquals(1, element.attributes().asList().size(), NUM_ATTRIBUTES),
              () -> assertEquals("mt-[5mm] py-[3mm] px-[5mm] border border-solid border-red-600",
                  Objects.requireNonNull(element.attribute(CLASS)).getValue(), ATTRIBUTES)
          );
        }

        @Nested
        class Title {

          @Test
          void certificateName() throws NullPointerException {
            final var header = headerBuilder.isDraft(true).isSent(false).build();
            final var element = header.create().child(1).child(0).child(0);
            assertAll(
                () -> assertEquals(P, element.tag(), TAG_TYPE),
                () -> assertEquals(0, element.children().size(), NUM_CHILDREN),
                () -> assertEquals(1, element.attributes().asList().size(), NUM_ATTRIBUTES),
                () -> assertEquals(CERTIFICATE_NAME, element.text(), TEXT),
                () -> assertEquals("font-bold inline",
                    Objects.requireNonNull(element.attribute(CLASS)).getValue(), ATTRIBUTES)
            );
          }

          @Test
          void certificateType() throws NullPointerException {
            final var expectedText = TYPE_VERSION_TEXT.formatted(CERTIFICATE_TYPE,
                CERTIFICATE_VERSION);
            final var header = headerBuilder.isDraft(true).isSent(false).build();
            final var element = header.create().child(1).child(0).child(1);
            assertAll(
                () -> assertEquals(P, element.tag(), TAG_TYPE),
                () -> assertEquals(0, element.children().size(), NUM_CHILDREN),
                () -> assertEquals(1, element.attributes().asList().size(), NUM_ATTRIBUTES),
                () -> assertEquals(expectedText, element.text(), TEXT),
                () -> assertEquals("inline",
                    Objects.requireNonNull(element.attribute(CLASS)).getValue(), ATTRIBUTES)
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
                () -> assertEquals(P, element.tag(), TAG_TYPE),
                () -> assertEquals(0, element.children().size(), NUM_CHILDREN),
                () -> assertEquals(0, element.attributes().asList().size(), NUM_ATTRIBUTES),
                () -> assertEquals(DRAFT_ALERT_MESSAGE.formatted(RECIPIENT_NAME), element.text(),
                    TEXT)
            );
          }

          @Test
          void alertMessageSigned() throws NullPointerException {
            final var header = headerBuilder.isDraft(false).isSent(false).build();
            final var element = header.create().child(1).child(1).child(0);
            assertAll(
                () -> assertEquals(P, element.tag(), TAG_TYPE),
                () -> assertEquals(0, element.children().size(), NUM_CHILDREN),
                () -> assertEquals(0, element.attributes().asList().size(), NUM_ATTRIBUTES),
                () -> assertEquals(SIGNED_ALERT_MESSAGE, element.text(), TEXT)
            );
          }

          @Test
          void alertMessageSent() throws NullPointerException {
            final var header = headerBuilder.isDraft(false).isSent(true).build();
            final var element = header.create().child(1).child(1).child(0);
            assertAll(
                () -> assertEquals(P, element.tag(), TAG_TYPE),
                () -> assertEquals(0, element.children().size(), NUM_CHILDREN),
                () -> assertEquals(0, element.attributes().asList().size(), NUM_ATTRIBUTES),
                () -> assertEquals(SENT_ALERT_MESSAGE.formatted(RECIPIENT_NAME), element.text(),
                    TEXT)
            );
          }
        }
      }
    }
  }

}
