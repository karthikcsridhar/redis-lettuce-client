// Copyright (C) 2016 - Karthik C Sridhar.  All rights reserved.

package com.csk.redis.output;

import com.csk.redis.codec.RedisCodec;
import com.csk.redis.protocol.CommandOutput;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * {@link Map} of keys and values output.
 *
 * @param <K> Key type.
 * @param <V> Value type.
 *
 * @author Karthik C Sridhar
 */
public class MapOutput<K, V> extends CommandOutput<K, V, Map<K, V>> {
    private K key;

    public MapOutput(RedisCodec<K, V> codec) {
        super(codec, new HashMap<K, V>());
    }

    @Override
    public void set(ByteBuffer bytes) {
        if (key == null) {
            key = codec.decodeKey(bytes);
            return;
        }

        V value = (bytes == null) ? null : codec.decodeValue(bytes);
        output.put(key, value);
        key = null;
    }
}
