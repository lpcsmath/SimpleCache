package de.csmath.cache

/**
  * Created by lpfeiler on 02.04.2017.
  */
trait BasicCache {

  type K
  type V

  def maxSize: Int = 100

  protected def victim: K

  def += (kv: (K,V)): BasicCache.this.type

  def -= (key: K): BasicCache.this.type

  def contains(key: K): Boolean

  def apply(key: K): V

  def get(key: K): Option[V]

  def update(key: K, value: V): Unit

  def size: Int

  def foreach(f: ((K, V)) => Unit): Unit

}
