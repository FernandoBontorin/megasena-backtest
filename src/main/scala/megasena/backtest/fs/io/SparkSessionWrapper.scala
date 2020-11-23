package megasena.backtest.fs.io

import org.apache.spark.sql.SparkSession

trait SparkSessionWrapper {
  val spark: SparkSession = SparkSession.builder.getOrCreate
}
