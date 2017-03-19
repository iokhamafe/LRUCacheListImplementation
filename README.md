# LRUCacheListImplementation
Java Implementation of LRU Caching. When accessing a large amount of data from a slow medium such as disk or network, 
a common speed up technique is to keep a smaller amount of data into a more accessible location known as cache. 
RU (Least Recently Used cache is a widely used cache algorithm for memory management).  
For example, a database system may keep data cached in memory so that it doesn't have to read the hard drive. 
Or a web browser might keep a cache of web pages on the local machine so that it doesn't have to download them over the network.  
In general, a cache is much too small to hold all the data you might possibly need, so at some point you are going to have to remove something from the cache in order to make room for new data. 
The goal is to retain those items that are more likely to be retrieved again soon. 
This requires a sensible algorithm for selecting what to remove from the cache. 
One simple but effective algorithm is the Least Recently Used, or LRU, algorithm. When performing LRU caching, you always throw out the data that was least recently used. 
The LRU cache is based on the idea that items that are most recently used are more likely to be used again in future.  
