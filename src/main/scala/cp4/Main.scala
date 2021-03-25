package com.yurwar
package cp4

import cp4.solver.{ElectreMethodSolver, ElectreThresholdAnalyseSolver}

object Main {
  def main(args: Array[String]): Unit = {
    println("==================== 1 TASK SOLVE ====================")
    new ElectreMethodSolver().solve()
    new ElectreThresholdAnalyseSolver().solve()
  }
}
