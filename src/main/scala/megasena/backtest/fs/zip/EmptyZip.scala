package megasena.backtest.fs.zip

import java.io.File

case object EmptyZip extends ZipArchive {
  def isEmpty = true
  def size = 0
  def head = throw new NoSuchElementException("EmptyZip.head")
  def tail = throw new NoSuchElementException("EmptyZip.tail")
  def incl(file: File): ZipArchive = new NonEmptyZip(file, EmptyZip)
  override def toString = "."
}
