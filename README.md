# CacheSimulator
A java program that is capable of simulating various types of caches on a sequence of memory addresses.
Supports three caches currently:

-Direct Mapping

-Fully Associative

-Set Associative (of any size and set #)

The program is structured so that each cache type has its own class which extend the cache abstract class.
This allows for a central tester to be used on any instance of a cache, and simplifies testing methods.
Main has a sample set of data, and shows the performance of each type of cache on a set of data.
![Cache Performance](https://github.com/ColinPollard/CacheSimulator/blob/master/Cache%20Performance.PNG)
