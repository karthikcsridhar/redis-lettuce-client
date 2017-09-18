// Copyright (C) 2016 - Karthik C Sridhar.  All rights reserved.

package com.csk.redis;

/**
 * Exception thrown when redis returns an error message, or when the client
 * fails for any reason.
 *
 * @author Karthik C Sridhar
 */
@SuppressWarnings("serial")
public class RedisException extends RuntimeException {
    public RedisException(String msg) {
        super(msg);
    }

    public RedisException(String msg, Throwable e) {
        super(msg, e);
    }
}
