package repository





import domain.Employee
import java.util.UUID
import cats.effect._
import scala.collection.mutable.ListBuffer
import cats.FlatMap
import cats.implicits._
import cats.effect._
import cats.effect.IO

final case class EmployeeRepository [F[_]: Sync](private val employees: ListBuffer[Employee])
                                                (implicit e: Effect[F])
  {
  val makeId: F[String] = e.delay(UUID.randomUUID().toString)
  def getEmployee(id: String): F[Option[Employee]] =
    e.delay { employees.find(_.id == id) }

  def addEmployee(employe: Employee): F[String] =
    for {
      uuid <- makeId
      _ <- e.delay { employees += Employee(uuid, employe.name, employe.lastName) }
    } yield uuid
}
