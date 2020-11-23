package megasena.backtest.fs.io

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, InputStream, OutputStream}

import scala.annotation.tailrec

object IOStream {

  def copy(is: InputStream, times: Int = 1, bufferSize: Int = 4096): List[InputStream] = {
    val bytesOut = new ByteArrayOutputStream
    stream(is, bytesOut)
    for (i <- (1 to times).toList) yield (new ByteArrayInputStream(bytesOut.toByteArray))
  }

  def stream(is: InputStream, os: OutputStream, bufferSize: Int = 4096) = {
    val buffer = new Array[Byte](bufferSize)

    @tailrec
    def doStream(total: Int): Int = {
      val n = is.read(buffer)
      if (n == -1)
        total
      else {
        os.write(buffer, 0, n)
        doStream(total + n)
      }
    }

    doStream(0)
  }

}
