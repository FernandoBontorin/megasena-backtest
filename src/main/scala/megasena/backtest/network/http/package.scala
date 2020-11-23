package megasena.backtest.network

import megasena.backtest.fs.io.FileFacade
import org.apache.http.client.methods.{HttpGet, HttpPost, HttpRequestBase}
import org.apache.http.impl.client.AutoRetryHttpClient

package object http {
  private var method: HttpRequestBase = _

  def get(url: String) = {
    method = new HttpGet(url)
    this
  }

  def post(url: String) = {
    method = new HttpPost(url)
    this
  }

  def save(filename: String): Unit = {
    new AutoRetryHttpClient().execute(method).getEntity.writeTo(new FileFacade(filename).out)
  }

}
