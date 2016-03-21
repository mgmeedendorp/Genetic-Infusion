package com.seremis.geninfusion.api.util

/**
  * The TypedName is a simple case class used in various library classes in the api.
  * It provides a name and a type to safely retrieve the value of
  * an IGene from an ISoul, for example.
  * This class should probably only be used in library objects.
  *
  * @param name The name of the variable.
  * @param clzz The class of which type the data is.
  * @tparam A The type of the data.
  */
case class TypedName[A](name: String, clzz: Class[A])
