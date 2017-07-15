package de.lenabrueder.rfc7807

import de.lenabrueder.rfc7807.Problem.UrlConfiguration
import play.api.libs.json.Json
import play.api.mvc.{Result, Results}

object PlayIntegration {
  implicit class ResultAsProblem[T <: Results.Status](val result: T) {
    def withProblem(problem: Problem): Result =
      result(Json.toJson(problem.copy(status = problem.status orElse Some(result.header.status))))
        .as("application/problem+json")

    def withProblem(ex: Exception): Result = withProblem(Problematic.fromException(ex))
    def withProblem(problematic: Problematic): Result =
      withProblem(problematic.asProblem)
    def withProblem(problem: String)(implicit urlConfiguration: UrlConfiguration): Result =
      withProblem(Problem(urlConfiguration.url(result.header.status.toString), problem))
    def asProblem(implicit urlConfiguration: UrlConfiguration): Result =
      withProblem(result.header.reasonPhrase getOrElse "unknown")
  }
}
