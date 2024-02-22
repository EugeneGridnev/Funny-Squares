package com.eugeneprojects.funnysquares.model

class Squares private constructor(
    val size: Int,
    val colors: List<Int>
) {
  constructor(
      size: Int,
      colorProducer: () -> Int
  ) : this(
      size = size,
      colors = (1..size*size).map { colorProducer() }
  )
}