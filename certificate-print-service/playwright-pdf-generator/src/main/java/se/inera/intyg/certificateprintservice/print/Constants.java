package se.inera.intyg.certificateprintservice.print;

public abstract class Constants {

  public static final String STYLE = "style";

  public static final String LEFT_MARGIN_INFO_STYLE = """
      position: absolute;
      left: 1cm;
      bottom: 15mm;
      border: red solid 1px;
      font-size: 10pt;
      transform: rotate(-90deg) translateY(-50%);
      transform-origin: top left;
      """;

  public static final String RIGHT_MARGIN_INFO_STYLE = """
      position: absolute;
      width: 100%;
      left: 20cm;
      bottom: 15mm;
      border: red solid 1px;
      font-size: 10pt;
      transform: rotate(-90deg) translateY(-50%);
      transform-origin: top left;
      """;


}