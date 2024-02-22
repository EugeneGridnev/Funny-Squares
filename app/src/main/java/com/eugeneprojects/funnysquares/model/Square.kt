package com.eugeneprojects.funnysquares.model

class Square private constructor(
    val size: Int,
    val colors: List<Int>
) {
  constructor(
      size: Int,
      colorsProducer: () -> Int
  ) : this(
      size = size,
      colors = (1..size*size).map { colorsProducer() }
  )
}