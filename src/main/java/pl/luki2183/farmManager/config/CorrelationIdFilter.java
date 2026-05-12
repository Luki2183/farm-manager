package pl.luki2183.farmManager.config;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

/**
 * Servlet filter that assigns a short correlation ID to each incoming request
 * and stores it in the SLF4J {@link org.slf4j.MDC} under the key {@code correlationId}.
 *
 * <p>The correlation ID is an 8-character prefix of a randomly generated UUID,
 * included automatically in all log entries made during the request lifecycle.
 * The MDC entry is always cleaned up in the {@code finally} block after the
 * filter chain completes.</p>
 *
 * <p>Registered as the highest-priority filter via {@link Order}({@code 1}).</p>
 */
@Component
@Order(1)
public class CorrelationIdFilter implements Filter {

    /** Static final key parameter for {@code MDC.put()} method.*/
    private static final String CORRELATION_ID_KEY = "correlationId";

    /**
     * Generates a correlation ID, binds it to the MDC, processes the request,
     * then removes correlation ID from the MDC.
     *
     * @param servletRequest  the incoming servlet request
     * @param servletResponse the outgoing servlet response
     * @param filterChain     the remaining filter chain to invoke
     * @throws IOException      if an I/O error occurs during filtering
     * @throws ServletException if a servlet error occurs during filtering
     */
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
