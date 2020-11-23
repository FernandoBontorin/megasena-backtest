package megasena.backtest.network

import megasena.backtest.fs.io.FileFacade
import org.apache.http.client.methods.{HttpGet, HttpPost, HttpRequestBase}
import org.apache.http.impl.client.AutoRetryHttpClient

package object http {
  private val client = new AutoRetryHttpClient()
  private var method: HttpRequestBase = null

  def get(url: String) = {
    method = new HttpGet(url)
    this
  }

  def post(url: String) = {
    method = new HttpPost(url)
    this
  }

  def save(filename: String): Unit = {
    client.execute(method).getEntity.writeTo(new FileFacade(filename).out)
  }

}
