package api.routes

import api.model.EmployeeRequest
import cats.effect.IO
import cats.effect.kernel.Sync
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl
import org.http4s.dsl.io.*
import cats.FlatMap
import org.http4s.circe._
import io.circe.generic.auto._
import io.circe.syntax._
import repository.EmployeeRepository

class EmployeeRoutes[F[_] : Sync](repo: EmployeeRepository[F]) extends Http4sDsl[F] {

  val EMPLOYEES = "employees"
  val routes = HttpRoutes.of[F] {
    case GET -> Root / EMPLOYEES / employeeId =>
      repo.getEmployee(employeeId)
        .flatMap {
          case Some(employee) => Response(status = Status.Ok).withBody(employee.asJson)
          case None => F.pure(Response(status = Status.NotFound))
        }

    case req@POST -> Root / EMPLOYEES =>
      req.decodeJson[EmployeeRequest]
        .flatMap(hutRepo.addHut)
        .flatMap(hut => Response(status = Status.Created).withBody(hut.asJson))
  }

}