package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.{ActorAttributes, Supervision}
import akka.stream.Supervision.Decider
import akka.stream.scaladsl.Flow
import com.lightbend.akkassembly.QualityAssurance.CarFailedInspection

object QualityAssurance {

  case class CarFailedInspection(car: UnfinishedCar) extends IllegalStateException("Car failed inspection")

}



class QualityAssurance {

  private val decider: Supervision.Decider = {
    case _: CarFailedInspection => Supervision.Resume
  }

  val inspect: Flow[UnfinishedCar, Car, NotUsed] = Flow[UnfinishedCar]
    .map(el => assembleCar(el))
    .withAttributes(
      ActorAttributes.supervisionStrategy(decider)
    )

  private def assembleCar(unfinishedCar: UnfinishedCar): Car = {
    unfinishedCar.isFinished match {
      case true => Car(serialNumber = SerialNumber(), wheels = unfinishedCar.wheels, color = unfinishedCar.color.get, engine = unfinishedCar.engine.get, upgrade = unfinishedCar.upgrade)
      case false => throw CarFailedInspection(unfinishedCar)
    }

  }
}
