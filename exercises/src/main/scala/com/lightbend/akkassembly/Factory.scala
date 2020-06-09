package com.lightbend.akkassembly

import akka.stream.Materializer
import akka.stream.scaladsl.{Sink, Source}

import scala.concurrent.Future

class Factory(
               val bodyShop: BodyShop,
               val paintShop: PaintShop,
               val engineShop: EngineShop,
               val wheelShop: WheelShop,
               val qualityAssurance: QualityAssurance,
               val upgradeShop: UpgradeShop)
             (implicit materializer: Materializer) {

  def orderCars(quantity: Int): Future[Seq[Car]] =
    bodyShop.cars
      .via(paintShop.paint)
      .via(engineShop.installEngine)
      .async
      .via(wheelShop.installWheels)
      .async
      .via(upgradeShop.installUpgrades)
      .via(qualityAssurance.inspect)
      .take(quantity)
      .runWith(Sink.seq)
}
