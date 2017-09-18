// Copyright (C) 2016 - Karthik C Sridhar.  All rights reserved.

package com.csk.redis.output;

import com.csk.redis.codec.RedisCodec;
import com.csk.redis.protocol.CommandOutput;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

/**
 * {@link Set} of value output.
 *
 * @param <V> Value type.
 *
 * @author Karthik C Sridhar
 */
public class ValueSetOutput<K, V> extends CommandOutput<K, V, Set<V>> {
    public ValueSetOutput(RedisCodec<K, V> codec) {
        super(codec, new HashSet<V>());
    }

    @Override
    public void set(ByteBuffer bytes) {
        output.add(bytes == null ? null : codec.decodeValue(bytes));
    }
}
