package megasena.backtest.fs.io

import java.io.FileOutputStream

import megasena.backtest.collections

import scala.reflect.io.File


class FileFacade(val where: String) {

  def scalaFile = new File(javaFile)

  def name: String = {
    filterExtension(collections.lastElementOf(filename.split("/")))
  }

  private def filterExtension(s: String): String = {
    if (fileExtension.nonEmpty) {
      s.replace(s".$fileExtension", "")
    } else s
  }

  def fileExtension: String = {
    val end = filename.split("/").reverse.head
    if (end.contains(".")) end.split("[.]").reverse.head else ""
  }

  def out: FileOutputStream = {
    new java.io.File(path).mkdirs
    new FileOutputStream(javaFile)
  }

  def path: String = {
    collections.removeLastElement(filename.split("/")).mkString("/")
  }

  def javaFile = new java.io.File(filename)

  def filename: String = where.replace("\\", "/")


}
