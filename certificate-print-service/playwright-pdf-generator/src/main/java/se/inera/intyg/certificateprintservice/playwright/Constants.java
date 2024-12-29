package se.inera.intyg.certificateprintservice.playwright;

public abstract class Constants {

  public static final String STYLE = "style";
  public static final String CONTENT = "content";

  public static final String LEFT_MARGIN_INFO_STYLE = """
      position: absolute;
      left: 1cm;
      bottom: 35mm;
      border: red solid 1px;
      font-size: 10pt;
      transform: rotate(-90deg) translateY(-50%);
      transform-origin: top left;
      """;

  public static final String RIGHT_MARGIN_INFO_STYLE = """
      position: absolute;
      width: 100%;
      left: 20cm;
      bottom: 35mm;
      border: red solid 1px;
      font-size: 10pt;
      transform: rotate(-90deg) translateY(-50%);
      transform-origin: top left;
      """;

}
