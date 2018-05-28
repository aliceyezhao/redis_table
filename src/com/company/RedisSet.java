package com.company;

import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class RedisSet implements Set<String>, Cloneable, Serializable {

    private transient Set<String> innerSet = new HashSet<String>();
    private String rootKey;
    private Jedis jedis;
    private int localSize;

    public RedisSet(String ipAddress, int portNumber, String rootKey) {
        this.jedis = new Jedis(ipAddress, portNumber);
        this.rootKey = rootKey;
        this.localSize = 0;
    }

    public RedisSet(Collection<String> collection) {

    }

    private Set<String> getSet() {
        return jedis.smembers(rootKey);
    }

    private int getLocalSize() {
        return localSize;
    }

    @Override
    public int size() {
        return jedis.scard(rootKey).intValue();
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    public boolean contains(Object o) {
        return jedis.sismember(rootKey, o.toString());
    }

    @Override
    public Iterator<String> iterator() {
        return getSet().iterator();
    }

    @Override
    public String[] toArray() {
        Object[] objectArray = getSet().toArray();
        String[] stringArray = new String[size()];
        for (int i = 0; i < size(); i++) {
            stringArray[i] = objectArray[i].toString();
        }
        return stringArray;
    }

    @Override
    public <String> String[] toArray(String[] a) {
        return getSet().toArray(a);
    }

    @Override
    public boolean add(String s) {
        if (getSet().contains(s)) {
            return false;
        }
        jedis.sadd(rootKey, s);
        innerSet.add(s);
        localSize++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        String key = o.toString();
        if (!getSet().contains(key)) {
        return false;
        }
        jedis.srem(rootKey, key);
        innerSet.remove(o);
        localSize--;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return getSet().containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        Set<String> oldSet = getSet();
        //jedis.sunion(rootKey, c);
        for (Object s : c) {
            if (!contains(s)) {
                this.add(s.toString());
            }
        }
        innerSet.addAll(c);
        return oldSet != getSet();
        //return true if set changes
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        Set<String> oldSet = getSet();
        //jedis.sinter(rootKey, c);
        for (Object s : c) {
            if (!contains(s)) {
                this.remove(s);
            }
        }
        innerSet.retainAll(c);
        return oldSet != getSet();

    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Set<String> oldSet = getSet();
        //jedis.sdiff(rootKey, c);
        for (Object s : c) {
            if (contains(s)) {
                this.remove(s);
            }
        }
        innerSet.removeAll(c);
        return oldSet != getSet();
    }

    @Override
    public void clear() {
        for (Object s : getSet()) {
            this.remove(s);
        }
        innerSet.clear();
    }

    @Override
    public boolean equals(Object o) {
        return getSet().equals(o);
    }

    @Override
    public int hashCode() {
        return getSet().hashCode();
    }
}
