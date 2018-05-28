package com.company;

import redis.clients.jedis.Jedis;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class RedisSet implements Set<String>, Cloneable, Serializable {

    private transient Set<String> localSet = new HashSet<String>();
    private String rootKey;
    private Jedis jedis;
    private int localSize;

    public RedisSet(String ipAddress, int portNumber, String rootKey) {
        this.jedis = new Jedis(ipAddress, portNumber);
        this.rootKey = rootKey;
        this.localSize = 0;
    }

    public Set<String> getLocalSet() {
        return localSet;
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
        localSet.add(s);
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
        localSet.remove(o);
        localSize--;
        return true;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Set<String> addedSet = getSet();
        addedSet.retainAll(c);
        return addedSet == c;
    }

    @Override
    public boolean addAll(Collection<? extends String> c) {
        Set<String> oldSet = getSet();
        //add each elem redis will handle duplicates
        for (String s : c) {
            this.add(s);
        }
        localSet.addAll(c);
        return oldSet != getSet(); //return true if set changes
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
//        Set<String> oldSet = getSet();
//        //jedis.sinter(rootKey, c);
//        for (Object s : c) {
//            if (!contains(s)) {
//                this.remove(s);
//            }
//        }
//        innerSet.retainAll(c);
//        return oldSet != getSet();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        Set<String> oldSet = getSet();
        //remove each elem, okay if elem not present
        for (Object s : c) {
            this.remove(s);
        }
        localSet.removeAll(c);
        return oldSet != getSet(); //return true if set changes
    }

    @Override
    public void clear() {
        for (Object s : getSet()) {
            this.remove(s);
        }
        localSet.clear();
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
