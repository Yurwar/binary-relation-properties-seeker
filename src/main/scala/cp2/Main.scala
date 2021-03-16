package com.yurwar
package cp2

import cp2.solver.DominationAndBlockingSolver

object Main {
  def main(args: Array[String]): Unit = {
    println("==================== 1 TASK SOLVE ====================")
    new DominationAndBlockingSolver().solve()
  }
}
