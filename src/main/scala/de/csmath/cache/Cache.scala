package de.csmath.cache

import scala.collection.mutable

/**
  * Created by lpfeiler on 02.04.2017.
  */
trait Cache[Key,Value] extends BasicCache {

  type K = Key
  type V = Value

  val cache: mutable.HashMap[K,V] = mutable.HashMap.empty

  def victim: K

  def += (kv: (K,V)): Cache.this.type = {
    val (newKey,_) = kv
    if (size == maxSize && !contains(newKey)) {
      cache -= victim
    }
    cache += kv
    this
  }

  def -= (key: K): Cache.this.type = {
    cache -= key
    this
  }

  def contains(key: K): Boolean = cache contains key

  def apply(key: K): V = cache(key)

  def get(key: K): Option[V] = cache.get(key)

  def update(key: K, value: V): Unit = cache.update(key, value)

  def size: Int = cache.size

  def foreach(f: ((K, V)) => Unit): Unit = cache.foreach(f)

}
