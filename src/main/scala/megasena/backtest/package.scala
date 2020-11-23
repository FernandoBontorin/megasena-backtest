package megasena

import megasena.backtest.fs.io.SparkSessionWrapper
import megasena.backtest.model.{Hit, Round}
import megasena.backtest.network.{Links, http}
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{col, udf}

package object backtest extends SparkSessionWrapper {
  var strategy: (Set[Round], Round, Map[String, Any]) => Boolean = _
  var dataFrame: DataFrame = _

  def withStrategy(strategy: (Set[Round], Round, Map[String, Any]) => Boolean) = {
    this.strategy = strategy
    this
  }

  def loadLatest = {
    http.get(Links.ALL_ROUNDS.getUrl).save("data/rounds.zip")
    fs.uncompress("data/rounds.zip")
    val df = spark.read.text("data/rounds/d_mega.htm")
    //TODO df.select(removeNBSP(col("value"))).write.text("data/rounds/d_mega")
  }

  private def removeNBSP = udf((value: String) => {
    value.replace("&nbsp", "")
  })

  def loadCustom(df: DataFrame) = {
    dataFrame = df
    this
  }

  def execute(df: DataFrame): Set[Hit] = ???
}
