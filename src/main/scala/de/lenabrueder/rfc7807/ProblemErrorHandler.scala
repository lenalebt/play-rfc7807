package de.lenabrueder.rfc7807

import play.api.http.HttpErrorHandler
import play.api.mvc._
import play.api.mvc.Results._
import scala.concurrent._
import javax.inject.Singleton;

/**configure using play.http.errorHandler = "de.lenabrueder.rfc7807.ProblemErrorHandler" in your config file*/
@Singleton
class ProblemErrorHandler extends HttpErrorHandler {
  import PlayIntegration.ResultAsProblem

  def onClientError(request: RequestHeader, statusCode: Int, message: String) = Future.successful(
    Status(statusCode).withProblem(message)
  )

  def onServerError(request: RequestHeader, exception: Throwable) = Future.successful(
    InternalServerError.withProblem(exception)
  )
}
