package com.example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import scala.io.StdIn

object EventServer {
  def main(args: Array[String]) {

    implicit val system = ActorSystem("my-system")
    implicit val materializer = ActorMaterializer()

    implicit val executionContext = system.dispatcher

    val route = EventRoutes.route

    val bindingFuture = Http().bindAndHandle(route, "localhost", 7798)

    println(s"Server online at http://localhost:7798/\nPress RETURN to stop...")
    StdIn.readLine() 
    bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())
  }
}
