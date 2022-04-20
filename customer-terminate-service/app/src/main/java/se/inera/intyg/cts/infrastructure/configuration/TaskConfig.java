package se.inera.intyg.cts.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import se.inera.intyg.cts.application.service.ExportService;
import se.inera.intyg.cts.application.task.CollectCertificateTextsTask;
import se.inera.intyg.cts.application.task.CollectCertificatesTask;
import se.inera.intyg.cts.application.task.ExportTask;

@Configuration
@EnableScheduling
public class TaskConfig {

  @Bean
  public CollectCertificatesTask collectCertificatesTask(ExportService exportService) {
    return new CollectCertificatesTask(exportService);
  }

  @Bean
  public CollectCertificateTextsTask collectCertificateTextsTask(ExportService exportService) {
    return new CollectCertificateTextsTask(exportService);
  }

  @Bean
  public ExportTask exportTask(ExportService exportService) {
    return new ExportTask(exportService);
  }
}
