package megasena.backtest.fs

import java.io.File

import megasena.backtest.network.Links.TODOS_CONCURSOS
import megasena.backtest.network.http
import org.scalatest.flatspec._
import org.scalatest.matchers._


class IOSystemsTests extends AnyFlatSpec with should.Matchers {

  val GET_CONCURSOS_ZIP = "target/megasena/concursos.zip"
  val POST_CONCURSOS = "target/megasena/post_concursos.html"
  val ZIP_RESOURCES_EXAMPLE = "src/test/resources/test.zip"
  val ZIP_RESOURCES_EXAMPLE_OUTPUT = "target/megasena/zip/test"

  "http" should s"GET ${TODOS_CONCURSOS.getUrl} and save at $GET_CONCURSOS_ZIP" in {
    http.get(TODOS_CONCURSOS.getUrl).save(GET_CONCURSOS_ZIP)
    assert(new File(GET_CONCURSOS_ZIP).exists)
  }

  it should s"POST ${TODOS_CONCURSOS.getUrl} and save at $POST_CONCURSOS" in {
    http.post(TODOS_CONCURSOS.getUrl).save(POST_CONCURSOS)
    assert(new File(POST_CONCURSOS).exists)
  }

  "unzip" should s"extract a zip file $ZIP_RESOURCES_EXAMPLE at $ZIP_RESOURCES_EXAMPLE_OUTPUT" in {
    megasena.backtest.fs.uncompress(ZIP_RESOURCES_EXAMPLE, ZIP_RESOURCES_EXAMPLE_OUTPUT)
  }

}
