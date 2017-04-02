# SimpleCache

Simply create a new cache with size mySize for your key type MyKey and your
value type MyValue like this

```scala
class MyCache(override val maxSize: Int = mySize) extends Cache[MyKey,MyValue] with Lru
```

When stacked with Lru, the cache will use a least recently used
displacement strategy.

You enter and retrieve data like this

```scala
val cache = new MyCache

cache += (newKey -> newEntry)

val value = cache(newKey)

cache.get(newKey) match {
  case Some(_) => println("Hit")
  case None    => println("Miss")
}
```

To trace the behaviour, just mix in the Trace trait.

```scala
val cache = new MyCache with Trace
```