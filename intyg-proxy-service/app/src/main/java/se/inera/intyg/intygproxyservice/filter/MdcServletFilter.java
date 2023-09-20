package se.inera.intyg.intygproxyservice.filter;


import static se.inera.intyg.intygproxyservice.common.MDCLogConstants.LOG_SESSION_ID_HEADER;
import static se.inera.intyg.intygproxyservice.common.MDCLogConstants.LOG_TRACE_ID_HEADER;
import static se.inera.intyg.intygproxyservice.common.MDCLogConstants.MDC_SESSION_ID_KEY;
import static se.inera.intyg.intygproxyservice.common.MDCLogConstants.MDC_TRACE_ID_KEY;

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
public class MdcServletFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    if (request instanceof final HttpServletRequest http) {
      MDC.put(MDC_TRACE_ID_KEY, http.getHeader(LOG_TRACE_ID_HEADER));
      MDC.put(MDC_SESSION_ID_KEY, http.getHeader(LOG_SESSION_ID_HEADER));
    }
    try {
      chain.doFilter(request, response);
    } finally {
      MDC.clear();
    }
  }
}
