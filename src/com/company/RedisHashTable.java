package com.company;

import java.io.Serializable;
import java.util.*;
//import java.util.concurrent.atomic.AtomicInteger;

import redis.clients.jedis.*;

public class RedisHashTable implements Map<String, String>, Cloneable, Serializable {

    private transient Map<String, String> innerMap = new HashMap<String, String>();
    //transient Set<redisNode> entrySet = new RedisSet<redisNode>();
    private String rootKey;
    private Jedis jedis;
    private int localSize;
    //private AtomicInteger size;

    public RedisHashTable(String ipAddress, int portNumber, String rootKey) {
        this.jedis = new Jedis(ipAddress, portNumber);
        this.rootKey = rootKey;
        this.localSize = 0;
        //this.size = new AtomicInteger(jedis.hlen(rootKey).intValue());
    }

    private Map<String, String> getMap() {
        return jedis.hgetAll(rootKey);
    }

    @Override
    public void clear() {
        getMap().clear();
        innerMap.clear();
        localSize = 0;
        //size = new AtomicInteger(0);
    }

    @Override
    public boolean containsKey(Object key) {
        //return keySet().contains(key);
        return jedis.hexists(rootKey, key.toString());
    }

    @Override
    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    @Override
    public Set<Map.Entry<String, String>> entrySet() {
        return getMap().entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return innerMap.equals(o);
    }

    @Override
    public String get(Object key) {
        if (innerMap.get(key) != null) {
            return innerMap.get(key);
        }
        return jedis.hget(rootKey, key.toString());
    }

    @Override
    public int hashCode() {
        return innerMap.hashCode();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public Set<String> keySet() {
        return jedis.hkeys(rootKey);
    }

    @Override
    public String put(String key, String value) {
        jedis.hset(rootKey, key, value);
        innerMap.put(key, value);
        if (!containsKey(key)) {
            localSize++;
        }
        //jedis.incr(size.toString());
        return value;
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        Map<String, String> map = new HashMap<String, String>();
        map.putAll(m);
        jedis.hmset(rootKey, map);
        localSize += map.size();
        //int newSize = size() + m.size();
        //size = new AtomicInteger(newSize);
    }

    @Override
    public String remove(Object key) {
        if (containsKey(key)) {
            localSize--;
            //jedis.decr(size.toString());
            String removed = get(key);
            jedis.hdel(rootKey, key.toString());
            innerMap.remove(key);
            return removed;
        }
        return null;
    }

    @Override
    public int size() {
        return jedis.hlen(rootKey).intValue();
    }

    @Override
    public Collection<String> values() {
        return jedis.hvals(rootKey);
    }

//    static class redisNode<String, String> implements Map.Entry<String, String>  {
//        final int hash;
//        final String key;
//        String value;
//        redisNode<String, String> next;
//
//        redisNode(int hash, String key, String value, redisNode<String, String> next) {
//            this.hash = hash;
//            this.key = key;
//            this.value = value;
//            this.next = next;
//        }
//
//        public final String getKey() {
//            return key;
//        }
//        public final String getValue() {
//            return value;
//        }
//       // public final String toString() { return key + "=" + value; }
//
//        public final int hashCode() {
//            return key.hashCode() ^ value.hashCode();
//        }
//
//        public final String setValue(String newValue) {
//            String oldValue = value;
//            value = newValue;
//            return oldValue;
//        }
//
//        public final boolean equals(Object o) {
//            if (o == this)
//                return true;
//            if (o instanceof Map.Entry) {
//                Map.Entry<String,String> e = (Map.Entry<String,String>) o;
//                if (key.equals(e.getKey()) && value.equals(e.getValue()))
//                    return true;
//            }
//            return false;
//        }
//    }

}
