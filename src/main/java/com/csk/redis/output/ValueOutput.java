// Copyright (C) 2016 - Karthik C Sridhar.  All rights reserved.

package com.csk.redis.output;

import com.csk.redis.codec.RedisCodec;
import com.csk.redis.protocol.CommandOutput;

import java.nio.ByteBuffer;

/**
 * Value output.
 *
 * @param <V> Value type.
 *
 * @author Karthik C Sridhar
 */
public class ValueOutput<K, V> extends CommandOutput<K, V, V> {
    public ValueOutput(RedisCodec<K, V> codec) {
        super(codec, null);
    }

    @Override
    public void set(ByteBuffer bytes) {
        output = (bytes == null) ? null : codec.decodeValue(bytes);
    }
}
