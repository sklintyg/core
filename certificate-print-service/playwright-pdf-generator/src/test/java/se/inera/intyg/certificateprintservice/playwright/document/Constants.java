package se.inera.intyg.certificateprintservice.playwright.document;

import org.jsoup.parser.Tag;

public class Constants {

  public static final String TEXT = "Text";
  public static final String TAG_TYPE = "Tag type";
  public static final String ATTRIBUTES = "Attributes";
  public static final String NUM_CHILDREN = "Number of children";
  public static final String NUM_ATTRIBUTES = "Number of Attributes";

  public static final String ALT = "alt";
  public static final String SRC = "src";
  public static final String HREF = "href";
  public static final String CLASS = "class";

  public static final Tag A = Tag.valueOf("a");
  public static final Tag P = Tag.valueOf("p");
  public static final Tag BR = Tag.valueOf("br");
  public static final Tag H2 = Tag.valueOf("h2");
  public static final Tag H3 = Tag.valueOf("h3");
  public static final Tag DIV = Tag.valueOf("div");
  public static final Tag IMG = Tag.valueOf("img");
  public static final Tag STRONG = Tag.valueOf("strong");

}
