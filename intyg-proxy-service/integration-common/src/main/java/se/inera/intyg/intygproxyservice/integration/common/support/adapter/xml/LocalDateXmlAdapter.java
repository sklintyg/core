package se.inera.intyg.intygproxyservice.integration.common.support.adapter.xml;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import se.inera.intyg.intygproxyservice.integration.common.support.adapter.LocalDateAdapter;

public class LocalDateXmlAdapter extends XmlAdapter<String, LocalDate> {

  @Override
  public LocalDate unmarshal(String value) {
    return LocalDateAdapter.parseDate(value);
  }

  @Override
  public String marshal(LocalDate value) {
    return LocalDateAdapter.printDate(value);
  }
}
