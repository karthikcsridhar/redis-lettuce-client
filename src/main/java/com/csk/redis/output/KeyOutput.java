// Copyright (C) 2013 - Karthik C Sridhar.  All rights reserved.

package com.csk.redis.output;

import com.csk.redis.codec.RedisCodec;
import com.csk.redis.protocol.CommandOutput;

import java.nio.ByteBuffer;

/**
 * Key output.
 *
 * @param <K> Key type.
 *
 * @author Karthik C Sridhar
 */
public class KeyOutput<K, V> extends CommandOutput<K, V, K> {
    public KeyOutput(RedisCodec<K, V> codec) {
        super(codec, null);
    }

    @Override
    public void set(ByteBuffer bytes) {
        output = (bytes == null) ? null : codec.decodeKey(bytes);
    }
}
