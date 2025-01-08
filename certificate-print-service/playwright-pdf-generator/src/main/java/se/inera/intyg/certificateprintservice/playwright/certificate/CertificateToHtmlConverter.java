package se.inera.intyg.certificateprintservice.playwright.certificate;

import static se.inera.intyg.certificateprintservice.playwright.Constants.CONTENT;
import static se.inera.intyg.certificateprintservice.playwright.Constants.STYLE;

import com.microsoft.playwright.Page;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.html.HTML.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Certificate;
import se.inera.intyg.certificateprintservice.pdfgenerator.api.Metadata;
import se.inera.intyg.certificateprintservice.playwright.element.ElementProvider;
import se.inera.intyg.certificateprintservice.playwright.element.InformationElementFactory;
import se.inera.intyg.certificateprintservice.playwright.pdf.TemplateToDocumentConverter;
import se.inera.intyg.certificateprintservice.playwright.text.TextFactory;

@Slf4j
@Component
@RequiredArgsConstructor
public class CertificateToHtmlConverter {

  private final TemplateToDocumentConverter templateToDocumentConverter;

  private String convert(Resource template, Metadata metadata, Page page, String htmlHeader,
      List<Element> children) throws IOException {
    final var document = templateToDocumentConverter.convert(template, htmlHeader, page, metadata);
    final var content = document.getElementById(CONTENT);

    if (content == null) {
      throw new IllegalStateException("Could not find element 'content' in document");
    }

    content.appendChildren(children);

    final var script = document.getElementsByTag(Tag.SCRIPT.toString());
    script.attr("src", templateToDocumentConverter.getScriptSource());
    return document.html();
  }

  public String certificate(Resource template, Certificate certificate, Page headerPage,
      String certificateHtml) throws IOException {
    final var metadata = certificate.getMetadata();
    final var children = new ArrayList<>(certificate.getCategories().stream()
        .map(CategoryConverter::category)
        .toList());

    if (metadata.isSigned()) {
      children.add(InformationElementFactory.issuerName(metadata));
    }

    children.add(InformationElementFactory.contactInfo(metadata));

    if (metadata.isSigned()) {
      children.add(InformationElementFactory.signingDate(metadata));
    }

    return convert(
        template,
        certificate.getMetadata(),
        headerPage,
        certificateHtml,
        children
    );
  }

  public String certificateInformation(Resource template, Page infoHeaderPage,
      String htmlHeader, Metadata metadata) throws IOException {
    final var children = new ArrayList<Element>();
    children.add(ElementProvider.element(Tag.STRONG).text(metadata.getName()));
    children.add(ElementProvider.element(Tag.P)
        .attr(STYLE, "white-space: pre-line;")
        .append(metadata.getDescription()));
    children.add(ElementProvider.element(Tag.BR));
    children.add(ElementProvider.element(Tag.STRONG).text("Skicka intyg till mottagare"));
    children.add(ElementProvider.element(Tag.P)
        .attr(STYLE, "white-space: pre-line;")
        .append(TextFactory.citizenInformation()));

    return convert(
        template,
        metadata,
        infoHeaderPage,
        htmlHeader,
        children
    );
  }
}
