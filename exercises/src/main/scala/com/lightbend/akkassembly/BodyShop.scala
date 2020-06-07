package com.lightbend.akkassembly

import java.util.concurrent.TimeUnit

import akka.actor.Cancellable
import akka.stream.scaladsl.Source

import scala.concurrent.duration.FiniteDuration

class BodyShop(buildTime: FiniteDuration) {

  val cars: Source[UnfinishedCar, Cancellable] = Source.tick(
    FiniteDuration(50, TimeUnit.MILLISECONDS),
    buildTime,
    UnfinishedCar()
  )

}
