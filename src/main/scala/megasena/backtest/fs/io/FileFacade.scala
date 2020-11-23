package megasena.backtest.fs.io

import java.io.FileOutputStream

import megasena.backtest.collections.collection

import scala.reflect.io.File


class FileFacade(val where: String) {

  def scalaFile = new File(javaFile)

  def path: String = {
    collection.removeLastElement(filename.split("/")).mkString("/")
  }

  def name: String = {
    filterExtension(collection.lastElementOf(filename.split("/")))
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

  def filename: String = where.replace("\\", "/")

  def out: FileOutputStream = {
    new java.io.File(path).mkdirs
    new FileOutputStream(javaFile)
  }

  def javaFile = new java.io.File(filename)


}
