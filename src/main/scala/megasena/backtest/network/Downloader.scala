package megasena.backtest.network

import java.io.FileOutputStream

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.AutoRetryHttpClient

class Downloader(url: String) {

  def save(to: String = lastElementOf(url.split("/"))): String = {
    new AutoRetryHttpClient().execute(new HttpGet(url)).getEntity.writeTo(new FileOutputStream(to))
    to
  }

  private def lastElementOf[T](array: Array[T]): T = {
    array(array.length - 1)
  }

}
