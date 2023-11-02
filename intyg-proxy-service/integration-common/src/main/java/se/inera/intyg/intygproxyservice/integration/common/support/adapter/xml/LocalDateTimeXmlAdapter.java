package se.inera.intyg.intygproxyservice.integration.common.support.adapter.xml;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import se.inera.intyg.intygproxyservice.integration.common.support.adapter.LocalDateAdapter;

public class LocalDateTimeXmlAdapter extends XmlAdapter<String, LocalDateTime> {

  @Override
  public LocalDateTime unmarshal(String value) {
    return LocalDateAdapter.parseDateTime(value);
  }

  @Override
  public String marshal(LocalDateTime value) {
    return LocalDateAdapter.printDateTime(value);
  }
}
