package ru.imakabr.votingsystem.util.exception;

public class TimeVoteLimitException extends RuntimeException
{
    public TimeVoteLimitException(String message) {
        super(message);
    }
}
