package com.company;
import java.util.Map;

public class RedisHashTableTests {

    public static void main(String[] args) {
        String ipAddress = args[0];
        int portNumber = Integer.parseInt(args[1]);
        String rootKey = args[2];

        Map<String, String> map = new RedisHashTable(ipAddress, portNumber, rootKey);
        //rootKey is rootkey
        map.put("a", "1");
        System.out.println(map.get("a")); // expect 1
        //redic-cli: hset rootkey b 2
        System.out.println(map.get("b")); // expect 2
        map.remove("b");
        //hget b should return nil
        System.out.println(map.get("b")); //expect nil
        System.out.println(map.size());   //map in redis is 1 (or 1 + initial size)
    }
}
