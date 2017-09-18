// Copyright (C) 2016 - Karthik C Sridhar.  All rights reserved.

package com.csk.redis.output;

import com.csk.redis.KeyValue;
import com.csk.redis.codec.RedisCodec;
import com.csk.redis.protocol.CommandOutput;

import java.nio.ByteBuffer;

/**
 * Key-value pair output.
 *
 * @param <K> Key type.
 * @param <V> Value type.
 *
 * @author Karthik C Sridhar
 */
public class KeyValueOutput<K, V> extends CommandOutput<K, V, KeyValue<K, V>> {
    private K key;

    public KeyValueOutput(RedisCodec<K, V> codec) {
        super(codec, null);
    }

    @Override
    public void set(ByteBuffer bytes) {
        if (bytes != null) {
            if (key == null) {
                key = codec.decodeKey(bytes);
            } else {
                V value = codec.decodeValue(bytes);
                output = new KeyValue<K, V>(key, value);
            }
        }
    }
}
