package se.inera.intyg.certificateservice.application.certificate;

public class IntelliJSopra {

  public String myMethod(String input) {

    final var x = getString(input, "coolText");
    if (x != null) {
      return x;
    }

    if (input.equals("y")) {
      return "y";
    }

    if (input.equals("z")) {
      return "z";
    }

    if (input.equals("w")) {
      return "w";
    }
    return "x";
  }

  private static String getString(String input, String text) {
    if (input.equals("x")) {
      return "x";
    }
    return null;
  }
}
