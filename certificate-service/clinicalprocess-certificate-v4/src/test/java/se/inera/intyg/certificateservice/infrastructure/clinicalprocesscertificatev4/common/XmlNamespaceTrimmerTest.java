package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.common;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import javax.xml.parsers.DocumentBuilderFactory;
import org.junit.jupiter.api.Test;

class XmlNamespaceTrimmerTest {

  private static final String INPUT_XML = """
      <?xml version="1.0" encoding="UTF-8"?>
      <ns4:Root xmlns:ns4="https://example.com/root" xmlns:ns5="https://example.com/unusedRoot">
        <ns4:Child xmlns:ns6="https://example.com/usedChild" xmlns:ns7="https://example.com/unusedChild">
          <ns4:Element>Value</ns4:Element>
          <ns6:SubElement>SubValue</ns6:SubElement>
        </ns4:Child>
      </ns4:Root>
      """;

  @Test
  void shouldPreserveUsedNamespaceAtRootLevel() {
    final var trimmedXml = XmlNamespaceTrimmer.trim(INPUT_XML);
    assertTrue(trimmedXml.contains("xmlns:ns4=\"https://example.com/root\""));
  }

  @Test
  void shouldTrimUnusedNamespaceAtRootLevel() {
    final var trimmedXml = XmlNamespaceTrimmer.trim(INPUT_XML);
    assertFalse(trimmedXml.contains("xmlns:ns5=\"https://example.com/unusedRoot\""));
  }

  @Test
  void shouldPreserveUsedNamespaceAtChildLevel() {
    final var trimmedXml = XmlNamespaceTrimmer.trim(INPUT_XML);
    assertTrue(trimmedXml.contains("xmlns:ns6=\"https://example.com/usedChild\""));
  }

  @Test
  void shouldTrimUnusedNamespaceAtChildLevel() {
    final var trimmedXml = XmlNamespaceTrimmer.trim(INPUT_XML);
    assertFalse(trimmedXml.contains("xmlns:ns7=\"https://example.com/unusedChild\""));
  }

  @Test
  void shouldReturnWellFormedXml() {
    final var trimmedXml = XmlNamespaceTrimmer.trim(INPUT_XML);

    assertDoesNotThrow(() -> {
      final var factory = DocumentBuilderFactory.newInstance();
      final var builder = factory.newDocumentBuilder();
      builder.parse(new java.io.ByteArrayInputStream(trimmedXml.getBytes()));
    });
  }

  @Test
  void shouldReturnOriginalInputIfExceptionOccurs() {
    final var invalidXml = "<invalid>";
    final var result = XmlNamespaceTrimmer.trim(invalidXml);
    assertEquals(invalidXml, result);
  }

  @Test
  void shouldFailIfNotThreadSafe() throws InterruptedException {
    final int threadCount = 10;
    final int iterations = 10;
    final var latch = new CountDownLatch(threadCount);
    final var errors = new ConcurrentLinkedQueue<String>();
    final var excpectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ns4:Root xmlns:ns4=\"https://example.com/root\"><ns4:Child xmlns:ns6=\"https://example.com/usedChild\"><ns4:Element>Value</ns4:Element><ns6:SubElement>SubValue</ns6:SubElement></ns4:Child></ns4:Root>";

    try (final var executorService = Executors.newFixedThreadPool(threadCount)) {
      for (int i = 0; i < threadCount; i++) {
        executorService.submit(() -> {
          try {
            for (int j = 0; j < iterations; j++) {
              final var result = XmlNamespaceTrimmer.trim(INPUT_XML);
              if (!result.equals(excpectedXml)) {
                errors.add("Inconsistent result detected");
              }
            }
          } finally {
            latch.countDown();
          }
        });
      }
      latch.await();
    }

    if (!errors.isEmpty()) {
      fail("Thread safety test failed: " + errors);
    }
  }
}
