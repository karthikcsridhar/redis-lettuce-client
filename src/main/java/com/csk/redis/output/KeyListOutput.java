// Copyright (C) 2016 - Karthik C Sridhar.  All rights reserved.

package com.csk.redis.output;

import com.csk.redis.codec.RedisCodec;
import com.csk.redis.protocol.CommandOutput;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link List} of keys output.
 *
 * @param <K> Key type.
 *
 * @author Karthik C Sridhar
 */
public class KeyListOutput<K, V> extends CommandOutput<K, V, List<K>> {
    public KeyListOutput(RedisCodec<K, V> codec) {
        super(codec, new ArrayList<K>());
    }

    @Override
    public void set(ByteBuffer bytes) {
        output.add(codec.decodeKey(bytes));
    }
}
