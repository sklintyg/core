package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

@Getter
public class SchemaValidatorErrorHandler implements ErrorHandler {

  private final List<SAXParseException> exceptions;

  public SchemaValidatorErrorHandler() {
    this.exceptions = new ArrayList<>();
  }

  @Override
  public void warning(SAXParseException exception) {
    exceptions.add(exception);
  }

  @Override
  public void error(SAXParseException exception) {
    exceptions.add(exception);
  }

  @Override
  public void fatalError(SAXParseException exception) {
    exceptions.add(exception);
  }
}
