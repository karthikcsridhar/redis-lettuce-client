// Copyright (C) 2016 - Karthik C Sridhar.  All rights reserved.

package com.csk.redis;

import org.junit.*;

import com.csk.redis.KeyValue;
import com.csk.redis.RedisClient;
import com.csk.redis.RedisConnection;
import com.csk.redis.ScoredValue;

import java.util.*;

public abstract class AbstractCommandTest {
    public static final String host = "localhost";
    public static final int    port = 6379;

    public static final String passwd = "passwd";

    protected static RedisClient client;
    protected RedisConnection<String, String> redis;
    protected String key   = "key";
    protected String value = "value";

    @BeforeClass
    public static void setupClient() {
        client = new RedisClient(host, port);
    }

    @AfterClass
    public static void shutdownClient() {
        client.shutdown();
    }

    @Before
    public final void openConnection() throws Exception {
        redis = client.connect();
        redis.flushall();
    }

    @After
    public final void closeConnection() throws Exception {
        redis.close();
    }

    protected List<String> list(String... args) {
        return Arrays.asList(args);
    }

    protected List<Object> list(Object... args) {
        return Arrays.asList(args);
    }

    protected List<ScoredValue<String>> svlist(ScoredValue<String>... args) {
        return Arrays.asList(args);
    }

    protected KeyValue<String, String> kv(String key, String value) {
        return new KeyValue<String, String>(key, value);
    }

    protected ScoredValue<String> sv(double score, String value) {
        return new ScoredValue<String>(score, value);
    }

    protected Set<String> set(String... args) {
        return new HashSet<String>(Arrays.asList(args));
    }

    public abstract class WithPasswordRequired {
        protected abstract void run(RedisClient client) throws Exception;

        public WithPasswordRequired() throws Exception {
            try {
                redis.configSet("requirepass", passwd);
                redis.auth(passwd);

                RedisClient client = new RedisClient(host, port);
                try {
                    run(client);
                } finally {
                    client.shutdown();
                }
            } finally {
                redis.configSet("requirepass", "");
            }
        }
    }
}
