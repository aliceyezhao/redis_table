# Redis Data Structures
A distributed data structure with redis that replaces in-memory operations with redis commands. All data structures also include a local data strucuture that is stored in memory and not connected to redis. When testing, keep in mind that redis shell commands are not reflected in the local data strucutres.

## Connect to the redis server
  
  $ redis server
  
  $ redis-cli
  
  This will display the IP address and port number, which are necessary for instantiating Redis Data Structures. The root key can be any String.
 
## RedisHashTable 
Implements Map<String, String>.
Use RedisHashTable to store key-value pairs both locally and in the redis server; they will be accessible from both. 
Run the RedisHashTable methods (put, remove, get, etc.) according to documentation. These pairs will be reflected in the redis server. 
See RedisHashTableTests for examples.

  $ hset rootKey1 key value //adds key-value pair to map associated with rootKey1 in the redis server 
  
  $ hdel rootKey1 key //removes key-value pair
  
  $ hget rootKey1 key //returns value if associated
  
  $ hgetall rootKey1 //returns all key-value pairs
  
## RedisSet
Implements Set<String>.
Use RedisSet to store Strings that will be accessible locally and from the redis server. 
Run the RedisHashTable methods (add, remove, get, etc.) according to documentation. These items will be reflected in the redis server. See RedisSetTests for examples.

  $ sadd rootKey2 element //adds element to set associated with rootKey2 in the redis server
  
  $ srem rootKey2 element //removes element
  
  $ smembers rootKey2 //returns all elements in set
  
## RedisInteger
Use RedisInteger to increment and decrement integers locally and in the redis server. You may set the starting value; the default is 0. Run the RedisInteger methods (redisIncr, redisDecr, get, set etc.) according to documentation. These changes will be reflected in the redis server. See RedisIntegerTests for examples.

  $ incr rootKey3 //increments integer associated with rootKey3 by 1 in the redis server
  
  $ decr rootKey3 //decerements integer by 1
  
  $ get rootKey3 //returns integer
  
  $ set rootKey3 0 //sets integer to 0

  
  

