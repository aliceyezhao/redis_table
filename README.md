# Redis Data Structures
A distributed data structure with redis that replaces in-memory operations with redis commands.

Connect to the redis server
  
  $ redis server
  
  $ redis-cli
  
  $ hset rootkey key value //to set key-value pair in the redis server
  
  $ hget rootkey key //returns value if associated
  
  $ hdel rootkey key //removes key-value pair
  
  $ hgeall rootkey //shows all key value pairs
 
 
Use RedisHashTable to store key-value pairs both locally and in the redis server; they will be accessible from both. 
Run the RedisHashTable methods (put, get, remove, putAll) according to documentation. These pairs will be reflected in the redis server. 
See RedisHashTableTests for examples.
  
