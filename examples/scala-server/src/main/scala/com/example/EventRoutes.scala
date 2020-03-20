package com.example

import akka.NotUsed
import akka.stream.scaladsl.Source

import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.http.scaladsl.model.sse.ServerSentEvent
import scala.concurrent.duration._

import java.time.LocalTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_TIME

object  EventRoutes {
  def route: Route = {
    import akka.http.scaladsl.marshalling.sse.EventStreamMarshalling._

    path("") {
      get {
        complete {
          Source
            .tick(2.seconds, 2.seconds, NotUsed)
            .map(_ => LocalTime.now())
            .map(time => ServerSentEvent(ISO_LOCAL_TIME.format(time)))
            .keepAlive(1.second, () => ServerSentEvent.heartbeat)
        }
      }
    }
  }
}
