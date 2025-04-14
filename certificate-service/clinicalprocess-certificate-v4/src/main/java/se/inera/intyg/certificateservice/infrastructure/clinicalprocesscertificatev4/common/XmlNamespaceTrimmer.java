package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.common;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Objects;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class XmlNamespaceTrimmer {

  private static final String XSLT_PATH = "xslt/trim-unused-namespaces.xslt";
  private static final Templates TEMPLATES;

  static {
    try (
        final var xsltInputStream = Objects.requireNonNull(
            XmlNamespaceTrimmer.class.getClassLoader().getResourceAsStream(XSLT_PATH))
    ) {
      final var factory = TransformerFactory.newDefaultInstance();
      TEMPLATES = factory.newTemplates(new StreamSource(xsltInputStream));
    } catch (Exception e) {
      throw new IllegalStateException("Failed to initialize Transformer Templates", e);
    }
  }

  public static String trim(String xml) {
    try {
      final var transformer = TEMPLATES.newTransformer();
      try (final var writer = new StringWriter()) {
        final var streamSource = new StreamSource(new StringReader(xml));
        transformer.transform(streamSource, new StreamResult(writer));
        return writer.toString();
      }
    } catch (Exception e) {
      log.warn("Failure trimming unused namespaces from XML", e);
      return xml;
    }
  }
}
