package megasena.backtest

import megasena.backtest
import megasena.backtest.io.SparkSessionWrapperTest
import megasena.backtest.model.{Hit, Round}
import org.scalatest.flatspec._
import org.scalatest.matchers._


class DataPipelineTests extends AnyFlatSpec with should.Matchers with SparkSessionWrapperTest {

  val GET_ROUNDS_ZIP = "data/megasena/rounds.zip"
  val POST_ROUNDS = "target/megasena/post_rounds.html"
  val ZIP_RESOURCES_EXAMPLE = "src/test/resources/test.zip"
  val ZIP_RESOURCES_EXAMPLE_OUTPUT = "target/megasena/zip/test"

  type StrategyType = (Array[Round], Round, Map[String, Any]) => Option[Hit]

  val lessNumbersHits: StrategyType = (previous: Array[Round], next: Round, options: Map[String, Any]) => ???

  "backtest" should "load" in {
    backtest.loadLatest
    backtest.withStrategy(lessNumbersHits)
    println(backtest.dataFrame.count)
    println(backtest.toRounds)
  }

}
