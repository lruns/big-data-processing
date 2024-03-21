/** (знакомство с фишками Scala) Придумать иерархию классов с Traits,
  * реализовать её. Методы классов должны использовать всё перечисленное:
  * Closure, Patrially applied function, Nested function, Anonymous function,
  * Function with Variable Arguments. (3 балла)
  */

import scala.collection.mutable.ListBuffer

trait Shape {
  def area: Double
  def perimeter: Double
}

class Circle(val radius: Double) extends Shape {

  def area: Double = math.Pi * radius * radius
  def perimeter: Double = 2 * math.Pi * radius
}

class Rectangle(val width: Double, val height: Double) extends Shape {
  def area: Double = width * height
  def perimeter: Double = 2 * (width + height)
}

class Triangle(val side1: Double, val side2: Double, val side3: Double)
    extends Shape {
  def area: Double = {
    val p = (side1 + side2 + side3) / 2
    // Example of a nested function
    def sqrt(s2: Double): Double = math.sqrt(s2)
    sqrt(p * (p - side1) * (p - side2) * (p - side3))
  }
  def perimeter: Double = side1 + side2 + side3
}

trait ShapeGroup {
  val shapes: ListBuffer[Shape] = new ListBuffer()

  def printShortShapesInfo(): Unit = {
    println(
      "Информация обо всех геометрических фигурах в группе:"
    )
    // Example of Anonymous function
    shapes.foreach { shape => printShapeInfo(shape) }
  }

  val printShapeInfo: Shape => Unit = shape =>
    println(
      s"Тип: ${shape.getClass.getSimpleName}"
    )


  def printAllShapesInfoWithHead(head1: String, head2: String): Unit = {
    // Second example of a nested function
    def printInfo(shape: Shape): Unit = {
      println(
        s"Тип: ${shape.getClass.getSimpleName}, Площадь: ${shape.area}, Периметр: ${shape.perimeter}"
      )
    }
    println(head1)
    println(head2)
    shapes.foreach(printInfo)
  }

  val totalArea: () => Double = () => shapes.map(_.area).sum

  // Example of Function with Variable Arguments
  def addShapes(shapesToAdd: Shape*): Unit = {
    // Second example of Anonymous function
    shapesToAdd.foreach(addShape)
  }

  // Example of Closure function (The only difference between a function and closure is that a closure uses one or more free variables)
  val addShape: Shape => Unit = shape => {
    println(s"Добавляем ${shape.getClass.getSimpleName} в группу.")
    shapes += shape
  }
}

object Main {
  def main(args: Array[String]): Unit = {
    val circle = new Circle(5)
    val rectangle = new Rectangle(4, 6)
    val triangle = new Triangle(3, 4, 5)

    val shapeGroup = new ShapeGroup{}
    shapeGroup.addShapes(circle, rectangle, triangle)

    shapeGroup.printShortShapesInfo()
    println(s"Площадь всех текущих фигур: ${shapeGroup.totalArea()} \n")


    shapeGroup.addShapes(
      new Circle(3),
      new Rectangle(2, 4),
      new Triangle(5, 12, 13)
    )
    println("\n")
    shapeGroup.printShapeInfo(circle)
    println("\n")

    // Example of use of Patrially applied function
    val printAllShapesInfo = shapeGroup.printAllShapesInfoWithHead("Информация обо всех геометрических фигурах в группе.", _: String)
    printAllShapesInfo("Давайте посмотрим:")
    println(s"Площадь всех текущих фигур: ${shapeGroup.totalArea()} \n")
  }
}
