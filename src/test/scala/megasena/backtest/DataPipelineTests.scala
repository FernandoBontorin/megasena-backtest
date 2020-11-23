package megasena.backtest

import megasena.backtest
import megasena.backtest.io.SparkSessionWrapperTest
import org.scalatest.flatspec._
import org.scalatest.matchers._


class DataPipelineTests extends AnyFlatSpec with should.Matchers with SparkSessionWrapperTest {

  val GET_ROUNDS_ZIP = "data/megasena/rounds.zip"
  val POST_ROUNDS = "target/megasena/post_rounds.html"
  val ZIP_RESOURCES_EXAMPLE = "src/test/resources/test.zip"
  val ZIP_RESOURCES_EXAMPLE_OUTPUT = "target/megasena/zip/test"

  "backtest" should "load" in {
    backtest.loadLatest
  }

}
