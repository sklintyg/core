package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.prefill;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7427.elements.QuestionGrundForMedicinsktUnderlag.questionGrundForMedicinsktUnderlag;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.CVType;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v33.Forifyllnad;

class CheckboxMultipleDatesConverterTest {

  CheckboxMultipleDatesConverter checkboxMultipleDatesConverter = new CheckboxMultipleDatesConverter();

  @Test
  void testPrefillCheckboxMultipleDates() throws JAXBException, ParserConfigurationException {
    //create svar from file or build tooling for elements to put in content?

    var forifyllnad = new Forifyllnad();
    var svar = new Svar();
    svar.setId("1");
    final var delsvarCode = new Delsvar();
    delsvarCode.setId("1.1");
    final var cvType = new CVType();
    cvType.setCode("FYSISKUNDERSOKNING");
    cvType.setCodeSystem("KV_FKMU_0001");
    cvType.setDisplayName("min undersökning vid fysiskt vårdmöte");
    svar.getDelsvar().add(delsvarCode);
    forifyllnad.getSvar().add(svar);

    JAXBContext context = JAXBContext.newInstance(CVType.class);
    Marshaller marshaller = context.createMarshaller();

    QName qName = new QName("urn:riv:clinicalprocess:healthcond:certificate:types:v3", "CVType");
    JAXBElement<CVType> root = new JAXBElement<>(qName, CVType.class, cvType);

    Document document = DocumentBuilderFactory.newInstance()
        .newDocumentBuilder().newDocument();
    marshaller.marshal(root, document);

    delsvarCode.getContent().add(document.getDocumentElement());

    final var delsvarDate = new Delsvar();
    delsvarDate.setId("1.2");
    delsvarDate.getContent().add("2023-10-01");
    svar.getDelsvar().add(delsvarDate);
    checkboxMultipleDatesConverter.prefillAnswer(List.of(svar),
        questionGrundForMedicinsktUnderlag());


  }


}