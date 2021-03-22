package com.yurwar
package cp3

import cp3.solver.RelationByCriteriaSolver

import com.yurwar.common.service.DefaultRelationService

object Main {
  def main(args: Array[String]): Unit = {
    println("==================== 1 TASK SOLVE ====================")
    new RelationByCriteriaSolver(new DefaultRelationService).solve()
  }
}
