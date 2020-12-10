package megasena.backtest.fs.zip

import java.io.File

case class NonEmptyZip(head: File, tail: ZipArchive) extends ZipArchive {
  def isEmpty = false

  def size: Int = 1 + tail.size

  def incl(file: File): ZipArchive = NonEmptyZip(head, tail incl file)

  override def toString: String = head.getName + ", " + tail.toString
}
