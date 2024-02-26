package se.inera.intyg.certificateservice.infrastructure.logging;

import static se.inera.intyg.certificateservice.infrastructure.logging.MDCLogConstants.MDC_SESSION_ID_KEY;
import static se.inera.intyg.certificateservice.infrastructure.logging.MDCLogConstants.MDC_TRACE_ID_KEY;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MDCServletFilter implements Filter {

  private final MDCHelper mdcHelper;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    try {
      if (request instanceof HttpServletRequest http) {
        MDC.put(MDC_SESSION_ID_KEY, mdcHelper.sessionId(http));
        MDC.put(MDC_TRACE_ID_KEY, mdcHelper.traceId(http));
      }
      chain.doFilter(request, response);
    } finally {
      MDC.clear();
    }
  }
}