// Copyright (C) 2016 - Karthik C Sridhar.  All rights reserved.

package com.csk.redis.protocol;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * {@link Charset}-related utilities.
 *
 * @author Karthik C Sridhar
 */
public class Charsets {
    public static final Charset ASCII = Charset.forName("US-ASCII");

    public static ByteBuffer buffer(String s) {
        return ByteBuffer.wrap(s.getBytes(ASCII));
    }
}
