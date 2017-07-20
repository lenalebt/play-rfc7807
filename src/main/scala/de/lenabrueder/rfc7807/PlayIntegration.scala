package de.lenabrueder.rfc7807

import de.lenabrueder.rfc7807.Problem.UrlConfiguration
import play.api.http.Writeable
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.Result

object PlayIntegration extends LowPriorityProblemImplicits {
  implicit class ResultAsProblem[T <: Result](val result: T) {
    def withProblem(problem: Problem)(implicit writeable: Writeable[JsValue]): Result =
      Result(
        result.header,
        writeable.toEntity(Json.toJson(problem.copy(status = problem.status orElse Some(result.header.status))))
      ).as("application/problem+json")

    def withProblem(ex: Throwable): Result =
      withProblem(Problematic.fromException(ex))
    def withProblem(problematic: Problematic): Result =
      withProblem(problematic.asProblem)
    def withProblem(problem: String)(implicit urlConfiguration: UrlConfiguration): Result =
      withProblem(Problem(urlConfiguration.url(result.header.status.toString), problem))
    def asProblem(implicit urlConfiguration: UrlConfiguration): Result =
      withProblem(result.header.reasonPhrase getOrElse "unknown")
  }
}
