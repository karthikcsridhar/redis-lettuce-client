// Copyright (C) 2016 - Karthik C Sridhar.  All rights reserved.

package com.csk.redis.output;

import com.csk.redis.ScoredValue;
import com.csk.redis.codec.RedisCodec;
import com.csk.redis.protocol.CommandOutput;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link List} of values and their associated scores.
 *
 * @param <V> Value type.
 *
 * @author Karthik C Sridhar
 */
public class ScoredValueListOutput<K, V> extends CommandOutput<K, V, List<ScoredValue<V>>> {
    private V value;

    public ScoredValueListOutput(RedisCodec<K, V> codec) {
        super(codec, new ArrayList<ScoredValue<V>>());
    }

    @Override
    public void set(ByteBuffer bytes) {
        if (value == null) {
            value = codec.decodeValue(bytes);
            return;
        }

        double score = Double.parseDouble(decodeAscii(bytes));
        output.add(new ScoredValue<V>(score, value));
        value = null;
    }
}
