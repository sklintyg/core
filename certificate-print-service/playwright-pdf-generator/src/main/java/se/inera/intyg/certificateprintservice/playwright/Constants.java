package se.inera.intyg.certificateprintservice.playwright;

public abstract class Constants {

  public static final String STYLE = "style";
  public static final String CONTENT = "content";

  public static final String LEFT_MARGIN_INFO_STYLE = """
      position: absolute;
      left: 1cm;
      bottom: 35mm;
      font-size: 10pt;
      transform: rotate(-90deg) translateY(-50%);
      transform-origin: top left;
      """;

  public static final String RIGHT_MARGIN_INFO_STYLE = """
      position: absolute;
      width: 100%;
      left: 20cm;
      bottom: 35mm;
      font-size: 10pt;
      transform: rotate(-90deg) translateY(-50%);
      transform-origin: top left;
      """;

  public static final String FOOTER_STYLE = """
      height: 25mm;
      width: 100%;
      font-size: 10pt;
      margin: 0 20mm;
      border-top: black solid 1px;
      justify-content: space-between;
      display: flex;
      """;

  public static final String HEADER_STYLE = """
      display: grid;
      width: 17cm;
      font-size: 10pt;
      margin: 10mm 20mm 0 20mm;
      """;

  public static final String DRAFT_WATERMARK_STYLE = """
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translateX(-50%) translateY(-50%) rotate(315deg);
      font-size: 100pt;
      color: rgb(128, 128, 128);
      opacity: 0.5;
      z-index: -1;
      """;
}
