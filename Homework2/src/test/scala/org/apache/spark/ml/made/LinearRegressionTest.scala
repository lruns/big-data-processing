package org.apache.spark.ml.made

import breeze.linalg.DenseVector
import breeze.numerics.abs
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.DataFrame
import org.scalatest.flatspec._
import org.scalatest.matchers._


class LinearRegressionTest extends AnyFlatSpec with should.Matchers with WithSpark {
  lazy val data: DataFrame = LinearRegressionTest._data
  lazy val trueCoefficients: DenseVector[Double] = LinearRegressionTest._trueCoefficients

  "Model" should "expanded by distributed gradient descent" in {
    val lr = new LinearRegression(1, 1000)
      .setInputCol("features")
      .setOutputCol("label")

    val model = lr.fit(data)

    val coefficients = model.weights.toArray

    assert(coefficients.length == trueCoefficients.length)

    for (i <- coefficients.indices) {
      assert(abs(coefficients(i) - trueCoefficients(i)) <= 0.1)
    }
  }
}

object LinearRegressionTest extends WithSpark {
  // Генерация случайной матрицы X и "истинных" весов модели
  val numRows = 100000
  val numCols = 3

  lazy val _trueCoefficients = DenseVector(1.5, 0.3, -0.7)

  lazy val X = Array.fill(numRows)(Vectors.dense(Array.fill(numCols)(scala.util.Random.nextDouble())))


  lazy val y = X.map { x =>
    val xVec = DenseVector(x.toArray)
    val noise = scala.util.Random.nextDouble() * 0.1 // добавляем небольшой шум
    _trueCoefficients.dot(xVec) + noise
  }
  lazy val _data: DataFrame = spark.createDataFrame(X.zip(y)
      .map { case (features, label) => (label, features) })
    .toDF("label", "features")
}