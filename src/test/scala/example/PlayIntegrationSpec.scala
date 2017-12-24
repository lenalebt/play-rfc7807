package example

import de.lenabrueder.rfc7807.UrlConfiguration
import de.lenabrueder.rfc7807.UrlConfiguration.CustomUrlConfiguration
import play.api.mvc.{Result, Results}
import spec.UnitSpec
import play.api.test._
import play.api.test.Helpers._

import scala.concurrent.Future

class PlayIntegrationSpec extends UnitSpec {
  "PlayIntegration" should "by default come with a url configuration" in {
    import de.lenabrueder.rfc7807.PlayIntegration.ResultAsProblem

    val p = Results.NotFound.asProblem

    assert(p.header.status == Results.NotFound.header.status)
    assert((contentAsJson(Future.successful(p)) \ "type").as[String].startsWith("https://httpstatuses.com"))
    assert(p.body.contentType.contains("application/problem+json"))
  }

  it should "have some kind of custom url configuration" in {
    import de.lenabrueder.rfc7807.PlayIntegration.ResultAsProblem

    implicit val config: UrlConfiguration = CustomUrlConfiguration("https://httpstatusdogs.com/%s")
    val p                                 = Results.NotFound.asProblem

    assert(p.header.status == Results.NotFound.header.status)
    assert((contentAsJson(Future.successful(p)) \ "type").as[String].startsWith("https://httpstatusdogs.com"))
    assert(p.body.contentType.contains("application/problem+json"))
  }

  it should "allow to fully customize the url configuration" in {
    import de.lenabrueder.rfc7807.PlayIntegration.ResultAsProblem

    implicit val config: UrlConfiguration = new UrlConfiguration {
      override def url(code: String): String = s"""https://http.cat/$code"""

      override val problemJsonType: String = "application/problemcat+json"
    }
    val p = Results.NotFound.asProblem

    assert(p.header.status == Results.NotFound.header.status)
    assert((contentAsJson(Future.successful(p)) \ "type").as[String].startsWith("https://http.cat"))
    assert(p.body.contentType.contains("application/problemcat+json"))
  }
}
