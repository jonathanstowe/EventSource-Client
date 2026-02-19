package com.example


import org.apache.pekko
import org.apache.pekko.NotUsed
import org.apache.pekko.http.scaladsl.model.sse.ServerSentEvent
import org.apache.pekko.http.scaladsl.server.Directives._
import org.apache.pekko.http.scaladsl.server.Route
import org.apache.pekko.stream.scaladsl.Source

import java.time.LocalTime
import java.time.format.DateTimeFormatter.ISO_LOCAL_TIME
import scala.concurrent.duration._

object  EventRoutes {
  def route: Route = {
    import pekko.http.scaladsl.marshalling.sse.EventStreamMarshalling._

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
