package fp101

sealed trait MySList {

  def value: Either[String, Int]

  def next: Either[String, MySList]

  override lazy val toString: String = {
    val v = value.map(_.toString).getOrElse("")
    val n = next.map {
      case MySNil => ""
      case x @ MySCons(_, _) => s" :: $x"
    }.getOrElse("")
    s"$v$n"
  }
}

object MySList {

  def apply(values: Int*): MySList = {
    val zero: MySList = MySNil
    values.toList.reverse.foldLeft(zero) {
      case (acc, value) => MySCons(value, acc)
    }
  }
}

case object MySNil extends MySList {

  override val value: Either[String, Int] = Left("cannot access value of nil")

  override val next: Either[String, MySList] = Left("cannot access next of nil")
}

final case class MySCons(v: Int, n: MySList) extends MySList {

  override def value: Either[String, Int] = Right(v)

  override def next: Either[String, MySList] = Right(n)
}

object Main {

  def main(args: Array[String]): Unit = {

    val l1 = MySList(1, 2, 3)
    println(l1)
    println(lastFn(l1))
  }

  def lastFn(l: MySList): Either[String, Int] =
    l match {
      case MySNil => Left("")
      case MySCons(v, MySNil) => Right(v)
      case MySCons(_, n) => lastFn(n)
    }
}
