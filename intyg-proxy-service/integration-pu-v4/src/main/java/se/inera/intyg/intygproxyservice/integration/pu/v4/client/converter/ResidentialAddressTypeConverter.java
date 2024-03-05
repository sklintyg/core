package se.inera.intyg.intygproxyservice.integration.pu.v4.client.converter;


import se.riv.strategicresourcemanagement.persons.person.v4.AddressInformationType;

public class ResidentialAddressTypeConverter {

  private ResidentialAddressTypeConverter() {
    throw new IllegalStateException("Utility class");
  }

  public static String postalAddress(AddressInformationType addressInformationType) {
    if (isResidentialAddressNull(addressInformationType)) {
      return null;
    }

    final var residentialAddress = addressInformationType.getResidentialAddress();
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

  public static String postalCode(AddressInformationType addressInformationType) {
    if (isResidentialAddressNull(addressInformationType)) {
      return null;
    }

    final var residentialAddress = addressInformationType.getResidentialAddress();
    if (residentialAddress.getPostalCode() == null || residentialAddress.getPostalCode() == 0) {
      return null;
    }
    return Integer.toString(residentialAddress.getPostalCode());
  }

  public static String city(AddressInformationType addressInformationType) {
    if (isResidentialAddressNull(addressInformationType)) {
      return null;
    }

    return addressInformationType.getResidentialAddress().getCity();
  }

  private static boolean containsAddress(String addressString) {
    return addressString != null;
  }

  private static void addDelimiterIfNeeded(StringBuilder addressBuilder) {
    if (addressBuilder.length() > 0) {
      addressBuilder.append(", ");
    }
  }

  private static boolean isResidentialAddressNull(AddressInformationType addressInformationType) {
    return addressInformationType == null || addressInformationType.getResidentialAddress() == null;
  }
}
