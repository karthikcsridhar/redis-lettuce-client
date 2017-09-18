// Copyright (C) 2016 - Karthik C Sridhar.  All rights reserved.

package com.csk.redis;

/**
 * Exception thrown when the thread executing a redis command is
 * interrupted.
 *
 * @author Karthik C Sridhar
 */
@SuppressWarnings("serial")
public class RedisCommandInterruptedException extends RedisException {
    public RedisCommandInterruptedException(Throwable e) {
        super("Command interrupted", e);
    }
}
