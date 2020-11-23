package megasena.backtest.fs.zip

import java.io.{File, FileInputStream}
import java.util.zip.{ZipEntry, ZipInputStream}

import scala.io.Source

case class CompressedFile(file: File) {

  type ZipPred = ZipEntry => Boolean
  val name = file.getName

  def this(name: String) = this(new File(name))

  def unzipAtSource(name: String) = unzipAs(new File(file.getParentFile, name).getAbsolutePath)

  def unzipAs(name: String): File = ZipReader.uncompress(name, file)

  def getFiles = filter(_ => true)

  def filter(p: ZipPred) = {
    val zis = zipInputStream
    mapReduce[Stream[ZipEntry], Stream[Iterator[String]]](_.filter(p), _.map(entry => Source.fromInputStream(zis).getLines))(zis)
  }

  def find(p: ZipPred) = {
    val zis = zipInputStream
    mapReduce[Option[ZipEntry], Option[Iterator[String]]](_.find(p), _.map(entry => Source.fromInputStream(zis).getLines))(zis)
  }

  def zipInputStream = new ZipInputStream(new FileInputStream(file))

  private def mapReduce[T, E](map: Stream[ZipEntry] => T, reduce: T => E)(zis: ZipInputStream) = reduce(map(iterate(zis)))

  private def iterate(zis: ZipInputStream): Stream[ZipEntry] = Stream.continually(zis.getNextEntry).takeWhile(_ != null)

}
