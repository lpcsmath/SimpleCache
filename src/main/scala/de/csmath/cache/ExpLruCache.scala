package de.csmath.cache

/**
  * Created by lpfeiler on 02.04.2017.
  */
class ExpLruCache(override val maxSize: Int = 3) extends Cache[Int,String] with Lru
