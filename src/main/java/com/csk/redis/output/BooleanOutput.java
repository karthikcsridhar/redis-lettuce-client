// Copyright (C) 2016 - Karthik C Sridhar.  All rights reserved.

package com.csk.redis.output;

import com.csk.redis.codec.RedisCodec;
import com.csk.redis.protocol.CommandOutput;

import java.nio.ByteBuffer;

/**
 * Boolean output. The actual value is returned as an integer
 * where 0 indicates false and 1 indicates true, or as a null
 * bulk reply for script output.
 *
 * @author Karthik C Sridhar
 */
public class BooleanOutput<K, V> extends CommandOutput<K, V, Boolean> {
    public BooleanOutput(RedisCodec<K, V> codec) {
        super(codec, null);
    }

    @Override
    public void set(long integer) {
        output = (integer == 1) ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public void set(ByteBuffer bytes) {
        output = (bytes != null) ? Boolean.TRUE : Boolean.FALSE;
    }
}
