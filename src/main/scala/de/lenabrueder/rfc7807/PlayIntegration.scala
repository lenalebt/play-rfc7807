package de.lenabrueder.rfc7807

import play.api.http.Writeable
import play.api.libs.json.{JsValue, Json}
import play.api.mvc.Result

object PlayIntegration {

  /** Converts `Result` objects to Problems.
    * Will overwrite the body of the result. Allows to write e.g. {{{Results.NotFound.asProblem}}} and get a nice
    * `application/problem+json` response.*/
  implicit class ResultAsProblem[T <: Result](val result: T) {
    def withProblem(problem: Problem)(implicit writeable: Writeable[JsValue],
                                      urlConfiguration: UrlConfiguration): Result =
      Result(
        result.header,
        writeable.toEntity(Json.toJson(problem.copy(status = problem.status orElse Some(result.header.status))))
      ).as(urlConfiguration.problemJsonType)

    def withProblem(ex: Throwable)(implicit urlConfiguration: UrlConfiguration): Result =
      withProblem(Problematic.fromException(ex))
    def withProblem(problematic: Problematic)(implicit urlConfiguration: UrlConfiguration): Result =
      withProblem(problematic.asProblem)
    def withProblem(problem: String)(implicit urlConfiguration: UrlConfiguration): Result =
      withProblem(Problem(urlConfiguration.url(result.header.status.toString), problem))
    def asProblem(implicit urlConfiguration: UrlConfiguration): Result =
      withProblem(result.header.reasonPhrase getOrElse "unknown")
  }
}
