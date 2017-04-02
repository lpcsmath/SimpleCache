package de.csmath.cache

/**
  * Created by lpfeiler on 02.04.2017.
  */
trait BasicCache {

  type K
  type V

  def maxSize: Int = 100

  def += (kv: (K,V)): Cache[K, V]

  def -= (key: K): Cache[K, V]

  def contains(key: K): Boolean

  def apply(key: K): V

  def get(key: K): Option[V]

  def update(key: K, value: V): Unit

  def size: Int

}
