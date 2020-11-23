package megasena.backtest.io

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession

trait SparkSessionWrapperTest {
  val spark: SparkSession = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    SparkSession.builder
      .master("local[1]")
      .config("spark.driver.host", "localhost")
      .appName("mega-sena application test")
      .getOrCreate
  }
}
