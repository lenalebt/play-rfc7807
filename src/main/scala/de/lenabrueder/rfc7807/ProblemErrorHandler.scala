package de.lenabrueder.rfc7807

import javax.inject.Singleton

import play.api.http.HttpErrorHandler
import play.api.mvc.Results._
import play.api.mvc._

import scala.concurrent._

/**configure using play.http.errorHandler = "de.lenabrueder.rfc7807.ProblemErrorHandler" in your config file*/
@Singleton
class ProblemErrorHandler extends HttpErrorHandler {
  import PlayIntegration._

  def onClientError(request: RequestHeader, statusCode: Int, message: String) = Future.successful(
    Status(statusCode).withProblem(message)
  )

  def onServerError(request: RequestHeader, exception: Throwable) = Future.successful(
    InternalServerError.withProblem(exception)
  )
}
