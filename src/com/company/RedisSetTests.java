package com.company;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class RedisSetTests {
    public static void main(String[] args) {
        String ipAddress = args[0];
        int portNumber = Integer.parseInt(args[1]);
        String rootKey = args[2];

        Set<String> set = new RedisSet(ipAddress, portNumber, rootKey);
        //rootKey is root
        //$ redic-cli: sadd root a
        set.add("b");
        //$ redic-cli: sadd root c
        //$ redic-cli: smembers root        //{"a", "b", "c"}
        System.out.println(set.size());     //expect 3 + initial size
        set.remove("b");
        System.out.println(set.size());     //expect 2 + initial size
        //$ redic-cli: sadd root d
        //$ redic-cli: srem root a
        //$ redic-cli: smembers root        //{"c", "d"}

        Collection<String> c = new HashSet<String>();
        c.add("d");
        c.add("e");
        c.add("c"); //{"c", "e", "d"}

        System.out.println(set.containsAll(c)); //expect false
        System.out.println(set.addAll(c));      //expect true
        //$ redic-cli: smembers root            //{"c", "d", "e"}
        System.out.println(set.size());         //expect 3 + initial size
        System.out.println(set.removeAll(c));   //expect true
        //$ redic-cli: smembers root            //{}
        System.out.println(set.size());         //expect 0 + initial size

        set.clear();
        System.out.println(set.size());         //expect 0
    }
}
