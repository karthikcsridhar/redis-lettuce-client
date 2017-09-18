// Copyright (C) 2016 - Karthik C Sridhar.  All rights reserved.

package com.csk.redis.output;

import com.csk.redis.codec.RedisCodec;
import com.csk.redis.protocol.CommandOutput;

import java.nio.ByteBuffer;

import static java.lang.Double.parseDouble;

/**
 * Double output, may be null.
 *
 * @author Karthik C Sridhar
 */
public class DoubleOutput<K, V> extends CommandOutput<K, V, Double> {
    public DoubleOutput(RedisCodec<K, V> codec) {
        super(codec, null);
    }

    @Override
    public void set(ByteBuffer bytes) {
        output = (bytes == null) ? null : parseDouble(decodeAscii(bytes));
    }
}
