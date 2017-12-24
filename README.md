# application/problem+json (RFC7807) plugin for Play framework

This plugin adds easy support for [RFC7807](https://tools.ietf.org/html/rfc7807) to your application.

## Usage

add the dependency

```scala
libraryDependencies += "de.lenabrueder" %% "play-rfc7807" % "2.6.1"
```

to your `build.sbt`. Versioning follows play version in major and minor versions, patch version is up to the library.
If you want to use the error handler as well, add

```
play.http.errorHandler = "de.lenabrueder.rfc7807.ProblemErrorHandler"
```

to your `application.conf`.

After that, you can use it through adding `import de.lenabrueder.rfc7807.PlayIntegration._` to your
controller. This adds some implicit functions to your Results, which allow you to do the following:

```scala
InternalServerError.withProblem("cannot store flight")
NotFound.asProblem
Forbidden.withProblem(new RuntimeException("something went wrong"))
Unauthorized.withProblem(Problem("https://httpstatuses.com/401", "no authorization"))
```

Result will be something like

```json
{
  "type": "https://httpstatuses.com/404",
  "title": "Cannot find requested object",
  "status": 404
}
```

You can configure URL generation by changing the import `import de.lenabrueder.rfc7807.PlayIntegration._` to
`import de.lenabrueder.rfc7807.PlayIntegration.ResultAsProblem` and providing an instance
of `UrlConfiguration` implicitly when calling the `withProblem` or `asProblem` functions.