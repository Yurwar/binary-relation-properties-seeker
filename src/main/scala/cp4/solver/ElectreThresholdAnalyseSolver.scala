package com.yurwar
package cp4.solver

import common.entity.ElectreCriteria
import common.service.{DefaultRelationService, RelationService}
import common.strategy.electre.ElectreRelationBuildingStrategy
import common.strategy.electre.impl.DefaultElectreRelationBuildingStrategy
import common.strategy.optimization.OptimizationStrategy
import common.strategy.optimization.impl.nm.NmOptimizationStrategy
import common.util.{ResultFileWriter, TaskFileReader}

import org.jfree.chart.annotations.XYTextAnnotation
import org.jfree.chart.{ChartFactory, ChartUtils}
import org.jfree.data.xy.{XYSeries, XYSeriesCollection}

import java.io.File

class ElectreThresholdAnalyseSolver extends Solver {
  val electreRelationBuildingStrategy: ElectreRelationBuildingStrategy = new DefaultElectreRelationBuildingStrategy
  val nmOptimizationStrategy: OptimizationStrategy = new NmOptimizationStrategy
  val relationService: RelationService = new DefaultRelationService
  val taskFileReader = new TaskFileReader
  val resultFileWriter = new ResultFileWriter("src/main/resources/cp4/Var-19-МатьораЮрій.txt")


  override def solve(): Unit = {
    val criteria = taskFileReader.parseElectreCriteria("src/main/resources/cp4/criteria_electre_task1.txt")

    val dSeries = new XYSeries("D Series")
    val cSeries = new XYSeries("C Series")
    val cdSeries = new XYSeries("C-D Series")

    val c = 0.5
    val d = 0.49

    val d1 = createDDataRange(criteria, 0.05, 0.5, 0.05, c)
    val c1 = createCDataRange(criteria, 0.5, 1, 0.05, d)
    val cd = createCDDataRange(criteria)

    val dAnno = createAnnotations(d1)
    val cAnno = createAnnotations(c1)
    val cdAnno = createAnnotations(cd)


    val dDataset = new XYSeriesCollection()
    val cDataset = new XYSeriesCollection()
    val cdDataset = new XYSeriesCollection()
    dDataset.addSeries(dSeries)
    cDataset.addSeries(cSeries)
    cdDataset.addSeries(cdSeries)
    d1.foreach(tup => dSeries.add(tup._1, tup._2))
    c1.foreach(tup => cSeries.add(tup._1, tup._2))
    cd.foreach(tup => cdSeries.add(tup._1, tup._2))

    val dChart = ChartFactory
      .createXYLineChart("D Threshold to relation core", "Concordance", "Core count", dDataset)
    val cChart = ChartFactory
      .createXYLineChart("C Threshold to relation core", "Discordance", "Core count", cDataset)
    val cdChart = ChartFactory
      .createXYLineChart("C-D Threshold to relation core", "Concordance/discordance delta", "Core count", cdDataset)

    val dPlot = dChart.getXYPlot
    val cPlot = cChart.getXYPlot
    val cdPlot = cdChart.getXYPlot

    dAnno.foreach(dPlot.addAnnotation(_))
    cAnno.foreach(cPlot.addAnnotation(_))
    cdAnno.foreach(cdPlot.addAnnotation(_))


    ChartUtils.saveChartAsJPEG(new File("src/main/resources/cp4/d_chart.jpeg"), dChart, 600, 600)
    ChartUtils.saveChartAsJPEG(new File("src/main/resources/cp4/c_chart.jpeg"), cChart, 600, 600)
    ChartUtils.saveChartAsJPEG(new File("src/main/resources/cp4/c-d_chart.jpeg"), cdChart, 600, 600)
  }

  private def createDDataRange(criteria: ElectreCriteria,
                               from: Double, until: Double, step: Double,
                               c: Double) = {
    val dRange = BigDecimal(from) until BigDecimal(until) by BigDecimal(step)
    val d1 = dRange.map(_.toDouble)
      .map(dStep => (dStep, findRelationCore(changeThresholds(criteria, c, dStep))))
      .map(tup => (tup._1, tup._2.size, tup._2))
    d1
  }

  private def createCDataRange(criteria: ElectreCriteria,
                               from: Double, to: Double, step: Double,
                               d: Double) = {
    val cRange = BigDecimal(from) to BigDecimal(to) by BigDecimal(step)
    cRange.map(_.toDouble)
      .map(cStep => (cStep, findRelationCore(changeThresholds(criteria, cStep, d))))
      .map(tup => (tup._1, tup._2.size, tup._2))
  }

  private def createCDDataRange(criteria: ElectreCriteria) = {
    val cRange = BigDecimal(0.5) to BigDecimal(1) by BigDecimal(0.05)
    val dRange = BigDecimal(0.05) until BigDecimal(0.5) by BigDecimal(0.05)

    cRange.reverse
      .zip(dRange)
      .map(tup => (tup._1.toDouble, tup._2.toDouble))
      .map(tup => (tup._1 - tup._2, findRelationCore(changeThresholds(criteria, tup._1, tup._2))))
      .map(tup => (tup._1, tup._2.size, tup._2))
  }

  private def createAnnotations(dataRange: IndexedSeq[(Double, Int, List[Int])]) = {
    dataRange.groupBy(tup => tup._3)
      .map(tup => tup._2.head)
      .map(tup => {
        new XYTextAnnotation(tup._3.mkString(","), tup._1, tup._2)
      })
  }

  def changeThresholds(criteria: ElectreCriteria, c: Double, d: Double): ElectreCriteria = {
    ElectreCriteria(criteria.alternativesRating, criteria.weights, c, d)
  }

  def findRelationCore(criteria: ElectreCriteria): List[Int] = {
    val relation = relationService
      .fulfillRelationFields(electreRelationBuildingStrategy.buildByCriteria(criteria))

    if (nmOptimizationStrategy.isApplicable(relation)) {
      nmOptimizationStrategy.getOptimalElements(relation).sorted
    } else {
      List.empty
    }
  }
}
