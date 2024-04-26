import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.sql.SparkSession
import breeze.linalg.{DenseVector => BDV}

object Main {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("DistributedGradientDescent")
      .master("local[*]")
      .getOrCreate()

    spark.sparkContext.setLogLevel("ERROR")

    // Генерация случайной матрицы X и "истинных" весов модели
    val numRows = 100000
    val numCols = 3
    val X = Array.fill(numRows)(Vectors.dense(Array.fill(numCols)(scala.util.Random.nextDouble())))
    val trueCoefficients = BDV(1.5, 0.3, -0.7)

    // Создание зависимой переменной y
    val y = X.map { x =>
      val xVec = BDV(x.toArray)
      val noise = scala.util.Random.nextDouble() * 0.1 // добавляем небольшой шум
      trueCoefficients.dot(xVec) + noise
    }

    import spark.implicits._
    val data = spark.createDataFrame(X.zip(y).map { case (features, label) => (label, features) }).toDF("label", "features")

    // Инициализация модели линейной регрессии
    val lr = new LinearRegression()
      .setMaxIter(100)
      .setRegParam(0.0)
      .setElasticNetParam(0.0)

    // Обучение модели с использованием распределенного градиентного спуска
    val model = lr.fit(data)

    // Оценка коэффициентов модели
    val coefficients = model.coefficients.toArray

    // Вывод "истинных" и оцененных весов модели
    println("---------------------------------------")
    println("True Coefficients: " + trueCoefficients)
    println("Estimated Coefficients: " + coefficients.mkString(", "))
    println("---------------------------------------")
    
    spark.stop()
  }
}
