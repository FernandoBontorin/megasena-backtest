package megasena.backtest.fs.zip

import java.io.File

import scala.annotation.tailrec

abstract class ZipArchive {
  def isEmpty: Boolean

  def head: File

  def tail: ZipArchive

  def incl(file: File): ZipArchive

  def size: Int

  def zipAtSource(name: String): File = this match {
    case EmptyZip => throw new Exception("Nothing to Zip")
    case NonEmptyZip(head, _) => zipAs(new File(head.getParentFile, name).getAbsolutePath)
  }

  def zipAs(name: String): File = {
    @tailrec
    def loop(z: ZipArchive, acc: List[File]): List[File] = z match {
      case EmptyZip => acc
      case NonEmptyZip(head, tail) => loop(tail, head :: acc)
    }

    ZipWriter.compress(name, loop(this, Nil): _*)
  }

  def ::(file: File): ZipArchive = this incl file
}

object ZipArchive {
  def apply(files: File*) = {
    @tailrec
    def loop(xs: List[File], acc: ZipArchive): ZipArchive = xs match {
      case Nil => acc
      case head :: tail => loop(tail, acc incl head)
    }

    loop(files.toList, EmptyZip)
  }
}