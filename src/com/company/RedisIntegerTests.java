package com.company;

public class RedisIntegerTests {
    public static void main(String[] args) {
        String ipAddress = args[0];
        int portNumber = Integer.parseInt(args[1]);
        String rootKey = args[2];

        int cap = 9;
        int startingValue = 5;

        //choose one constructor below (both use same rootKey)
        RedisInteger ai0 = new RedisInteger(ipAddress, portNumber, rootKey);
        //RedisInteger ai = new RedisInteger(ipAddress, portNumber, rootKey, startingValue);

        //rootKey is key
        System.out.println(ai0.get());  //expect 0
        for (int i = 0; i < cap; i++) {
            ai0.redisIncr();
        }
        System.out.println(ai0.get());  //expect cap
        for (int i = 0; i < cap / 2; i++) {
            ai0.redisDecr();
        }
        System.out.println(ai0.get()); //expect cap / 2 rounded up
        //$ redic-cli: incr key        //+1
        //$ redic-cli: incr key        //+1
        //$ redic-cli: decr key        //-1
        ai0.clear();
        System.out.println(ai0.get());       //expect 0

        //---------------------------------------------------------------------
//        for (int i = 0; i < cap; i++) {
//            ai.redisIncr();
//        }
//        System.out.println(ai0.get());  //expect cap + startingValue
//        for (int i = 0; i < cap / 2; i++) {
//            ai.redisDecr();
//        }
//        System.out.println(ai.get()); //expect startingValue + (cap / 2) rounded down
    }
}
