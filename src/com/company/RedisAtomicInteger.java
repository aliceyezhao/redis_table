//package com.company;
//
//import java.util.concurrent.atomic.AtomicInteger;
//import redis.clients.jedis.*;
//
//public class RedisAtomicInteger extends AtomicInteger {
//
//    private AtomicInteger atomicInteger;
//    private Jedis jedis;
//    private int innerInt;
//
//    RedisAtomicInteger(String ipAddress, int portNumber, int innerInt) {
//        this.innerInt = innerInt;
//        this.atomicInteger = new AtomicInteger(innerInt);
//        this.jedis = new Jedis(ipAddress, portNumber);
//    }
//
//    @Override
//    public final int incrementAndGet() {
//        jedis.incr()
//    }
//
//    @Override
//    public final int decrementAndGet() {
//        jedis.decr()
//    }
