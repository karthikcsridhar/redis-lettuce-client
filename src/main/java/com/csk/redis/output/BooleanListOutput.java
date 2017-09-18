// Copyright (C) 2016 - Karthik C Sridhar.  All rights reserved.

package com.csk.redis.output;

import com.csk.redis.codec.RedisCodec;
import com.csk.redis.protocol.CommandOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link java.util.List} of boolean output.
 *
 * @author Karthik C Sridhar
 */
public class BooleanListOutput<K, V> extends CommandOutput<K, V, List<Boolean>> {
    public BooleanListOutput(RedisCodec<K, V> codec) {
        super(codec, new ArrayList<Boolean>());
    }

    @Override
    public void set(long integer) {
        output.add((integer == 1) ? Boolean.TRUE : Boolean.FALSE);
    }
}
