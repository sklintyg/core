package se.inera.intyg.intygproxyservice.integration.fakepu.repository;

import se.inera.intyg.intygproxyservice.integration.fakepu.repository.model.ParsedAddress;

public class AddressConverter {

  private AddressConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static String convert(ParsedAddress residentialAddress) {
    final var addressBuilder = new StringBuilder();
    if (containsAddress(residentialAddress.getCareOf())) {
      addressBuilder.append(residentialAddress.getCareOf());
    }
    if (containsAddress(residentialAddress.getPostalAddress1())) {
      addDelimiterIfNeeded(addressBuilder);
      addressBuilder.append(residentialAddress.getPostalAddress1());
    }
    if (containsAddress(residentialAddress.getPostalAddress2())) {
      addDelimiterIfNeeded(addressBuilder);
      addressBuilder.append(residentialAddress.getPostalAddress2());
    }
    return addressBuilder.toString();
  }

  private static boolean containsAddress(String addressString) {
    return addressString != null;
  }

  private static void addDelimiterIfNeeded(StringBuilder addressBuilder) {
    if (!addressBuilder.isEmpty()) {
      addressBuilder.append(", ");
    }
  }
}
