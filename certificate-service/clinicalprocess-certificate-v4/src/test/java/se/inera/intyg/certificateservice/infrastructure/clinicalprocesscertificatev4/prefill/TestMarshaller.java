package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.function.Function;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public class TestMarshaller {

  public static <T> Element getElement(T object, Function<T, JAXBElement<T>> jaxbElementCreator) {
    try {
      final var jaxbElement = jaxbElementCreator.apply(object);
      final var context = JAXBContext.newInstance(object.getClass());
      final var marshaller = context.createMarshaller();
      final var writer = new StringWriter();
      marshaller.marshal(jaxbElement, writer);

      final var docFactory = DocumentBuilderFactory.newInstance();
      docFactory.setNamespaceAware(true);
      final var doc = docFactory.newDocumentBuilder()
          .parse(new InputSource(new StringReader(writer.toString())));
      return doc.getDocumentElement();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
