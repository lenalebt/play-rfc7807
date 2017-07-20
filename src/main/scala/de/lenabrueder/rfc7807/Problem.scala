package de.lenabrueder.rfc7807

import de.lenabrueder.rfc7807.Problem.UrlConfiguration
import play.api.http.Status
import play.api.libs.json.JsonNaming.SnakeCase
import play.api.libs.json.{Json, JsonConfiguration}

case class Problem(`type`: String,
                   title: String,
                   status: Option[Int] = None,
                   detail: Option[String] = None,
                   instance: Option[String] = None)

object Problem {
  private implicit val config = JsonConfiguration(SnakeCase)
  implicit val format         = Json.format[Problem]

  sealed trait UrlConfiguration {
    def url(code: String): String
  }
  case object DefaultUrlConfiguration extends UrlConfiguration {
    override def url(code: String): String = s"https://httpstatuses.com/$code"
  }
  case class CustomUrlConfiguration(customUrl: String) extends UrlConfiguration {
    def url(code: String) = customUrl.format(code)
  }
}

trait LowPriorityProblemImplicits {
  implicit val defaultUrlConfiguration = Problem.DefaultUrlConfiguration
}

/**use this to extend your exceptions to be able to directly convert them to a problem instance*/
trait Problematic {
  def asProblem: Problem
}
trait EasyProblematic extends Problematic {
  def errorCode: String
  def message: Option[String] = None
  def urlConfiguration: UrlConfiguration

  override def asProblem: Problem =
    Problem(`type` = urlConfiguration.url(errorCode), title = message getOrElse "unknown")
}
object Problematic {
  def fromException(ex: Throwable)(implicit urlConfiguration: UrlConfiguration) = new Problematic {
    override def asProblem: Problem = {
      ex match {
        case p: Problematic => p.asProblem
        case _              => Problem(urlConfiguration.url(Status.INTERNAL_SERVER_ERROR.toString), ex.getMessage)
      }
    }
  }
}
