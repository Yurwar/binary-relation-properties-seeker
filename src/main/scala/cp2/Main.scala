package com.yurwar
package cp2

import common.service.{DefaultRelationService, RelationService}
import cp2.solver.{DominationAndBlockingSolver, NmKSolver}

object Main {
  def main(args: Array[String]): Unit = {
    val relationService: RelationService = new DefaultRelationService()
    println("==================== 1 TASK SOLVE ====================")
    new DominationAndBlockingSolver(relationService).solve()
    println("==================== 2 TASK SOLVE ====================")
    new NmKSolver(relationService).solve()
  }
}
