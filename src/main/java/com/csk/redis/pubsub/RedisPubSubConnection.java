// Copyright (C) 2016 - Karthik C Sridhar.  All rights reserved.

package com.csk.redis.pubsub;

import com.csk.redis.RedisAsyncConnection;
import com.csk.redis.codec.RedisCodec;
import com.csk.redis.protocol.Command;
import com.csk.redis.protocol.CommandArgs;

import org.jboss.netty.channel.*;

import static com.csk.redis.protocol.CommandType.*;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.*;

/**
 * An asynchronous thread-safe pub/sub connection to a redis server. After one or
 * more channels are subscribed to only pub/sub related commands or {@link #quit}
 * may be called.
 *
 * Incoming messages and results of the {@link #subscribe}/{@link #unsubscribe}
 * calls will be passed to all registered {@link RedisPubSubListener}s.
 *
 * A {@link com.csk.redis.protocol.ConnectionWatchdog} monitors each
 * connection and reconnects automatically until {@link #close} is called. Channel
 * and pattern subscriptions are renewed after reconnecting.
 *
 * @author Karthik C Sridhar
 */
public class RedisPubSubConnection<K, V> extends RedisAsyncConnection<K, V> {
    private List<RedisPubSubListener<K, V>> listeners;
    private Set<K> channels;
    private Set<K> patterns;

    /**
     * Initialize a new connection.
     *
     * @param queue     Command queue.
     * @param codec     Codec used to encode/decode keys and values.
     * @param timeout   Maximum time to wait for a responses.
     * @param unit      Unit of time for the timeout.
     */
    public RedisPubSubConnection(BlockingQueue<Command<K, V, ?>> queue, RedisCodec<K, V> codec, long timeout, TimeUnit unit) {
        super(queue, codec, timeout, unit);
        listeners = new CopyOnWriteArrayList<RedisPubSubListener<K, V>>();
        channels  = new HashSet<K>();
        patterns  = new HashSet<K>();
    }

    /**
     * Add a new listener.
     *
     * @param listener Listener.
     */
    public void addListener(RedisPubSubListener<K, V> listener) {
        listeners.add(listener);
    }

    /**
     * Remove an existing listener.
     *
     * @param listener Listener.
     */
    public void removeListener(RedisPubSubListener<K, V> listener) {
        listeners.remove(listener);
    }

    public void psubscribe(K... patterns) {
        dispatch(PSUBSCRIBE, new PubSubOutput<K, V>(codec), args(patterns));
    }

    public void punsubscribe(K... patterns) {
        dispatch(PUNSUBSCRIBE, new PubSubOutput<K, V>(codec), args(patterns));
    }

    public void subscribe(K... channels) {
        dispatch(SUBSCRIBE, new PubSubOutput<K, V>(codec), args(channels));
    }

    public void unsubscribe(K... channels) {
        dispatch(UNSUBSCRIBE, new PubSubOutput<K, V>(codec), args(channels));
    }

    @Override
    public synchronized void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        super.channelConnected(ctx, e);

        if (channels.size() > 0) {
            subscribe(toArray(channels));
            channels.clear();
        }

        if (patterns.size() > 0) {
            psubscribe(toArray(patterns));
            patterns.clear();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        PubSubOutput<K, V> output = (PubSubOutput<K, V>) e.getMessage();
        for (RedisPubSubListener<K, V> listener : listeners) {
            switch (output.type()) {
                case message:
                    listener.message(output.channel(), output.get());
                    break;
                case pmessage:
                    listener.message(output.pattern(), output.channel(), output.get());
                    break;
                case psubscribe:
                    patterns.add(output.pattern());
                    listener.psubscribed(output.pattern(), output.count());
                    break;
                case punsubscribe:
                    patterns.remove(output.pattern());
                    listener.punsubscribed(output.pattern(), output.count());
                    break;
                case subscribe:
                    channels.add(output.channel());
                    listener.subscribed(output.channel(), output.count());
                    break;
                case unsubscribe:
                    channels.remove(output.channel());
                    listener.unsubscribed(output.channel(), output.count());
                    break;
            }
        }
    }

    private CommandArgs<K, V> args(K... keys) {
        CommandArgs<K, V> args = new CommandArgs<K, V>(codec);
        args.addKeys(keys);
        return args;
    }

    @SuppressWarnings("unchecked")
    private <T> T[] toArray(Collection<T> c) {
        Class<T> cls = (Class<T>) c.iterator().next().getClass();
        T[] array = (T[]) Array.newInstance(cls, c.size());
        return c.toArray(array);
    }
}
