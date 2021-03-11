package com.yurwar

import solver.{PreferenceRestoreSolver, ReductionRestoreSolver, RelationClassSeekSolver}

object Main {
  def main(args: Array[String]): Unit = {
    println("==================== 1 TASK SOLVE ====================")
    new RelationClassSeekSolver().solve()
    println("==================== 2.1 TASK SOLVE ====================")
    new ReductionRestoreSolver().solve()
    println("==================== 2.1 TASK SOLVE ====================")
    new PreferenceRestoreSolver().solve()
  }
}
