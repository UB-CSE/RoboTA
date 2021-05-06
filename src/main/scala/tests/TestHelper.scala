package tests

object TestHelper {
  val EPSILON: Double = 0.00001

  def compareDoubles(d1: Double, d2: Double): Boolean = {
    Math.abs(d1 - d2) < EPSILON
  }
}
