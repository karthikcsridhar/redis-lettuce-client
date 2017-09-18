// Copyright (C) 2016 - Karthik C Sridhar.  All rights reserved.

package com.csk.redis.output;

import com.csk.redis.codec.RedisCodec;
import com.csk.redis.protocol.CommandOutput;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link List} of string output.
 *
 * @author Karthik C Sridhar
 */
public class StringListOutput<K, V> extends CommandOutput<K, V, List<String>> {
    public StringListOutput(RedisCodec<K, V> codec) {
        super(codec, new ArrayList<String>());
    }

    @Override
    public void set(ByteBuffer bytes) {
        output.add(bytes == null ? null : decodeAscii(bytes));
    }
}
