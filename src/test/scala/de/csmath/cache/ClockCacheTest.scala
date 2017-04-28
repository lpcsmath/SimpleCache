package de.csmath.cache

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by lpfeiler on 28.04.2017.
  */
class ClockCacheTest extends FlatSpec with Matchers {

  "A ClockCache" should "not exceed its max size" in new ClockCacheFixture {

    List( (1,"a"), (2,"b"), (3,"c"), (4,"d"), (5,"e") ) foreach { entry =>
      clockCache += entry
    }

    clockCache       should have size clockCache.maxSize
  }

  it should "replace the entry according to second chance clock algorithm" in new ClockCacheFixture {

    List( (1,"a"), (2,"b"), (3,"c"), (4,"d") ) foreach { entry =>
      clockCache += entry
    }
    clockCache(2)
    clockCache += (5,"e")

    clockCache       should have size clockCache.maxSize
    clockCache.cache should contain (2,"b")
    clockCache.cache should contain (4,"d")
    clockCache.cache should contain (5,"e")

    clockCache += (6,"f")
    clockCache(4)
    clockCache += (7,"g")

    clockCache         should have size clockCache.maxSize
    clockCache.cache   should contain (4,"d")
    clockCache.cache   should contain (6,"f")
    clockCache.cache   should contain (7,"g")

  }

}

abstract class ClockCache(override val maxSize: Int) extends Cache[Int,String] with Clock

trait ClockCacheFixture {

  val clockCache = new ClockCache(3) with Trace

}
