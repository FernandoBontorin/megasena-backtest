package megasena.backtest.fs

import java.io.File

import megasena.backtest.network.Links.ALL_ROUNDS
import megasena.backtest.network.http
import org.scalatest.flatspec._
import org.scalatest.matchers._


class IOSystemsTests extends AnyFlatSpec with should.Matchers {

  val GET_ROUNDS_ZIP = "target/megasena/rounds.zip"
  val POST_ROUNDS = "target/megasena/post_rounds.html"
  val ZIP_RESOURCES_EXAMPLE = "src/test/resources/test.zip"
  val ZIP_RESOURCES_EXAMPLE_OUTPUT = "target/megasena/zip/test"

  "http" should s"GET ${ALL_ROUNDS.getUrl} and save at $GET_ROUNDS_ZIP" in {
    http.get(ALL_ROUNDS.getUrl).save(GET_ROUNDS_ZIP)
    assert(new File(GET_ROUNDS_ZIP).exists)
  }

  it should s"POST ${ALL_ROUNDS.getUrl} and save at $POST_ROUNDS" in {
    http.post(ALL_ROUNDS.getUrl).save(POST_ROUNDS)
    assert(new File(POST_ROUNDS).exists)
  }

  "unzip" should s"extract a zip file $ZIP_RESOURCES_EXAMPLE at $ZIP_RESOURCES_EXAMPLE_OUTPUT" in {
    megasena.backtest.fs.uncompress(ZIP_RESOURCES_EXAMPLE, ZIP_RESOURCES_EXAMPLE_OUTPUT)
  }

  it should s"uncompressed $ZIP_RESOURCES_EXAMPLE_OUTPUT/file1.txt" in {
    assert(new File(ZIP_RESOURCES_EXAMPLE_OUTPUT + "/file1.txt").exists)
  }

  it should s"uncompressed $ZIP_RESOURCES_EXAMPLE_OUTPUT/file2.txt" in {
    assert(new File(ZIP_RESOURCES_EXAMPLE_OUTPUT + "/file2.txt").exists)
  }

}
