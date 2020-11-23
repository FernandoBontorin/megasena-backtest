package megasena.backtest.fs.zip

import java.io._
import java.util.zip.{ZipEntry, ZipOutputStream}

import megasena.backtest.fs.io.IOStream

object ZipWriter {

  def compress(filename: String, files: File*): File = {
    val output = new File(filename)
    val fos = new FileOutputStream(output)
    val zos = new ZipOutputStream(new BufferedOutputStream(fos))
    files.foreach({
      input =>
        val fis = new FileInputStream(input)
        val entry = new ZipEntry(input.getName)
        zos.putNextEntry(entry)
        IOStream.stream(fis, zos)
        zos.flush()
        fis.close()
    })
    zos.close()
    fos.close()
    output
  }

}
