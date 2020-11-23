package megasena.backtest

package object collections {
  def lastElementOf[T](array: Array[T]): T = {
    array.reverse.head
  }

  def removeLastElement[T](array: Array[T]): Array[T] = {
    array.reverse.tail.reverse
  }
}
