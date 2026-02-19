package com.example


import org.apache.pekko.actor.ActorSystem
import org.apache.pekko.http.scaladsl.Http
import org.apache.pekko.http.scaladsl.server.Route

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.io.StdIn
import scala.util.{Failure, Success}

object EventServer {
  def main(args: Array[String]): Unit = {

    implicit val system: ActorSystem = ActorSystem("my-system")
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    val route: Route = EventRoutes.route

    val bindingFuture: Future[Http.ServerBinding] = Http().newServerAt("localhost", 7798).bind(route)

    bindingFuture.onComplete {
        case Success(binding) =>
            val address = binding.localAddress
            system.log.info("Server online at http://{}:{}/", address.getHostString, address.getPort)
        case Failure(ex) =>
            system.log.error("Failed to bind HTTP endpoint, terminating system", ex)
            system.terminate()
    }
    StdIn.readLine() 
    bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())
  }
}
