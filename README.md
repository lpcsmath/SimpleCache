# SimpleCache

Simply create a new cache with size mySize for your key type MyKey and your
value type MyValue like this

```scala
class MyCache(override val maxSize: Int = mySize)
                           extends Cache[MyKey,MyValue] with Lru
```

When stacked with Lru, the cache will use a least recently used
displacement strategy.
