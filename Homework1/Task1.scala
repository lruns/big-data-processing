/** <p>а. (знакомство со стандартной библиотекой) Написать программу-телефонный
  * справочник. При запуске программа должна читать данные из файла, если файла
  * нет — начинать с пустой базы номеров. Использовать String Interpolator.
  * Формат представления данных в файле придумать самостоятельно. Она должна
  * уметь хранить имена и номера телефонов, в интерактивном режиме осуществлять
  * следующие операции:</p>
  *
  * <ul> 
  *     <li>0 - выйти</li> 
  *     <li>1 - добавить запись (имя и телефон)/li> 
  *     <li>2 - найти телефон по имени/li>
  *     <li>3 - найти имя по телефону/li>
  *     <li>4 - сохранить текущие данные в файл/li> 
  *     <li>5 - показать содержимое всей телефонной книги/li> 
  * </ul> 
  * 
  * (2 балла)
  */

import scala.io.StdIn
import scala.util.{Try, Success, Failure}
import java.io._

object PhoneBook {
  val fileName = "phonebook.txt"
  var phoneBook: Map[String, String] = null

  def main(args: Array[String]): Unit = {
    phoneBook = loadPhoneBook()
    var running = true

    println(
      "Добро пожаложовать в программу телефонной книги! Вот список доступных команд:"
    )
    println("0 - выйти")
    println("1 - добавить запись (имя и телефон)")
    println("2 - найти телефон по имени")
    println("3 - найти имя по телефону")
    println("4 - сохранить текущие данные в файл")
    println("5 - показать содержимое всей телефонной книги")

    while (running) {
      print("\nВведите номер команды: ")

      val choice = Try(StdIn.readInt())
      choice match {
        case Success(0) => running = false
        case Success(1) => addRecord()
        case Success(2) => findPhoneNumber()
        case Success(3) => findName()
        case Success(4) => savePhoneBook()
        case Success(5) => showPhoneBook()
        case _ => println("Некорректный ввод команды, попробуйте снова.")
      }
    }
  }

  def loadPhoneBook() = {
    Try {
      val source = scala.io.Source.fromFile(fileName)
      val lines =
        try source.getLines().toList
        finally source.close()
      lines.map { line =>
        val Array(name, number) = line.split(",")
        name -> number
      }.toMap
    } match {
      case Success(phoneBook) => phoneBook
      case Failure(_)         => Map.empty[String, String]
    }
  }

  def addRecord() = {
    print("Введите имя: ")
    val name = StdIn.readLine()
    print("Введите номер телефона: ")
    val number = StdIn.readLine()
    println(s"Запись внесена - $name: $number")
    phoneBook += (name -> number)
  }

  def findPhoneNumber() = {
    print("Введите имя для поиска: ")
    val name = StdIn.readLine()
    phoneBook.get(name) match {
      case Some(number) => println(s"Номер телефона для $name: $number")
      case None         => println(s"Имя $name не найдено в телефонной книге.")
    }
  }

  def findName() = {
    print("Введите номер телефона для поиска: ")
    val number = StdIn.readLine()
    phoneBook.find(_._2 == number) match {
      case Some((name, _)) => println(s"Имя для номера $number: $name")
      case None => println(s"Номер $number не найден в телефонной книге.")
    }
  }

  def savePhoneBook() = {
    val writer = new PrintWriter(new File(fileName))
    try {
      phoneBook.foreach { case (name, number) =>
        writer.println(s"$name,$number")
      }
      println("Данные успешно сохранены.")
    } finally {
      writer.close()
    }
  }

  def showPhoneBook() = {
    if (phoneBook.isEmpty) {
      println("Телефонная книга пуста.")
    } else {
      println("Содержимое телефонной книги:")
      phoneBook.foreach { case (name, number) =>
        println(s"$name: $number")
      }
    }
  }
}
