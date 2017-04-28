package de.csmath.cache

import org.scalatest._

/**
  * Created by lpfeiler on 02.04.2017.
  */
class LruCacheTest extends FlatSpec with Matchers {

  "An ExpLruCache" should "not exceed its max size" in new LruFixture {


    List( (1,"a"), (2,"b"), (3,"c"), (4,"d"), (5,"e") ) foreach { entry =>
      lruCache += entry
    }

    lruCache       should have size lruCache.maxSize
    lruCache.lrus  should have size lruCache.maxSize
  }

  it should "replace the least recently used entry" in new LruFixture {

    List( (1,"a"), (2,"b"), (3,"c") ) foreach { entry =>
      lruCache += entry
    }
    lruCache(1)
    List(  (4,"d"), (5,"e") ) foreach { entry =>
      lruCache += entry
    }

    lruCache       should have size lruCache.maxSize
    lruCache.cache should contain (1,"a")
    lruCache.cache should contain (4,"d")
    lruCache.cache should contain (5,"e")

    lruCache.get(4)
    List(  (6,"f"), (7,"g") ) foreach { entry =>
      lruCache += entry
    }

    lruCache         should have size lruCache.maxSize
    lruCache.lrus    should have size lruCache.maxSize
    lruCache.invLrus should have size lruCache.maxSize
    lruCache.cache   should contain (4,"d")
    lruCache.cache   should contain (6,"f")
    lruCache.cache   should contain (7,"g")

  }

}

class LruCache(override val maxSize: Int) extends Cache[Int,String] with Lru

trait LruFixture {
  val lruCache = new LruCache(3) with Trace
}
