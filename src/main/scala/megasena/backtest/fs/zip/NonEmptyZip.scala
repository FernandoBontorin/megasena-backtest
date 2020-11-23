package megasena.backtest.fs.zip

import java.io.File

case class NonEmptyZip(head: File, tail: ZipArchive) extends ZipArchive {
  def isEmpty = false
  def size = 1 + tail.size
  def incl(file: File): ZipArchive = new NonEmptyZip(head, tail incl file)
  override def toString = head.getName + ", " + tail.toString
}
