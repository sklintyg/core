package se.inera.intyg.certificateservice.testability.certificate.service.fillservice.fk7443;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7443.CertificateModelFactoryFK7443.FK7443_V1_0;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7443.CertificateModelFactoryFK7443.QUESTION_PERIOD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7443.CertificateModelFactoryFK7443.QUESTION_SYMPTOM_ID;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO;
import se.inera.intyg.certificateservice.testability.certificate.service.fillservice.TestabilityCertificateFillService;

@Component
public class TestabilityCertificateFillServiceFK7443 implements TestabilityCertificateFillService {

    @Override
    public List<CertificateModelId> certificateModelIds() {
        return List.of(FK7443_V1_0);
    }

    @Override
    public List<ElementData> fill(CertificateModel certificateModel,
        TestabilityFillTypeDTO fillType) {
        if (TestabilityFillTypeDTO.EMPTY.equals(fillType)) {
            return Collections.emptyList();
        }
        return fillWithValues(certificateModel);
    }

    private static List<ElementData> fillWithValues(CertificateModel certificateModel) {
        final var elementSpecificationSymptom = certificateModel.elementSpecification(
            QUESTION_SYMPTOM_ID);
        final var elementValueSymptom = elementSpecificationSymptom.configuration().emptyValue();
        final var elementData = new ArrayList<ElementData>();
        if (elementValueSymptom instanceof ElementValueText elementValueText) {
            final var symptom = elementValueText.withText(
                "Barnet har hostat och haft feber i två veckor. Barnet har svårt att sova på grund av hostan och har därmed ingen energi. Symtomen tyder på vanlig influensa.");
            elementData.add(
                ElementData.builder()
                    .id(QUESTION_SYMPTOM_ID)
                    .value(symptom)
                    .build()
            );
        }

        final var elementSpecificationPeriod = certificateModel.elementSpecification(
            QUESTION_PERIOD_ID
        );
        final var elementValuePeriod = elementSpecificationPeriod.configuration().emptyValue();
        if (elementValuePeriod instanceof ElementValueDateRangeList elementValueDateRangeList) {
            final var period = elementValueDateRangeList.withDateRangeList(
                List.of(
                    DateRange.builder()
                        .dateRangeId(new FieldId("HALVA"))
                        .from(LocalDate.now())
                        .to(LocalDate.now().plusDays(14))
                        .build()
                )
            );
            elementData.add(
                ElementData.builder()
                    .id(QUESTION_PERIOD_ID)
                    .value(period)
                    .build()
            );
        }

        return elementData;
    }
}
