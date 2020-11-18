package megasena.backtest.network

import java.io.File

import org.scalatest.flatspec._
import org.scalatest.matchers._


class DownloaderTest extends AnyFlatSpec with should.Matchers {

  "Downloader" should "download concursos and save at target/test.zip" in {
    val target = "target/test.zip"
    val dw = new Downloader(Links.TODOS_CONCURSOS.getUrl)
    val result_target = dw.save(target)
    assert(result_target.equals(target))
    assert(new File(target).exists)
  }

}
