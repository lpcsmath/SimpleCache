package de.csmath.cache

import org.scalatest._

/**
  * Created by lpfeiler on 02.04.2017.
  */
class ExpLruCacheTest extends FlatSpec with Matchers {

  "An ExpLruCache" should "not exceed its max size" in {
    val cache = new ExpLruCache

    List( (1,"a"), (2,"b"), (3,"c"), (4,"d"), (5,"e") ) foreach { entry =>
      cache += entry
    }

    cache       should have size cache.maxSize
    cache.lrus  should have size cache.maxSize
  }

  it should "replace the least recently used entry" in {
    val cache = new ExpLruCache

    List( (1,"a"), (2,"b"), (3,"c") ) foreach { entry =>
      cache += entry
    }
    cache(1)
    List(  (4,"d"), (5,"e") ) foreach { entry =>
      cache += entry
    }

    cache       should have size cache.maxSize
    cache.cache should contain (1,"a")
    cache.cache should contain (4,"d")
    cache.cache should contain (5,"e")

    cache.get(4)
    List(  (6,"f"), (7,"g") ) foreach { entry =>
      cache += entry
    }

    cache         should have size cache.maxSize
    cache.lrus    should have size cache.maxSize
    cache.invLrus should have size cache.maxSize
    cache.cache   should contain (4,"d")
    cache.cache   should contain (6,"f")
    cache.cache   should contain (7,"g")

  }

}
