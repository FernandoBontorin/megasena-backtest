package megasena.backtest

import megasena.backtest.fs.io.FileFacade
import megasena.backtest.fs.zip.CompressedFile

package object fs {

  def uncompress(zipLocation: String): Unit = {
    val f = new FileFacade(zipLocation)
    uncompress(zipLocation, s"${f.path}/${f.name}")
  }

  def uncompress(zipLocation: String, target: String): Unit = {
    val f = new FileFacade(zipLocation)
    CompressedFile(f.javaFile).unzipAs(target)
  }

  def writeFile(data: Array[Byte], target: String): Unit = {
    new FileFacade(target).out.write(data)
  }


}
