package dev.earlspilner.library.feign.advice;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Alexander Dudkin
 */
@Service
public class RetrieveMessageErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();

    @Autowired
    private Map<String, FeignExceptionHandler> feignExceptionHandlers;

    @Override
    public Exception decode(String s, Response response) {
        String requestUrl = response.request().url();
        FeignExceptionHandler handler = feignExceptionHandlers.get(String.valueOf(response.status()));

        if (handler != null) {
            return handler.handleException(response);
        }

        return new Exception("No handler found. Generic exception for URL: " + requestUrl);
    }
}
