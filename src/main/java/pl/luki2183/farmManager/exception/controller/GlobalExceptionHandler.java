package pl.luki2183.farmManager.exception.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.luki2183.farmManager.exception.dto.ErrorDto;
import pl.luki2183.farmManager.exception.dto.FieldError;
import pl.luki2183.farmManager.exception.model.NotFoundException;
import pl.luki2183.farmManager.exception.model.PrimaryKeyViolationException;
import pl.luki2183.farmManager.exception.model.WeatherInfoException;

import java.util.List;

/**
 * Global exception handler providing centralized error handling across the application.
 *
 * <p>Handles both REST API requests and Thymeleaf view requests by detecting
 * the request type and returning either a JSON {@link ErrorDto} response or
 * redirecting to the {@code error} view accordingly.</p>
 *
 * <p>Mapped exception types and their HTTP status codes:</p>
 * <ul>
 *     <li>{@link NotFoundException} = {@code 404 Not Found}</li>
 *     <li>{@link PrimaryKeyViolationException} = {@code 409 Conflict}</li>
 *     <li>{@link WeatherInfoException} = {@code 502 Bad Gateway}</li>
 *     <li>{@link IllegalArgumentException} = {@code 400 Bad Request}</li>
 *     <li>{@link Exception} (fallback) = {@code 500 Internal Server Error}</li>
 * </ul>
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link NotFoundException} and all its subclasses.
     *
     * @param ex      the thrown exception
     * @param request the current HTTP request
     * @param model   the Spring MVC model, used when rendering the error view
     * @return a {@link ResponseEntity} with {@code 404} for API requests,
     *         or the {@code error} view for browser requests
     */
    @ExceptionHandler(NotFoundException.class)
    public Object handleNotFound(NotFoundException ex, HttpServletRequest request, Model model) {
        return handle(ex, HttpStatus.NOT_FOUND, "Not Found", request, model);
    }

    /**
     * Handles {@link PrimaryKeyViolationException} thrown when a duplicate resource is posted.
     *
     * @param ex      the thrown exception
     * @param request the current HTTP request
     * @param model   the Spring MVC model, used when rendering the error view
     * @return a {@link ResponseEntity} with {@code 409} for API requests,
     *         or the {@code error} view for browser requests
     */
    @ExceptionHandler(PrimaryKeyViolationException.class)
    public Object handleConflict(PrimaryKeyViolationException ex, HttpServletRequest request, Model model) {
        return handle(ex, HttpStatus.CONFLICT, "Conflict", request, model);
    }

    /**
     * Handles {@link WeatherInfoException} thrown when the external Google Weather API is unavailable
     * or returns an unexpected response.
     *
     * @param ex      the thrown exception
     * @param request the current HTTP request
     * @param model   the Spring MVC model, used when rendering the error view
     * @return a {@link ResponseEntity} with {@code 502} for API requests,
     *         or the {@code error} view for browser requests
     */
    @ExceptionHandler(WeatherInfoException.class)
    public Object handleWeatherInfo(WeatherInfoException ex, HttpServletRequest request, Model model) {
        return handle(ex, HttpStatus.BAD_GATEWAY, "Weather Service Unavailable", request, model);
    }

    /**
     * Handles {@link IllegalArgumentException} thrown when invalid input is supplied.
     *
     * @param ex      the thrown exception
     * @param request the current HTTP request
     * @param model   the Spring MVC model, used when rendering the error view
     * @return a {@link ResponseEntity} with {@code 400} for API requests,
     *         or the {@code error} view for browser requests
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Object handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request, Model model) {
        return handle(ex, HttpStatus.BAD_REQUEST, "Bad Request", request, model);
    }

    /**
     * Fallback handler for any unhandled exception type. Logs the full stack trace
     * at error level before delegating to the standard error response.
     *
     * @param ex      the thrown exception
     * @param request the current HTTP request
     * @param model   the Spring MVC model, used when rendering the error view
     * @return a {@link ResponseEntity} with {@code 500} for API requests,
     *         or the {@code error} view for browser requests
     */
    @ExceptionHandler(Exception.class)
    public Object handleGeneric(Exception ex, HttpServletRequest request, Model model) {
        log.error("Unhandled exception on {}: {}", request.getRequestURI(), ex.getMessage(), ex);
        return handle(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected Error", request, model);
    }

    /**
     * Routes the exception to either a JSON or view response based on the request type.
     *
     * @param ex         the thrown exception
     * @param httpStatus the HTTP status to use in the response
     * @param title      a short human-readable error title for the view
     * @param request    the current HTTP request
     * @param model      the Spring MVC model
     * @return a {@link ResponseEntity} for API requests, or a view name {@link String} for browser requests
     */
    private Object handle(Exception ex, HttpStatus httpStatus, String title, HttpServletRequest request, Model model) {
        if (isApiRequest(request)) {
            return buildJsonResponse(ex, httpStatus, request);
        }
        return buildViewResponse(ex, httpStatus, title, model);
    }

    /**
     * Populates the model with error attributes and returns the logical name
     * of the error Thymeleaf template.
     *
     * @param ex         the thrown exception
     * @param httpStatus the HTTP status associated with the error
     * @param title      a short human-readable error title
     * @param model      the Spring MVC model to populate
     * @return the logical name of the error view ({@code "error"})
     */
    private String buildViewResponse(Exception ex, HttpStatus httpStatus, String title, Model model) {
        model.addAttribute("errorTitle", title);
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("errorCode", httpStatus.value());
        return "error";
    }

    /**
     * Builds a JSON {@link ResponseEntity} containing an {@link ErrorDto}
     * with a single {@link FieldError} entry.
     *
     * @param ex         the thrown exception
     * @param httpStatus the HTTP status to include in the response
     * @param request    the current HTTP request, used to populate the error path
     * @return a {@link ResponseEntity} wrapping the {@link ErrorDto}
     */
    private ResponseEntity<ErrorDto> buildJsonResponse(Exception ex, HttpStatus httpStatus, HttpServletRequest request) {
        FieldError error = FieldError.builder()
                .code(httpStatus)
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        ErrorDto result = new ErrorDto();
        result.setErrorList(List.of(error));
        return ResponseEntity.status(httpStatus).body(result);
    }

    /**
     * Determines whether the current request expects a JSON response.
     *
     * <p>A request is treated as an API request if its path starts with {@code /api}
     * or its {@code Accept} header contains {@code application/json}.</p>
     *
     * @param request the current HTTP request
     * @return {@code true} if the request should receive a JSON response,
     *         {@code false} if it should receive a view response
     */
    private boolean isApiRequest(HttpServletRequest request) {
        String path = request.getRequestURI();
        String accept = request.getHeader("Accept");
        return path.startsWith("/api") || (accept != null && accept.contains("application/json"));
    }
}
