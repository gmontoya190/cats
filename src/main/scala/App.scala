import api.routes.EmployeeRoutes
import cats.effect.kernel.Async
import cats.effect.{ExitCode, IO, IOApp}
import org.http4s.blaze.server.BlazeServerBuilder
import scala.collection.mutable.ListBuffer
import org.http4s.HttpApp
import cats.effect.*
import org.http4s.server.Server
import org.http4s.server.Router
import repository.EmployeeRepository
import domain.Employee
object App extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    Flow.run[IO].use(_ => IO.never).as(ExitCode.Success)
  }
  object Flow {
    def run[F[_]: Sync]: Resource[F, Server] = {
      val employeeRepo: EmployeeRepository = new EmployeeRepository[F](new ListBuffer[Employee]())
      BlazeServerBuilder[F]
        .bindHttp(8080, "0.0.0.0")
        .withHttpApp(new EmployeeRoutes[F](employeeRepo).routes.orNotFound)
        .resource
      }
    }
  }
