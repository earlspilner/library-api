package dev.earlspilner.auth.feign.advice;

import feign.FeignException;
import feign.Response;

/**
 * @author Alexander Dudkin
 */
public interface FeignExceptionHandler {
    FeignException handleException(Response response);
}
