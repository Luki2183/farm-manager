package pl.luki2183.farmManager.config;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(1)
public class CorrelationIdFilter implements Filter {

    private static final String CORRELATION_ID_KEY = "correlationId";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            String correlationId = UUID.randomUUID().toString().substring(0, 8);
            MDC.put(CORRELATION_ID_KEY, correlationId);
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.remove(CORRELATION_ID_KEY);
        }
    }
}
