package megasena.backtest.fs.zip

import java.io.{File, FileInputStream, FileOutputStream, InputStream}
import java.util.zip.{ZipEntry, ZipInputStream}

import megasena.backtest.fs.io.IOStream

import scala.io.Source

object ZipReader {

  def uncompress(outputName: String, file: File): File = this.uncompress(outputName, new ZipInputStream(new FileInputStream(file)))

  def uncompress(outputName: String, zis: ZipInputStream): File = {
    val output = new File(outputName)
    Stream
      .continually(zis.getNextEntry)
      .takeWhile(_ != null)
      .foreach { entry =>
        val fileName = entry.getName
        val newFile = new File(output, fileName)
        new File(newFile.getParent).mkdirs()
        if (!fileName.endsWith("/")) {
          val fos = new FileOutputStream(newFile)
          IOStream.stream(zis, fos)
          fos.close()
        }
      }
    zis.closeEntry()
    zis.close()
    output
  }

  def find(zis: ZipInputStream)(p: ZipEntry => Boolean): Option[InputStream] =
    Stream.continually(zis.getNextEntry).takeWhile(_ != null).find(p).map(_ => zis)

  def readLines(zis: ZipInputStream): Iterator[String] = {
    val entry = zis.getNextEntry
    Source.fromInputStream(zis).getLines
  }

}
