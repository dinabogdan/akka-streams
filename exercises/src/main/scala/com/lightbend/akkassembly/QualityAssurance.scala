package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.scaladsl.Flow

class QualityAssurance {

  val inspect: Flow[UnfinishedCar, Car, NotUsed] = Flow[UnfinishedCar]
    .filter(el => el.isFinished)
    .map(el => Car(serialNumber = SerialNumber(), wheels = el.wheels, color = el.color.get, engine = el.engine.get, upgrade = el.upgrade))

}
