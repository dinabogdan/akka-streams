package com.lightbend.akkassembly

import akka.NotUsed
import akka.stream.{FlowShape, UniformFanInShape}
import akka.stream.scaladsl.{Balance, Flow, GraphDSL, Merge}

class UpgradeShop {

  val installUpgrades: Flow[UnfinishedCar, UnfinishedCar, NotUsed] = Flow.fromGraph(
    GraphDSL.create() {
      implicit builder: GraphDSL.Builder[NotUsed] =>

        import GraphDSL.Implicits._

        val balance = builder.add(Balance[UnfinishedCar](3))
        val merge = builder.add(Merge[UnfinishedCar](3))

        balance.out(0).map(car => car.installUpgrade(Upgrade.DX)) ~> merge.in(0)
        balance.out(1).map(car => car.installUpgrade(Upgrade.Sport)) ~> merge.in(1)
        balance.out(2).map(car => car) ~> merge.in(2)

        FlowShape(balance.in, merge.out)

    }
  )
}
