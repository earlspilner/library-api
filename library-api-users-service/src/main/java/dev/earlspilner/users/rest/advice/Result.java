package dev.earlspilner.users.rest.advice;

/**
 * @author Alexander Dudkin
 */
public sealed interface Result<T> permits Success, Failure { }
