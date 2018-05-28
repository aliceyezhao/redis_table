package com.company;

import java.util.concurrent.atomic.AtomicInteger;
import redis.clients.jedis.*;

public class RedisInteger {

    private Jedis jedis;
    private int localInt;
    private String rootKey;

    //given start integer, default is 0
    RedisInteger(String ipAddress, int portNumber, String rootKey, int value) {
        this.localInt = value;
        this.jedis = new Jedis(ipAddress, portNumber);
        this.rootKey = rootKey;
        this.set(value);
    }

    RedisInteger(String ipAddress, int portNumber, String rootKey) {
        this.localInt = 0;
        this.jedis = new Jedis(ipAddress, portNumber);
        this.rootKey = rootKey;
    }

    public int get() {
        return Integer.parseInt(jedis.get(rootKey));
    }

    public int set(int value) {
        localInt = value;
        jedis.set(rootKey, Integer.toString(value));
        return value;
    }

    public void clear() {
        this.set(0);
        localInt = 0;
    }

    public int redisIncr() {
        localInt++;
        return jedis.incr(rootKey).intValue();
    }

    public int redisDecr() {
        localInt--;
        return jedis.decr(rootKey).intValue();
    }

    private int getLocalInt() {
        return localInt;
    }
}
