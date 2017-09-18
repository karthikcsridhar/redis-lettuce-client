// Copyright (C) 2016 - Karthik C Sridhar.  All rights reserved.

package com.csk.redis.output;

import com.csk.redis.codec.RedisCodec;
import com.csk.redis.protocol.CommandOutput;

import java.util.Date;

/**
 * Date output with no milliseconds.
 *
 * @author Karthik C Sridhar
 */
public class DateOutput<K, V> extends CommandOutput<K, V, Date> {
    public DateOutput(RedisCodec<K, V> codec) {
        super(codec, null);
    }

    @Override
    public void set(long time) {
        output = new Date(time * 1000);
    }
}
