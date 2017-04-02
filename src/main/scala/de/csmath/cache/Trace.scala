package de.csmath.cache

/**
  * Created by lpfeiler on 02.04.2017.
  */
trait Trace extends BasicCache {

  abstract override def apply(key: K): V = {
    println(s"Read value of key $key")
    super.apply(key)
  }

  abstract override def get(key: K): Option[V] = {
    println(s"Read value of key $key")
    super.get(key)
  }

  abstract override def += (kv: (K,V)): Cache[K, V] = {
    val (key,value) = kv
    val res = super.+=(kv)
    println(s"Enter $value for key $key")
    println("Cache looks now like this:")
    foreach { e =>
      println(e)
    }
    res
  }

  abstract override def -= (key: K): Cache[K, V] = {
    val res = super.-=(key)
    println(s"Delete value for key $key")
    println("Cache looks now like this:")
    foreach(e => println(e))
    res
  }

}
