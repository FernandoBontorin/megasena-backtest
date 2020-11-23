package megasena.backtest.collections

object collection {
  def lastElementOf[T](array: Array[T]): T = {
    array.reverse.head
  }

  def removeLastElement[T](array: Array[T]): Array[T] = {
    array.reverse.tail.reverse
  }
}
