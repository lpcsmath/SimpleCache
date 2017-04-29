package de.csmath.cache

import scala.collection.mutable._

/**
  * Created by lpfeiler on 28.04.2017.
  */
trait Clock extends BasicCache {

  private[cache] val ring: Array[(K,Boolean)] = new Array(maxSize)

  private[cache] val keyPos: HashMap[K,Int] = HashMap.empty

  private[cache] var index: Int = 0

  def moveIndex: Unit = index = (index + 1) % maxSize

  def victim = {
    var (key,rBit) = ring(index)
    while (rBit) {
      ring(index) = (key,false)
      moveIndex
      val (k,r) = ring(index)
      key = k
      rBit = r
    }
    keyPos -= key
    key
  }

  abstract override def += (kv: (K,V)): Clock.this.type = {
    super.+=(kv)
    val (key,_) = kv
    ring(index) = (key,true)
    keyPos += (key -> index)
    moveIndex
    this
  }

  abstract override def -= (key: K): Clock.this.type = {
    if (keyPos.keySet contains key) {
      index = keyPos(key)
      ring(index) = (key,false)
      keyPos -= key
    }
    super.-=(key)
  }

  abstract override def apply(key: K): V = {
    val value = super.apply(key)
    ring(keyPos(key)) = (key,true)
    value
  }

  abstract override def get(key: K): Option[V] = {
    super.get(key) match {
      case value@Some(_) =>
        ring(keyPos(key)) = (key,true)
        value
      case None => None
    }
  }

}
