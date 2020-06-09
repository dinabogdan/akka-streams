package com.lightbend.akkassembly

import akka.stream.Materializer
import akka.stream.scaladsl.{Sink, Source}

import scala.concurrent.Future

class Factory(
               val bodyShop: BodyShop,
               val paintShop: PaintShop,
               val engineShop: EngineShop,
               val wheelShop: WheelShop,
               val qualityAssurance: QualityAssurance)
             (implicit materializer: Materializer) {

  def orderCars(quantity: Int): Future[Seq[Car]] =
    bodyShop.cars
      .via(paintShop.paint)
      .via(engineShop.installEngine)
      .via(wheelShop.installWheels)
      .via(qualityAssurance.inspect)
      .take(quantity)
      .runWith(Sink.seq)
}
