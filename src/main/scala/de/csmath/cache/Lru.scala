package de.csmath.cache

import scala.collection.mutable

/**
  * Created by lpfeiler on 02.04.2017.
  */
trait Lru extends BasicCache {

  val lrus: mutable.TreeMap[Long,K] = mutable.TreeMap.empty

  val invLrus: mutable.HashMap[K,Long] = mutable.HashMap.empty

  var time: Long = 0

  abstract override def += (kv: (K,V)): Cache[K, V] = {
    val (newKey,_) = kv
    if (size == maxSize && !contains(newKey)) {
      val (oldTime,displaceKey) = lrus.head
      deleteLruEntry(oldTime,displaceKey)
      super.-=(displaceKey)
    }
    updateLru(newKey)
    super.+=(kv)
  }

  abstract override def -= (key: K) = {
    lrus.find( {case (_,v) => v == key} ) match {
      case Some((time,cacheKey)) =>
        deleteLruEntry(time,cacheKey)
      case _ =>
    }
    super.-=(key)
  }

  abstract override def apply(key: K): V = {
    val value = super.apply(key)
    updateLru(key)
    value
  }

  abstract override def get(key: K): Option[V] = {
    super.get(key) match {
      case value@Some(_) =>
        updateLru(key)
        value
      case None => None
    }
  }

  private def updateLru(key: K): Unit = {
    invLrus.get(key) match {
      case Some(oldTime) =>
        invLrus -= key
        lrus -= oldTime
      case _ =>
    }
    lrus += (time -> key)
    invLrus += (key -> time)
    time += 1
  }

  private def deleteLruEntry(oldTime: Long, displaceKey: K): Unit = {
    lrus -= oldTime
    invLrus -= displaceKey
  }

}
