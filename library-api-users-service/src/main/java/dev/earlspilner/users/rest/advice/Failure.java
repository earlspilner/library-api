package dev.earlspilner.users.rest.advice;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;

/**
 * @author Alexander Dudkin
 */
public record Failure<T>(String exMessage, int httpStatus) implements Result<T> {

    public ProblemDetail toProblemDetail() {
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(httpStatus), exMessage);
    }

}
