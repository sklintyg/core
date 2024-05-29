package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.common.xmlschema.LSInputImpl;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;

@Slf4j
public class SchemaResourceResolverV4 implements LSResourceResolver {

  private static final String CORE_COMPONENTS_XSD_PATH = "schemas/certificate/4.0/core_components/";

  @Override
  public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId,
      String baseURI) {
    try {
      final var input = new LSInputImpl();
      final var schemaPath = CORE_COMPONENTS_XSD_PATH + systemId.replaceAll("(\\.\\./){2}.*/", "");
      final var fileReader = getFileStreamSource(schemaPath);
      input.setPublicId(publicId);
      input.setSystemId(systemId);
      input.setBaseURI(baseURI);
      input.setCharacterStream(fileReader);
      return input;

    } catch (IOException e) {
      log.error("Failure reading xsd resource {}", systemId, e);
      return null;
    }
  }

  private InputStreamReader getFileStreamSource(String path) throws IOException {
    final var classLoader = getClass().getClassLoader();
    try (final var inputStream = classLoader.getResourceAsStream(path)) {
      return new InputStreamReader(
          new ByteArrayInputStream(Objects.requireNonNull(inputStream).readAllBytes()));
    }
  }
}
