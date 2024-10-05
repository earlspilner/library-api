package dev.earlspilner.users.rest.advice;

/**
 * @author Alexander Dudkin
 */
public record Success<T>(T value) implements Result<T> { }
