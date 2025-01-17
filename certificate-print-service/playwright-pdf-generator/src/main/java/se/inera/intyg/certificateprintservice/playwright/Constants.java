package se.inera.intyg.certificateprintservice.playwright;

public abstract class Constants {

  public static final String STYLE = "style";
  public static final String CONTENT = "content";

  public static final String LEFT_MARGIN_INFO_STYLE = """
      border: red solid 1px;
      font-size: 10pt;
      transform: rotate(-90deg) translateY(-50%);
      transform-origin: top left;
      """;
  //      position: absolute;
  //      translateY(-50%)
  //      left: -1cm;
  // bottom: 35mm;

  public static final String RIGHT_MARGIN_INFO_STYLE = """
      border: red solid 1px;
      font-size: 10pt;
      transform: rotate(-90deg) translateY(-50%);
      transform-origin: top left;
      """;
//  position: absolute;
//  width: 100%;
//  left: 20cm;
//  bottom: 35mm;

  public static final String FOOTER_STYLE = """
      margin: 0 20mm 0 20mm;
      border: blue solid 1px;
      width: 17cm;
      height: 30mm;
      font-size: 10pt;
      border-top: black solid 1px;
      justify-content: space-between;
      display: flex;
      """;
  //      margin: 0 20mm;
  //      height: 25mm;

  public static final String HEADER_STYLE = """
      margin: 0 20mm 0 20mm;
      border: blue solid 1px;
      display: grid;
      width: 17cm;
      font-size: 10pt;
      """;
  //

  public static final String WATERMARK_STYLE = """      
      transform: translateX(-50%) translateY(-50%) rotate(315deg);
      font-size: 100pt;
      color: rgb(128, 128, 128);
      opacity: 0.5;
      z-index: -1;
      """;
}
