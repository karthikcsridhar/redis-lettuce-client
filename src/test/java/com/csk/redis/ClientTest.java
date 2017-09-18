// Copyright (C) 2016 - Karthik C Sridhar.  All rights reserved.

package com.csk.redis;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.csk.redis.RedisClient;
import com.csk.redis.RedisCommandInterruptedException;
import com.csk.redis.RedisConnection;
import com.csk.redis.RedisException;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class ClientTest extends AbstractCommandTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test(expected = RedisException.class)
    public void close() throws Exception {
        redis.close();
        redis.get(key);
    }

    @Test(expected = RedisException.class)
    public void shutdown() throws Exception {
        RedisClient client = new RedisClient(host);
        RedisConnection<String, String> connection = client.connect();
        client.shutdown();
        connection.get(key);
    }

    @Test(expected = RedisException.class, timeout = 100)
    public void timeout() throws Exception {
        redis.setTimeout(0, TimeUnit.MICROSECONDS);
        redis.get(key);
    }

    @Test
    public void reconnect() throws Exception {
        redis.set(key, value);
        redis.quit();
        assertEquals(value, redis.get(key));
    }

    @Test(expected = RedisCommandInterruptedException.class, timeout = 10)
    public void interrupt() throws Exception {
        Thread.currentThread().interrupt();
        redis.blpop(0, key);
    }

    @Test
    public void connectFailure() throws Exception {
        RedisClient client = new RedisClient("invalid");
        exception.expect(RedisException.class);
        exception.expectMessage("Unable to connect");
        client.connect();
    }

    @Test
    public void connectPubSubFailure() throws Exception {
        RedisClient client = new RedisClient("invalid");
        exception.expect(RedisException.class);
        exception.expectMessage("Unable to connect");
        client.connectPubSub();
    }
}
