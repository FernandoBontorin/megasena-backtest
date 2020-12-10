package megasena

import megasena.backtest.fs.io.SparkSessionWrapper
import megasena.backtest.model.{Hit, Round}
import megasena.backtest.network.{Links, http}
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.types.{StringType, StructField, StructType}

package object backtest extends SparkSessionWrapper {

  val DEFAULT_LATEST_ROUNDS = "data/rounds/d_mega_latest"
  val DEFAULT_SCHEMA_LATEST_ROUNDS = StructType(Seq(
    StructField("id", StringType),
    StructField("date", StringType),
    StructField("number_1", StringType),
    StructField("number_2", StringType),
    StructField("number_3", StringType),
    StructField("number_4", StringType),
    StructField("number_5", StringType),
    StructField("number_6", StringType),
    StructField("round_income", StringType),
    StructField("winners_sena", StringType),
    StructField("city", StringType),
    StructField("FU", StringType),
    StructField("reward_sena_value", StringType),
    StructField("winners_quina", StringType),
    StructField("reward_quina_value", StringType),
    StructField("winners_quadra", StringType),
    StructField("reward_quadra_value", StringType),
    StructField("accumulated", StringType),
    StructField("accumulated_value", StringType),
    StructField("estimated_reward", StringType),
    StructField("accumulated_annual_sena", StringType)
  ))
  var strategy: (Array[Round], Round, Map[String, Any]) => Option[Hit] = _
  var dataFrame: DataFrame = _
  type StrategyType = (Array[Round], Round, Map[String, Any]) => Option[Hit]

  def withStrategy(strategy: StrategyType) = {
    this.strategy = strategy
    this
  }

  def loadLatest = {

    http.get(Links.ALL_ROUNDS.getUrl).save("data/rounds.zip")
    fs.uncompress("data/rounds.zip")
    val fileText = spark.read.text("data/rounds/d_mega.htm")
    val text = fileText.collect.map(r => r.getAs[String]("value")).mkString
    val processedFile = defaultClear(text)
    fs.writeFile(processedFile.getBytes, DEFAULT_LATEST_ROUNDS)
    dataFrame = spark.read.option("header", "true").option("delimiter", ";").schema(DEFAULT_SCHEMA_LATEST_ROUNDS).csv(DEFAULT_LATEST_ROUNDS).cache
    this
  }

  def defaultClear(row: String): String = {
    row.replaceAll("<html>.*<table", "<table")
      .replaceAll("<table.*Acumulado_Mega_da_Virada</font></small></th></tr>", "<table>")
      .replaceAll("</table>.*", "</table>")
      .replaceAll("&nbsp", "")
      .replaceAll(" rowspan=\".{1,2}\"", "")
      .replaceAll(" bgcolor=#.{6}", "")
      .replaceAll(" ", "")
      .replaceAll("<tr>", "\\\n<tr>")
      .replaceAll("<tr><td>.*</td><td>\\w*</td></tr>\n", "")
      .replaceAll("</table>", "")
      .replaceAll("</td><td>", ";")
      .replaceAll("<tr><td>", "")
      .replaceAll("</td></tr>", "")
      .replaceAll("<table>", "id,date,number_1,number_2,number_3,number_4,number_5,number_6," +
        "round_income,winners_sena,city,FU,reward_sena_value,winners_quina,reward_quina_value,winners_quadra," +
        "reward_quadra_value,accumulated,accumulated_value,estimated_reward,accumulated_annual_sena")
  }

  def withDataFrame(df: DataFrame) = {
    dataFrame = df
    this
  }

  def toRounds: Set[Round] = {
    dataFrame.collect.map(r => {
      Round(r.getAs[String]("id").parseValueAs.toInt,
        r.getAs[String]("date"),
        Set(
          r.getAs[String]("number_1").parseValueAs.toInt,
          r.getAs[String]("number_2").parseValueAs.toInt,
          r.getAs[String]("number_3").parseValueAs.toInt,
          r.getAs[String]("number_4").parseValueAs.toInt,
          r.getAs[String]("number_5").parseValueAs.toInt,
          r.getAs[String]("number_6").parseValueAs.toInt
        ),
        r.getAs[String]("reward_sena_value").parseValueAs.toDouble)
    }).toSet
  }

  def execute(options: Map[String, Any]): Set[Hit] = {
    var hits: Array[Hit] = Array[Hit]()
    toRounds.toArray.tail.foldLeft(toRounds.toArray.take(1)){ (previous, next) => {
      hits ++: Array(strategy(previous, next, options).orNull)
      previous ++: Array(next)
    }
    }
    hits.toSet
  }

  implicit class StringNumberTips(s: String) {
    def parseValueAs: String = {
      s.replace(".", "").replace(",", ".")
    }
  }

}
